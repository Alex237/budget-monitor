package fr.alvini.insta.budgetmonitor.tests;

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

public class TestCategoryAdd extends Activity {
	private EditText description = null;
	private Button btnAddCategory = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_add_category);
		
		description = (EditText) findViewById(R.id.test_cat_desc_add_edit);
		
		btnAddCategory = (Button) findViewById(R.id.btn_test_cat_add);
		btnAddCategory.setOnClickListener(addCategoryListener);
	}
	
	private OnClickListener addCategoryListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Category category = new Category();
			category.setDescription(description.getText().toString());
			CategoryDAO catDAO = new CategoryDAO(TestCategoryAdd.this);
			catDAO.ajouter(category);
			Intent category_home = new Intent(TestCategoryAdd.this,TestCategory.class);
			startActivity(category_home);
		}
		
	};
}
