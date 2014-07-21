package fr.alvini.insta.budgetmonitor.activities;

import android.app.Activity;
import fr.alvini.insta.budgetmonitor.HomeActivity;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.R.id;
import fr.alvini.insta.budgetmonitor.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class AjoutBudget extends Activity implements OnClickListener{
	
	private EditText montant;
	private EditText libelle;
	private String recurrence;
	//liste déroulante concernant la récurrence 
	static final String[] recurrent = new String[]{
			"Aucune" , "Quotidiennement", "Hebdomadairement", "Mensuel(le)", "Annuelle"};
	private Button ajouterBudget;
	private Button annuler;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_budget);
        
        //correspondance entre les objets et les widgets
        this.montant = (EditText)findViewById(R.id.montant);
        this.libelle = (EditText)findViewById(R.id.libelle);
        this.ajouterBudget = (Button)findViewById(R.id.ajouterBudget);
        this.annuler = (Button)findViewById(R.id.annuler);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //rendre le bouton écoutable
        this.ajouterBudget.setOnClickListener(this);
        this.annuler.setOnClickListener(this);
	     
	     
	     final Spinner repeter = (Spinner) findViewById(R.id.repeter);
	     // Create an ArrayAdapter using the string array and a default spinner layout
	     ArrayAdapter<String> adapterRecurrence = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recurrent );
	     // Specify the layout to use whintenten the list of choices appears
	     adapterRecurrence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     // Apply the adapter to the spinner
	     repeter.setAdapter(adapterRecurrence);
	    
	     
	     
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
		return true;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.ajouterBudget){
			Intent  unIntent = new Intent(this, HomeActivity.class);
			//unIntent.putExtra("choixUtilisateur",this.choixOperation);
			unIntent.putExtra("montant",this.montant.getText().toString());
			unIntent.putExtra("libelle",this.libelle.getText().toString());
			unIntent.putExtra("repeter", this.recurrence);
			this.startActivity(unIntent);
		}
		
		if(id == R.id.annuler){
			System.exit(0);
		}
 		
	}
	
}
	
