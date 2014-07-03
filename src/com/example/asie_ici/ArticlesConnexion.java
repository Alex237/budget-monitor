package com.example.asie_ici;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ArticlesConnexion extends AsyncTask<Void, Integer, ArrayList<Articles>>{
	
	Context myContext;
	
	int TIMEOUT_MILLISEC = 30000; // = 10 seconds
	MainActivity activite;
	GestionArticles gestion;
	
	
	public ArticlesConnexion(Context applicationContext, MainActivity activ) {
		this.myContext = applicationContext;	
		this.activite = activ;
	}

	
	public ArticlesConnexion(Context applicationContext,
			GestionArticles gest) {
		// TODO Auto-generated constructor stub
		this.myContext = applicationContext;	
		this.gestion = gest;
	}


	public ArrayList<Articles> receiveResult()
	{
		//cette connexion ne concerne que la r�cup�ration des donn�es sur la listeView
		ArrayList<Articles> mylist = new ArrayList<Articles>();
		
		 InputStream is = null;
		 String result = "";
		    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("user","1"));
	        
	        //entrer l'adresse ip de la machine en Listen ip:80 pour y acc�der depuis le tel sur easyphp,
	        //ne pas oublier de rajouter Allow from 192.168.1.90 pour acc�der � 192.168.1.90/home depuis le tel sinon marche pas et mettre
	        //en commentaire dans <Directory "${path}/home"> le deny from all
	        
	        //on indique quel fichier de connexion php utiliser pour la connexion � la base de donn�es
	        String strURL = MainActivity.IP+"connexion_mysql/connexion_mysql.php?user=1&format=json";
	        //Log.i("url",strURL);
	        
	        try{
	        	HttpClient httpclient = new DefaultHttpClient();
			 
	            HttpPost httppost = new HttpPost(strURL);
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	           
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
	    	Log.i("info", result);
	    	//on convertit les donn�es en tableau Json
	    	JSONArray jArray = new JSONArray(result);

			//Log.i("info2", jArray.toString());
			//Log.i("info", "ici ok");
			
			for (int i = 0; i < jArray.length(); i++) {
				//Log.i("info", "bon ou pas");
				//on r�cup�re les donn�es du php et on les convertit en objet Json
				JSONObject jObject = jArray.getJSONObject(i);
				int id = jObject.getInt("id_article");
				String titre = jObject.getString("titre");
				int prix = jObject.getInt("prix");
				int stock = jObject.getInt("stock");
				int reduction = jObject.getInt("reduction");
				Articles unArticle = new Articles();
				//puis on instancie l'article et on lui donne les valeurs r�cup�r�es de la BDD
				unArticle.setId(id);
				unArticle.setTitre(titre);
				unArticle.setPrix(prix);
				unArticle.setStock(stock);
				unArticle.setReduction(reduction);
				mylist.add(unArticle);
			}
			return mylist;
	     }
	     catch(Exception e){
	    	 e.printStackTrace();
				System.out.println("nok" + e.getMessage());
				
				return null;
	     }	
	}


	@Override
	protected ArrayList<Articles> doInBackground(Void... arg0) {
		ArrayList<Articles> mylist = new ArrayList<Articles>();
		mylist = receiveResult();
		return mylist;
	}
	
	protected void onPostExecute(ArrayList<Articles> array){
		//Log.i("info", "On va afficher une liste de "+array.size()+" elements");
		this.activite.setResultGrid(array);
		
	}

}
