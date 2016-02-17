/**
 * 
 */
package it.indieCODE.sweng2013.shared;

import java.sql.Date;


//import java.text.DecimalFormatSymbols;

/**
 * @author phra
 *
 */
public abstract class Utils {

	public interface MySerializable {
		public byte[] serialize();
	}
	
	public static int julianDay(int year, int month, int day) {
		int a = (14 - month) / 12;
		int y = year + 4800 - a;
		int m = month + 12 * a - 3;
		int jdn = day + (153 * m + 2)/5 + 365*y + y/4 - y/100 + y/400 - 32045;
		return jdn;
	}

	@SuppressWarnings("deprecation")
	public static int daysFromNow(Date data) {
		Date now = new Date(System.currentTimeMillis());
		System.out.println("now: year = " + (now.getYear() + 1900) + ", month = " + now.getMonth() + ", day = " + now.getDate());
		return julianDay(data.getYear(), data.getMonth(), data.getDate()) - julianDay(now.getYear(), now.getMonth(), now.getDate());
	}

	public static boolean isStringNumeric(String str) {
		//DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
		char localeMinusSign = '-';//currentLocaleSymbols.getMinusSign();

		if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) return false;

		boolean isDecimalSeparatorFound = false;
		char localeDecimalSeparator = ',';//currentLocaleSymbols.getDecimalSeparator();

		for (char c : str.substring(1).toCharArray()) {
			if (!Character.isDigit(c)) {
				if ( c == localeDecimalSeparator && !isDecimalSeparatorFound ) {
					isDecimalSeparatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	public static String escapeHtml(String html) {
		if (html == null) return null;
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	//	/**
	//	 * @param x
	//	 * @return
	//	 */
	//	public static byte[] serializeFloat(float x){
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		DataOutputStream w = new DataOutputStream(baos);
	//		try {
	//			w.writeFloat(x);
	//			w.flush();
	//			w.close();
	//		} catch (IOException e) {
	//			return null;
	//		}
	//		return baos.toByteArray();
	//	}
	//
	//	/**
	//	 * @param x
	//	 * @param y
	//	 * @return
	//	 */
	//	public static byte[] serialize2Float(float x, float y){
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		DataOutputStream w = new DataOutputStream(baos);
	//		try {
	//			w.writeFloat(x);
	//			w.writeFloat(y);
	//			w.flush();
	//			w.close();
	//		} catch (IOException e) {
	//			return null;
	//		}
	//		return baos.toByteArray();
	//	}
	//
	//	/**
	//	 * @param x
	//	 * @return
	//	 */
	//	public static byte[] serializeInt(int x){
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		DataOutputStream w = new DataOutputStream(baos);
	//		try {
	//			w.writeInt(x);
	//			w.flush();
	//			w.close();
	//		} catch (IOException e) {
	//			return null;
	//		}
	//		return baos.toByteArray();
	//	}
	//
	//	/**
	//	 * @param list
	//	 * @return
	//	 */
	//	public static byte[] serializeManyInt(int... list){
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		DataOutputStream w = new DataOutputStream(baos);
	//
	//		for (int x : list) {
	//			try {
	//				w.writeInt(x);
	//				w.flush();
	//			} catch (IOException e) {
	//				return null;
	//			}
	//		}
	//		try {
	//			w.close();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return baos.toByteArray();
	//	}
	//	
	//	/**
	//	 * @param list
	//	 * @return
	//	 */
	//	public static byte[] serializeManyStrings(String... list){
	//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//		DataOutputStream w = new DataOutputStream(baos);
	//
	//		for (String x : list) {
	//			try {
	//				w.writeUTF(x);
	//			} catch (IOException e) {
	//				return null;
	//			}
	//		}
	//		try {
	//			w.flush();
	//			w.close();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return baos.toByteArray();
	//	}
	//
	//	/**
	//	 * @param list
	//	 * @return
	//	 */
	//	public static byte[] seralizeManyObjects(LinkedList<? extends MySerializable> list) {
	//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	//		try {
	//			outputStream.write(Utils.serializeInt(list.size()));
	//			for (MySerializable obj : list)
	//				outputStream.write(obj.serialize());
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return outputStream.toByteArray();
	//	}
	//	
	//	/*
	//	public static <T> LinkedList<T> deserializeManyObjects(Class<? extends MySerializable> c, byte[] buf) {
	//		LinkedList<T> list = new LinkedList<T>();
	//		
	//		return null;
	//	}*/
	//
	//	/**
	//	 * @param bytes
	//	 * @return
	//	 */
	//	public static byte[] concatBytes(byte[]... bytes) {
	//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	//		for (byte[] obj : bytes) {
	//			try {
	//				outputStream.write(obj);
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		}
	//		return outputStream.toByteArray();
	//	}
	//	
	//	/**
	//	 * @param bytes
	//	 * @return
	//	 */
	//	public static int deserializeInt(byte[] bytes) {
	//		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
	//		DataInputStream dis = new DataInputStream(stream);
	//		try {
	//			return dis.readInt();
	//		} catch (IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		return 0;
	//	}

}
