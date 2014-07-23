package fr.alvini.insta.budgetmonitor.tests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import fr.alvini.insta.budgetmonitor.R;

public class TestChooseActivity extends Activity{
	private ListView listChoose = null;
	private ArrayAdapter<String> liste = null;
	private String[] actions = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_choose_act);
		
		listChoose = (ListView)findViewById(R.id.listChoose);
		
		actions = new String[] {"Budget","Category","Operation","Recurrence","Parameter"};
		
		liste = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,actions);
		listChoose.setOnItemClickListener(listChooseListener);
		listChoose.setAdapter(liste);
		
	}
	
	private OnItemClickListener listChooseListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(TestChooseActivity.this, String.valueOf(id), Toast.LENGTH_LONG).show();
			Intent getActivity = null;
			switch(Integer.valueOf(String.valueOf(id))) {
			case 0:
				getActivity = new Intent(TestChooseActivity.this,TestBudget.class);
				break;
			case 1:
				getActivity = new Intent(TestChooseActivity.this,fr.alvini.insta.budgetmonitor.activities.CategoryList.class);
				break;
			case 2:
				getActivity = new Intent(TestChooseActivity.this,TestOperation.class);
				break;
			case 3:
				getActivity = new Intent(TestChooseActivity.this,TestRecurrence.class);
				break;
			case 4:
				getActivity = new Intent(TestChooseActivity.this,TestParameter.class);
				break;
			}
			startActivity(getActivity);
		}
		
	};
}
