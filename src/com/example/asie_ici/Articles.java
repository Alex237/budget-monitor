package com.example.asie_ici;

public class Articles {
	
	private int id;
	private String titre;
	private int stock;
	private int prix;
	private int reduction;
	private String statut;
	private String date_entree;
	private String date_suppression;
	
	public Articles(int id, String titre, int stock, int prix, int reduction, String statut, String date_entree, String date_suppression){
		this.id = id;
		this.titre = titre;
		this.stock = stock;
		this.prix = prix;
		this.reduction = reduction;
		this.statut = statut;
		this.date_entree = date_entree;
		this.date_suppression = date_suppression;
	}
	public Articles(){
		this.id = 0;
		this.titre = "";
		this.prix = 0;
		this.stock = 0;
		this.reduction = 0;
		this.statut = "";
		this.date_entree = "";
		this.date_suppression = "";
	}

	public String getDate_suppression() {
		return date_suppression;
	}
	public void setDate_suppression(String date_suppression) {
		this.date_suppression = date_suppression;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getReduction() {
		return reduction;
	}
	public void setReduction(int reduction) {
		this.reduction = reduction;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getDate_entree() {
		return date_entree;
	}
	public void setDate_entree(String date_entree) {
		this.date_entree = date_entree;
	}
	
	public void afficherArticle(){
		this.getTitre();
	}
	
	//public String toString() {
   //     return this.id + ". " + this.titre;
    //}
	
}
