package fr.alvini.insta.budgetmonitor.tests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.CategoryDAO;
import fr.alvini.insta.budgetmonitor.model.Category;

public class TestCategory extends Activity {
	private Button category_add = null;
	private Button category_list = null;
	private Button category_update = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_category);
		
		category_add = (Button) findViewById(R.id.category_add);
		category_add.setOnClickListener(categoryAddListener);
		
		category_list = (Button) findViewById(R.id.category_list);
		category_list.setOnClickListener(categoryListListener);
		
//		category_update = (Button) findViewById(R.id.category_upd);
//		category_update.setOnClickListener(categoryUpdateListener);
	}
	
	private OnClickListener categoryAddListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent openAddCategory = new Intent(TestCategory.this,TestCategoryAdd.class);
			startActivity(openAddCategory);
		}
		
	};

	private OnClickListener categoryListListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent openListBudget = new Intent(TestCategory.this,TestCategoryList.class);
			startActivity(openListBudget);
		}
		
	};
	
	/*
	private OnClickListener categoryUpdateListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent openUpdateCategory = new Intent(TestCategory.this,TestCategoryList.class);
			Category category = new Category();
			CategoryDAO catDAO = new CategoryDAO(TestCategory.this);
			category = catDAO.selectionner(1);
			openUpdateCategory.putExtra("IDCategory", category.getId_category());
			startActivity(openUpdateCategory);
		}
		
	};
	*/
}
