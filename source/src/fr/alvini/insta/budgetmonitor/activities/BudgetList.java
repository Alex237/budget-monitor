package fr.alvini.insta.budgetmonitor.activities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.BudgetDAO;
import fr.alvini.insta.budgetmonitor.dao.RecurrenceDAO;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.ObjectModel;

public class BudgetList extends Activity {
	ListView listeBudget = null;
	BudgetDAO budDAO = null;
	RecurrenceDAO recDAO = null;
	List<Budget> listBudgets = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget_list);

		listeBudget = (ListView) findViewById(R.id.budgetList);

		budDAO = new BudgetDAO(BudgetList.this);
		
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
				recDAO = new RecurrenceDAO(BudgetList.this);
				budgetSingle.setRecurrence(recDAO.selectionner(budgetSingle.getRecurrence().getId_recurrence()));
				element = new HashMap<String, String>();
				element.put("IdBudget", "Budget : "+String.valueOf(budgetSingle.getDescription()));
				element.put("Datas",String.valueOf(budgetSingle.getAmount())+"-"+
									String.valueOf(ObjectModel.formatDate(budgetSingle.getDateBegin(),false))+"-"+
									String.valueOf(ObjectModel.formatDate(budgetSingle.getDateEnd(),false))+"-"+
									String.valueOf(budgetSingle.getRecurrence().getDescription())+"-"+
									String.valueOf(budgetSingle.getId_budget()));
				Toast.makeText(BudgetList.this, String.valueOf(budgetSingle.getAmount()), Toast.LENGTH_LONG).show();
				listeBudgets.add(element);
			}
		}

		if (listeBudgets.size() > 0) {
			ListAdapter adapter = new SimpleAdapter(this,
					listeBudgets,
					android.R.layout.simple_list_item_2, 
					new String[] {"IdBudget", "Datas"},
					new int[] {android.R.id.text1, android.R.id.text2 });
			listeBudget.setAdapter(adapter);
		} else {

		}
		
	}
}
