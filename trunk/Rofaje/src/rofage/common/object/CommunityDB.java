package rofage.common.object;

import java.io.Serializable;
import java.util.Hashtable;

public class CommunityDB implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String DB_FILENAME	= "db";
	private Hashtable<String, Float> 	myNotes 	= new Hashtable<String, Float>(); // <CRC, myNote>
	private Hashtable<String, Float>	avgNotes	= new Hashtable<String, Float>(); // <CRC, avgNote>
	
	public Hashtable<String, Float> getMyNotes() {
		return myNotes;
	}
	public Hashtable<String, Float> getAvgNotes() {
		return avgNotes;
	}
	
}
