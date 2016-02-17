package it.indieCODE.sweng2013.client;

import it.indieCODE.sweng2013.shared.Affitto;
import it.indieCODE.sweng2013.shared.Auto;

import java.sql.Date;
import java.util.LinkedList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	public LinkedList<Auto> allAuto() throws IllegalArgumentException;
	public String greetServer(String name) throws IllegalArgumentException;
	public String testing(String name) throws IllegalArgumentException;
	String noleggio(int IDauto, int IDcliente, int IDagenzia, Date datafine, String guidatore, int seggiolini, boolean navigatore);
	String consegnaAuto(int ID, boolean multa);
	String gestioneFlotta();
	LinkedList<Auto> ricerca(String marca, String modello, int ID, Date anno, int cilindrata);
	LinkedList<Affitto> getAffittiLocal();
	String richiestaAuto(int ID, int IDagenzia);
	String pagamento(String cc, int amount);
	String registrazione(String name, String surname, Date nascita, String cf, String patente);
}
