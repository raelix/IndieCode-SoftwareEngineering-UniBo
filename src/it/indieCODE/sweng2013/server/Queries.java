package it.indieCODE.sweng2013.server;

import it.indieCODE.sweng2013.shared.Affitto;
import it.indieCODE.sweng2013.shared.Auto;
import it.indieCODE.sweng2013.shared.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.LinkedList;

public abstract class Queries {
	public static final String GETALLAUTO = "SELECT DISTINCT * FROM auto;";
	public static final String GETALLCLIENTS = "SELECT DISTINCT * FROM clients";
	public static final String GETALLAFFITTI = "SELECT DISTINCT * FROM affitti";
	public static final String GETAVAILABLEAUTO = "SELECT DISTINCT * FROM auto a where a.id NOT IN (SELECT DISTINCT a.idauto FROM affitti a);";
	public static final String GETAGENCIES = "SELECT DISTINCT * FROM agencies;";
	public static final String GETCLIENTS = "SELECT DISTINCT * FROM clients;";
	public static final String INSERTCLIENT = "INSERT INTO clients VALUES (DEFAULT,?,?,?,?,?) RETURNING *;";
	public static final String DELETEAUTO = "DELETE FROM auto a where a.id = ?;";
	public static final String DELETECLIENT = "DELETE FROM clients c where c.id = ?;";
	public static final String DELETEAUTOAFFITTI = "DELETE FROM affitti a where a.idauto = ?;";
	public static final String INSERTAUTO = "INSERT INTO auto VALUES (DEFAULT,?,?,?,?,?) RETURNING *;";
	public static final String INSERTAUTOAFFITTI = "INSERT INTO affitti VALUES (?,?,DEFAULT,?,?,?,?,?,?) RETURNING *;";
	public static final String SELECTAUTO = "SELECT DISTINCT * FROM auto a WHERE a.id = ?;";
	public static final String SELECTCLIENTID = "SELECT DISTINCT * FROM clients c WHERE c.id = ?;";
	public static final String SELECTCLIENTCF = "SELECT DISTINCT * FROM clients c WHERE c.cf = ?;";
	public static final String SELECTAFFITTO = "SELECT DISTINCT * FROM affitti a WHERE a.idauto = ?;";

