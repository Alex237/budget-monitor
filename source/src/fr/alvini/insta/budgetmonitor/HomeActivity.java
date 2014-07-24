package fr.alvini.insta.budgetmonitor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import fr.alvini.insta.budgetmonitor.activities.*;
import fr.alvini.insta.budgetmonitor.adaptater.*;
import fr.alvini.insta.budgetmonitor.dao.*;
import fr.alvini.insta.budgetmonitor.model.*;
import fr.alvini.insta.holographlib.*;

public class HomeActivity extends FragmentActivity implements ActionBar.TabListener {

	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	private ViewPager mViewPager;
	private Resources stringRessource;
	private static Button addbudgetBtn;
	private static Button changBudgetBtn;

	// Drawer NAV
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles1;
	private String[] mMenuTitlesHead;
	private Integer[] mImageTitles = {
			R.drawable.list,
			R.drawable.cloudupload,
			R.drawable.cog,
			R.drawable.infocircle,
			R.drawable.shield,
			R.drawable.questioncircle,
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		this.stringRessource = getResources();
		String[] listOfTab = this.stringRessource
				.getStringArray(R.array.tab_navigation);

		this.mTitle = mDrawerTitle = getTitle();
		this.mMenuTitles1 = getResources().getStringArray(
				R.array.drawer_menu_array);
		this.mMenuTitlesHead = getResources().getStringArray(
				R.array.drawer_menu_header);
		this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		this.mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);


		CustomList adapterIcon = new
				CustomList(HomeActivity.this, mMenuTitles1, mImageTitles);
		mDrawerList.setAdapter(adapterIcon);
		// set up the drawer's list view with items and click listener
		//mDrawerList.setAdapter(new ArrayAdapter<String>(this,
		//		R.layout.drawer_list_item, mMenuTitles1));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
				) {
			@Override
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		this.mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager(), listOfTab);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		// actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		this.mViewPager = (ViewPager) findViewById(R.id.pagePrincipale);
		this.mViewPager.setAdapter(this.mAppSectionsPagerAdapter);
		this.mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select
				// the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if
				// we have a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < this.mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(this.mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	// M�thode qui se d�clenchera lorsque vous appuierez sur le bouton menu du
	// t�l�phone
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Cr�ation d'un MenuInflater qui va permettre d'instancier un Menu XML
		// en un objet Menu
		//MenuInflater inflater = getMenuInflater();
		// Instanciation du menu XML sp�cifier en un objet Menu
		//inflater.inflate(R.menu.activity_menu, menu);

		// Il n'est pas possible de modifier l'ic�ne d'ent�te du sous-menu via
		// le fichier XML on le fait donc en JAVA
//		 menu.getItem(0).getSubMenu().setHeaderIcon(R.drawable.option_white);

		return true;
	}

	// M�thode qui se d�clenchera au clic sur un item
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// On regarde quel item a �t� cliqu� gr�ce � son id et on d�clenche une action
//		switch (item.getItemId()) {
	//	case R.id.parametres:
//			Toast.makeText(HomeActivity.this, "Paramatres ", Toast.LENGTH_SHORT)
//			.show();
			/*
		case R.id.mentionsLegales:
			Toast.makeText(HomeActivity.this, "Mentions Légales",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.aPropos:
			*/
//			Toast.makeText(HomeActivity.this, "à Propos", Toast.LENGTH_SHORT)
//			.show();
			//Intent  unIntent = new Intent(this, A_propos.class);
			//unIntent.putExtra("choixUtilisateur",this.choixOperation);
//			unIntent.putExtra("montant",this.montant.getText().toString());
//			unIntent.putExtra("libelle",this.libelle.getText().toString());
//			unIntent.putExtra("repeter", this.recurrence);
			
			//this.startActivity(unIntent);

			/*
			return true;
		case R.id.categoriesMgt:
			Intent IntentCategMgt = new Intent(HomeActivity.this,CategoryList.class);
			startActivity(IntentCategMgt);
			return true;
		default:*/
			return super.onOptionsItemSelected(item);
		//}
