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

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AjoutArticles extends Activity implements OnClickListener{

	private Button retour, valider;
	private EditText titre, prix, stock;
	private Context ct;
	//private String statut = "a completer";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajout_articles);
		
		//correspondance entre les objets et les widgets
		this.retour = (Button)this.findViewById(R.id.retour);
		this.valider = (Button)this.findViewById(R.id.valider);
		this.titre = (EditText)this.findViewById(R.id.editnom1);
		this.prix = (EditText)this.findViewById(R.id.editprix1);
		this.stock = (EditText)this.findViewById(R.id.editstock1);
		this.ct = getApplicationContext();
		
		//rendre les deux boutons �coutables
		this.valider.setOnClickListener(this);
		this.retour.setOnClickListener(this);
		
		//instanciation d'un objet article
		//ArticleServiceImp.initialiser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ajout_articles, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if(id == R.id.retour){
			System.exit(0);
		}
		else if(id == R.id.valider){
			//appel de l'activity Main
			
			Intent unIntent = new Intent(ct, MainActivity.class);
			
			//on r�cup�re les donn�es entr�es en editText
			String Stitre, Sprix, Sstock, Sstatut;
			Stitre = this.titre.getText().toString();
	      	Sprix = this.prix.getText().toString();
	      	Sstock= this.stock.getText().toString();
	      	Sstatut = "a%20completer";
	      	//on r�cup�re la date du syst�me
	      	Date d = new Date();
	      	SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
	      	String date_entree = f.format(d);
			//Log.i("test1", Sstatut);
			
			//Log.i("info3", Stitre);
	      	//on appel la fonction pour le traitement mysql 
			Articles article = receiveResult(Stitre, Integer.parseInt(Sstock), Integer.parseInt(Sprix), Sstatut, date_entree);
			Log.i("info", article.getTitre());
			startActivity(unIntent);
		}
	}
	
	public Articles receiveResult(String titre, int stock, int prix, String statut, String date_entree) 
    {    
        Articles article = new Articles();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("titre",titre));
        nameValuePairs.add(new BasicNameValuePair("stock",String.valueOf(stock)));
        nameValuePairs.add(new BasicNameValuePair("prix",String.valueOf(prix)));
        nameValuePairs.add(new BasicNameValuePair("date_entree",date_entree));
        
        //entrer l'adresse ip de la machine en Listen ip:80 pour y acc�der depuis le tel sur easyphp,
        //ne pas oublier de rajouter Allow from 192.168.1.90 pour acc�der � 192.168.1.90/home depuis le tel sinon marche pas et mettre
        //en commentaire dans <Directory "${path}/home"> le deny from all
        String stringWithSpace = MainActivity.IP+"connexion_mysql/connexion_mysql3.php?titre='"+titre+"'&stock="+stock+"&prix="+prix+"&statut='"+statut+"'&date_entree="+date_entree+"&format=json";
        //dans l'url on passe les variables en get pour les r�cup�rer dans le fichier php
        //192.168.1.252
        String strURL = stringWithSpace.replaceAll(" ","%20"); 
        Log.i("url",strURL);
        InputStream is = null; 
        String result = ""; 
       
	   //le traitement est pour se connecter � la base de donn�es et on convertit tout en String  
        try{
        	HttpClient httpclient = new DefaultHttpClient();
		 
            HttpPost httppost = new HttpPost(strURL);
           // httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
           
             HttpResponse response = httpclient.execute(httppost);
             
             HttpEntity entity = response.getEntity();
 
             is = entity.getContent();
             Log.i("info", is.toString());
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
                        	Log.i("boucle", line);
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
            	
            	 	//on modifie l'article
                    article.setTitre(titre);
                    article.setStock(stock); 
                    article.setPrix(prix); 
                    article.setStatut(statut);
                  
                     Log.i("info", titre + prix + stock + statut);
  
     			
                 
            return article;
             }
             catch(Exception e){
    	    	 e.printStackTrace();
    				System.out.println("nok" + e.getMessage());
    				
    				return null;
    	     }	
    }

}
