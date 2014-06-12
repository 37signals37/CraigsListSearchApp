package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class WebPageReader {

	main.MasterControlVariables mcv = null;
	Vector<String> v_htmlLines = new Vector<String>();
	
	URL url = null;
	HttpURLConnection huc = null;
	BufferedReader in = null;
	String line = null;
	
	public WebPageReader(main.MasterControlVariables mcv){
		this.mcv = mcv;
	}
	
	public Vector<String> getHTMLinLines(String urlAddress){
		v_htmlLines.clear();
		
		try {
			url = new URL(urlAddress);

			huc = (HttpURLConnection)  url.openConnection();
			huc.setRequestMethod("GET");
			huc.connect();

			if(huc.getResponseCode() == 400){
				System.out.println("400 Error");
			}else if(huc.getResponseCode() == 404){
				System.out.println("404 Error");
			}else{
				in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((line = in.readLine()) != null) {
					v_htmlLines.add(line);
				}
				in.close();										
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return v_htmlLines;
	}
	
}