//		return true;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// selectItem(position);
//			Toast.makeText(HomeActivity.this,
//					"Drawer item : " + position + " | ID : " + id + "\n " + mMenuTitles1[position],
//					Toast.LENGTH_SHORT).show();
			mDrawerLayout.closeDrawer(mDrawerList);
			int Id = (int) id;
			switch(Id){
				case 0:
					Intent intentGerer = new Intent(HomeActivity.this, Gerer.class);
					startActivity(intentGerer);
					break;
				case 1:
					Intent intentExporter = new Intent(HomeActivity.this, Exporter.class);
					startActivity(intentExporter);
					break;
				case 2:
					Intent intentParametres = new Intent(HomeActivity.this, Parametres.class);
					startActivity(intentParametres);
					break;
				case 3:
					Intent intentApropos = new Intent(HomeActivity.this, A_propos.class);
					startActivity(intentApropos);
					break;
				case 4:
					Intent intentMention = new Intent(HomeActivity.this, MentionsLegales.class);
					startActivity(intentMention);
					break;
				case 5:
					Intent intentAide = new Intent(HomeActivity.this, Aide.class);
					startActivity(intentAide);
					break;
				default:
					mDrawerLayout.closeDrawer(mDrawerList);
					
			}
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		this.mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter{
		String[] myTabList;

		public AppSectionsPagerAdapter(FragmentManager fm, String[] tabList) {
			super(fm);
			this.myTabList = tabList;
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:

				return new ResumeSectionFragment();
			case 1:
				return new OperationSectionFragment();
			case 2:
				return new BudgetSectionFragment();

			default:
				// The other sections of the app are dummy placeholders.
				Fragment fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			int total = this.myTabList.length;
			return total;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String myTitle = this.myTabList[(position)];
			return myTitle;
		}
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */
	public static class ResumeSectionFragment extends Fragment {

		private ListView mListBudget, legendListView;
		private BudgetDAO budDAO = null;
		private OperationDAO opeDAO = null;
		private CategoryDAO catDAO = null;
		private List<String> listBudgetsDesc = null;
		private List<Long> listBudgetsId = null;
		private List<Budget> listBudgets = null;
		private double totalAmount;
		private List<String> categorList;
		private List<String> colorId;
		
		private PieGraph pg;

		String[] budgetText = {"ll n'y a aucun budget"} ;

		private Integer[] iconBudget = {R.drawable.money};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			
			budDAO = new BudgetDAO(container.getContext());
			try {
				listBudgets = budDAO.selectionnerAll();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			listBudgetsDesc = new ArrayList<String>();
			listBudgetsId = new ArrayList<Long>();
			Budget budgetSelected = new Budget();
			if (listBudgets.size() > 0) {
				int i = 0;
				for(Budget budget : listBudgets) {
					listBudgetsDesc.add("Budget : "+budget.getDescription());
					listBudgetsId.add(budget.getId_budget());
					if (i == 0)
						budgetSelected = budget;
					i++;
				}
			}else{
				listBudgetsDesc.add("Il n'y a rien à afficher");
			}
			Bundle args = getArguments();
			View rootView = inflater.inflate(R.layout.fragment_section_resume,
					container, false);
			
			this.mListBudget = (ListView) rootView.findViewById(R.id.listViewBudget);
			//CustomList adapterBudget = new CustomList(getActivity(), budgetText, iconBudget);
			CustomListBis adapterBudget = new CustomListBis(getActivity(), listBudgetsDesc, iconBudget);
			this.mListBudget.setAdapter(adapterBudget);
			
			this.mListBudget.setOnItemClickListener(listBudgetListener);

			opeDAO = new OperationDAO(getActivity());
			List<Operation> listOpes = new ArrayList<Operation>();
			try {
				listOpes = opeDAO.selectionnerParBudget(budgetSelected.getId_budget());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			HashMap<String, Double> catesAndAmounts = new HashMap<String, Double>();
			this.categorList = new ArrayList<String>();
			List<String> categories = new ArrayList<String>();
			List<Double> amountByCategories = new ArrayList<Double>();
			totalAmount = 0;
			for(Operation ope : listOpes) {
				System.out.println("Debug 3 : "+ope.getCategory().getId_category());
				catDAO = new CategoryDAO(getActivity());
				ope.setCategory(catDAO.selectionner(ope.getCategory().getId_category()));
				totalAmount += ope.getAmount();
				
				if (categories.size() == 0) {
					System.out.println(ope.getCategory().getDescription());
					categories.add(ope.getCategory().getDescription());
					amountByCategories.add(ope.getAmount());
					this.categorList.add(ope.getCategory().getDescription());
				} else {
					if (categories.contains(ope.getCategory().getDescription())) {
						System.out.println("value index of  : "+categories.indexOf(String.valueOf(ope.getCategory().getDescription())));

						categorList.add(ope.getCategory().getDescription());
						System.out.println("value double : "+Double.valueOf(amountByCategories.get(categories.indexOf(String.valueOf(ope.getCategory().getDescription())))));
						amountByCategories.set(
								categories.indexOf(
									String.valueOf(
										ope.getCategory().getDescription()
										)
									),
									Double.valueOf(
										amountByCategories.get(
											categories.indexOf(
												String.valueOf(
													ope.getCategory().getDescription()
												)
											)
										)+ope.getAmount()
									)
								);
					} else {
						categories.add(ope.getCategory().getDescription());
						this.categorList.add(ope.getCategory().getDescription());
						amountByCategories.add(ope.getAmount());
					}
				}
			}
			
			pg = (PieGraph) rootView.findViewById(R.id.graph);
			this.colorId = new ArrayList<String>();
			if (categories.size() > 0) {
				int j = 0;
				
				for(String cat : categories) {
					System.out.println(cat+" : "+amountByCategories.get(categories.indexOf(cat)));
					PieSlice slice = new PieSlice();
					String color = ObjectModel.generate(6);
					slice.setColor(Color.parseColor("#"+color));
					colorId.add("#" + color.toString());
					slice.setValue(2);
					slice.setGoalValue(Float.valueOf(String.valueOf(amountByCategories.get(categories.indexOf(cat)))));
					pg.addSlice(slice);
				}
			}
			pg.setInnerCircleRatio(150);
			pg.setPadding(2);
			pg.setDuration(3000);// default if unspecified is 300 ms
			pg.setInterpolator(new AccelerateDecelerateInterpolator());
			pg.animateToGoalValues();
			
			((TextView) rootView.findViewById(R.id.montantRestantText))
			.setText(getString(R.string.resume_reamining, (double) (budgetSelected.getAmount()-totalAmount)));

			
			((TextView) rootView.findViewById(R.id.montantText2))
			.setText(getString(R.string.resume_total, (double) budgetSelected.getAmount()));
			this.legendListView = (ListView) rootView.findViewById(R.id.legende);
			LegendeListAdaptater adapterLegende = new LegendeListAdaptater(getActivity(), categorList, colorId);
			this.legendListView.setAdapter(adapterLegende);
			return rootView;
		}
		
		private OnItemClickListener listBudgetListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				List<String> myColorId = new ArrayList<String>();
				List<String> mycategoryList = new ArrayList<String>();
				//Toast.makeText(getActivity(), String.valueOf(listBudgetsId.get(position)), Toast.LENGTH_SHORT).show();
				opeDAO = new OperationDAO(getActivity());
				List<Operation> listOpes = new ArrayList<Operation>();
				Budget budgetSelected = budDAO.selectionner(listBudgetsId.get(position));
				try {
					listOpes = opeDAO.selectionnerParBudget(listBudgetsId.get(position));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				HashMap<String, Double> catesAndAmounts = new HashMap<String, Double>();
				List<String> categories = new ArrayList<String>();
				List<Double> amountByCategories = new ArrayList<Double>();
				totalAmount = 0;
				for(Operation ope : listOpes) {
					catDAO = new CategoryDAO(getActivity());
					ope.setCategory(catDAO.selectionner(ope.getCategory().getId_category()));
					totalAmount += ope.getAmount();
					
					if (categories.size() == 0) {
						System.out.println("Debug : "+ope.getCategory().getDescription());
						categories.add(ope.getCategory().getDescription());
						mycategoryList.add(ope.getCategory().getDescription());
						amountByCategories.add(ope.getAmount());
					} else {
						if (categories.contains(ope.getCategory().getDescription())) {
							System.out.println("value index of  : "+categories.indexOf(String.valueOf(ope.getCategory().getDescription())));
							System.out.println("value double : "+Double.valueOf(amountByCategories.get(categories.indexOf(String.valueOf(ope.getCategory().getDescription())))));
							mycategoryList.add(ope.getCategory().getDescription());
							amountByCategories.set(
									categories.indexOf(
										String.valueOf(
											ope.getCategory().getDescription()
											)
										),
										Double.valueOf(
											amountByCategories.get(
												categories.indexOf(
													String.valueOf(
														ope.getCategory().getDescription()
													)
												)
											)+ope.getAmount()
										)
									);
						} else {
							categories.add(ope.getCategory().getDescription());
							amountByCategories.add(ope.getAmount());
							mycategoryList.add(ope.getCategory().getDescription());
						}

					}
				}
				
//				PieGraph pg = (PieGraph) rootView.findViewById(R.id.graph);
				pg.removeSlices();

				if (categories.size() > 0) {
					int j = 0;
					
					for(String cat : categories) {
						System.out.println(cat+" : "+amountByCategories.get(categories.indexOf(cat)));
						PieSlice slice = new PieSlice();
						String color = ObjectModel.generate(6);
						slice.setColor(Color.parseColor("#"+color));
						myColorId.add("#" + color.toString());
						slice.setValue(2);
						slice.setGoalValue(Float.valueOf(String.valueOf(amountByCategories.get(categories.indexOf(cat)))));
						pg.addSlice(slice);
					}
				}

				pg.setInnerCircleRatio(150);
				pg.setPadding(2);
				
				pg.setDuration(3000);// default if unspecified is 300 ms
				pg.setInterpolator(new AccelerateDecelerateInterpolator());
				pg.animateToGoalValues();
				((TextView) getActivity().findViewById(R.id.montantRestantText))
				.setText(getString(R.string.resume_reamining, (double) (budgetSelected.getAmount()-totalAmount)));

				
				((TextView) getActivity().findViewById(R.id.montantText2))
				.setText(getString(R.string.resume_total, (double) budgetSelected.getAmount()));

				LegendeListAdaptater adapterLegende = new LegendeListAdaptater(getActivity(), mycategoryList, myColorId);
				//((ListView) getActivity().findViewById(R.id.legende)).setAdapter(null);
				((ListView) getActivity().findViewById(R.id.legende)).setAdapter(adapterLegende);
			}
			
		};
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class OperationSectionFragment extends Fragment {

		private TextView remainAmount = null;
		private List<Budget> listBudgets = null;
		private Button 	addOperationBtn;
		private Button changOperationBtn;
		private List<Operation> operations = null;
		private OperationDAO opDao = null ;
		private BudgetDAO budDAO = null;
		private CategoryDAO catDAO = null;
		private RecurrenceDAO recDAO = null;
		private ListView listView = null;
		private Budget budgetDisplayed = null;
		private List<Operation> listOperations = null;
		private ListView mListBudget;
		private List<Long> operationsIds = null;
		private double remainingAmount = 0;
		private Spinner spinnerBudget;

		private Integer[] iconBudget = {R.drawable.money};

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_operations,
					container, false);
			Bundle args = getArguments();

			remainAmount = (TextView) rootView.findViewById(R.id.montantRestantTextOperation);
			// Get DAO for budget and get all budgets
			budDAO = new BudgetDAO(getActivity().getApplicationContext());
			try {
				listBudgets = budDAO.selectionnerAll();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final List<Long> budgetsIds = new ArrayList<Long>();
			List<String> budgetsString = new ArrayList<String>();
			if (listBudgets.size() > 0) {
				for (Budget bud : listBudgets) {
					budgetsString.add(bud.getDescription());
					budgetsIds.add(bud.getId_budget());
				}
			} else {
				
				budgetsString.add("Ajouter un budget");
				budgetsIds.add((long)0);
			}
			budgetDisplayed = new Budget();
			spinnerBudget = (Spinner)rootView.findViewById(R.id.operationsSpinnerHomeActivity);
			
			ArrayAdapter<String> adapterBudgets = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, budgetsString);
			// Specify the layout to use when the list of choices appears
			adapterBudgets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);			
//			Toast.makeText(getActivity().getApplicationContext(), String.valueOf(adapterBudgets.getCount()), Toast.LENGTH_LONG).show();
			// Apply the adapter to the spinner
			spinnerBudget.setAdapter(adapterBudgets);
			
			spinnerBudget.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
//					Toast.makeText(getActivity(), String.valueOf(budgetsIds.get((int)spinnerBudget.getSelectedItemId())), Toast.LENGTH_LONG).show();
					opDao = new OperationDAO(getActivity().getApplicationContext());
					listOperations = new ArrayList<Operation>();
					if (budgetsIds.get((int)spinnerBudget.getSelectedItemId()) != 0) {
						try {
							System.out.println("Debug 2 : "+spinnerBudget.getSelectedItemId());
							listOperations = opDao.selectionnerParBudget(budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
	//						System.out.println(operations);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						
					}
					
					ArrayList<String> operationsString = new ArrayList<String>();
					operationsIds = new ArrayList<Long>();
					budDAO = new BudgetDAO(getActivity());
					if (budgetsIds.get((int)spinnerBudget.getSelectedItemId()) != 0) {
						budgetDisplayed = budDAO.selectionner(budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
					} else {
						budgetDisplayed = new Budget();
					}
					Toast.makeText(getActivity(), String.valueOf(budgetDisplayed.getDescription()), Toast.LENGTH_LONG).show();
					remainingAmount = budgetDisplayed.getAmount();
					if (listOperations.size() > 0) {
				        for(Operation op : listOperations) {
				        	budDAO = new BudgetDAO(getActivity().getApplicationContext());
				        	op.setBudget(budDAO.selectionner(op.getBudget().getId_budget()));
				        	catDAO = new CategoryDAO(getActivity().getApplicationContext());
				        	op.setCategory(catDAO.selectionner(op.getCategory().getId_category()));
				        	recDAO = new RecurrenceDAO(getActivity().getApplicationContext());
				        	op.setRecurrence(recDAO.selectionner(op.getRecurrence().getId_recurrence()));
//				        	Toast.makeText(getActivity(), String.valueOf(op.getType()), Toast.LENGTH_SHORT).show();
				        	if (op.getType().matches("Revenu"))
				        		remainingAmount += op.getAmount();
				        	else
				        		remainingAmount -= op.getAmount();
				        	operationsString.add("€ - "+op.getDescription());
				        	operationsIds.add(op.getId_operation());
				        }
			        } else {
		        		operationsString.add("Il n'existe aucune opération pour ce budget.");
			        }
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, operationsString);
					listView.setAdapter(adapter);
					remainAmount.setText(getString(R.string.operation_reamining, remainingAmount));
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
				
			});
			
			
			// Get ListView object from xml
			listView = (ListView) rootView.findViewById(R.id.listViewOperation);

			// Defined Array values to show in ListView
			String[] values = new String[] { "Aucunr données"};
			opDao = new OperationDAO(rootView.getContext());
			try {
				operations = opDao.selectionnerAll();
//				System.out.println(operations);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<String> operationsString = new ArrayList<String>();
			operationsIds = new ArrayList<Long>();
	        
			if (operations.size() > 0) {
		        for(Operation op : operations) {
		        	budDAO = new BudgetDAO(rootView.getContext());
		        	op.setBudget(budDAO.selectionner(op.getBudget().getId_budget()));
		        	catDAO = new CategoryDAO(rootView.getContext());
		        	op.setCategory(catDAO.selectionner(op.getCategory().getId_category()));
		        	recDAO = new RecurrenceDAO(rootView.getContext());
		        	op.setRecurrence(recDAO.selectionner(op.getRecurrence().getId_recurrence()));
		        	operationsString.add(op.getDescription());
		        	operationsIds.add(op.getId_operation());
		        }
	        } else {
	        	for(String val : values) {
	        		operationsString.add(val);
	        		//operationsIds.add(val);
	        	}
	        	
	        }

			// Define a new Adapter
			// First parameter - Context
			// Second parameter - Layout for the row
			// Third parameter - ID of the TextView to which the data is written
			// Forth - the Array of data
//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.id.text1, values);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, operationsString);


			// Assign adapter to ListView
			listView.setAdapter(adapter); 

			// ListView Item Click Listener
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					// ListView Clicked item index
					int itemPosition     = position;

					// ListView Clicked item value
					String  itemValue    = (String) listView.getItemAtPosition(position);
					
					Intent unIntent = new Intent(getActivity().getApplicationContext(), ModifierOperation.class);
					//Long id_operation = operationsIds.get(getId());
					//unIntent.getLongExtra("id_operation",id_operation);
					//System.out.println("id_operation "+ operationsIds.get(getId()));
				//	System.out.println("getId "+ operationsIds.get(itemPosition));
//					System.out.println("itemvalue : "+itemValue);
//					System.out.println("itemPosition : "+itemPosition);
					long id_operation = operationsIds.get((int)itemPosition);
					unIntent.putExtra("id_operation",id_operation);
					startActivity(unIntent);
//					Toast.makeText(getActivity(), String.valueOf(id_operation), Toast.LENGTH_LONG).show();
				}

			});

			this.addOperationBtn = (Button) rootView.findViewById(R.id.addOperation);
			this.addOperationBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					Toast.makeText(getActivity().getApplicationContext(), "Hey !!  you touch me :D Add something" , Toast.LENGTH_LONG).show();
					Intent unIntent = new Intent(getActivity(), AjoutOperation.class);
					unIntent.putExtra("Id_budget", budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
					startActivity(unIntent);
				}
			});

			this.changOperationBtn = (Button) rootView.findViewById(R.id.changeOperation);
			this.changOperationBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					Toast.makeText(getActivity().getApplicationContext(),
//							"Buddah was a black dude" , Toast.LENGTH_LONG)
//							.show();
					
				}
			});
			return rootView;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class BudgetSectionFragment extends Fragment {

		private TextView remainAmount = null;
		private ListView mListBudget;
		private BudgetDAO budDAO = null;
		private OperationDAO opeDAO = null;
		private CategoryDAO catDAO = null;
		private RecurrenceDAO recDAO = null;
		private Budget budgetDisplayed = null;
		private List<String> listBudgetsDesc = null;
		private List<Budget> listBudgets = null;
		private List<Operation> listOperations = null;
		private Spinner spinnerBudget;
		
		private double remainingAmount = 0;
		
		private Button 	addBudgetBtn;
		private Button changeBudgetBtn;

		String[] budgetText = {"Aucune"} ;

		private Integer[] iconBudget = {
				R.drawable.info,
				R.drawable.calendar,
				R.drawable.calendar,
				R.drawable.undo,
				R.drawable.eur,
				R.drawable.eur,
		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_budget, container, false);
			//Bundle args = getArguments();

			remainAmount = (TextView) rootView.findViewById(R.id.budgetRestantText);
			// Get DAO for budget and get all budgets
			budDAO = new BudgetDAO(getActivity().getApplicationContext());
			try {
				listBudgets = budDAO.selectionnerAll();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final List<Long> budgetsIds = new ArrayList<Long>();
			List<String> budgetsString = new ArrayList<String>();
			if (listBudgets.size() > 0) {
				for (Budget bud : listBudgets) {
					budgetsString.add(bud.getDescription());
					budgetsIds.add(bud.getId_budget());
				}
			} else {
				
				budgetsString.add("Ajouter un budget");
				budgetsIds.add((long)0);
			}
			budgetDisplayed = new Budget();
			spinnerBudget = (Spinner)rootView.findViewById(R.id.budgetsSpinnerHomeActivity);
			spinnerBudget.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
//					Toast.makeText(getActivity(), String.valueOf(budgetsIds.get((int)spinnerBudget.getSelectedItemId())), Toast.LENGTH_LONG).show();
					opeDAO = new OperationDAO(getActivity().getApplicationContext());
					listOperations = new ArrayList<Operation>();
					if (budgetsIds.get((int)spinnerBudget.getSelectedItemId()) != 0) {
						try {
							listOperations = opeDAO.selectionnerParBudget(budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
	//						System.out.println(operations);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						
					ArrayList<String> operationsString = new ArrayList<String>();
					budDAO = new BudgetDAO(getActivity());
					if (budgetsIds.get((int)spinnerBudget.getSelectedItemId()) != 0) {
						budgetDisplayed = budDAO.selectionner(budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
						recDAO = new RecurrenceDAO(getActivity());
						budgetDisplayed.setRecurrence(recDAO.selectionner(budgetDisplayed.getRecurrence().getId_recurrence()));
						
						operationsString.add("Désignation : "+budgetDisplayed.getDescription());
						operationsString.add("Date de début : "+Budget.formatDate(budgetDisplayed.getDateBegin(),false));
						operationsString.add("Date de fin : "+Budget.formatDate(budgetDisplayed.getDateEnd(),false));
	//					System.out.println("Recurrence dans budget : "+budgetDisplayed.getRecurrence().getId_recurrence());
						if (budgetDisplayed.getRecurrence().getId_recurrence() != 0)
							operationsString.add("Récurrence : "+budgetDisplayed.getRecurrence().getDescription());
						else
							operationsString.add("Récurrence : Aucune");
						operationsString.add("Montant d'origine : "+budgetDisplayed.getAmount()+ " €");
						
	//					Toast.makeText(getActivity(), String.valueOf(budgetDisplayed.getAmount()), Toast.LENGTH_LONG).show();
						remainingAmount = budgetDisplayed.getAmount();
					} else {
						remainingAmount = 0;
					}
					if (listOperations.size() > 0) {
				        for(Operation op : listOperations) {
//				        	budDAO = new BudgetDAO(getActivity().getApplicationContext());
//				        	op.setBudget(budDAO.selectionner(op.getBudget().getId_budget()));
//				        	catDAO = new CategoryDAO(getActivity().getApplicationContext());
//				        	op.setCategory(catDAO.selectionner(op.getCategory().getId_category()));
//				        	recDAO = new RecurrenceDAO(getActivity().getApplicationContext());
//				        	op.setRecurrence(recDAO.selectionner(op.getRecurrence().getId_recurrence()));
//				        	Toast.makeText(getActivity(), String.valueOf(op.getType()), Toast.LENGTH_SHORT).show();
				        	if (op.getType().matches("Revenu"))
				        		remainingAmount += op.getAmount();
				        	else
				        		remainingAmount -= op.getAmount();
//				        	operationsString.add(op.getDescription());
				        }
			        } else {
//		        		operationsString.add("Il n'existe aucune opération pour ce budget.");
			        }
					operationsString.add("Montant restant : "+remainingAmount+ " €");
					String[] stringArray = operationsString.toArray(new String[operationsString.size()]);
					CustomList adapterBudget = new CustomList(getActivity(), stringArray, iconBudget);
					//CustomListBis adapterBudget = new CustomListBis(getActivity(), operationsString, iconBudget);
					mListBudget.setAdapter(adapterBudget);
					remainAmount.setText(getString(R.string.resume_reamining, remainingAmount));
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
				
			});
			
			// Create an ArrayAdapter using the string array and a default spinner layout
			ArrayAdapter<String> adapterBudgets = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, budgetsString);
			// Specify the layout to use when the list of choices appears
			adapterBudgets.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);			
//			Toast.makeText(getActivity().getApplicationContext(), String.valueOf(adapterBudgets.getCount()), Toast.LENGTH_LONG).show();
			// Apply the adapter to the spinner
			spinnerBudget.setAdapter(adapterBudgets);
			
			
//			Toast.makeText(getActivity().getApplicationContext(), String.valueOf(budgetsIds.get((int)spinnerBudget.getSelectedItemId())), Toast.LENGTH_LONG).show();

			opeDAO = new OperationDAO(rootView.getContext());
			listOperations = new ArrayList<Operation>();
			if (budgetsIds.get((int)spinnerBudget.getSelectedItemId()) != 0) {
				try {
					listOperations = opeDAO.selectionnerParBudget(budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
	//				System.out.println(operations);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ArrayList<String> operationsString = new ArrayList<String>();
			budDAO = new BudgetDAO(getActivity());
			if (budgetsIds.get((int)spinnerBudget.getSelectedItemId()) != 0) {
				budgetDisplayed = budDAO.selectionner(budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
				operationsString.add("Désignation : "+budgetDisplayed.getDescription());
				operationsString.add("Date de début : "+Budget.formatDate(budgetDisplayed.getDateBegin(),false));
				operationsString.add("Date de fin : "+Budget.formatDate(budgetDisplayed.getDateEnd(),false));
	//			System.out.println("Recurrence dans budget : "+budgetDisplayed.getRecurrence().getId_recurrence());
				if (budgetDisplayed.getRecurrence() != null)
					operationsString.add("Récurrence : "+budgetDisplayed.getRecurrence().getDescription());
				else
					operationsString.add("Récurrence : Aucune");
				operationsString.add("Montant d'origine : "+budgetDisplayed.getAmount()+ " €");
				
	//			Toast.makeText(getActivity(), String.valueOf(budgetDisplayed.getAmount()), Toast.LENGTH_LONG).show();
				remainingAmount = budgetDisplayed.getAmount();
			} else {
				remainingAmount = 0;
			}
			
			if (listOperations.size() > 0) {
		        for(Operation op : listOperations) {
//		        	budDAO = new BudgetDAO(getActivity().getApplicationContext());
//		        	op.setBudget(budDAO.selectionner(op.getBudget().getId_budget()));
//		        	catDAO = new CategoryDAO(getActivity().getApplicationContext());
//		        	op.setCategory(catDAO.selectionner(op.getCategory().getId_category()));
//		        	recDAO = new RecurrenceDAO(getActivity().getApplicationContext());
//		        	op.setRecurrence(recDAO.selectionner(op.getRecurrence().getId_recurrence()));
//		        	Toast.makeText(getActivity(), String.valueOf(op.getType()), Toast.LENGTH_SHORT).show();
		        	if (op.getType().matches("Revenu"))
		        		remainingAmount += op.getAmount();
		        	else
		        		remainingAmount -= op.getAmount();
//		        	operationsString.add(op.getDescription());
		        }
	        } else {
//        		operationsString.add("Il n'existe aucune opération pour ce budget.");
	        }
			operationsString.add("Montant restant : "+remainingAmount+ " €");
			
			this.mListBudget = (ListView) rootView.findViewById(R.id.listViewBudget);
			
			String[] stringArray = operationsString.toArray(new String[operationsString.size()]);
			CustomList adapterBudget = new CustomList(getActivity(), stringArray, iconBudget);
			//CustomListBis adapterBudget = new CustomListBis(getActivity(), operationsString, iconBudget);
			mListBudget.setAdapter(adapterBudget);
			remainAmount.setText(getString(R.string.resume_reamining, remainingAmount));
			
//			CustomList adapterBudget = new CustomList(getActivity(), budgetText, iconBudget);
//			CustomListBis adapterBudget = new CustomListBis(getActivity(), operationsString, iconBudget);
//			this.mListBudget.setAdapter(adapterBudget);

//			remainAmount.setText(getString(R.string.resume_reamining, remainingAmount));
			
			this.addBudgetBtn = (Button) rootView.findViewById(R.id.addBudgetBtn);
			this.addBudgetBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					Toast.makeText(getActivity().getApplicationContext(), "Hey !!  you touch me :D Add something" , Toast.LENGTH_LONG).show();
					Intent unIntent = new Intent(getActivity(), BudgetAdd.class);
					startActivity(unIntent);
				}
			});

			this.changeBudgetBtn = (Button) rootView.findViewById(R.id.changeBudgetBtn);
			this.changeBudgetBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
//					Toast.makeText(getActivity().getApplicationContext(), "Buddah was a black dude" , Toast.LENGTH_LONG).show();
					Intent getBudgetDetails = new Intent(getActivity(), BudgetDetails.class);
//					long id_budget = 1;
					getBudgetDetails.putExtra("Id_budget", budgetsIds.get((int)spinnerBudget.getSelectedItemId()));
					startActivity(getBudgetDetails);
				}
			});
			
			
			
			
			return rootView;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_dummy,
					container, false);
			Bundle args = getArguments();
			((TextView) rootView.findViewById(android.R.id.text1))
			.setText(getString(R.string.dummy_section_text,
					args.getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
