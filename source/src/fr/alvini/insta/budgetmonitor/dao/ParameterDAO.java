package fr.alvini.insta.budgetmonitor.dao;

import java.util.ArrayList;
import java.util.List;

import fr.alvini.insta.budgetmonitor.bdd.Database;
import fr.alvini.insta.budgetmonitor.model.Parameter;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ParameterDAO extends DAOBase {
	private Context context;

	public ParameterDAO(Context pContext) {
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
	public void ajouter(Parameter parameter) {
		Parameter parameter_exist = this.selectionner();
		if (parameter_exist == null) {
			super.open();
			ContentValues values = new ContentValues();
			values.put(Database.PARAMETER_COLOR, parameter.getColor());
			values.put(Database.PARAMETER_FONT, parameter.getFont());
			values.put(Database.PARAMETER_STYLE, parameter.getStyle());
			mDb.insert(Database.PARAMETER_TABLE_NAME, null, values);
			super.close();
		}
	}

	/**
	 * @param id
	 *            l'identifiant du métier à supprimer
	 */
	public void supprimer(long id) {
		super.open();
		mDb.delete(Database.PARAMETER_TABLE_NAME, Database.PARAMETER_KEY + " = ? ", new String[] {String.valueOf(id)});
		super.close();
	}

	/**
	 * @param m
	 *            le métier modifié
	 */
	public void modifier(Parameter parameter) {
		super.open();
		ContentValues values = new ContentValues();
		values.put(Database.PARAMETER_COLOR, parameter.getColor());
		values.put(Database.PARAMETER_FONT, parameter.getFont());
		values.put(Database.PARAMETER_STYLE, parameter.getStyle());
		mDb.update(Database.PARAMETER_TABLE_NAME, values, Database.PARAMETER_KEY + " = ? ", new String[] {String.valueOf(parameter.getId_parameter())});
		super.close();
	}

	/**
	 * @param id
	 *            l'identifiant du métier à récupérer
	 */
	public Parameter selectionner() {
		super.open();
		String sql = "SELECT "+Database.PARAMETER_KEY+" as _id, "
						+Database.PARAMETER_COLOR+ ", "
						+Database.PARAMETER_FONT+", "
						+Database.PARAMETER_STYLE+
					 " FROM "+Database.PARAMETER_TABLE_NAME+"";
		Cursor cursor = mDb.rawQuery(sql, new String[] {});
		Parameter parameter = null;
		//Toast.makeText(pContext, cursor.getCount(), Toast.LENGTH_LONG).show();
		if (cursor.getCount() > 0) {
			while(cursor.moveToNext()) {
				long id_parameter = cursor.getLong(0);
				String color = cursor.getString(1);
				String font = cursor.getString(2);
				String style = cursor.getString(3);
				parameter = Parameter.getInstance(color, font, style);
			}
		}
		super.close();
		return parameter;
	}
}
