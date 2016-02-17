package it.indieCODE.sweng2013.shared;

import java.io.Serializable;
import java.util.Date;


public class Affitto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8235990486772159076L;
	 int IDauto, IDcliente, IDagenzia, costo, seggiolini;
	 String guidatore;
	 boolean navigatore;
	 Date datainizio, datafine;
	
	public Affitto() {
		
	}
	
	public Affitto(int IDauto, int IDcliente, Date datainizio, int costo, Date datafine, String guidatore, int seggiolini, boolean navigatore, int IDagenzia) {
		this.IDauto = IDauto;
		this.IDcliente = IDcliente;
		this.datainizio = datainizio;
		this.costo = costo;
		this.datafine = datafine;
		this.guidatore = guidatore;
		this.seggiolini = seggiolini;
		this.navigatore = navigatore;
		this.IDagenzia = IDagenzia;
	}

	/**
	 * @return the iDauto
	 */
	public int getIDauto() {
		return IDauto;
	}

	/**
	 * @return the iDcliente
	 */
	public int getIDcliente() {
		return IDcliente;
	}

	/**
	 * @return the iDagenzia
	 */
	public int getIDagenzia() {
		return IDagenzia;
	}

	/**
	 * @return the costo
	 */
	public int getCosto() {
		return costo;
	}

	/**
	 * @return the seggiolini
	 */
	public int getSeggiolini() {
		return seggiolini;
	}

	/**
	 * @return the guidatore
	 */
	public String getGuidatore() {
		return Utils.escapeHtml(guidatore);
	}

	/**
	 * @return the navigatore
	 */
	public boolean isNavigatore() {
		return navigatore;
	}

	/**
	 * @return the datainizio
	 */
	public Date getDatainizio() {
		return datainizio;
	}

	/**
	 * @return the datafine
	 */
	public Date getDatafine() {
		return datafine;
	}
	
}
