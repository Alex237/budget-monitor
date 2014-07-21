package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import fr.alvini.insta.budgetmonitor.HomeActivity;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.BudgetDAO;
import fr.alvini.insta.budgetmonitor.dao.RecurrenceDAO;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class AjoutBudget extends Activity implements OnClickListener{
	
	private EditText amount;
	private EditText description;
	private String recurrence;
	//liste déroulante concernant la récurrence
	private List<Recurrence> recurrents = null;
	private RecurrenceDAO recDAO = null;
	static final String[] recurrent = new String[]{
			"Aucune" , "Quotidiennement", "Hebdomadairement", "Mensuel(le)", "Annuelle"};
	private Button addBudget;
	private Button annuler;
	private DatePicker date_begin = null;
	private DatePicker date_end = null;
	private Date dateBegin = null;
	private Date dateEnd = null;
	private Spinner recurrences = null;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_budget);
        
        //correspondance entre les objets et les widgets
        this.amount = (EditText)findViewById(R.id.budget_add_amount_edit);
        this.description = (EditText)findViewById(R.id.budget_add_description_edit);
        this.date_begin = (DatePicker) findViewById(R.id.budget_add_date_begin_datePicker);
        this.date_end = (DatePicker) findViewById(R.id.budget_add_date_end_datePicker);
        this.addBudget = (Button)findViewById(R.id.btn_budget_add);
        this.annuler = (Button)findViewById(R.id.annuler);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //rendre le bouton �coutable
        this.addBudget.setOnClickListener(this);
        this.annuler.setOnClickListener(this);
	     
        dateBegin = new Date(date_begin.getYear(),date_begin.getMonth(),date_begin.getDayOfMonth());
        dateEnd = new Date(date_end.getYear(),date_end.getMonth(),date_end.getDayOfMonth());
	     
        recDAO = new RecurrenceDAO(AjoutBudget.this);
        recurrents = recDAO.selectionnerAll();
        List<String> recurrentsString = new ArrayList<String>();
        for(Recurrence rec : recurrents) {
        	recurrentsString.add(rec.getDescription());
        }
	     recurrences = (Spinner) findViewById(R.id.budget_add_recurrencesSpinner);
	     // Create an ArrayAdapter using the string array and a default spinner layout
	     ArrayAdapter<String> adapterRecurrence = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recurrentsString );
	     // Specify the layout to use whintenten the list of choices appears
	     adapterRecurrence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     // Apply the adapter to the spinner
	     recurrences.setAdapter(adapterRecurrence);
	    
	     
	     
	   //on récupère le choix de l'utilisateur (liste déroulante) 
	     recurrences.setOnItemSelectedListener(new OnItemSelectedListener() {
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
		return true;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.btn_budget_add){
			Toast.makeText(AjoutBudget.this, String.valueOf(recurrence.toString()), Toast.LENGTH_LONG).show();

			Budget budgetToAdd = new Budget();
			budgetToAdd.setDescription(description.getText().toString());
			budgetToAdd.setAmount(Double.valueOf(this.amount.getText().toString()));
			budgetToAdd.setDateBegin(dateBegin);
			budgetToAdd.setDateEnd(dateEnd);
			Recurrence recurrenceChosen = new Recurrence();
			recurrenceChosen = recDAO.selectionnerParDescription(recurrence.toString());
			budgetToAdd.setRecurrence(recurrenceChosen);
			BudgetDAO budDAO = new BudgetDAO(AjoutBudget.this);
			budDAO.ajouter(budgetToAdd);
			
			Intent  unIntent = new Intent(this, HomeActivity.class);
			//unIntent.putExtra("choixUtilisateur",this.choixOperation);
//			unIntent.putExtra("amount",this.amount.getText().toString());
//			unIntent.putExtra("description",this.description.getText().toString());
//			unIntent.putExtra("recurrences", this.recurrence);
			this.startActivity(unIntent);
			
		}
		
		if(id == R.id.annuler){
			System.exit(0);
		}
 		
	}
	
}
	
