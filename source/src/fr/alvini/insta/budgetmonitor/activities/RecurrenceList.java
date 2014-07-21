package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.RecurrenceDAO;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class RecurrenceList extends Activity {
	ListView listeRecurrence = null;
	RecurrenceDAO recDAO = null;
	List<Recurrence> listRecurrences = null;
	List<HashMap<String, String>> listeRecurrences = null;
	List<Long> listeRecurrenceIds = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recurrence_list);

		listeRecurrence = (ListView) findViewById(R.id.recurrenceList);

		recDAO = new RecurrenceDAO(RecurrenceList.this);
		
		listeRecurrences = new ArrayList<HashMap<String, String>>();
		
		listeRecurrenceIds = new ArrayList<Long>();

		listRecurrences = new ArrayList<Recurrence>();
		listRecurrences = recDAO.selectionnerAll();
		
		// Toast.makeText(MainActivity.this, listMetiers.size(),
		// Toast.LENGTH_LONG).show();
		if (listRecurrences.size() > 0) {
			int i = 0;
			HashMap<String, String> element;
			for (Recurrence recurrenceSingle : listRecurrences) {
				element = new HashMap<String, String>();
				element.put("Description", String.valueOf(recurrenceSingle.getDescription()));
//				element.put("Datas","Montant : "+ String.valueOf(recurrenceSingle.getDescription()));
				listeRecurrences.add(element);
				listeRecurrenceIds.add(recurrenceSingle.getId_recurrence());
			}
		}

		if (listeRecurrences.size() > 0) {
			ListAdapter adapter = new SimpleAdapter(this,
					listeRecurrences,
					android.R.layout.simple_list_item_1, 
//					new String[] {"IdRecurrence", "Datas"},
					new String[] {"Description"},
//					new int[] {android.R.id.text1, android.R.id.text2 });
					new int[] {android.R.id.text1});
			listeRecurrence.setAdapter(adapter);
		} else {

		}
//		listeRecurrence.setOnItemClickListener(categOnItemListener);
	}
	
/*	
	private OnItemClickListener categOnItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent listCateg = new Intent(RecurrenceList.this,RecurrenceDetails.class);
			listCateg.putExtra("ID_object", listeRecurrenceIds.get((int)id));
			startActivity(listCateg);
		}
		
	};
*/	
}
