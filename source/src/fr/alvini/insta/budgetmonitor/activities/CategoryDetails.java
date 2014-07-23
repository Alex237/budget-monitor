package fr.alvini.insta.budgetmonitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.CategoryDAO;
import fr.alvini.insta.budgetmonitor.model.Category;

public class CategoryDetails extends Activity {
	private EditText description = null;
	private Button btnUpdCategory = null;
	private Button btnDelCategory = null;
	protected Category categ = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_details);
		
		Intent intentPassed = getIntent();
		categ = new Category();
		CategoryDAO catDAO = new CategoryDAO(CategoryDetails.this);
		long idPassed = intentPassed.getLongExtra("ID_object", -1);
		if (idPassed != -1)
			categ = catDAO.selectionner(idPassed);
//		Toast.makeText(CategoryDetails.this, categ.getDescription(), Toast.LENGTH_LONG).show();
		
		description = (EditText) findViewById(R.id.categoryDetails_desc_edit);
		if (categ.getDescription() != null)
			description.setText(categ.getDescription());

		btnUpdCategory = (Button) findViewById(R.id.btn_category_upd);
		btnUpdCategory.setOnClickListener(updCategoryListener);
		
		btnDelCategory = (Button) findViewById(R.id.btn_category_del);
		btnDelCategory.setOnClickListener(delCategoryListener);
	}
	
	private OnClickListener updCategoryListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			categ.setDescription(description.getText().toString());
			CategoryDAO catDAO = new CategoryDAO(CategoryDetails.this);
			catDAO.modifier(categ);
			Intent category_home = new Intent(CategoryDetails.this,CategoryList.class);
			startActivity(category_home);
		}
	};
	private OnClickListener delCategoryListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			CategoryDAO catDAO = new CategoryDAO(CategoryDetails.this);
			catDAO.supprimer(categ.getId_category());
			Intent category_home = new Intent(CategoryDetails.this,CategoryList.class);
			startActivity(category_home);
		}
	};
}
