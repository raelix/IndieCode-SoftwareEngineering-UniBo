package it.indieCODE.sweng2013.client;

import it.indieCODE.sweng2013.shared.Affitto;
import it.indieCODE.sweng2013.shared.Auto;

import java.sql.Date;
import java.util.LinkedList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	public void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	public void testing(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void noleggio(int IDauto, int IDcliente, int IDagenzia, Date datafine, String guidatore, int seggiolini, boolean navigatore, AsyncCallback<String> callback) throws IllegalArgumentException;
	void consegnaAuto(int ID, boolean multa, AsyncCallback<String> callback) throws IllegalArgumentException;
	void gestioneFlotta(AsyncCallback<String> callback);
	void ricerca(String marca, String modello, int ID, Date anno, int cilindrata, AsyncCallback<LinkedList<Auto>> callback);
	void richiestaAuto(int ID, int IDagenzia, AsyncCallback<String> callback);
	void pagamento(String cc, int amount, AsyncCallback<String> callback);
	void registrazione(String name, String surname, Date nascita, String cf, String patente, AsyncCallback<String> callback);
	void allAuto(AsyncCallback<LinkedList<Auto>> callback);
	void getAffittiLocal(AsyncCallback<LinkedList<Affitto>> callback);

}
