package fr.alvini.insta.budgetmonitor.model;

public class Parameter extends ObjectModel {
	private static Parameter instance;
	protected long id_parameter;
	protected String color = null;
	protected String font = null;
	protected String style = null;
	
	private Parameter(String pColor, String pFont, String pStyle) {
		super();
		this.setColor(pColor);
		this.setFont(pFont);
		this.setStyle(pStyle);
	}
	
	/**
	 * @param id_parameter
	 * @param color
	 * @param font
	 * @param style
	 */
	public Parameter(long pId_parameter, String pColor, String pFont, String pStyle) {
		this(pColor, pFont, pStyle);
		this.setId_parameter(pId_parameter);
	}
	
	/**
	 * @return the id_parameter
	 */
	public long getId_parameter() {
		return id_parameter;
	}

	/**
	 * @param id_parameter the id_parameter to set
	 */
	public void setId_parameter(long id_parameter) {
		this.id_parameter = id_parameter;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(String font) {
		this.font = font;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	public static Parameter getInstance(String pColor, String pFont, String pStyle) {
		if (instance == null) {
			instance = new Parameter(pColor, pFont, pStyle);
		}
		
		return instance;
	}

}
