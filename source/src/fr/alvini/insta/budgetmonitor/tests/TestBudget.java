package fr.alvini.insta.budgetmonitor.tests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.alvini.insta.budgetmonitor.R;

public class TestBudget extends Activity {
	private Button budget_add = null;
	private Button budget_list = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_budget);
		
		budget_add = (Button) findViewById(R.id.test_budget_add);
		budget_add.setOnClickListener(budgetAddListener);
		
		budget_list = (Button) findViewById(R.id.test_budget_list);
		budget_list.setOnClickListener(budgetListListener);
	}
	
	private OnClickListener budgetAddListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent openAddBudget = new Intent(TestBudget.this,TestBudgetAdd.class);
			startActivity(openAddBudget);
		}
		
	};

	private OnClickListener budgetListListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent openListBudget = new Intent(TestBudget.this,TestBudgetList.class);
			startActivity(openListBudget);
		}
		
	};
}
