package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.CategoryDAO;
import fr.alvini.insta.budgetmonitor.model.Category;

public class CategoryList extends Activity {
	ListView listeCategory = null;
	CategoryDAO budDAO = null;
	List<Category> listCategorys = null;
	List<HashMap<String, String>> listeCategorys = null;
	List<Long> listeCategoryIds = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list);

		listeCategory = (ListView) findViewById(R.id.categoryList);

		budDAO = new CategoryDAO(CategoryList.this);
		
		listeCategorys = new ArrayList<HashMap<String, String>>();
		
		listeCategoryIds = new ArrayList<Long>();

		listCategorys = new ArrayList<Category>();
		listCategorys = budDAO.selectionnerAll();
		
		// Toast.makeText(MainActivity.this, listMetiers.size(),
		// Toast.LENGTH_LONG).show();
		if (listCategorys.size() > 0) {
			int i = 0;
			HashMap<String, String> element;
			for (Category categorySingle : listCategorys) {
				element = new HashMap<String, String>();
				element.put("IdCategory", "Id du category "+String.valueOf(categorySingle.getId_category()));
				element.put("Datas","Montant : "+ String.valueOf(categorySingle.getDescription()));
				listeCategorys.add(element);
				listeCategoryIds.add(categorySingle.getId_category());
			}
		}

		if (listeCategorys.size() > 0) {
			ListAdapter adapter = new SimpleAdapter(this,
					listeCategorys,
					android.R.layout.simple_list_item_2, 
					new String[] {"IdCategory", "Datas"},
					new int[] {android.R.id.text1, android.R.id.text2 });
			listeCategory.setAdapter(adapter);
		} else {

		}
		listeCategory.setOnItemClickListener(categOnItemListener);
	}
	
	private OnItemClickListener categOnItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent listCateg = new Intent(CategoryList.this,CategoryDetails.class);
			listCateg.putExtra("ID_object", listeCategoryIds.get((int)id));
			startActivity(listCateg);
		}
		
	};
}
