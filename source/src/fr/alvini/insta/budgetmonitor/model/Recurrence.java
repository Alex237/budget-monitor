package fr.alvini.insta.budgetmonitor.model;

public class Recurrence extends ObjectModel {
	protected long id_recurrence;
	protected int day;
	protected int week;
	protected int month;
	protected int year;

	public Recurrence() {
		
	}
	
	/**
	 * @param id_recurrence
	 * @param day
	 * @param week
	 * @param month
	 * @param year
	 */
	public Recurrence(long pId_recurrence, int pDay, int pWeek, int pMonth, int pYear) {
		super();
		this.setId_recurrence(pId_recurrence);
		this.setDay(pDay);
		this.setWeek(pWeek);
		this.setMonth(pMonth);
		this.setYear(pYear);
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
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * @return the week
	 */
	public int getWeek() {
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(int week) {
		this.week = week;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
}
