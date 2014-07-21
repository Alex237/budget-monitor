package fr.alvini.insta.budgetmonitor.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import fr.alvini.insta.budgetmonitor.HomeActivity;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.R.id;
import fr.alvini.insta.budgetmonitor.R.layout;

public class Gerer extends Activity implements OnClickListener{
	
	private TextView travaux;
	private Button retour;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerer);
        
        this.travaux = (TextView)findViewById(R.id.travaux);
        this.retour = (Button)findViewById(R.id.retour);
        
        retour.setOnClickListener(this);
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
		if(id == R.id.retour){
			System.exit(0);
		}
	}

	
}
