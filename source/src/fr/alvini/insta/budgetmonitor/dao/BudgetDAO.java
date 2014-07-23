package fr.alvini.insta.budgetmonitor.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.alvini.insta.budgetmonitor.bdd.Database;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.ObjectModel;
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
	 *            le m�tier � ajouter � la base
	 */
	public void ajouter(Budget budget) {
		super.open();
		String dateBegin = ObjectModel.formatDate(budget.getDateBegin(),true);
		String dateEnd = ObjectModel.formatDate(budget.getDateEnd(),true);
		System.out.println("Dans ajouter dateBegin: "+dateBegin+" - dateEnd: "+dateEnd);

		ContentValues values = new ContentValues();
		values.put(Database.BUDGET_DESCRIPTION, budget.getDescription());
		values.put(Database.BUDGET_AMOUNT, budget.getAmount());
		values.put(Database.BUDGET_BEGIN_DATE, dateBegin);
		values.put(Database.BUDGET_ENDING_DATE, dateEnd);
		if (budget.getRecurrence() != null) {
			values.put(Database.BUDGET_RECURRENCE, String.valueOf(budget.getRecurrence().getId_recurrence()));
		} else {
			values.put(Database.BUDGET_RECURRENCE, 0);
		}
//		System.out.println(values.toString());
		mDb.insert(Database.BUDGET_TABLE_NAME, null, values);
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du m�tier � supprimer
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.BUDGET_TABLE_NAME, Database.BUDGET_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param m
	 * 
	 */
	public void modifier(Budget budget) {
		super.open();
//		System.out.println("Avant String dans modifier dateBegin: "+budget.getDateBegin().get(Calendar.YEAR)+budget.getDateBegin().get(Calendar.MONTH)+budget.getDateBegin().get(Calendar.DAY_OF_MONTH)+" - dateEnd: "+budget.getDateEnd().get(Calendar.YEAR)+budget.getDateEnd().get(Calendar.MONTH)+budget.getDateEnd().get(Calendar.DAY_OF_MONTH));
		String dateBegin = ObjectModel.formatDate(budget.getDateBegin(),true);
		String dateEnd = ObjectModel.formatDate(budget.getDateEnd(),true);
//		System.out.println("Dans modifier dateBegin: "+dateBegin+" - dateEnd: "+dateEnd);

		ContentValues values = new ContentValues();
		values.put(Database.BUDGET_AMOUNT, budget.getAmount());
		values.put(Database.BUDGET_BEGIN_DATE, dateBegin);
		values.put(Database.BUDGET_ENDING_DATE, dateEnd);
		if (budget.getRecurrence() != null)
			values.put(Database.BUDGET_RECURRENCE, String.valueOf(budget.getRecurrence().getId_recurrence()));
		else
			values.put(Database.BUDGET_RECURRENCE, 0);
		mDb.update(Database.BUDGET_TABLE_NAME, values, Database.BUDGET_KEY + " = ? ", new String[] {String.valueOf(budget.getId_budget())});
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du m�tier � r�cup�rer
	 * 
	 */
	public Budget selectionner(long id) {
		super.open();
		String sql = "SELECT "+Database.BUDGET_KEY+" as _id, "
						+Database.BUDGET_DESCRIPTION+ ", "
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
			
			String description = cursor.getString(1);
			double amount = cursor.getDouble(2);

			String date_begin_str = cursor.getString(3);
			GregorianCalendar date_begin = new GregorianCalendar(Integer.valueOf(ObjectModel.getDateElement("year", date_begin_str)), Integer.valueOf(ObjectModel.getDateElement("month", date_begin_str)), Integer.valueOf(ObjectModel.getDateElement("day", date_begin_str)));
			
			String date_end_str = cursor.getString(4);
			GregorianCalendar date_end = new GregorianCalendar(Integer.valueOf(ObjectModel.getDateElement("year", date_end_str)), Integer.valueOf(ObjectModel.getDateElement("month", date_end_str)), Integer.valueOf(ObjectModel.getDateElement("day", date_end_str)));

			Recurrence recurrence = new Recurrence();
			if (cursor.getInt(5) != 0) {
//				RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
				recurrence.setId_recurrence(cursor.getInt(5));
			}
			//System.out.println("Results are : "+cursor.getLong(0)+" / "+cursor.getString(1)+" / "+cursor.getFloat(2)+".");
			budget = new Budget(id_budget, description, amount, date_begin, date_end, recurrence);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return budget;
	}
	
	/**
	 * @param id
	 *            l'identifiant du m�tier � r�cup�rer
	 * @throws ParseException 
	 */
	public List<Budget> selectionnerAll() throws ParseException {
		List<Budget> list = new ArrayList<Budget>();
		super.open();
		String sql = "SELECT "+Database.BUDGET_KEY+" as _id, "
							+Database.BUDGET_DESCRIPTION+ ", "
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
				
				String description = cursor.getString(1);
				double amount = cursor.getDouble(2);
				
				String date_begin_str = cursor.getString(3);
				GregorianCalendar date_begin = new GregorianCalendar(Integer.valueOf(ObjectModel.getDateElement("year", date_begin_str)), Integer.valueOf(ObjectModel.getDateElement("month", date_begin_str)), Integer.valueOf(ObjectModel.getDateElement("day", date_begin_str)));
				
				String date_end_str = cursor.getString(4);
				GregorianCalendar date_end = new GregorianCalendar(Integer.valueOf(ObjectModel.getDateElement("year", date_end_str)), Integer.valueOf(ObjectModel.getDateElement("month", date_end_str)), Integer.valueOf(ObjectModel.getDateElement("day", date_end_str)));
				
				Recurrence recurrence = new Recurrence();
				if (cursor.getInt(5) != 0) {
//					RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
					recurrence.setId_recurrence(cursor.getInt(5));
				}
				//System.out.println("Results are : "+cursor.getLong(0)+" / "+cursor.getString(1)+" / "+cursor.getFloat(2)+".");
				budget = new Budget(id_budget, description, amount, date_begin, date_end, recurrence);
				list.add(budget);
			}
		}
		super.close();
		return list;
	}

}
