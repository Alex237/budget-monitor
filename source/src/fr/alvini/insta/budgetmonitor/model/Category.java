package fr.alvini.insta.budgetmonitor.model;

public class Category extends ObjectModel {
	protected long id_category;
	protected String description;

	public Category() {
		super();
	}
	
	public Category(String pDescription) {
		this();
		this.setDescription(pDescription);
	}
	
	public Category(long pId_category, String pDescription) {
		this(pDescription);
		this.setId_category(pId_category);
	}

	/**
	 * @return the id_category
	 */
	public long getId_category() {
		return id_category;
	}

	/**
	 * @param id_category the id_category to set
	 */
	public void setId_category(long id_category) {
		this.id_category = id_category;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
