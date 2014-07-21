package fr.alvini.insta.budgetmonitor.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.alvini.insta.budgetmonitor.bdd.Database;
import fr.alvini.insta.budgetmonitor.model.Category;

public class CategoryDAO extends DAOBase {
	private Context context;

	public CategoryDAO(Context pContext) {
		super(pContext);
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}
	
	
	/**
	 * @param m
	 *            le métier à ajouter à la base
	 */
	public void ajouter(Category category) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.CATEGORY_DESCRIPTION, category.getDescription());
		mDb.insert(Database.CATEGORY_TABLE_NAME, null, values);
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à supprimer
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.CATEGORY_TABLE_NAME, Database.CATEGORY_KEY + " = ? ", new String[] {String.valueOf(id)});
		System.out.println("Je souhaite supprimer dans "+Database.CATEGORY_TABLE_NAME+" avec le champ : "+Database.CATEGORY_KEY+" et l'id : "+id+".");
		super.close();
	}

	/**
	 * @param m
	 *            le métier modifié
	 */
	public void modifier(Category category) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.CATEGORY_DESCRIPTION, category.getDescription());
		mDb.update(Database.CATEGORY_TABLE_NAME, values, Database.CATEGORY_KEY + " = ? ", new String[] {String.valueOf(category.getId_category())});
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 */
	public Category selectionner(long id) {
		super.open();
		String sql = "SELECT "+Database.CATEGORY_KEY+" as _id, "
						+Database.CATEGORY_DESCRIPTION+
					 " FROM "+Database.CATEGORY_TABLE_NAME+
					 " WHERE "+Database.CATEGORY_KEY+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {String.valueOf(id)});
		Category category = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_category = cursor.getLong(0);
			String description = cursor.getString(1);
			category = new Category(id_category, description);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return category;
	}
	
	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 */
	public List<Category> selectionnerAll() {
		List<Category> list = new ArrayList<Category>();
		super.open();
		String sql = "SELECT "+Database.CATEGORY_KEY+" as _id, "
						+Database.CATEGORY_DESCRIPTION+
					 " FROM "+Database.CATEGORY_TABLE_NAME+"";
		Cursor cursor = mDb.rawQuery(sql, new String[] {});
		Category category = null;
		//Toast.makeText(pContext, cursor.getCount(), Toast.LENGTH_LONG).show();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				long id_category = cursor.getLong(0);
				String description = cursor.getString(1);
				category = new Category(id_category, description);
				
				list.add(category);
			}
		}
		super.close();
		return list;
	}
}
