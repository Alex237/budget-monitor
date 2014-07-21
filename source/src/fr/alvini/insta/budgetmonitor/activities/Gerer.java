package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.model.Category;

public class Gerer extends Activity /*implements OnClickListener*/{
	
	private TextView travaux;
	private Button retour;
	private ListView managementList;
	private List<HashMap<String, String>> listManagement = null;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gerer);
        
        this.managementList = (ListView) findViewById(R.id.managementList);
        this.managementList.setOnItemClickListener(managementListener);
        
        /*
        this.travaux = (TextView)findViewById(R.id.travaux);
        this.retour = (Button)findViewById(R.id.retour);
        */
        
        listManagement = new ArrayList<HashMap<String,String>>();
		int i = 0;
		HashMap<String, String> categMgt = new HashMap<String, String>();
		categMgt.put("Item", "Gérer les catégories");
		listManagement.add(categMgt);
		HashMap<String, String> recDisplay = new HashMap<String, String>();
		recDisplay.put("Item", "Afficher les récurrences");
		listManagement.add(recDisplay);
		HashMap<String, String> budgetMgt = new HashMap<String, String>();
		budgetMgt.put("Item", "Gestion des budgets");
		listManagement.add(budgetMgt);

		if (listManagement.size() > 0) {
			ListAdapter adapter = new SimpleAdapter(this,
					listManagement,
					android.R.layout.simple_list_item_1, 
					new String[] {"Item"},
					new int[] {android.R.id.text1});
			this.managementList.setAdapter(adapter);
		} else {

		}
        
//        retour.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
/*
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.retour){
			System.exit(0);
		}
	}
*/	
	private OnItemClickListener managementListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				Intent getCategories = new Intent(Gerer.this,CategoryList.class);
				startActivity(getCategories);
				break;
			case 1:
				Intent getRecurrences = new Intent(Gerer.this,RecurrenceList.class);
				startActivity(getRecurrences);
				break;
			case 2:
				Intent getBudgets = new Intent(Gerer.this,BudgetList.class);
				startActivity(getBudgets);
				break;
			default:
				Toast.makeText(Gerer.this, "Cette fonctionnalité est à venir", Toast.LENGTH_LONG).show();
				break;
			}
		}
		
	};

	
}
