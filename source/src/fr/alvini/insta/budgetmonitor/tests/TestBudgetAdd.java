package fr.alvini.insta.budgetmonitor.tests;

import java.text.ParseException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.BudgetDAO;
import fr.alvini.insta.budgetmonitor.dao.DAOBase;
import fr.alvini.insta.budgetmonitor.model.Budget;

public class TestBudgetAdd extends Activity {
	private EditText amount = null;
	private EditText dateBegin = null;
	private EditText dateEnd = null;
	private Button btnAddBudget = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_add_budget);
		
		amount = (EditText) findViewById(R.id.budget_add_amount_edit);
		dateBegin = (EditText) findViewById(R.id.budget_add_datebegin_edit);
		dateEnd = (EditText) findViewById(R.id.budget_add_dateend_edit);
		
		btnAddBudget = (Button) findViewById(R.id.btn_budget_add);
		btnAddBudget.setOnClickListener(addBudgetListener);
	}
	
	private OnClickListener addBudgetListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Budget budget = new Budget();
			budget.setAmount(Double.valueOf(amount.getText().toString()));
			Date date_begin = null;
			try {
				date_begin = DAOBase.sdf.parse(dateBegin.getText().toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			budget.setDateBegin(date_begin);
			Date date_end = null;
			try {
				date_end = DAOBase.sdf.parse(dateEnd.getText().toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			budget.setDateEnd(date_end);
			BudgetDAO budDAO = new BudgetDAO(TestBudgetAdd.this);
			budDAO.ajouter(budget);
			Intent budget_home = new Intent(TestBudgetAdd.this,TestBudget.class);
			startActivity(budget_home);
		}
		
	};
}
