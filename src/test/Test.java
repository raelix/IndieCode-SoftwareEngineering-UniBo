package test;

import static org.junit.Assert.*;
import it.indieCODE.sweng2013.server.GreetingServiceImpl;
import it.indieCODE.sweng2013.server.Queries;
import it.indieCODE.sweng2013.shared.Auto;
import it.indieCODE.sweng2013.shared.Cliente;

import java.sql.Date;
import java.util.LinkedList;

public class Test {

	@SuppressWarnings("deprecation")
	@org.junit.Test
	public void test() {
		GreetingServiceImpl test = new GreetingServiceImpl();
		int id1 = Queries.addAutoLocal(new Auto(-1, "Jeep", "Defender", 3000, new Date(new java.util.Date("19/1/2011").getTime()),3,-1));
		int id2 = Queries.addAutoLocal(new Auto(-1, "Renault", "Clio", 1400, new Date(new java.util.Date("9/2/2010").getTime()),2,-1));
		int id3 = Queries.addAutoLocal(new Auto(-1, "Maserati", "Ghibli", 2500, new Date(new java.util.Date("1/4/2010").getTime()),1,-1));
		assertNotSame(id1, id2);
		assertNotSame(id2, id3);
		assertNotSame(id1, id3);

		Queries.removeAuto(id1);
		Auto auto2 = Queries.getAuto(id2);
		Auto auto3 = Queries.getAuto(id3);
		assertNull(Queries.getAuto(id1));
		assertNotNull(auto2);
		assertNotNull(auto3);

		int id4 = Queries.aggiungiCliente("Mario", "Rossi", new Date(new java.util.Date("1/4/1980").getTime()), "MARIOROSSI", "ajcmp93ak");
		int id5 = Queries.aggiungiCliente("Antonio", "Verdi", new Date(new java.util.Date("10/2/1975").getTime()), "ANTONIOVERDI", "joascewi");
		int id6 = Queries.aggiungiCliente("Mario", "Rossi", new Date(new java.util.Date("1/4/1980").getTime()), "MARIOROSSI", "ajcmp93ak");
		assertNotSame(id4, id5);
		assertEquals(-2, id6);
		System.out.println("id4 = " + id4 + ", id5 = " + id5 +", id6 = " + id6);
		Cliente client1 = Queries.getClient(id4);
		Cliente client2 = Queries.getClient(id5);
		Cliente client3 = Queries.getClient(id6);
		
		assertNotSame(client1, client2);
		assertNull(client3);
		

		Queries.removeClient(client2.getID());
		
		assertEquals(true,Queries.removeClient(client2.getID()));
		
		//assertEquals(false,Queries.removeClient(-2));
		System.out.println("id2 = " + id2 + ", client1 = " + client1);

		
		String result = test.noleggio(id2, client1.getID(), -1, new Date(new java.util.Date("2013/7/13").getTime()), null, 3, false);
		assertEquals("noleggio OK", result);
		result = test.noleggio(id2, client1.getID(), -1, new Date(new java.util.Date("2013/7/13").getTime()), null, 3, false);
		assertEquals("noleggio KO", result);

		result = test.consegnaAuto(id2, false);
		assertNotSame("ERROR", result);

		result = test.consegnaAuto(-2, true);
		assertEquals("ERROR", result);
		
		

		LinkedList<Auto> autos = test.ricerca(null, null, id2, null, -1);
		assertEquals(true, !autos.isEmpty());

		autos = test.ricerca(null, null, -4, null, -1);
		assertNull(autos);



		//CLEAN
		System.out.println("cancello auto con id " + auto2.getID() + " e " + auto3.getID());
		System.out.println(Queries.removeClient(client1.getID()));
		Queries.removeAuto(auto2.getID());
		Queries.removeAuto(auto3.getID());


		//fail("Not yet implemented");
	}

}
