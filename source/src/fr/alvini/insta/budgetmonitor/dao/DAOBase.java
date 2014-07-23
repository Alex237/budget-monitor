package fr.alvini.insta.budgetmonitor.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import fr.alvini.insta.budgetmonitor.bdd.Database;

public abstract class DAOBase {
	// Nous sommes à la première version de la base
	// Si je décide de la mettre à jour, il faudra changer cet attribut
//	protected final static int VERSION = 1;
	// Le nom du fichier qui représente la base de données
//	protected final static String NOM = "BudgetMonitorDBTemp.db";

	protected SQLiteDatabase mDb = null;
	protected Database mDatabase = null;

	public DAOBase(Context pContext) {
//		this.mDatabase = Database.getInstance(pContext, NOM, null, VERSION);
		System.out.println("Constructeur DAO");
		this.mDatabase = new Database(pContext);
	}

	public void open() {
		// Pas besoin de fermer la dernière base puisque getWritableDatabase
		// s'en charge
//		System.out.println("Open de DAOBase : "+mDatabase.toString());
		System.out.println("Open");
		mDb = mDatabase.getWritableDatabase();
//		return mDb;
	}

	public void close() {
		System.out.println("Close");
		mDatabase.close();
		mDb.close();
	}

	public SQLiteDatabase getDb() {
		return mDb;
	}
	
	public boolean isOpen() {
		return mDb.isOpen();
	}
}
