package com.example.asie_ici;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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
import android.view.KeyCharacterMap.UnavailableException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.asie_ici.R;
import com.example.asie_ici.MainActivity;

public class GestionArticles extends Activity implements OnClickListener{
	
	private Button retour, valider, recalculer, supprimer;
	private EditText titre, prix, stock, reduction;
	private Context ct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gestion_articles);
		
		//correspondance entre les objets et les widgets
		this.retour = (Button)this.findViewById(R.id.retour);
		this.valider = (Button)this.findViewById(R.id.valider);
		this.recalculer = (Button)this.findViewById(R.id.recalculer);
		this.supprimer = (Button)this.findViewById(R.id.supprimer);
		this.titre = (EditText)this.findViewById(R.id.editnom);
		this.prix = (EditText)this.findViewById(R.id.editprix);
		this.stock = (EditText)this.findViewById(R.id.editstock);
		this.reduction = (EditText)this.findViewById(R.id.editreduction);
		this.ct = getApplicationContext();
		
		 //rendre les trois boutons ecoutables
		this.recalculer.setOnClickListener(this);
		this.valider.setOnClickListener(this);
		this.retour.setOnClickListener(this);
		this.supprimer.setOnClickListener(this);
		
	}
	@Override
	protected void onStart() {
	super.onStart();
	String id = getIntent().getExtras().getString("id");
	Log.i("info", "id");
	//on r�cup�re les informations de l'article en BDD suivant son ID
	Articles article = receiveResult(Integer.parseInt(id));
	Log.i("info", article.getTitre());
	//et on les affiche dans les EditText
	titre.setText(article.getTitre().toString());
	prix.setText(String.valueOf(article.getPrix()).toString());
	stock.setText(String.valueOf(article.getStock()).toString());
	reduction.setText(String.valueOf(article.getReduction()).toString());
	
	}
	
    public Articles receiveResult(int id) 
    {    
        Articles article = null;
       //on indique quel fichier de connexion php utiliser pour la connexion � la base de donn�es 
        StringBuilder strURLBuilder = new StringBuilder();
                strURLBuilder.append(MainActivity.IP+"connexion_mysql/connexion_mysql2.php");
        strURLBuilder.append("?").append("id=").append(id).append("&format=json");
        //on convertit tout en string
        String strURL = strURLBuilder.toString(); 
         
        InputStream is = null; 
        String result = ""; 
              //connexion � la base de donn�es
            try{ 
                HttpClient httpclient = new DefaultHttpClient(); 
              
            HttpPost httppost = new HttpPost(strURL); 
                     HttpResponse response = httpclient.execute(httppost); 
                  
                     HttpEntity entity = response.getEntity(); 
      
                     is = entity.getContent(); 
                     Log.i("info", "ici ok"); 
            } 
            catch(Exception e){ 
                System.out.println("nok ClientProtocolExcetion " + e.toString() + e.getStackTrace()); 
 
            } 
            try{ 
            		//on parcours le fichier php et on r�cup�re les informations
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8); 
                        StringBuilder sb = new StringBuilder(); 
                        String line = null; 
                        if (reader != null){ 
                        while ((line = reader.readLine()) != null) { 
                            sb.append(line + "\n"); 
                        } 
                        } 
                        is.close() ; 
                        result=sb.toString(); 
                        Log.i("info", "ici ok"); 
            }catch(Exception e){ 
                    Log.e("log_tag", "Error converting result " + e.toString()); 
                    return null; 
            } 
             try{    
                //Log.i("info", result); 
            	 //on convertit les informations r�cup�r�es du fichier php au format tableau json
                JSONArray jArray = new JSONArray(result);
            	if(jArray.length() == 1 ){
                article = new Articles();
                JSONObject jObject = jArray.getJSONObject(0); 
               // Log.i("info2", jObject.toString()); 
               //on r�cup�re les informations que l'on met sous forme d'objets json
                int Sid = jObject.getInt("id_article"); 
                String Stitre = jObject.getString("titre"); 
                int Sprix = jObject.getInt("prix"); 
                int Sstock = jObject.getInt("stock"); 
                int Sreduction = jObject.getInt("reduction"); 
                //on modifie l'article 
                article.setId(Sid); 
                article.setTitre(Stitre); 
                article.setPrix(Sprix); 
                article.setStock(Sstock); 
                article.setReduction(Sreduction); 
             
               // Log.i("info", "Stitre" + "Sprix" + "Sstock"); 
                //reduction.setText(Sreduction);    
            }else{
                article = null;
            } 
                 
            return article;
             }
             catch(Exception e){
    	    	 e.printStackTrace();
    				System.out.println("nok" + e.getMessage());
    				
    				return null;
    	     }	
    }
    
    public Articles receiveResult2(int id1, String date_suppression) 
    {    
    	//m�me chose que pour les autres
    	  Articles article = new Articles();
          ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
          //sauf qu'ici on rentre en param�tre l'id puisqu'on r�cup�re les info en BDD par rapport � un id et on y ajoute
          //une date de suppression pour pouvoir le supprimer de la liste
          nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(id1)));
         
          Log.i("id", String.valueOf(id1));
          
          String stringWithSpace = MainActivity.IP+"connexion_mysql/connexion_mysql5.php?id="+id1+"&date_suppression="+date_suppression+"&format=json";
          //on remplace les espaces dans les url comme dans le titre par exemple par des caract�res autoris�s
          String strURL = stringWithSpace.replaceAll(" ","%20");
  	      
          
          Log.i("url",strURL);
          InputStream is = null; 
          String result = ""; 
         
              
            try{ 
                HttpClient httpclient = new DefaultHttpClient(); 
              
            HttpPost httppost = new HttpPost(strURL); 
                     HttpResponse response = httpclient.execute(httppost); 
                  
                     HttpEntity entity = response.getEntity(); 
      
                     is = entity.getContent(); 
                     Log.i("info", "ici ok"); 
            } 
            catch(Exception e){ 
                System.out.println("nok ClientProtocolExcetion " + e.toString() + e.getStackTrace()); 
 
            } 
            try{ 
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8); 
                        StringBuilder sb = new StringBuilder(); 
                        String line = null; 
                        if (reader != null){ 
                        while ((line = reader.readLine()) != null) { 
                            sb.append(line + "\n"); 
                        } 
                        } 
                        is.close() ; 
                        result=sb.toString(); 
                        Log.i("info", "ici ok"); 
            }catch(Exception e){ 
                    Log.e("log_tag", "Error converting result " + e.toString()); 
                    return null; 
            } 
             try{    
                //Log.i("info", result); 
                JSONArray jArray = new JSONArray(result);
            	if(jArray.length() == 1 ){
                article = new Articles();
                JSONObject jObject = jArray.getJSONObject(0); 
               // Log.i("info2", jObject.toString()); 
               
                int Sid = jObject.getInt("id_article"); 
                String Stitre = jObject.getString("titre"); 
                int Sprix = jObject.getInt("prix"); 
                int Sstock = jObject.getInt("stock"); 
                int Sreduction = jObject.getInt("reduction"); 
                article.setId(Sid); 
                article.setTitre(Stitre); 
                article.setPrix(Sprix); 
                article.setStock(Sstock); 
                article.setReduction(Sreduction);
                
             
               // Log.i("info", "Stitre" + "Sprix" + "Sstock"); 
                //reduction.setText(Sreduction);    
            }else{
                article = null;
            } 
                 
            return article;
             }
             catch(Exception e){
    	    	 e.printStackTrace();
    				System.out.println("nok" + e.getMessage());
    				
    				return null;
    	     }	
    }
          
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gestion_articles, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
			if(id == R.id.retour){
				Intent  unIntent = new Intent(this, MainActivity.class);
				this.startActivity(unIntent);
			}
			else if(id == R.id.recalculer){
				
					//on r�cup�re les valeurs des EditText qu'on convertit en chiffre pour faire les calculs
					int prix1, reduc1;
					prix1 = Integer.parseInt(this.prix.getText().toString());
					reduc1 = Integer.parseInt(this.reduction.getText().toString());
					int resultat = prix1 - ((prix1 * reduc1) / 100);
					//on r�cup�re le resultat puis on le convertit en string pour l'affichage
					String Sprix = String.valueOf(resultat);
					// r�cup�ration des valeurs dans des variables
					String Stitre, Sstock, Sreduction;
					Stitre = this.titre.getText().toString();
			      	Sstock = this.stock.getText().toString();
			      	Sreduction= this.reduction.getText().toString();
			    	//affichage des variables dans editText
					titre.setText(Stitre);
					prix.setText(Sprix);
					stock.setText(Sstock);
					reduction.setText(Sreduction);
			
			}
			else if(id == R.id.valider){
				//appel de l'activity Main
				Intent  unIntent = new Intent(this, MainActivity.class);
				
				//on r�cup�re les donn�es des EditText par rapport � un id
				String Sid = getIntent().getExtras().getString("id");
				//Log.i("id", Sid);
				String Stitre, Sprix, Sstock, Sreduction;
				Stitre = this.titre.getText().toString();
		      	Sprix = this.prix.getText().toString();
		      	Sstock= this.stock.getText().toString();
		      	Sreduction= this.reduction.getText().toString();
		      	//Log.i("reduction1", Sreduction);
		      	//on r�cup�re les info pour les enregistrer en BDD en faisant un update
		      	Articles article = receiveResult(Integer.parseInt(Sid), Stitre, Integer.parseInt(Sstock), Integer.parseInt(Sprix), Integer.parseInt(Sreduction));
		      	//on r�cup�re les donn�es
				this.startActivity(unIntent);
			}
			else if(id == R.id.supprimer){
				//appel de l'activity Main
				Intent  unIntent = new Intent(this, MainActivity.class);
				//on r�cup�re les donn�es des EditText
				String Sid = getIntent().getExtras().getString("id");
				Log.i("id", Sid);
				//on cr�e la date de suppression
				Date d = new Date();
		      	SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
		      	String date_suppression = f.format(d);
		      	//Log.i("reduction1", Sreduction);
		      	//on r�cup�re les info de l'id en BDD pour la suppression et on lui ins�re la date de suppression
		      	Articles article = receiveResult2(Integer.valueOf(Sid), date_suppression);
		      	//on r�cup�re les donn�es
				this.startActivity(unIntent);
			}
	}
	
	public Articles receiveResult(int Sid, String titre, int stock, int prix, int reduction) 
    {    
        Articles article = new Articles();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id",String.valueOf(Sid)));
        nameValuePairs.add(new BasicNameValuePair("titre",titre));
        nameValuePairs.add(new BasicNameValuePair("stock",String.valueOf(stock)));
        nameValuePairs.add(new BasicNameValuePair("prix",String.valueOf(prix)));
        nameValuePairs.add(new BasicNameValuePair("reduction",String.valueOf(reduction)));
        Log.i("id", String.valueOf(Sid));
        Log.i("titre",titre);
        Log.i("prix", String.valueOf(prix));
        String stringWithSpace = MainActivity.IP+"connexion_mysql/connexion_mysql4.php?id="+Sid+"&titre='"+titre+"'&stock="+stock+"&prix="+prix+"&reduction="+reduction+"&format=json";
        String strURL = stringWithSpace.replaceAll(" ","%20");
	      
        
        Log.i("url",strURL);
        InputStream is = null; 
        String result = ""; 
       
	     
        try{
        	HttpClient httpclient = new DefaultHttpClient();
		 
            HttpPost httppost = new HttpPost(strURL);
           // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
           
             HttpResponse response = httpclient.execute(httppost);
             
             HttpEntity entity = response.getEntity();
 
             is = entity.getContent();
             //Log.i("info", is.toString());
        }
             
            catch(Exception e){ 
                System.out.println("nok ClientProtocolExcetion " + e.toString() + e.getStackTrace()); 
 
            } 
            try{ 
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8); 
                        StringBuilder sb = new StringBuilder(); 
                        String line = null; 
                        if (reader != null){ 
                        while ((line = reader.readLine()) != null) { 
                        	//Log.i("boucle", line);
                            sb.append(line + "\n"); 
                        } 
                        } 
                        is.close() ; 
                        result=sb.toString(); 
                        Log.i("info", "ici ok"); 
                       
            }catch(Exception e){ 
                    Log.e("log_tag", "Error converting result " + e.toString()); 
                    return null; 
            } 
             try{    
	            		//Log.i("reduction", String.valueOf(reduction));
	                 	//on modifie l'article
	                 	article.setTitre(titre); 
	                 	article.setPrix(prix); 
	                 	article.setStock(stock); 
	                 	article.setReduction(reduction); 
	            	 	
	                  
	                    // Log.i("info", titre + prix + stock + reduction);
		             	
     			
                 
            return article;
             }
             catch(Exception e){
    	    	 e.printStackTrace();
    				System.out.println("nok" + e.getMessage());
    				
    				return null;
    	     }	
    }

	
	
}
