package fr.alvini.insta.budgetmonitor.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	 * @param m
	 *            le métier à ajouter à la base
	 */
	public void ajouter(Recurrence recurrence) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.RECURRENCE_DAY, recurrence.getDay());
		values.put(Database.RECURRENCE_WEEK, recurrence.getWeek());
		values.put(Database.RECURRENCE_MONTH, recurrence.getMonth());
		values.put(Database.RECURRENCE_YEAR, recurrence.getYear());
		mDb.insert(Database.RECURRENCE_TABLE_NAME, null, values);
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à supprimer
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.RECURRENCE_TABLE_NAME, Database.RECURRENCE_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param m
	 *            le métier modifié
	 */
	public void modifier(Recurrence recurrence) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.RECURRENCE_DAY, recurrence.getDay());
		values.put(Database.RECURRENCE_WEEK, recurrence.getWeek());
		values.put(Database.RECURRENCE_MONTH, recurrence.getMonth());
		values.put(Database.RECURRENCE_YEAR, recurrence.getYear());
		mDb.update(Database.RECURRENCE_TABLE_NAME, values, Database.RECURRENCE_KEY + " = ? ", new String[] {String.valueOf(recurrence.getId_recurrence())});
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 */
	public Recurrence selectionner(long id) {
		super.open();
		String sql = "SELECT "+Database.RECURRENCE_KEY+" as _id, "
						+Database.RECURRENCE_DAY+ ", "
						+Database.RECURRENCE_WEEK+", "
						+Database.RECURRENCE_MONTH+ ", "
						+Database.RECURRENCE_YEAR+
					 " FROM "+Database.RECURRENCE_TABLE_NAME+
					 " WHERE "+Database.RECURRENCE_KEY+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {String.valueOf(id)});
		Recurrence recurrence = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_recurrence = cursor.getLong(0);
			
			int day = cursor.getInt(1);
			int week = cursor.getInt(2);
			int month = cursor.getInt(3);
			int year = cursor.getInt(4);
			recurrence = new Recurrence(id_recurrence, day, week, month, year);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return recurrence;
	}
	
	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 */
	public List<Recurrence> selectionnerAll() {
		List<Recurrence> list = new ArrayList<Recurrence>();
		super.open();
		String sql = "SELECT "+Database.RECURRENCE_KEY+" as _id, "
						+Database.RECURRENCE_DAY+ ", "
						+Database.RECURRENCE_WEEK+", "
						+Database.RECURRENCE_MONTH+ ", "
						+Database.RECURRENCE_YEAR+
					 " FROM "+Database.RECURRENCE_TABLE_NAME+"";
		Cursor cursor = mDb.rawQuery(sql, new String[] {});
		Recurrence recurrence = null;
		//Toast.makeText(pContext, cursor.getCount(), Toast.LENGTH_LONG).show();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				long id_recurrence = cursor.getLong(0);
				
				int day = cursor.getInt(1);
				int week = cursor.getInt(2);
				int month = cursor.getInt(3);
				int year = cursor.getInt(4);
				recurrence = new Recurrence(id_recurrence, day, week, month, year);
				
				list.add(recurrence);
			}
		}
		super.close();
		return list;
	}


}
