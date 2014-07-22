package fr.alvini.insta.budgetmonitor.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
import fr.alvini.insta.budgetmonitor.R;
import fr.alvini.insta.budgetmonitor.dao.BudgetDAO;
import fr.alvini.insta.budgetmonitor.dao.RecurrenceDAO;
import fr.alvini.insta.budgetmonitor.model.Budget;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class BudgetAdd extends Activity implements OnClickListener, OnDateChangedListener {

	private EditText amount;
	private EditText description;
	private String recurrence;
	private long id_recurrence;
	// liste déroulante concernant la récurrence
	private List<Recurrence> recurrents = null;
	private RecurrenceDAO recDAO = null;
	private Button addBudget;
	private Button cancel;
	private Calendar cal = null;
	private DatePicker date_begin = null;
	private DatePicker date_end = null;
	private GregorianCalendar dateBegin = null;
	private GregorianCalendar dateEnd = null;
	private Spinner recurrences = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.budget_add);

		// correspondance entre les objets et les widgets
		this.amount = (EditText) findViewById(R.id.budget_add_amount_edit);
		this.description = (EditText) findViewById(R.id.budget_add_description_edit);
		this.date_begin = (DatePicker) findViewById(R.id.budget_add_date_begin_datePicker);
		this.date_end = (DatePicker) findViewById(R.id.budget_add_date_end_datePicker);
		this.addBudget = (Button) findViewById(R.id.btn_budget_add);
		this.cancel = (Button) findViewById(R.id.btn_budget_cancel);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		cal = Calendar.getInstance();
		this.date_begin.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
		this.date_end.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), this);
		
		// rendre le bouton écoutable
		this.addBudget.setOnClickListener(this);
		this.cancel.setOnClickListener(this);

		dateBegin = new GregorianCalendar(date_begin.getYear(), date_begin.getMonth(), date_begin.getDayOfMonth());
		dateEnd = new GregorianCalendar(date_end.getYear(), date_end.getMonth(), date_end.getDayOfMonth());

		recDAO = new RecurrenceDAO(BudgetAdd.this);
		recurrents = recDAO.selectionnerAll();
		final List<Long> recurrentsIds = new ArrayList<Long>();
		List<String> recurrentsString = new ArrayList<String>();
		for (Recurrence rec : recurrents) {
			recurrentsString.add(rec.getDescription());
			recurrentsIds.add(rec.getId_recurrence());
		}
		recurrences = (Spinner) findViewById(R.id.budget_add_recurrencesSpinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<String> adapterRecurrence = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, recurrentsString);
		// Specify the layout to use when the list of choices appears
		adapterRecurrence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		recurrences.setAdapter(adapterRecurrence);

		// on récupère le choix de l'utilisateur (liste déroulante)
		recurrences.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				id_recurrence = recurrentsIds.get((int)parent.getSelectedItemId());
//				Toast.makeText(BudgetAdd.this, String.valueOf(id_recurrence), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		if (view.getId() == R.id.budget_add_date_begin_datePicker) {
//			System.out.println("Date debut modifiée");
			dateBegin = new GregorianCalendar(date_begin.getYear(), date_begin.getMonth(), date_begin.getDayOfMonth());
		}
		if (view.getId() == R.id.budget_add_date_end_datePicker) {
//			System.out.println("Date fin modifiée");
			dateEnd = new GregorianCalendar(date_end.getYear(), date_end.getMonth(), date_end.getDayOfMonth());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.btn_budget_add) {
			Budget budgetToAdd = new Budget();
			budgetToAdd.setDescription(description.getText().toString());
			budgetToAdd.setAmount(Double.valueOf(this.amount.getText().toString()));
			budgetToAdd.setDateBegin(dateBegin);
			budgetToAdd.setDateEnd(dateEnd);
			Recurrence recurrenceChosen = new Recurrence();
			for(Recurrence recurr : recurrents) {
				if (recurr.getId_recurrence() == id_recurrence)
					recurrenceChosen = recurr;
			}
			budgetToAdd.setRecurrence(recurrenceChosen);
				
			BudgetDAO budDAO = new BudgetDAO(BudgetAdd.this);
			budDAO.ajouter(budgetToAdd);

			System.exit(0);
			
//			Intent unIntent = new Intent(this, HomeActivity.class);
//			this.startActivity(unIntent);
		}
		if (id == R.id.btn_budget_cancel) {
			System.exit(0);
		}
	}
}
