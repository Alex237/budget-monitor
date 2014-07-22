package fr.alvini.insta.budgetmonitor.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.alvini.insta.budgetmonitor.bdd.Database;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.Category;
import fr.alvini.insta.budgetmonitor.model.Operation;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class OperationDAO extends DAOBase {
	private Context context;

	public OperationDAO(Context pContext) {
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
	 * @param operation
	 * Add an operation
	 */
	public void ajouter(Operation operation) {
		super.open();
		ContentValues values = new ContentValues();
		String date_added = Operation.formatDate(operation.getDate_added(), true);
		values.put(Database.OPERATION_DESCRIPTION, operation.getDescription());
		values.put(Database.OPERATION_TYPE, operation.getType());
		values.put(Database.OPERATION_AMOUNT, operation.getAmount());
		values.put(Database.OPERATION_ADD_DATE, date_added);
		values.put(Database.OPERATION_BUDGET, operation.getBudget().getId_budget());
		if (operation.getCategory() != null)
			values.put(Database.OPERATION_CATEGORY, operation.getCategory().getId_category());
		else
			values.put(Database.OPERATION_CATEGORY, 0);
		if (operation.getRecurrence() != null)
			values.put(Database.OPERATION_RECURRENCE, operation.getRecurrence().getId_recurrence());
		else
			values.put(Database.OPERATION_RECURRENCE, 0);
		values.put(Database.OPERATION_RECURRENCE_STATUS, 0);
		mDb.insert(Database.OPERATION_TABLE_NAME, null, values);
		super.close();
	}

	/**
	 * @param id
	 * Delete the operation from DB with its id
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.OPERATION_TABLE_NAME, Database.OPERATION_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param operation
	 * Update an operation
	 */
	public void modifier(Operation operation) {
		super.open();
		ContentValues values = new ContentValues();
		String date_added = Operation.formatDate(operation.getDate_added(), true);
		values.put(Database.OPERATION_DESCRIPTION, operation.getDescription());
		values.put(Database.OPERATION_TYPE, operation.getType());
		values.put(Database.OPERATION_AMOUNT, operation.getAmount());
		values.put(Database.OPERATION_ADD_DATE, date_added);
		values.put(Database.OPERATION_BUDGET, operation.getBudget().getId_budget());
		if (operation.getCategory() != null)
			values.put(Database.OPERATION_CATEGORY, operation.getCategory().getId_category());
		else
			values.put(Database.OPERATION_CATEGORY, 0);
		if (operation.getRecurrence() != null)
			values.put(Database.OPERATION_RECURRENCE, operation.getRecurrence().getId_recurrence());
		else
			values.put(Database.OPERATION_RECURRENCE, 0);
		values.put(Database.OPERATION_RECURRENCE_STATUS, 0);
		mDb.update(Database.OPERATION_TABLE_NAME, values, Database.OPERATION_KEY + " = ? ", new String[] {String.valueOf(operation.getId_operation())});
		super.close();
	}

	/**
	 * @param id
	 * Get an operation with its id
	 * @throws ParseException 
	 */
	public Operation selectionner(long id) throws ParseException {
		super.open();
		String sql = "SELECT "+Database.OPERATION_KEY+" as _id, "
						+Database.OPERATION_DESCRIPTION+ ", "
						+Database.OPERATION_TYPE+", "
						+Database.OPERATION_AMOUNT+ ", "
						+Database.OPERATION_ADD_DATE+ ", "
						+Database.OPERATION_BUDGET+ ", "
						+Database.OPERATION_CATEGORY+ ", "
						+Database.OPERATION_RECURRENCE+ ", "
						+Database.OPERATION_RECURRENCE_STATUS+
					 " FROM "+Database.OPERATION_TABLE_NAME+
					 " WHERE "+Database.OPERATION_KEY+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {String.valueOf(id)});
		Operation operation = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_operation = cursor.getLong(0);

			String description = cursor.getString(1);
			String type = cursor.getString(2);
			double amount = cursor.getDouble(3);
			
			String date_added_str = cursor.getString(4);
			GregorianCalendar date_added = new GregorianCalendar(Integer.valueOf(Budget.getDateElement("year", date_added_str)), Integer.valueOf(Budget.getDateElement("month", date_added_str)), Integer.valueOf(Budget.getDateElement("day", date_added_str)));
			
			
			Budget budget = null;
			if (cursor.getLong(5) != 0) {
				BudgetDAO budDAO = new BudgetDAO(this.getContext());
				budget = budDAO.selectionner(cursor.getLong(5));
			}
			
			Category category = null;
			if (cursor.getLong(6) != 0) {
				CategoryDAO catDAO = new CategoryDAO(this.getContext());
				category = catDAO.selectionner(cursor.getLong(6));
			}
			
			Recurrence recurrence = null;
			if (cursor.getInt(7) != 0) {
				RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
				recurrence = recDAO.selectionner(cursor.getLong(7));
			}
			Integer rec_status = cursor.getInt(8);
			operation = new Operation(id_operation, budget, category, amount, description, type, date_added, recurrence, rec_status);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return operation;
	}
	
	/**
	 * @param id
	 * Gets all operations
	 * @throws ParseException 
	 */
	public List<Operation> selectionnerAll() throws ParseException {
		List<Operation> list = new ArrayList<Operation>();
		super.open();
		String sql = "SELECT "+Database.OPERATION_KEY+" as _id, "
						+Database.OPERATION_DESCRIPTION+ ", "
						+Database.OPERATION_TYPE+", "
						+Database.OPERATION_AMOUNT+ ", "
						+Database.OPERATION_ADD_DATE+ ", "
						+Database.OPERATION_BUDGET+ ", "
						+Database.OPERATION_CATEGORY+ ", "
						+Database.OPERATION_RECURRENCE+ ", "
						+Database.OPERATION_RECURRENCE_STATUS+
					 " FROM "+Database.OPERATION_TABLE_NAME+"";
		Cursor cursor = mDb.rawQuery(sql, new String[] {});
		Operation operation = null;
		//Toast.makeText(pContext, cursor.getCount(), Toast.LENGTH_LONG).show();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				long id_operation = cursor.getLong(0);

				String description = cursor.getString(1);
				String type = cursor.getString(2);
				double amount = cursor.getDouble(3);
				
				String date_added_str = cursor.getString(4);
				System.out.println(date_added_str);
				GregorianCalendar date_added = new GregorianCalendar(Integer.valueOf(Budget.getDateElement("year", date_added_str)), Integer.valueOf(Budget.getDateElement("month", date_added_str)), Integer.valueOf(Budget.getDateElement("day", date_added_str)));
				
				Budget budget = null;
				System.out.println(cursor.getInt(5));
				if (cursor.getInt(5) != 0) {
					BudgetDAO budDAO = new BudgetDAO(this.getContext());
					System.out.println("Tests :"+budDAO.toString());
					budget = budDAO.selectionner(cursor.getInt(5));
				}
				
				Category category = null;
				if (cursor.getInt(6) != 0) {
					CategoryDAO catDAO = new CategoryDAO(this.getContext());
					category = catDAO.selectionner(cursor.getInt(6));
				}
				
				Recurrence recurrence = null;
				if (cursor.getInt(7) != 0) {
					RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
					recurrence = recDAO.selectionner(cursor.getInt(7));
				}
				Integer rec_status = cursor.getInt(8);
				operation = new Operation(id_operation, budget, category, amount, description, type, date_added, recurrence, rec_status);
				
				list.add(operation);
			}
		}
		super.close();
		return list;
	}
	
	/**
	 * @param id
	 * Get all operations for a budget
	 * @throws ParseException 
	 */
	public Operation selectionnerParBudget(long id) throws ParseException {
		super.open();
		String sql = "SELECT "+Database.OPERATION_KEY+" as _id, "
						+Database.OPERATION_DESCRIPTION+ ", "
						+Database.OPERATION_TYPE+", "
						+Database.OPERATION_AMOUNT+ ", "
						+Database.OPERATION_ADD_DATE+ ", "
						+Database.OPERATION_BUDGET+ ", "
						+Database.OPERATION_CATEGORY+ ", "
						+Database.OPERATION_RECURRENCE+ ", "
						+Database.OPERATION_RECURRENCE_STATUS+
					 " FROM "+Database.OPERATION_TABLE_NAME+
					 " WHERE "+Database.OPERATION_BUDGET+ " = ?";
		Cursor cursor = mDb.rawQuery(sql, new String[] {String.valueOf(id)});
		Operation operation = null;
		if (cursor.getCount() > 0) {
			cursor.moveToNext();
			long id_operation = cursor.getLong(0);

			String description = cursor.getString(1);
			String type = cursor.getString(2);
			double amount = cursor.getDouble(3);
			
			String date_added_str = cursor.getString(4);
			GregorianCalendar date_added = new GregorianCalendar(Integer.valueOf(Budget.getDateElement("year", date_added_str)), Integer.valueOf(Budget.getDateElement("month", date_added_str)), Integer.valueOf(Budget.getDateElement("day", date_added_str)));
			
			
			Budget budget = null;
			if (cursor.getLong(5) != 0) {
				BudgetDAO budDAO = new BudgetDAO(this.getContext());
				budget = budDAO.selectionner(cursor.getLong(5));
			}
			
			Category category = null;
			if (cursor.getLong(6) != 0) {
				CategoryDAO catDAO = new CategoryDAO(this.getContext());
				category = catDAO.selectionner(cursor.getLong(6));
			}
			
			Recurrence recurrence = null;
			if (cursor.getInt(7) != 0) {
				RecurrenceDAO recDAO = new RecurrenceDAO(this.getContext());
				recurrence = recDAO.selectionner(cursor.getLong(7));
			}
			Integer rec_status = cursor.getInt(8);
			operation = new Operation(id_operation, budget, category, amount, description, type, date_added, recurrence, rec_status);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return operation;
	}
}
