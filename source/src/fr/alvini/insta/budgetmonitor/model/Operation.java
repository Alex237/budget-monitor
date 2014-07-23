package fr.alvini.insta.budgetmonitor.model;

import java.util.GregorianCalendar;

public class Operation extends ObjectModel {
	protected long id_operation;
	protected String description = null;
	protected String type = null;
	protected double amount;
	protected GregorianCalendar date_added = null;
	protected Budget budget = null;
	protected Category category = null;
	protected Recurrence recurrence = null;
	protected int recurrence_status = 0;

	public Operation() {
		super();
	}
	
	public Operation(double pAmount, String pDescription, String pType, GregorianCalendar pGregorianCalendar_added, int pRec_status) {
		this();
		this.setAmount(pAmount);
		this.setDescription(pDescription);
		this.setType(pType);
		this.setDate_added(pGregorianCalendar_added);
		this.setRecurrence_status(pRec_status);
	}
	
	public Operation(long pId_operation, double pAmount, String pDescription, String pType, GregorianCalendar pGregorianCalendar_added, int pRec_status) {
		this(pAmount, pDescription, pType, pGregorianCalendar_added, pRec_status);
		this.setId_operation(pId_operation);
	}
	
	public Operation(Budget pBudget, Category pCategory, double pAmount, String pDescription, String pType, GregorianCalendar pGregorianCalendar_added, Recurrence pRecurrence, int pRec_status) {
		this(pAmount, pDescription, pType, pGregorianCalendar_added, pRec_status);
		this.setBudget(pBudget);
		this.setCategory(pCategory);
		this.setRecurrence(pRecurrence);
	}
	
	public Operation(long pId_operation, Budget pBudget, Category pCategory, double pAmount, String pDescription, String pType, GregorianCalendar pGregorianCalendar_added, Recurrence pRecurrence, int pRec_status) {
		this(pBudget, pCategory, pAmount, pDescription, pType, pGregorianCalendar_added, pRecurrence, pRec_status);
		this.setId_operation(pId_operation);
	}


	/**
	 * @return the id_operation
	 */
	public long getId_operation() {
		return id_operation;
	}

	/**
	 * @param id_operation the id_operation to set
	 */
	public void setId_operation(long id_operation) {
		this.id_operation = id_operation;
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = Math.abs(amount);
	}

	/**
	 * @return the date_added
	 */
	public GregorianCalendar getDate_added() {
		return date_added;
	}

	/**
	 * @param date_added the date_added to set
	 */
	public void setDate_added(GregorianCalendar date_added) {
		this.date_added = date_added;
	}

	/**
	 * @return the budget
	 */
	public Budget getBudget() {
		return budget;
	}

	/**
	 * @param budget the budget to set
	 */
	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the recurrence
	 */
	public Recurrence getRecurrence() {
		return recurrence;
	}

	/**
	 * @param recurrence the recurrence to set
	 */
	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	
	/**
	 * @return the recurrence_status
	 */
	public int getRecurrence_status() {
		return recurrence_status;
	}

	
	/**
	 * @param recurrence_status the recurrence_status to set
	 */
	public void setRecurrence_status(int recurrence_status) {
		this.recurrence_status = recurrence_status;
	}

	
}
