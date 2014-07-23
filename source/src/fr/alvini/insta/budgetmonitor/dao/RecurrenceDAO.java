package fr.alvini.insta.budgetmonitor.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.alvini.insta.budgetmonitor.bdd.Database;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class RecurrenceDAO extends DAOBase {
	private Context context;

	public RecurrenceDAO(Context pContext) {
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
	 * @param recurrence
	 * Add an entry in recurrence table
	 */
	public void ajouter(Recurrence recurrence) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.RECURRENCE_DESCRIPTION, recurrence.getDescription());
		mDb.insert(Database.RECURRENCE_TABLE_NAME, null, values);
		super.close();
	}

	/**
	 * @param id
	 * Delete entry for the id passed in parameter
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.RECURRENCE_TABLE_NAME, Database.RECURRENCE_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param recurrence
	 * Update data for the object recurrence in parameter
	 */
	public void modifier(Recurrence recurrence) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.RECURRENCE_DESCRIPTION, recurrence.getDescription());
		mDb.update(Database.RECURRENCE_TABLE_NAME, values, Database.RECURRENCE_KEY + " = ? ", new String[] {String.valueOf(recurrence.getId_recurrence())});
		super.close();
	}

	/**
	 * @param id
	 * Get datas for a specific id from Recurrence table
	 */
	public Recurrence selectionner(long id) {
		super.open();
		String sql = "SELECT "+Database.RECURRENCE_KEY+" as _id, "
						+Database.RECURRENCE_DESCRIPTION+
					 " FROM "+Database.RECURRENCE_TABLE_NAME+
					 " WHERE "+Database.RECURRENCE_KEY+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {String.valueOf(id)});
		Recurrence recurrence = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_recurrence = cursor.getLong(0);
			
			String description = cursor.getString(1);
			recurrence = new Recurrence(id_recurrence, description);
		}
		super.close();
		return recurrence;
	}
	
	/**
	 * Get all datas from Recurrence table
	 */
	public List<Recurrence> selectionnerAll() {
		List<Recurrence> list = new ArrayList<Recurrence>();
		super.open();
		String sql = "SELECT "+Database.RECURRENCE_KEY+" as _id, "
						+Database.RECURRENCE_DESCRIPTION+
					 " FROM "+Database.RECURRENCE_TABLE_NAME+"";
		Cursor cursor = mDb.rawQuery(sql, new String[] {});
		Recurrence recurrence = null;
		//Toast.makeText(pContext, cursor.getCount(), Toast.LENGTH_LONG).show();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				long id_recurrence = cursor.getLong(0);
				
				String description = cursor.getString(1);
				recurrence = new Recurrence(id_recurrence, description);
				
				list.add(recurrence);
			}
		}
		super.close();
		return list;
	}


	/**
	 * @param id
	 * Get datas for a specific id from Recurrence table
	 */
	public Recurrence selectionnerParDescription(String pDescription) {
		super.open();
		String sql = "SELECT "+Database.RECURRENCE_KEY+" as _id, "
						+Database.RECURRENCE_DESCRIPTION+
					 " FROM "+Database.RECURRENCE_TABLE_NAME+
					 " WHERE "+Database.RECURRENCE_DESCRIPTION+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {pDescription});
		Recurrence recurrence = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_recurrence = cursor.getLong(0);
			
			String description = cursor.getString(1);
			recurrence = new Recurrence(id_recurrence, description);
		}
		super.close();
		return recurrence;
	}
}
