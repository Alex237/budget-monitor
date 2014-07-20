package fr.alvini.insta.budgetmonitor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.alvini.insta.budgetmonitor.bdd.Database;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class BudgetDAO extends DAOBase {
	private Context context;

	public BudgetDAO(Context pContext) {
		super(pContext);
		this.setContext(pContext);
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
	public void ajouter(Budget budget) {
		super.open();
		String dateBegin = sdf.format(budget.getDateBegin());
		String dateEnd = sdf.format(budget.getDateEnd());

		ContentValues values = new ContentValues();
		values.put(Database.BUDGET_AMOUNT, budget.getAmount());
		values.put(Database.BUDGET_BEGIN_DATE, dateBegin);
		values.put(Database.BUDGET_ENDING_DATE, dateEnd);
		if (budget.getRecurrence() != null)
			values.put(Database.BUDGET_RECURRENCE, String.valueOf(budget.getRecurrence().getId_recurrence()));
		//System.out.println(values.toString());
		mDb.insert(Database.BUDGET_TABLE_NAME, null, values);
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à supprimer
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.BUDGET_TABLE_NAME, Database.BUDGET_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param m
	 *            le métier modifié
	 */
	public void modifier(Budget budget) {
		super.open();
		String dateBegin = sdf.format(budget.getDateBegin());
		String dateEnd = sdf.format(budget.getDateEnd());

		ContentValues values = new ContentValues();
		values.put(Database.BUDGET_AMOUNT, budget.getAmount());
		values.put(Database.BUDGET_BEGIN_DATE, dateBegin);
		values.put(Database.BUDGET_ENDING_DATE, dateEnd);
		if (budget.getRecurrence() != null) {
			values.put(Database.BUDGET_RECURRENCE, String.valueOf(budget.getRecurrence().getId_recurrence()));
		}
		mDb.update(Database.BUDGET_TABLE_NAME, values, Database.BUDGET_KEY + " = ? ", new String[] {String.valueOf(budget.getId_budget())});
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 * @throws ParseException 
	 */
	public Budget selectionner(long id) throws ParseException {
		super.open();
		String sql = "SELECT "+Database.BUDGET_KEY+" as _id, "
						+Database.BUDGET_AMOUNT+ ", "
						+Database.BUDGET_BEGIN_DATE+", "
						+Database.BUDGET_ENDING_DATE+ ", "
						+Database.BUDGET_RECURRENCE+
					 " FROM "+Database.BUDGET_TABLE_NAME+
					 " WHERE "+Database.BUDGET_KEY+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {String.valueOf(id)});
		Budget budget = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_budget = cursor.getLong(0);
			
			double amount = cursor.getDouble(1);
			
			String date_begin_str = cursor.getString(2);
			Date date_begin = sdf.parse(date_begin_str);
			
			String date_end_str = cursor.getString(3);
			Date date_end = sdf.parse(date_begin_str);

			Recurrence recurrence = null;
			if (cursor.getInt(4) != 0) {
				RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
				recurrence = recDAO.selectionner(cursor.getInt(4));
			}
			//System.out.println("Results are : "+cursor.getLong(0)+" / "+cursor.getString(1)+" / "+cursor.getFloat(2)+".");
			budget = new Budget(id_budget, amount, date_begin, date_end, recurrence);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return budget;
	}
	
	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 * @throws ParseException 
	 */
	public List<Budget> selectionnerAll() throws ParseException {
		List<Budget> list = new ArrayList<Budget>();
		super.open();
		String sql = "SELECT "+Database.BUDGET_KEY+" as _id, "
							+Database.BUDGET_AMOUNT+ ", "
							+Database.BUDGET_BEGIN_DATE+", "
							+Database.BUDGET_ENDING_DATE+ ", "
							+Database.BUDGET_RECURRENCE+
					 " FROM "+Database.BUDGET_TABLE_NAME+"";
		Cursor cursor = mDb.rawQuery(sql, new String[] {});
		Budget budget = null;
		//Toast.makeText(pContext, cursor.getCount(), Toast.LENGTH_LONG).show();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				long id_budget = cursor.getLong(0);
				
				double amount = cursor.getDouble(1);
				
				String date_begin_str = cursor.getString(2);
				Date date_begin = sdf.parse(date_begin_str);
				
				String date_end_str = cursor.getString(3);
				Date date_end = sdf.parse(date_begin_str);

				Recurrence recurrence = null;
				if (cursor.getInt(4) != 0) {
					RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
					recurrence = recDAO.selectionner(cursor.getInt(4));
				}
				//System.out.println("Results are : "+cursor.getLong(0)+" / "+cursor.getString(1)+" / "+cursor.getFloat(2)+".");
				budget = new Budget(id_budget, amount, date_begin, date_end, recurrence);
				list.add(budget);
			}
		}
		super.close();
		return list;
	}

}
