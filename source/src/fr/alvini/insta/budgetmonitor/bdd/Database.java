package fr.alvini.insta.budgetmonitor.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{
	protected static Database instance;
	protected static boolean boo;
	
	/* ########## PARAMETER TABLE BEGIN ########## */
	public static final String PARAMETER_TABLE_NAME = "parameter";

	public static final String PARAMETER_KEY = "id_parameter";
	public static final String PARAMETER_COLOR = "color";
	public static final String PARAMETER_FONT = "font";
	public static final String PARAMETER_STYLE = "style";

	public static final String PARAMETER_CREATION = "CREATE TABLE "+PARAMETER_TABLE_NAME+" ("+PARAMETER_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+PARAMETER_COLOR+" TEXT, "+PARAMETER_FONT+" TEXT, "+PARAMETER_STYLE+" TEXT); ";
	public static final String PARAMETER_DROP = "DROP TABLE IF EXISTS " + PARAMETER_TABLE_NAME + ";";
	/* ########## PARAMETER TABLE END ########## */
	
	
	/* ########## BUDGET TABLE BEGIN ########## */
	public static final String BUDGET_TABLE_NAME = "budget";

	public static final String BUDGET_KEY = "id_budget";
	public static final String BUDGET_DESCRIPTION = "description";
	public static final String BUDGET_AMOUNT = "amount";
	public static final String BUDGET_BEGIN_DATE = "begin_date";
	public static final String BUDGET_ENDING_DATE = "ending_date";
	public static final String BUDGET_RECURRENCE = "id_recurrence";
	
	public static final String BUDGET_CREATION = "CREATE TABLE "+BUDGET_TABLE_NAME+" ("+BUDGET_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+BUDGET_DESCRIPTION+" TEXT, "+BUDGET_AMOUNT+" REAL, "+BUDGET_BEGIN_DATE+" INTEGER, "+BUDGET_ENDING_DATE+" INTEGER, "+BUDGET_RECURRENCE+" INTEGER); ";
	public static final String BUDGET_DROP = "DROP TABLE IF EXISTS " + BUDGET_TABLE_NAME + ";";
	/* ########## BUDGET TABLE END ########## */
	
	/* ########## OPERATION TABLE BEGIN ########## */
	public static final String OPERATION_TABLE_NAME = "operation";

	public static final String OPERATION_KEY = "id_operation";
	public static final String OPERATION_DESCRIPTION = "description";
	public static final String OPERATION_TYPE = "type";
	public static final String OPERATION_AMOUNT = "amount";
	public static final String OPERATION_ADD_DATE = "add_date";
	public static final String OPERATION_BUDGET = "id_budget";
	public static final String OPERATION_CATEGORY = "id_category";
	public static final String OPERATION_RECURRENCE = "id_recurrence";
	public static final String OPERATION_RECURRENCE_STATUS = "recurrence_status";
	
	public static final String OPERATION_CREATION = "CREATE TABLE "+OPERATION_TABLE_NAME+" ("+OPERATION_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+OPERATION_DESCRIPTION+" TEXT, "+OPERATION_TYPE+" TEXT, "+OPERATION_AMOUNT+" REAL, "+OPERATION_ADD_DATE+" INTEGER, "+OPERATION_BUDGET+" INTEGER, "+OPERATION_CATEGORY+" INTEGER, "+OPERATION_RECURRENCE+" INTEGER, "+OPERATION_RECURRENCE_STATUS+" INTEGER); ";
	public static final String OPERATION_DROP = "DROP TABLE IF EXISTS " + OPERATION_TABLE_NAME + ";";
	/* ########## OPERATION TABLE END ########## */
	
	/* ########## CATEGORY TABLE BEGIN ########## */
	public static final String CATEGORY_TABLE_NAME = "category";

	public static final String CATEGORY_KEY = "id_category";
	public static final String CATEGORY_DESCRIPTION = "description";
	
	public static final String[] CATEGORIES = new String[]{"Par défaut","Alimentation", "Bar", "Courses", "Divers", "Epargne",  "Impots", "Logement/Charges", "Salaire", "Transport"};
	
	public static final String CATEGORY_CREATION = "CREATE TABLE "+CATEGORY_TABLE_NAME+" ("+CATEGORY_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CATEGORY_DESCRIPTION+" TEXT); ";
	public static final String CATEGORY_DROP = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME + ";";
	/* ########## CATEGORY TABLE END ########## */
	
	/* ########## RECURRENCE TABLE BEGIN ########## */
	public static final String RECURRENCE_TABLE_NAME = "recurrence";

	public static final String RECURRENCE_KEY = "id_recurrence";
	public static final String RECURRENCE_DESCRIPTION = "description";
	
	public static final String[] RECURRENCES = new String[] {"Aucun","Quotidienne","Hebdomadaire","Mensuelle","Annuelle"};
	
	public static final String RECURRENCE_CREATION = "CREATE TABLE "+RECURRENCE_TABLE_NAME+" ("+RECURRENCE_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+RECURRENCE_DESCRIPTION+" TEXT); ";
	public static final String RECURRENCE_DROP = "DROP TABLE IF EXISTS " + RECURRENCE_TABLE_NAME + ";";
	/* ########## RECURRENCE TABLE END ########## */
	
	
	public static final String CREATE_TABLES = PARAMETER_CREATION+" "+RECURRENCE_CREATION+" "+CATEGORY_CREATION+" "+BUDGET_CREATION+" "+OPERATION_CREATION;
	public static final String DROP_TABLES = PARAMETER_DROP+" "+RECURRENCE_DROP+" "+CATEGORY_DROP+" "+BUDGET_DROP+" "+OPERATION_DROP;
	
	/*
	public static Database getInstance(Context context, String name, CursorFactory factory, int version) {
		if (instance == null) {
			new Database(context, name, factory, version);
		}
		return instance;
	}
	*/

	public Database(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(RECURRENCE_CREATION);
		db.execSQL(PARAMETER_CREATION);
		db.execSQL(BUDGET_CREATION);
		db.execSQL(CATEGORY_CREATION);
		db.execSQL(OPERATION_CREATION);
		
		insertDatas(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(RECURRENCE_DROP);
		db.execSQL(PARAMETER_DROP);
		db.execSQL(BUDGET_DROP);
		db.execSQL(CATEGORY_DROP);
		db.execSQL(OPERATION_DROP);
		onCreate(db);
	}
	
	private void insertDatas(SQLiteDatabase db) {
		// Begin add Categories
		String sqlCateg = "SELECT "+CATEGORY_KEY+" as _id, "
							  +CATEGORY_DESCRIPTION+
					 " FROM "+CATEGORY_TABLE_NAME+"";
		Cursor cursorCateg = db.rawQuery(sqlCateg, new String[] {});
		if (cursorCateg.getCount() == 0) {
			for(String category : CATEGORIES) {
				ContentValues values = new ContentValues();
				values.put(CATEGORY_DESCRIPTION, category);
				db.insert(CATEGORY_TABLE_NAME, null, values);
			}
		}
		// End add Categories
		
		//Begin add Recurrences
		String sqlRecur = "SELECT "+Database.RECURRENCE_KEY+" as _id, "
				+Database.RECURRENCE_DESCRIPTION+
			 " FROM "+Database.RECURRENCE_TABLE_NAME+"";
		Cursor cursorRecur = db.rawQuery(sqlRecur, new String[] {});
		if (cursorRecur.getCount() == 0) {
			for(String recurrence : RECURRENCES) {
				ContentValues values = new ContentValues();
				values.put(RECURRENCE_DESCRIPTION, recurrence);
				db.insert(RECURRENCE_TABLE_NAME, null, values);
			}
		}
		//End add Recurrences
	}
}
