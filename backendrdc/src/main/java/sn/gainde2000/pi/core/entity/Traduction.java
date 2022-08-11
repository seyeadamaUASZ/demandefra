package sn.gainde2000.pi.core.entity;

public class Traduction {
	private String reference;
	private String defaultLangue;
	private String selectedLangue;
	private Langue langue;
	
	public Traduction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Traduction(String reference, String defaultLangue, String selectedLangue, Langue langue) {
		super();
		this.reference = reference;
		this.defaultLangue = defaultLangue;
		this.selectedLangue = selectedLangue;
		this.langue = langue;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getDefaultLangue() {
		return defaultLangue;
	}
	public void setDefaultLangue(String defaultLangue) {
		this.defaultLangue = defaultLangue;
	}
	public String getSelectedLangue() {
		return selectedLangue;
	}
	public void setSelectedLangue(String selectedLangue) {
		this.selectedLangue = selectedLangue;
	}
	public Langue getLangue() {
		return langue;
	}
	public void setLangue(Langue langue) {
		this.langue = langue;
	}
	
	
	
	
	
}
