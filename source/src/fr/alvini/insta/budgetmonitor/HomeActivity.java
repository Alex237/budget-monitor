package fr.alvini.insta.budgetmonitor;

import fr.alvini.insta.budgetmonitor.activities.AjoutBudget;
import fr.alvini.insta.budgetmonitor.activities.AjoutOperation;
import fr.alvini.insta.budgetmonitor.adaptater.CustomList;
import fr.alvini.insta.holographlib.PieGraph;
import fr.alvini.insta.holographlib.PieSlice;
import android.os.Bundle;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fr.alvini.insta.budgetmonitor.tests.*;
import fr.alvini.insta.holographlib.PieGraph;
import fr.alvini.insta.holographlib.PieSlice;

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
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
				// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// selectItem(0);
		}

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

	// Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du
	// téléphone
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Création d'un MenuInflater qui va permettre d'instancier un Menu XML
		// en un objet Menu
		MenuInflater inflater = getMenuInflater();
		// Instanciation du menu XML spécifier en un objet Menu
		inflater.inflate(R.menu.activity_menu, menu);

		// Il n'est pas possible de modifier l'icône d'entête du sous-menu via
		// le fichier XML on le fait donc en JAVA
//		 menu.getItem(0).getSubMenu().setHeaderIcon(R.drawable.option_white);

		return true;
	}

	// Méthode qui se déclenchera au clic sur un item
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// On regarde quel item a été cliqué grâce à son id et on déclenche une action
		switch (item.getItemId()) {
		case R.id.parametres:
			Toast.makeText(HomeActivity.this, "Paramètres ", Toast.LENGTH_SHORT)
			.show();
		case R.id.mentionsLegales:
			Toast.makeText(HomeActivity.this, "Mentions Légales",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.aPropos:
			Toast.makeText(HomeActivity.this, "à Propos", Toast.LENGTH_SHORT)
			.show();
			// For testing
			Intent testIntent = new Intent(HomeActivity.this,TestChooseActivity.class);
			startActivity(testIntent);
			// End for testing
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
//		return true;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
	ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mDrawerLayout.closeDrawer(mDrawerList);
			// selectItem(position);
			Toast.makeText(HomeActivity.this,
					"Drawer item : " + position + " | ID : " + id + "\n " + mMenuTitles1[position],
					Toast.LENGTH_SHORT).show();
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

		private ListView mListBudget;

		String[] budgetText = {
				"Budget de Janvier",
				"Some one is dead x_X",
				"Buddah kill him",
				"I don't know why...",
				"Budget de Mais",
				"Budget de Juin",
				"Budget de Juillet",
		} ;

		Double[] prix = {
				1200.00,
				1100.50,
				1500.08,
				2000.44,
				800.34,
				2230.50,
				1321.46
		};

		private Integer[] iconBudget = {
				R.drawable.money,
				R.drawable.ambulance,
				R.drawable.money,
				R.drawable.money,
				R.drawable.money,
				R.drawable.money,
				R.drawable.money,
		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_section_resume,
					container, false);
			this.mListBudget = (ListView) rootView.findViewById(R.id.listViewBudget);
			CustomList adapterBudget = new
					CustomList(getActivity(), budgetText, iconBudget);
			this.mListBudget.setAdapter(adapterBudget);

			PieGraph pg = (PieGraph) rootView.findViewById(R.id.graph);

			PieSlice slice = new PieSlice();
			slice.setColor(Color.parseColor("#5A90BE"));
			slice.setValue(2);
			slice.setGoalValue((float) 50);
			pg.addSlice(slice);

			slice = new PieSlice();
			slice.setColor(Color.parseColor("#B58EAD"));
			slice.setValue(2);
			slice.setGoalValue((float) 10);
			pg.addSlice(slice);

			slice = new PieSlice();
			slice.setColor(Color.parseColor("#D1866F"));
			slice.setValue(2);
			slice.setGoalValue((float) 10);
			pg.addSlice(slice);

			slice = new PieSlice();
			slice.setColor(Color.parseColor("#AA7968"));
			slice.setValue(2);
			slice.setGoalValue((float) 10);
			pg.addSlice(slice);

			slice = new PieSlice();
			slice.setColor(Color.parseColor("#A2BE8D"));
			slice.setValue(2);
			slice.setGoalValue((float) 10);
			pg.addSlice(slice);

			slice = new PieSlice();
			slice.setColor(Color.parseColor("#96B6B3"));
			slice.setValue(2);
			slice.setGoalValue((float) 10);
			pg.addSlice(slice);

			pg.setInnerCircleRatio(150);
			pg.setPadding(2);

			pg.setDuration(2000);// default if unspecified is 300 ms
			pg.setInterpolator(new AccelerateDecelerateInterpolator());
			pg.animateToGoalValues();

			/* Demonstration of a collection-browsing activity.
			rootView.findViewById(R.id.resume).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							// Intent intent = new Intent(private,
							// OperationActivity.class);
							// startActivity(intent);
						}
					});

			// Demonstration of navigating to external activities.
			rootView.findViewById(R.id.operation).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							// Create an intent that asks the user to pick a
							// photo, but using
							// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that
							// relaunching
							// the application from the device home screen does
							// not return
							// to the external activity.
							Intent externalActivityIntent = new Intent(
									Intent.ACTION_PICK);
							externalActivityIntent.setType("image/*");
							externalActivityIntent
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(externalActivityIntent);
						}
					});
			 */
			return rootView;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class OperationSectionFragment extends Fragment {

		private Button 	addOperationBtn;
		private Button changOperationBtn;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_operations,
					container, false);
			Bundle args = getArguments();

			// Get ListView object from xml
			final ListView listView = (ListView) rootView.findViewById(R.id.listViewOperation);

			// Defined Array values to show in ListView
			String[] values = new String[] { "Menace de Black Buddah", 
					"Rancon de Rasta Buddah",
					"Recidive de Buddah",
					"Corruption des moines tibetins", 
					"Fake Example", 
					"Fake fake fake", 
					"Rasta Buddah Destiny", 
					"Black Buddah Power !" 
			};

			// Define a new Adapter
			// First parameter - Context
			// Second parameter - Layout for the row
			// Third parameter - ID of the TextView to which the data is written
			// Forth - the Array of data
			//ArrayAdapter<String> adaptater = new ArrayAdapter<String>(getActivity(), android.R.id.text1, values);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, android.R.id.text1, values);


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

					// Show Alert 
					Toast.makeText(getActivity(),
							"Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
							.show();

				}

			});

			this.addOperationBtn = (Button) rootView.findViewById(R.id.addOperation);
			this.addOperationBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Hey !!  you touch me :D Add something" , Toast.LENGTH_LONG)
							.show();
					Intent unIntent = new Intent(getActivity(), AjoutOperation.class);
					startActivity(unIntent);
				}
			});

			this.changOperationBtn = (Button) rootView.findViewById(R.id.changeOperation);
			this.changOperationBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Buddah was a black dude" , Toast.LENGTH_LONG)
							.show();
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

		private Button 	addBudgetBtn;
		private Button changeBudgetBtn;


		private ListView mListBudget;

		String[] budgetText = {
				"Budget de Janvier",
				"Some one is dead x_X",
				"Buddah kill him",
				"I don't know why...",
				"Budget de Mais",
				"Budget de Juin",
				"Budget de Juillet",
		} ;

		Double[] prix = {
				1200.00,
				1100.50,
				1500.08,
				2000.44,
				800.34,
				2230.50,
				1321.46
		};

		private Integer[] iconBudget = {
				R.drawable.money,
				R.drawable.ambulance,
				R.drawable.money,
				R.drawable.money,
				R.drawable.money,
				R.drawable.money,
				R.drawable.money,
		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_budget, container, false);
			Bundle args = getArguments();

			this.mListBudget = (ListView) rootView.findViewById(R.id.listViewBudget);
			CustomList adapterBudget = new
					CustomList(getActivity(), budgetText, iconBudget);
			this.mListBudget.setAdapter(adapterBudget);

			this.addBudgetBtn = (Button) rootView.findViewById(R.id.addBudgetBtn);
			this.addBudgetBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Hey !!  you touch me :D Add something" , Toast.LENGTH_LONG)
							.show();
					Intent unIntent = new Intent(getActivity(), AjoutBudget.class);
					startActivity(unIntent);
				}
			});

			this.changeBudgetBtn = (Button) rootView.findViewById(R.id.changeBudgetBtn);
			this.changeBudgetBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Buddah was a black dude" , Toast.LENGTH_LONG)
							.show();
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