	public static Cliente getClient(int ID) {
		ResultSet rs = makePreparedQueryLocal(SELECTCLIENTID,ID);
		try {
			rs.next();
			return new Cliente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6));
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static boolean removeClient(int ID) {
		ResultSet rs = makePreparedQueryLocal(SELECTCLIENTID,ID);
		try {
			rs.next();
			makePreparedQueryLocal(DELETECLIENT, ID);
			return true;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return false;
	}

	public static Cliente getClientCF(String CF) {
		ResultSet rs = makePreparedQueryLocal(SELECTCLIENTCF,CF);
		try {
			rs.next();
			return new Cliente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6));
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static Affitto getAffitto(int ID) {
		ResultSet rs = makePreparedQueryLocal(SELECTAFFITTO,ID);
		try {
			rs.next();
			return new Affitto(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getInt(4),rs.getDate(5),rs.getString(6),rs.getInt(7),rs.getBoolean(8),rs.getInt(9));
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static Auto getAuto(int ID) {
		ResultSet rs = makePreparedQueryLocal(SELECTAUTO,ID);
		try {
			rs.next();
			return new Auto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getInt(6),-1);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public static LinkedList<Auto> getAllAutosLocal() {
		return Queries.getAllAutos("localhost",-1);
	}

	public static LinkedList<Auto> getAllAutos(String ip,int IDagenzia){
		LinkedList<Auto> list = new LinkedList<Auto>();
		if (ip.equalsIgnoreCase("")) return null;
		ResultSet rs = Queries.makePreparedQuery(ip,Queries.GETAVAILABLEAUTO);
		if (rs == null) return null;
		try {
			while(rs.next()) {
				list.offer(new Auto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getInt(6),IDagenzia));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return list;
	}

	public static LinkedList<Affitto> getAllAffittiLocal() {
		return getAllAffitti("localhost");
	}

	public static LinkedList<Affitto> getAllAffitti(String ip) {
		LinkedList<Affitto> list = new LinkedList<Affitto>();
		if (ip.equalsIgnoreCase("")) return null;
		ResultSet rs = Queries.makePreparedQuery(ip,Queries.GETALLAFFITTI);
		if (rs == null) return null;
		try {
			while(rs.next()) {
				list.offer(new Affitto(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getInt(4),rs.getDate(5),rs.getString(6),rs.getInt(7),rs.getBoolean(8),rs.getInt(9)));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}

		return list;
	}

	public static LinkedList<Cliente> getAllClientsLocal() {
		return getAllClients("localhost");
	}

	public static LinkedList<Cliente> getAllClients(String ip){
		LinkedList<Cliente> list = new LinkedList<Cliente>();
		if (ip == "") return null;
		ResultSet rs = Queries.makePreparedQuery(ip,Queries.GETALLCLIENTS);
		if (rs == null) return null;
		try {
			while(rs.next()) {
				list.offer(new Cliente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6)));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return list;
	}

	public static void removeAuto(int ID) {
		makePreparedQueryLocal(DELETEAUTO, ID);
	}

	public static boolean addAutoLocalAffitti(int IDauto, int IDcliente, int costo, Date datafine, String guidatore, int seggiolini, boolean navigatore) {
		if (makePreparedQueryLocal(INSERTAUTOAFFITTI,IDauto,IDcliente,costo,datafine,guidatore,seggiolini,navigatore,-1) != null) return true;
		else return false;
	}

	public static void removeAutoAffitti(int ID) {
		makePreparedQueryLocal(DELETEAUTOAFFITTI, ID);
	}

	public static int addAutoLocal(Auto auto) {
		ResultSet rs = makePreparedQueryLocal(INSERTAUTO, auto.getMarca(), auto.getModello(), auto.getCilindrata(), auto.getAnno(), auto.getCategoria());
		try {
			if (rs != null && rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return -1;
	}

	public static boolean addAutoRemoteAffitti(Auto auto, String ip, int IDcliente, int costo, Date datafine, String guidatore, int seggiolini, boolean navigatore) {
		ResultSet rs = makePreparedQuery(ip, INSERTAUTO, auto.getMarca(), auto.getModello(), auto.getCilindrata(), auto.getAnno(), auto.getCategoria());
		try {
			rs.next();
			if (makePreparedQuery(ip, INSERTAUTOAFFITTI, rs.getInt(1),IDcliente,costo,datafine,guidatore,seggiolini,navigatore,GreetingServiceImpl.IDAGENZIA) != null) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static Auto trasferisciAuto(String ip, int ID) {
		ResultSet rs = makePreparedQuery(ip,SELECTAUTO,ID);
		try {
			if (rs.next()) {
				makePreparedQuery(ip, DELETEAUTO,ID);
				makePreparedQueryLocal(INSERTAUTO,rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getInt(6));
				return new Auto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDate(5),rs.getInt(6),-1);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return null;
	}

	/*
	public static String getIPAgency(int IDagenzia) {
		ResultSet rs = makeQueryLocal("SELECT DISTINCT a.IP FROM agencies a WHERE a.ID = '"+ IDagenzia + "';");
		try {
			if (rs.next()) return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	 */

	/**
	 * @deprecated use makePreparedQueryLocal instead.
	 * @param query
	 * @return
	 */
	@Deprecated
	public static ResultSet makeQueryLocal(String query) {
		return makeQuery("127.0.0.1", query);
	}

	/**
	 * @deprecated use makePreparedQuery instead.
	 * @param ip
	 * @param query
	 * @return
	 */
	@Deprecated
	public static ResultSet makeQuery(String ip, String query) {
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://" + ip + ":5432/indieCODE","pgadmin", "pgadmin");
			if (connection != null)	{
				Statement s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				connection.close();
				return rs;
			} else {
				System.out.println("ERROR CONNECT DB");
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int aggiungiCliente(String name, String surname, Date nascita, String cf, String patente) {
		ResultSet rs;
		try {
			rs = Queries.makePreparedQueryLocal(SELECTCLIENTCF, cf);
			if (rs != null && rs.next()) return -2;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		int ID = -1;
		try {
			rs = Queries.makePreparedQueryLocal(INSERTCLIENT, name, surname, nascita, cf, patente);
			if (rs != null) {
				rs.next();
				ID = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}

	public static int aggiungiClienteRemote(String ip, String name, String surname, Date nascita, String cf, String patente) {
		ResultSet rs;
		try {
			rs = Queries.makePreparedQuery(ip, SELECTCLIENTCF, cf);
			if (rs != null && rs.next()) return -2;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		int ID = -1;
		try {
			rs = Queries.makePreparedQuery(INSERTCLIENT, name, surname, nascita, cf, patente);
			if (rs != null) {
				rs.next();
				ID = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}

	public static ResultSet makePreparedQuery(String ip, String query, Object... args) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			int i = 0;
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":5432/indieCODE","pgadmin", "pgadmin");
			stmt = conn.prepareStatement(query);
			for (Object arg : args) {
				if (arg != null) System.out.println(i + " : " + arg.toString());
				else System.out.println(i + " : null");
				if (arg == null) {
					stmt.setNull(++i, java.sql.Types.NULL);
				} else if (arg instanceof Integer) {
					stmt.setInt(++i, (Integer)arg);
				} else if (arg instanceof Date) {
					stmt.setDate(++i, (Date)arg);
				} else if (arg instanceof Float) {
					stmt.setFloat(++i, (Float)arg);
				} else if (arg instanceof String) {
					stmt.setString(++i, (String)arg);
				} else if (arg instanceof Boolean) {
					stmt.setBoolean(++i, (Boolean)arg);
				}
			}
			return stmt.executeQuery();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		} catch (SQLException e) {
			//if (query != DELETEAUTOAFFITTI && query != DELETEAUTO)
				//e.printStackTrace();
		}
		return null;
	}

	public static ResultSet makePreparedQueryLocal(String query, Object... args) {
		return makePreparedQuery("localhost", query, args);
	}

}
