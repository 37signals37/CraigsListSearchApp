package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class FileManager {

	main.MasterControlVariables mcv = null;
	BufferedReader in = null;
	Vector<String> v_flInLns = new Vector<String>();
	String line = null;
	
	public FileManager(main.MasterControlVariables mcv){
		this.mcv = mcv;
	}
	
	public Vector<String> getFileInLines(File inFl){
		v_flInLns.clear();
		
		BufferedReader in = getBufferedReaderIn(inFl.getPath());
		try {
			while((line = in.readLine()) != null){			
				v_flInLns.add(line);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return v_flInLns;
	}

	private BufferedReader getBufferedReaderIn(String path){
		try {
			in = new BufferedReader(new FileReader(path));
			return in;			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void writeOutLinesToFile(File outFl, Vector<String> outLns){
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(outFl));
			for(String s: outLns){
				out.write(s); out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
