package fr.alvini.insta.budgetmonitor.model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.GregorianCalendar;

public class Budget extends ObjectModel {
	protected long id_budget;
	protected String description = null;
	protected Category category = null;
	protected Recurrence recurrence = null;
	protected double amount;
	protected GregorianCalendar dateBegin = null;
	protected GregorianCalendar dateEnd = null;
	
	public Budget() {
		super();
	}
	
	public Budget(String pDescription, double pAmount, GregorianCalendar pGregorianCalendarBegin, GregorianCalendar pGregorianCalendarEnd) {
		this();
		this.setDescription(pDescription);
		this.setAmount(pAmount);
		this.setDateBegin(pGregorianCalendarBegin);
		this.setDateEnd(pGregorianCalendarEnd);
	}
	
	public Budget(String pDescription, double pAmount, GregorianCalendar pGregorianCalendarBegin, GregorianCalendar pGregorianCalendarEnd, Recurrence pRecurrence) {
		this(pDescription, pAmount,pGregorianCalendarBegin,pGregorianCalendarEnd);
		this.setRecurrence(pRecurrence);
	}
	
	public Budget(String pDescription, double pAmount, GregorianCalendar pGregorianCalendarBegin, GregorianCalendar pGregorianCalendarEnd, Category pCategory) {
		this(pDescription, pAmount,pGregorianCalendarBegin,pGregorianCalendarEnd);
		this.setCategory(pCategory);
	}

	public Budget(String pDescription, double pAmount, GregorianCalendar pGregorianCalendarBegin, GregorianCalendar pGregorianCalendarEnd, Recurrence pRecurrence, Category pCategory) {
		this(pDescription, pAmount,pGregorianCalendarBegin,pGregorianCalendarEnd,pRecurrence);
		this.setCategory(pCategory);
	}

	public Budget(long pId_budget, String pDescription, double pAmount, GregorianCalendar pGregorianCalendarBegin, GregorianCalendar pGregorianCalendarEnd, Recurrence pRecurrence, Category pCategory) {
		this(pDescription, pAmount,pGregorianCalendarBegin,pGregorianCalendarEnd,pRecurrence, pCategory);
		this.setId_budget(pId_budget);
	}
	
	public Budget(long pId_budget, String pDescription, double pAmount, GregorianCalendar pGregorianCalendarBegin, GregorianCalendar pGregorianCalendarEnd, Recurrence pRecurrence) {
		this(pDescription, pAmount,pGregorianCalendarBegin,pGregorianCalendarEnd,pRecurrence);
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

	public GregorianCalendar getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(GregorianCalendar dateBegin) {
		this.dateBegin = dateBegin;
	}

	public GregorianCalendar getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(GregorianCalendar dateEnd) {
		this.dateEnd = dateEnd;
	}

}
