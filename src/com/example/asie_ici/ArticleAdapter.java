package com.example.asie_ici;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter {
	//tout ça c'est pour affiche les infos dans la liste View et pour se faire on a besoin d'un adaptateur
	List<Articles> mesArticles;
	LayoutInflater inflater;
	public ArticleAdapter(Context context,List<Articles> unArticle) {
	inflater = LayoutInflater.from(context);
	this.mesArticles =  unArticle;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mesArticles.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mesArticles.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	private class ViewHolder {
		TextView articleId;
		TextView articleTitre;
		}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.itemarticle, null);
			holder.articleId = (TextView)convertView.findViewById(R.id.articleId);
			holder.articleTitre = (TextView)convertView.findViewById(R.id.articleTitre);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//c'est ici qu'on va récupérer les données et les afficher dans la liste view n°2 et la liste view ca répéter ses informations 
		//pour en faire une longue liste (c'est la vue)
		String id = String.valueOf(mesArticles.get(position).getId());
		holder.articleId.setText(id);
		holder.articleTitre.setText(mesArticles.get(position).getTitre());
		return convertView;
	}

}
