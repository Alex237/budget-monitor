package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fr.alvini.insta.budgetmonitor.HomeActivity;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.model.*;
import fr.alvini.insta.budgetmonitor.dao.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AjoutOperation extends Activity implements OnClickListener{

	private long id_budget;
	private BudgetDAO budDAO = null;
	private String choixOperation;
	//liste déroulante concernant l'opération 
	static final String[] operation = new String[]{
		"Dépense", "Revenu"};

	private long id_category;
	private String choixCategorieUt;
	private List<Category> categories = null;
	private CategoryDAO catDAO = null;
	//liste déroulante concernant l'opération 
	static final String[] categorie = new String[]{
		"Par défaut","Alimentation", "Bar", "Courses", "Divers", "Epargne",  "Impots", "Logement/Charges", "Salaire", "Transport"};

	private EditText montant;
	private EditText libelle;
	private String recurrence;
	private List<Recurrence> recurrents = null;
	private RecurrenceDAO recDAO = null;
	//liste déroulante concernant la récurrence 
	static final String[] recurrent = new String[]{
		"Aucune", "Quotidiennement", "Hebdomadairement", "Mensuel(le)", "Annuelle"};
	private Button ajouterOperation;
	private Button show_popup;
	private GridLayout container;

	//déclaration popup
	private Dialog custom;
	private Button addbtn;
	private Button annuler;
	private EditText ajoutCateg;
	private String newCateg;
	
	private ArrayAdapter<String> adapterCategorie;
	private Spinner choixCat;
	private List<String> categoriesString = null;
	
	private Calendar cal;
	private GregorianCalendar date_added;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_operation);
		//this.container = (GridLayout)findViewById(R.id.gridLayout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intentPassed = getIntent();
		id_budget = intentPassed.getLongExtra("Id_budget", -1);

		//correspondance entre les objets et les widgets
		this.montant = (EditText)findViewById(R.id.montant);
		this.libelle = (EditText)findViewById(R.id.libelle);
		this.ajouterOperation = (Button)findViewById(R.id.ajouterOperation);
		this.show_popup = (Button)findViewById(R.id.show_popup);
		this.annuler = (Button)findViewById(R.id.annulerOperation);


		//rendre le bouton écoutable
		this.ajouterOperation.setOnClickListener(this);
		this.show_popup.setOnClickListener(this);
		this.annuler.setOnClickListener(this);
	
		recDAO = new RecurrenceDAO(AjoutOperation.this);
        recurrents = recDAO.selectionnerAll();
        final List<Long> recurrentsIds = new ArrayList<Long>();
        List<String> recurrentsString = new ArrayList<String>();
        for (Recurrence rec : recurrents) {
			recurrentsString.add(rec.getDescription());
			recurrentsIds.add(rec.getId_recurrence());
		}
        
        catDAO = new CategoryDAO(AjoutOperation.this);
        categories = catDAO.selectionnerAll();
        categoriesString = new ArrayList<String>();
        final List<Long> categoriesIds = new ArrayList<Long>();
        for(Category cat : categories) {
        	categoriesString.add(cat.getDescription());
        	categoriesIds.add(cat.getId_category());
        }
		
		

		final Spinner choix = (Spinner) findViewById(R.id.choixUtilisateur);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operation );
		// Specify the layout to use whintenten the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		choix.setAdapter(adapter);


		this.choixCat = (Spinner) findViewById(R.id.choixCategorie);
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapterCategorie = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesString );
		// Specify the layout to use whintenten the list of choices appears
		adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		this.choixCat.setAdapter(adapterCategorie);


		final Spinner repeter = (Spinner) findViewById(R.id.repeter);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapterRecurrence = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recurrentsString);
		// Specify the layout to use whintenten the list of choices appears
		adapterRecurrence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		repeter.setAdapter(adapterRecurrence);





		//on récupère le choix de l'utilisateur (liste déroulante) 
		choix.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {

				choixOperation =(String)parent.getSelectedItem();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}


		});


		//on récupère le choix de l'utilisateur (liste déroulante) 
		choixCat.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {

				choixCategorieUt =(String)parent.getSelectedItem(); 
				id_category = categoriesIds.get((int)parent.getSelectedItemId());
//				Toast.makeText(AjoutOperation.this, String.valueOf(categoriesIds.get((int)parent.getSelectedItemId())), Toast.LENGTH_LONG).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}


		});


		//on récupère le choix de l'utilisateur (liste déroulante) 
		repeter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {

				recurrence =(String)parent.getSelectedItem(); 

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}


		});




	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		MenuInflater inflater = getMenuInflater();
		// Instanciation du menu XML sp�cifier en un objet Menu
		inflater.inflate(R.menu.activity_menu, menu);
		return true;
	}


	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.ajouterOperation){
			Intent  unIntent = new Intent(this, HomeActivity.class);
			
			Category choixCategorie = catDAO.selectionner(id_category);
//			System.out.println("Debug 5 : "+choixCategorie.getId_category());
//			Toast.makeText(getApplicationContext(), choixCategorie.getDescription(), Toast.LENGTH_LONG).show();
//			CategoryDAO test = new CategoryDAO(this);
//			test.ajouter(choixCategorie);
			budDAO = new BudgetDAO(AjoutOperation.this);
			Budget testBudget = budDAO.selectionner(id_budget);
//			testBudget.setId_budget(id_budget);
			
			Recurrence recurrenceChosen = new Recurrence();
			recurrenceChosen = recDAO.selectionnerParDescription(recurrence.toString());
			
			//m'occuper de l'ajout de la date;
			//Date date = new Date();
			cal = Calendar.getInstance();
			this.date_added =  new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			
			
			//pour éviter les erreurs
			if(this.montant.getText().toString().matches("")){
				this.montant.setText("0.0");
			}
			
			double amount = Double.parseDouble(this.montant.getText().toString());
			String description = this.libelle.getText().toString();
		
			Operation operation = new Operation(testBudget, choixCategorie, amount, description, choixOperation, date_added, recurrenceChosen, 0);
			OperationDAO operationDao = new OperationDAO(AjoutOperation.this);
			operationDao.ajouter(operation);
			
			this.startActivity(unIntent);
		}

		if(id == R.id.annulerOperation){
			System.exit(0);
		}

		if(id == R.id.show_popup){

			//on créée la boîte de dialogue ainsi que les champs
			custom = new Dialog(AjoutOperation.this);
			custom.setContentView(R.layout.popup_ajout_categorie);
			ajoutCateg = (EditText)custom.findViewById(R.id.ajoutCateg);
			addbtn = (Button)custom.findViewById(R.id.addbtn);
			annuler = (Button)custom.findViewById(R.id.canbtn);
			custom.setTitle("Ajouter une catégorie : ");

			//Cette ligne permet de faire apparaître le clavier en appuyant sur l'editText
			final InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			ajoutCateg.setOnTouchListener(new OnTouchListener(){	
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					if (v == ajoutCateg)
					{
						mgr.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
					}

					return false;
				}


			});


			//on rend les boutons écoutables
			addbtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					newCateg = ajoutCateg.getText().toString();
					
					Category categ = new Category(newCateg);
					catDAO.ajouter(categ);
					
					adapterCategorie.clear();
			        categories = catDAO.selectionnerAll();
			       
			        for(Category cat : categories) {
			        	categoriesString.add(cat.getDescription());
			        }
					
			        adapterCategorie.notifyDataSetChanged();
			        
					custom.dismiss();
				}
			});

			annuler.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					custom.dismiss();
				}
			});
			custom.show();

		}

	}

}
