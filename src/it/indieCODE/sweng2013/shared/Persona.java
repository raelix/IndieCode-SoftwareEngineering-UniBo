/**
 * 
 */
package it.indieCODE.sweng2013.shared;

import java.io.Serializable;

/**
 * @author phra
 *
 */
public abstract class Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2982300103823643297L;
	private long ID;
	private String name, surname, cf;
	private static long IDs = 0;

	/**
	 * @param name
	 * @param surname
	 * @param cf
	 */
	public Persona(String name, String surname, String cf) {
		this.ID = Persona.getNewID();
		this.name = name;
		this.surname = surname;
		this.cf = cf;
	}
	
	/**
	 * 
	 */
	public Persona() {
		
	}

	/**
	 * @return
	 */
	public final long getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public final String getSurname() {
		return surname;
	}

	/**
	 * @return
	 */
	public final String getCf() {
		return cf;
	}
	
	/**
	 * @return
	 */
	public static final long getNewID() {
		return ++Persona.IDs;
	}

}
