package com.example.asie_ici;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Total extends Activity implements OnClickListener {

	private TextView total;
	private EditText montant;
	
     @Override
 	public void onClick(View v) {
 		// TODO Auto-generated method stub
    	 this.total = (TextView)this.findViewById(R.id.totalFinal);
    	 this.montant = (EditText)this.findViewById(R.id.editmontant);
    		
         this.total.setOnClickListener(this);
    	 this.montant.setOnClickListener(this);
    	 
    	 this.montant.setText("517");
 	}
     /*
     protected void onStart() {
    		super.onStart();
  
    		Articles article = receiveResult();
    		Log.i("info", article.getTitre());
    		//et on les affiche dans les EditText
    		titre.setText(article.getTitre().toString());
    		prix.setText(String.valueOf(article.getPrix()).toString());
    		stock.setText(String.valueOf(article.getStock()).toString());
    		reduction.setText(String.valueOf(article.getReduction()).toString());
    		
    		}
    		
     public EditText receiveResult()
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
 	        String strURL = MainActivity.IP+"connexion_mysql/connexion_mysql6.php?user=1&format=json";
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
 				int montantTest = jObject.getInt("total");
 			}
 			return montant;

 	     }
 	     catch(Exception e){
 	    	 e.printStackTrace();
 				System.out.println("nok" + e.getMessage());
 				
 				return null;
 	     }	
 	
 	 
 	
 	}*/
     
}
