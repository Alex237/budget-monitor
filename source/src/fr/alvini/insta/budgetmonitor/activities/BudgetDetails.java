package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.Spinner;
import fr.alvini.insta.budgetmonitor.HomeActivity;
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.BudgetDAO;
import fr.alvini.insta.budgetmonitor.dao.RecurrenceDAO;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class BudgetDetails extends Activity implements OnClickListener, OnDateChangedListener {

	private EditText amount;
	private EditText description;
	private String recurrence;
	private long id_recurrence;
	// liste d�roulante concernant la r�currence
	private List<Recurrence> recurrents = null;
	private RecurrenceDAO recDAO = null;
	private Button detailsBudget;
	private Button delete;
	private Calendar cal;
	private DatePicker date_begin = null;
	private DatePicker date_end = null;
	private GregorianCalendar dateBegin = null;
	private GregorianCalendar dateEnd = null;
	private Spinner recurrences = null;
	
	private Budget getBudget = null;
	private BudgetDAO budDao = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget_details);
		Intent intentPassed = getIntent();
		long id_budget = intentPassed.getLongExtra("Id_budget", -1);
		if (id_budget != -1) {
			budDao = new BudgetDAO(BudgetDetails.this);
			getBudget = budDao.selectionner(id_budget);
			System.out.println(getBudget.getId_budget());
		} else {
			getBudget = new Budget();
		}
		

		// correspondance entre les objets et les widgets
		this.amount = (EditText) findViewById(R.id.budget_details_amount_edit);
		this.description = (EditText) findViewById(R.id.budget_details_description_edit);
		this.date_begin = (DatePicker) findViewById(R.id.budget_details_date_begin_datePicker);
		this.date_end = (DatePicker) findViewById(R.id.budget_details_date_end_datePicker);
		this.detailsBudget = (Button) findViewById(R.id.btn_budget_update);
		this.delete = (Button) findViewById(R.id.btn_budget_delete);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.amount.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

		// rendre le bouton �coutable
		this.detailsBudget.setOnClickListener(this);
		this.delete.setOnClickListener(this);

		cal = Calendar.getInstance();
		this.date_begin.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
		this.date_end.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);

		recDAO = new RecurrenceDAO(BudgetDetails.this);
		recurrents = recDAO.selectionnerAll();
		final List<Long> recurrentsIds = new ArrayList<Long>();
		List<String> recurrentsString = new ArrayList<String>();
		int position = -1;
		int i = 0;
		for (Recurrence rec : recurrents) {
			recurrentsString.add(rec.getDescription());
			recurrentsIds.add(rec.getId_recurrence());
			if (rec.getId_recurrence() == getBudget.getRecurrence().getId_recurrence())
				position = i;
			i++;
		}
		recurrences = (Spinner) findViewById(R.id.budget_details_recurrencesSpinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<String> adapterRecurrence = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recurrentsString);
		// Specify the layout to use when the list of choices appears
		adapterRecurrence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		recurrences.setAdapter(adapterRecurrence);

		// on r�cup�re le choix de l'utilisateur (liste d�roulante)
		recurrences.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				id_recurrence = recurrentsIds.get((int)parent.getSelectedItemId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		// Fill the edittext and other stuff with datas
		this.amount.setText(String.valueOf(getBudget.getAmount()));
		this.description.setText(getBudget.getDescription());
//		this.date_begin.updateDate(2015, 0, 5);
		this.date_begin.updateDate(getBudget.getDateBegin().get(Calendar.YEAR), getBudget.getDateBegin().get(Calendar.MONTH)-1, getBudget.getDateBegin().get(Calendar.DAY_OF_MONTH));
		dateBegin = new GregorianCalendar(getBudget.getDateBegin().get(Calendar.YEAR), getBudget.getDateBegin().get(Calendar.MONTH)-1, getBudget.getDateBegin().get(Calendar.DAY_OF_MONTH));
		this.date_end.updateDate(getBudget.getDateEnd().get(Calendar.YEAR), getBudget.getDateEnd().get(Calendar.MONTH)-1, getBudget.getDateEnd().get(Calendar.DAY_OF_MONTH));
		dateEnd = new GregorianCalendar(getBudget.getDateEnd().get(Calendar.YEAR), getBudget.getDateEnd().get(Calendar.MONTH)-1, getBudget.getDateEnd().get(Calendar.DAY_OF_MONTH));
		if (position != -1)
			this.recurrences.setSelection(position);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this detailss items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.btn_budget_update) {
//			BudgetDAO budDAO = new BudgetDAO(BudgetDetails.this);
			getBudget.setDescription(description.getText().toString());
			if (this.amount.getText().toString().matches(""))
				this.amount.setText("0.0");
			getBudget.setAmount(Double.valueOf(this.amount.getText().toString()));
			getBudget.setDateBegin(dateBegin);
			getBudget.setDateEnd(dateEnd);
			Recurrence recurrenceChosen = new Recurrence();
			for(Recurrence recurr : recurrents) {
				if (recurr.getId_recurrence() == id_recurrence)
					recurrenceChosen = recurr;
			}
			getBudget.setRecurrence(recurrenceChosen);
//			Toast.makeText(BudgetDetails.this, String.valueOf(getBudget.getDescription()), Toast.LENGTH_LONG).show();
			budDao.modifier(getBudget);

//			System.exit(0);
			Intent unIntent = new Intent(this, HomeActivity.class);
			this.startActivity(unIntent);

		}

		if (id == R.id.btn_budget_delete) {
			AlertDialog.Builder alertDelete = new AlertDialog.Builder(BudgetDetails.this);
			alertDelete.setPositiveButton("Oui", dialogPositiveListener);
			alertDelete.setNegativeButton("Non", dialogNegativeListener);
			alertDelete.setMessage("Voulez vous vraiment supprimer le budget ?");
			alertDelete.show();
		}

	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		if (view.getId() == R.id.budget_details_date_begin_datePicker) {
			System.out.println("Date debut modifi�e :"+date_begin.getYear()+"-"+date_begin.getMonth()+"-"+date_begin.getDayOfMonth());
			dateBegin = new GregorianCalendar(date_begin.getYear(), date_begin.getMonth(), date_begin.getDayOfMonth());
		}
		if (view.getId() == R.id.budget_details_date_end_datePicker) {
			System.out.println("Date fin modifi�e :"+date_end.getYear()+"-"+date_end.getMonth()+"-"+date_end.getDayOfMonth());
			dateEnd = new GregorianCalendar(date_end.getYear(), date_end.getMonth(), date_end.getDayOfMonth());
		}
	}

	public Dialog.OnClickListener dialogPositiveListener = new Dialog.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			budDao.supprimer(getBudget.getId_budget());
			System.exit(0);
		}
	};

	public Dialog.OnClickListener dialogNegativeListener = new Dialog.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {			
		}
	};
}
