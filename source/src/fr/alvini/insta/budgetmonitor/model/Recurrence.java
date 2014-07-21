package fr.alvini.insta.budgetmonitor.model;

public class Recurrence extends ObjectModel {
	protected long id_recurrence = 0;
	protected String description = null;

	public Recurrence() {
		super();
	}
	
	/**
	 * @param id_recurrence
	 * @param description
	 */
	public Recurrence(long pId_recurrence, String pDescription) {
		this();
		this.setId_recurrence(pId_recurrence);
		this.setDescription(pDescription);
	}

	/**
	 * @return the id_recurrence
	 */
	public long getId_recurrence() {
		return id_recurrence;
	}

	/**
	 * @param id_recurrence the id_recurrence to set
	 */
	public void setId_recurrence(long id_recurrence) {
		this.id_recurrence = id_recurrence;
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
