package fr.alvini.insta.budgetmonitor.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.BudgetDAO;
import fr.alvini.insta.budgetmonitor.model.Budget;

public class TestBudgetList extends Activity {
	ListView listeBudget = null;
	BudgetDAO budDAO = null;
	List<Budget> listBudgets = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_list_budget);

		listeBudget = (ListView) findViewById(R.id.list_budget);

		budDAO = new BudgetDAO(TestBudgetList.this);
		
		List<HashMap<String, String>> listeBudgets = new ArrayList<HashMap<String, String>>();

		listBudgets = new ArrayList<Budget>();
		try {
			listBudgets = budDAO.selectionnerAll();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Toast.makeText(MainActivity.this, listMetiers.size(),
		// Toast.LENGTH_LONG).show();
		if (listBudgets.size() > 0) {
			int i = 0;
			HashMap<String, String> element;
			for (Budget budgetSingle : listBudgets) {
				element = new HashMap<String, String>();
				element.put("Id_budget", "Id du budget "+String.valueOf(budgetSingle.getId_budget()));
				element.put(
						"datas",
						"Montant : "
								+ String.valueOf(budgetSingle.getAmount()));
				listeBudgets.add(element);
			}
		}

		if (listeBudgets.size() > 0) {
			ListAdapter adapter = new SimpleAdapter(this, listeBudgets,
					android.R.layout.simple_list_item_2, new String[] {
							"Id_budget", "datas	" }, new int[] {
							android.R.id.text1, android.R.id.text2 });
			listeBudget.setAdapter(adapter);
		} else {

		}
	}
}
