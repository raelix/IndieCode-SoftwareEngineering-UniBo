/**
 * 
 */
package it.indieCODE.sweng2013.shared;

import java.sql.Date;

/**
 * @author phra
 *
 */
public class Cliente {
	
	private final Date nascita;
	private final int ID;
	private final String patente, nome, cognome, cf;

	public Cliente(int ID, String name, String surname, Date nascita, String cf, String patente) {
		this.nome = name;
		this.cognome = surname;
		this.ID = ID;
		this.nascita = nascita;
		this.patente = patente;
		this.cf = cf;
	}
	
	public int getID() {
		return ID;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCf() {
		return cf;
	}

	public String getPatente() {
		return patente;
	}

	public Date getNascita() {
		return nascita;
	}
}
