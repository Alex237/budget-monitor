package fr.alvini.insta.budgetmonitor.model;

import java.util.Date;
import java.util.List;

import fr.alvini.insta.budgetmonitor.model.Category;
import fr.alvini.insta.budgetmonitor.model.Recurrence;

public class Budget extends ObjectModel {
	protected long id_budget;
	protected String description = null;
	protected Category category = null;
	protected Recurrence recurrence = null;
	protected double amount;
	protected Date dateBegin = null;
	protected Date dateEnd = null;
	
	public Budget() {
		super();
	}
	
	public Budget(String pDescription, double pAmount, Date pDateBegin, Date pDateEnd) {
		this();
		this.setDescription(pDescription);
		this.setAmount(pAmount);
		this.setDateBegin(pDateBegin);
		this.setDateEnd(pDateEnd);
	}
	
	public Budget(String pDescription, double pAmount, Date pDateBegin, Date pDateEnd, Recurrence pRecurrence) {
		this(pDescription, pAmount,pDateBegin,pDateEnd);
		this.setRecurrence(pRecurrence);
	}
	
	public Budget(String pDescription, double pAmount, Date pDateBegin, Date pDateEnd, Category pCategory) {
		this(pDescription, pAmount,pDateBegin,pDateEnd);
		this.setCategory(pCategory);
	}

	public Budget(String pDescription, double pAmount, Date pDateBegin, Date pDateEnd, Recurrence pRecurrence, Category pCategory) {
		this(pDescription, pAmount,pDateBegin,pDateEnd,pRecurrence);
		this.setCategory(pCategory);
	}

	public Budget(long pId_budget, String pDescription, double pAmount, Date pDateBegin, Date pDateEnd, Recurrence pRecurrence, Category pCategory) {
		this(pDescription, pAmount,pDateBegin,pDateEnd,pRecurrence, pCategory);
		this.setId_budget(pId_budget);
	}
	
	public Budget(long pId_budget, String pDescription, double pAmount, Date pDateBegin, Date pDateEnd, Recurrence pRecurrence) {
		this(pDescription, pAmount,pDateBegin,pDateEnd,pRecurrence);
		this.setId_budget(pId_budget);
	}
	
	public long getId_budget() {
		return id_budget;
	}

	public void setId_budget(long id_budget) {
		this.id_budget = id_budget;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Recurrence getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = Math.abs(amount);
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

}
