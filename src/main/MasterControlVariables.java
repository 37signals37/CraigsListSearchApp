package main;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;


public class MasterControlVariables {

	public File f_bscSrchTrms = new File("c:\\BargainSkimmer\\BasicSearchTerms.txt");
	public File f_bscSrchTrmsMods = new File("c:\\BargainSkimmer\\BasicSearchTermsMods.txt");
	public File f_rmvFrvr = new File("c:\\BargainSkimmer\\RemoveForever.txt");
	public File f_mstRcntLstngs = new File("c:\\BargainSkimmer\\MostRecentListings.txt");
	public File f_audioAlert = new File("c:\\BargainSkimmer\\AudioAlert.wav");
	
	public Vector<String> v_bscSrchTrms = new Vector<String>();
	
	public Vector<String> v_srchTrmMdfrsFlLns = new Vector<String>();
	public HashMap<String, String> h_srchTrmNMdfrs = new HashMap<String, String>(); 
	
	public Vector<String> v_rmvdLstngs = new Vector<String>();
	
	public utilities.GUIHandler guih = new utilities.GUIHandler();
	public utilities.FileManager fm = new utilities.FileManager(this);
	public utilities.WebPageReader wpr = new utilities.WebPageReader(this);
	
	
	public String[] lnSplt = null;
	
	public MasterControlVariables(){
		loadBasicSearchTermsFromFile();
		loadSearchTermsModsFromFile();
		loadRemoveForeverListings();
	}
	
	public void addListingToRemoveListings(String line){
		v_rmvdLstngs.add(line);
		fm.writeOutLinesToFile(f_rmvFrvr, v_rmvdLstngs);
		loadRemoveForeverListings();
	}
	
	public void loadRemoveForeverListings() {
		v_rmvdLstngs.clear();
		v_rmvdLstngs.addAll(fm.getFileInLines(f_rmvFrvr));	
	}

	public void addSrchTrmAndModifiersToHashMap(String srchTrm, String mdfrs){
		h_srchTrmNMdfrs.put(srchTrm, mdfrs);
		saveSearchTermModsListToFile();
	}
	
	public void loadBasicSearchTermsFromFile(){
		v_bscSrchTrms.clear();
		v_bscSrchTrms.addAll(fm.getFileInLines(f_bscSrchTrms));		
	}

	public void loadSearchTermsModsFromFile(){
		v_srchTrmMdfrsFlLns.clear();
		v_srchTrmMdfrsFlLns.addAll(fm.getFileInLines(f_bscSrchTrmsMods));
		
		h_srchTrmNMdfrs.clear();
		for(String s: v_srchTrmMdfrsFlLns){
			lnSplt = s.split("\\|");
			h_srchTrmNMdfrs.put(lnSplt[0], lnSplt[1] + "|" + lnSplt[2] + "|" + lnSplt[3] + "|" + lnSplt[4]);
		}
		
	}
	
	public void saveBasicSearchTermListToFile() {
		Collections.sort(v_bscSrchTrms);
		fm.writeOutLinesToFile(f_bscSrchTrms, v_bscSrchTrms);
	}
	
	public void saveSearchTermModsListToFile() {
		for(String s: v_bscSrchTrms){
			if(!h_srchTrmNMdfrs.containsKey(s)){
				h_srchTrmNMdfrs.put(s, "NA|NA|NA|NA");
			}
		}
		
		v_srchTrmMdfrsFlLns.clear();
		Collections.sort(v_bscSrchTrms);
		for(String s: v_bscSrchTrms){
			v_srchTrmMdfrsFlLns.add(s + "|" + h_srchTrmNMdfrs.get(s));
		}
		
		fm.writeOutLinesToFile(f_bscSrchTrmsMods, v_srchTrmMdfrsFlLns);
		
	}
	
	
}
