package it.indieCODE.sweng2013.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.LinkedList;

import it.indieCODE.sweng2013.client.GreetingService;
import it.indieCODE.sweng2013.shared.Affitto;
import it.indieCODE.sweng2013.shared.Auto;
import it.indieCODE.sweng2013.shared.Cliente;
import it.indieCODE.sweng2013.shared.FieldVerifier;
import it.indieCODE.sweng2013.shared.Utils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415374760807891368L;
	public static final int IDAGENZIA = 0;
	/**
	 * 
	 */
	private final LinkedList<String> addresses;

	/**
	 * 
	 */
	public GreetingServiceImpl() {
		this.addresses = new LinkedList<String>();
		//this.addresses.offer("192.168.1.132");
		//this.addresses.offer("");
		//this.addresses.offer("");
	}

	@Override
	public String testing(String str) {
		String resp = "";
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(str)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}
		System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
		ResultSet rs = Queries.makePreparedQueryLocal("select * from test");
		if (rs == null) return "ERROR CONNECT DB";
		try {
			while(rs.next()) {
				resp += "\n" + rs.getString(1) + " " + rs.getInt(2);
				//System.out.println("result: id = " + rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Escape data from the client to avoid cross-site script vulnerabilities.
		str = Utils.escapeHtml(str);
		System.out.println(resp);
		return "Hello, request = " + str + ", response = " + resp + "<br><br>I am running very good<br>";
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#greetServer(java.lang.String)
	 */
	@Override
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = Utils.escapeHtml(input);
		userAgent = Utils.escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#consegnaAuto(int, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public String consegnaAuto(int ID, boolean multa) {
		Affitto rem = Queries.getAffitto(ID);
		if (rem == null) return "ERROR";
		Queries.removeAutoAffitti(ID);
		if (multa) return "" + (rem.getCosto() + 100);
		else return "" + rem.getCosto();
	}


	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#noleggio(java.lang.String, int, int)
	 */
	@Override
	public String noleggio(int IDauto, int IDcliente, int IDagenzia, Date datafine, String guidatore, int seggiolini, boolean navigatore) {
		int giorni = Utils.daysFromNow(datafine);
		int costo;
		Auto auto = Queries.getAuto(IDauto);
		switch (auto.getCategoria()) {
		case Auto.LUXURY:
			costo = 100 * giorni;
			break;
		case Auto.UTILITARY:
			costo = 60 * giorni;
			break;
		case Auto.SPORT:
			costo = 80 * giorni;
			break;
		case Auto.MINI:
			costo = 40 * giorni;
			break;
		default:
			return "ERRORE";
		}
		if (navigatore) costo += 30;
		if (seggiolini > 0) costo += seggiolini*10;
		if (guidatore != null) costo += 50;
		if (IDagenzia == -1) {
			if (Queries.addAutoLocalAffitti(IDauto, IDcliente, costo, datafine, guidatore, seggiolini, navigatore))
				return "noleggio OK";
			else return "noleggio KO";
		} else if (this.addresses.size() - 1 >= IDagenzia && IDagenzia >= 0) {
			//Auto auto = Queries.getAuto(IDauto);
			Queries.removeAuto(IDauto);
			assert(auto != null);
			Cliente client = Queries.getClient(IDcliente);
			Queries.aggiungiClienteRemote(addresses.get(IDagenzia), client.getNome(), client.getCognome(), client.getNascita(), client.getCf(), client.getPatente());
			Queries.addAutoRemoteAffitti(auto, addresses.get(IDagenzia), IDcliente, costo, datafine, guidatore, seggiolini, navigatore);
		}
		return "noleggio KO";
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#gestioneFlotta()
	 */
	@Override
	public String gestioneFlotta() {
		return "";
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#pagamento(java.lang.String, int)
	 */
	@Override
	public String pagamento(String cc, int amount) {
		return "Pagamento effettuato";
	}

	@Override
	public String registrazione(String name, String surname, Date nascita, String cf, String patente) {
		int id = Queries.aggiungiCliente(name, surname, nascita, cf, patente);
		switch (id) {
		case -1: 
			return "Registrazione fallita.";
		case -2:
			return ""+Queries.getClientCF(cf).getID();

		default:
			return ""+id;
		}
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#ricerca(it.indieCODE.sweng2013.shared.Auto)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public LinkedList<Auto> ricerca(String marca, String modello, int ID, Date anno, int cilindrata) {
		LinkedList<Auto> list;


		//		System.out.println("marca "+marca+" modello "+modello+" id "+ID+" anno "+anno+" cililndrata "+cilindrata);
		if (!(list = this.cercaAutoLocal(ID,cilindrata,anno,marca,modello)).isEmpty()) {
			//			String ret = "RESULT LOCAL: ";
			for (int i = 0; i < list.size(); i++) {
				Auto auto = list.get(i);
				if ((marca == null || auto.getMarca().toLowerCase().contains(marca.toLowerCase())) &&  
						(modello == null || auto.getModello().toLowerCase().contains(modello.toLowerCase()))	&& 
						(ID == -1 || ID == auto.getID()) && 
						(anno == null || anno.getYear() == auto.getAnno().getYear()) && 
						(cilindrata == -1 || (auto.getCilindrata() > cilindrata - 101 &&  auto.getCilindrata() < cilindrata + 101))){continue;}
				//				ret += "<br>" + auto.toHTML();
				else list.remove(i--);
			}
			if(list.size() > 0)
				return list;

		} 
		if (!(list = this.faiRicercaRemota(ID,cilindrata,anno,marca,modello)).isEmpty()) {
			for (int i = 0; i < list.size(); i++) {

				Auto auto = list.get(i);
				//ret += "<br>" + auto.toHTML();
				if ((marca == null || auto.getMarca().toLowerCase().contains(marca.toLowerCase())) && 
						(modello == null || auto.getModello().toLowerCase().contains(modello.toLowerCase()))	&& 
						(ID == -1 || ID == auto.getID()) && 
						(anno == null || anno.getYear() == auto.getAnno().getYear()) && 
						(cilindrata == -1 || (auto.getCilindrata() > cilindrata - 101 &&  auto.getCilindrata() < cilindrata + 101))){continue;}
				else list.remove(i--);		
				//		
			}
			if(list.size() > 0)
				return list;
		}
		return null;
	}

	private LinkedList<Auto> cercaAutoLocal(int ID, int cilindrata, Date anno, String marca, String modello) {
		return Queries.getAllAutosLocal();
	}

	@Override
	public LinkedList<Affitto> getAffittiLocal() {
		return Queries.getAllAffittiLocal();
	}
	/**
	 * @param auto
	 * @return
	 * @throws ProtocolException
	 */
	private LinkedList<Auto> faiRicercaRemota(int ID, int cilindrata, Date anno, String marca, String modello) {
		LinkedList<Auto> list = new LinkedList<Auto>();
		//String resp = "";
		int i = 0;
		System.out.println("ricerca remota");
		for (String ip : this.addresses) {
			list.addAll(Queries.getAllAutos(ip,i++));
			System.out.println("ricerca remota lunghezza lista "+list.size());
			System.out.println("Hello, requested auto = " + Utils.escapeHtml(modello) + ",<br><br>I am running very good<br>");
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see it.indieCODE.sweng2013.client.GreetingService#richiestaAuto(int, java.lang.String)
	 */
	@Override
	public String richiestaAuto(int ID, int IDagenzia) {

		//String IP = Queries.getIPAgency(IDagenzia);
		String IP;
		Auto auto;
		if (this.addresses.size() - 1 >= IDagenzia && IDagenzia >= 0) {
			IP = this.addresses.get(IDagenzia);
			if ((auto = Queries.trasferisciAuto(IP, ID)) != null) 
				return "SUCCESS " + auto.getMarca();
		}
		return "FAILURE";
	}

	@Override
	public LinkedList<Auto> allAuto() throws IllegalArgumentException {

		return Queries.getAllAutosLocal();
	}

}
