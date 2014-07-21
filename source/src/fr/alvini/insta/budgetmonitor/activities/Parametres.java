package fr.alvini.insta.budgetmonitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import fr.alvini.insta.budgetmonitor.HomeActivity;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.R.id;
import fr.alvini.insta.budgetmonitor.R.layout;

public class Parametres extends Activity implements OnClickListener{

	private String choixCouleur;
	private String choixPolice;
	private String choixStyle;
	static final String[] couleur = new String[]{
		"Noir", "Bleu", "Vert", "Blanc"};
	static final String[] police = new String[]{
		"Arial", "Calibri"};
	static final String[] style = new String[]{
		"style1", "style2"};
	
	private Button enregistrer;
	private Button annuler;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);
        
        this.enregistrer = (Button)findViewById(R.id.enregistrer);
        this.annuler = (Button)findViewById(R.id.annuler);
        
        enregistrer.setOnClickListener(this);
        annuler.setOnClickListener(this);
        
        final Spinner choixC = (Spinner) findViewById(R.id.choixCouleur);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapterCouleur = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, couleur );
		// Specify the layout to use whintenten the list of choices appears
		adapterCouleur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		choixC.setAdapter(adapterCouleur);
		
		final Spinner choixP = (Spinner) findViewById(R.id.choixPolice);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapterPolice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, police );
		// Specify the layout to use whintenten the list of choices appears
		adapterPolice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		choixP.setAdapter(adapterPolice);
		
		final Spinner choixS = (Spinner) findViewById(R.id.choixStyle);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> adapterStyle = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, style );
		// Specify the layout to use whintenten the list of choices appears
		adapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		choixS.setAdapter(adapterStyle);
        
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
		if(id == R.id.enregistrer){
			Intent  unIntent = new Intent(this, HomeActivity.class);
			//unIntent.putExtra("choixUtilisateur",this.choixOperation);
			unIntent.putExtra("couleur", this.choixCouleur);
			unIntent.putExtra("police", this.choixPolice);
			unIntent.putExtra("style", this.choixStyle);
			this.startActivity(unIntent);
		}
		
		if(id == R.id.annuler){
			System.exit(0);
		}
		
	}
}
