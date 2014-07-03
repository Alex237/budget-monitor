package com.example.asie_ici;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	//private ArticleService articleService = new ArticleServiceImp();
	private Button ajouter;
	//private Button total;
	private ListView malisteArticle;
	private Context ct;
	public static final String IP = "http://192.168.1.95/";

	ArrayList<Articles> donnees = new ArrayList<Articles>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//correspondance objets avec les widgets
		this.ajouter = (Button)this.findViewById(R.id.ajouter);
		//this.total = (Button)this.findViewById(R.id.totalFinal);
		this.ct = getApplicationContext();
		
		//cr�ation listView
    	this.malisteArticle = (ListView)findViewById(R.id.malisteArticle);
		 
		//rendre les boutons ecoutables
        this.ajouter.setOnClickListener(this);
       // this.total.setOnClickListener(this);
       
    	
    	ArticlesConnexion mabdd = new ArticlesConnexion(getApplicationContext(), this);
    	mabdd.execute();
    	
    	malisteArticle.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    			long id) {
    			// Ton traitement souhait� lors du click,
    	        // la variable position est en fait l'indice de l'item cliqu� 
    	        //� partir de l� tu peux r�cup�rer l'objet souhait� de ton tableau

    			Intent unIntent = new Intent(ct, GestionArticles.class);
    			//on r�cup�re donc l'id de l'article qui est dans le layout itemarticle
    			unIntent.putExtra("id", ((TextView)view.findViewById(R.id.articleId)).getText().toString());
    			Log.i("info", ((TextView)view.findViewById(R.id.articleId)).getText().toString());
    			startActivity(unIntent);
    			}

    	});
    /*
    		 		malisteArticle.setOnItemLongClickListener(new OnItemLongClickListener() {
    	 
    				public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
    		    			long id) {
    				// Je  recupere l'element sur laquelle on a fait un appui long:
    				Intent unIntent = new Intent(ct, MainActivity.class);
    				unIntent.putExtra("id", ((TextView)view.findViewById(R.id.articleId)).getText().toString());
    				String id1 = getIntent().getExtras().getString("id");
    				Articles article = receiveResult(Integer.parseInt(id1));
    		      	
    		      	startActivity(unIntent);
    				
    		      	
    					return true;
    	 
    				}
    	 
    	 
    				
    			}); */
      	
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
			if(id == R.id.ajouter){
				//appel de l'activity Sauvegarde
				Intent  unIntent = new Intent(ct, AjoutArticles.class);
				this.startActivity(unIntent);
			}
			/*if(id == R.id.totalFinal){
				Intent unIntent = new Intent(ct, Total.class);
				this.startActivity(unIntent);
			}
			*/
			
	}
	
	
	public void setResultGrid(ArrayList<Articles> array) {
		// TODO Auto-generated method stub
		Log.i("info", "donnees n'est pas null "+ donnees.size());
		malisteArticle = (ListView) findViewById(R.id.malisteArticle);
		if (donnees != null){
			//une fois r�cup�r�e les informations de la BDD on les affiche gr�ce � l'adaptateur
			//malisteArticle.setAdapter(new ArticleAdapter(this, donnees));
			ArticleAdapter adapter = new ArticleAdapter(this, array);
	    	malisteArticle.setAdapter(adapter);
		}
	}

}

	

