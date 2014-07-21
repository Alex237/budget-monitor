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
	 * @param m
	 *            le m�tier � ajouter � la base
	 */
	public void ajouter(Operation operation) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.OPERATION_DESCRIPTION, operation.getDescription());
		values.put(Database.OPERATION_TYPE, operation.getType());
		values.put(Database.OPERATION_AMOUNT, operation.getAmount());
		values.put(Database.OPERATION_ADD_DATE, String.valueOf(operation.getDate_added()));
		values.put(Database.OPERATION_BUDGET, operation.getBudget().getId_budget());
		if (operation.getCategory() != null)
			values.put(Database.OPERATION_CATEGORY, operation.getCategory().getId_category());
		if (operation.getRecurrence() != null)
			values.put(Database.OPERATION_RECURRENCE, operation.getRecurrence().getId_recurrence());
		mDb.insert(Database.OPERATION_TABLE_NAME, null, values);
		// AJOUTER STATUT DE LA RECURRENCE
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du m�tier � supprimer
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.OPERATION_TABLE_NAME, Database.OPERATION_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param m
	 *            le m�tier modifi�
	 */
	public void modifier(Operation operation) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.OPERATION_DESCRIPTION, operation.getDescription());
		values.put(Database.OPERATION_TYPE, operation.getType());
		values.put(Database.OPERATION_AMOUNT, operation.getAmount());
		values.put(Database.OPERATION_ADD_DATE, String.valueOf(operation.getDate_added()));
		values.put(Database.OPERATION_BUDGET, operation.getBudget().getId_budget());
		if (operation.getCategory() != null)
			values.put(Database.OPERATION_CATEGORY, operation.getCategory().getId_category());
		if (operation.getRecurrence() != null)
			values.put(Database.OPERATION_RECURRENCE, operation.getRecurrence().getId_recurrence());
		mDb.update(Database.OPERATION_TABLE_NAME, values, Database.OPERATION_KEY + " = ? ", new String[] {String.valueOf(operation.getId_operation())});
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du m�tier � r�cup�rer
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
						+Database.OPERATION_RECURRENCE+
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String date_added_str = cursor.getString(4);
			Date date_added = sdf.parse(date_added_str);
			
			
			
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
			operation = new Operation(id_operation, budget, category, amount, description, type, date_added, recurrence);
			//System.out.println("Results are : "+metier.getId()+" / "+metier.getIntitule()+" / "+metier.getSalaire()+".");
		}
		super.close();
		return operation;
	}
	
	/**
	 * @param id
	 *            l'identifiant du m�tier � r�cup�rer
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
						+Database.OPERATION_RECURRENCE+
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
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String date_added_str = cursor.getString(4);
				Date date_added = sdf.parse(date_added_str);
				
				
				
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
				operation = new Operation(id_operation, budget, category, amount, description, type, date_added, recurrence);
				
				list.add(operation);
			}
		}
		super.close();
		return list;
	}
}
