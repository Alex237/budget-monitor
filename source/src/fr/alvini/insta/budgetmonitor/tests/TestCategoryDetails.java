package fr.alvini.insta.budgetmonitor.tests;

import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.CategoryDAO;
import fr.alvini.insta.budgetmonitor.model.Category;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.Toast;

public class TestCategoryDetails extends Activity {
	private EditText description = null;
	private Button btnUpdCategory = null;
	private Button btnDelCategory = null;
	protected Category categ = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_details_category);
		
		Intent intentPassed = getIntent();
		categ = new Category();
		CategoryDAO catDAO = new CategoryDAO(TestCategoryDetails.this);
		long idPassed = intentPassed.getLongExtra("ID_object", -1);
		if (idPassed != -1)
			categ = catDAO.selectionner(idPassed);
		Toast.makeText(TestCategoryDetails.this, categ.getDescription(), Toast.LENGTH_LONG).show();
		
		description = (EditText) findViewById(R.id.test_cat_desc_details_edit);
		if (categ.getDescription() != null)
			description.setText(categ.getDescription());

		btnUpdCategory = (Button) findViewById(R.id.btn_test_cat_upd);
		btnUpdCategory.setOnClickListener(updCategoryListener);
		
		btnDelCategory = (Button) findViewById(R.id.btn_test_cat_del);
		btnDelCategory.setOnClickListener(delCategoryListener);
	}
	
	private OnClickListener updCategoryListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			categ.setDescription(description.getText().toString());
			CategoryDAO catDAO = new CategoryDAO(TestCategoryDetails.this);
			catDAO.modifier(categ);
			Intent category_home = new Intent(TestCategoryDetails.this,TestCategoryList.class);
			startActivity(category_home);
		}
	};
	private OnClickListener delCategoryListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			CategoryDAO catDAO = new CategoryDAO(TestCategoryDetails.this);
			catDAO.supprimer(categ.getId_category());
			Intent category_home = new Intent(TestCategoryDetails.this,TestCategoryList.class);
			startActivity(category_home);
		}
	};
}
