/**
 * 
 */
package it.indieCODE.sweng2013.shared;


import java.io.Serializable;
import java.util.Date;

/**
 * @author phra
 *
 */
public class Auto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4318340330237040940L;
	/**
	 * 
	 */
	private int cilindrata, ID, categoria,IDagenzia;
	private Date anno;
	private String marca, modello;
	public static final int LUXURY = 1;
	public static final int UTILITARY = 2;
	public static final int SPORT = 3;
	public static final int MINI = 4;
	/**
	 * @param ID
	 * @param string
	 * @param string2
	 * @param i
	 * @param j
	 */
	public Auto(int ID, String marca, String modello, int cilindrata, Date anno, int categoria,int IDagenzia) {
		this.ID = ID;
		this.cilindrata = cilindrata;
		this.anno = anno;
		this.marca = marca;
		this.modello = modello;
		this.categoria = categoria;
		this.IDagenzia = IDagenzia;
	}

	/**
	 * 
	 */
	public Auto(){
		this.ID = 0;
		this.cilindrata = 0;
		this.anno = new Date();
		this.marca = "null";
		this.modello = "null";
	}
	
	/**
	 * @param ID
	 */
	public Auto(int ID){
		this();
		this.ID = ID;
	}
	
	public String toHTML() {
		return "ID = " + ID + ", MARCA = " + Utils.escapeHtml(marca) + ", MODELLO = " + Utils.escapeHtml(modello) + ", ANNO = " + anno + ", CILINDRATA = " + cilindrata;
	}

	/**
	 * @return
	 */
	public final int getCilindrata() {
		return cilindrata;
	}

	/**
	 * @return
	 */
	public final Date getAnno() {
		return anno;
	}
	
	

	public int getCategoria() {
		return categoria;
	}


	public int getIDagenzia() {
		return IDagenzia;
	}

	public void setIDagenzia(int iDagenzia) {
		IDagenzia = iDagenzia;
	}

	/**
	 * @return
	 */
	public final int getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public String getModello() {
		return Utils.escapeHtml(modello);
	}

	/**
	 * @return
	 */
	public String getMarca() {
		return Utils.escapeHtml(marca);
	}
	
}