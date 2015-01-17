package jsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
// Requires gson jars
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Wunderground {         
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Get from http://www.wunderground.com/weather/api/
//		String key;
//		if(args.length < 1) {
//			System.out.println("Enter key: ");
//			
//			Scanner in = new Scanner(System.in);
//			key = in.nextLine();
//		} else {
//			key = args[0];
//		}
		
		String sURL = "http://api.wunderground.com/api/7940be4d28156ec3/geolookup/q/autoip.json";
		
		// Connect to the URL
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		
		// Convert to a JSON object to print data
    	JsonParser jp = new JsonParser();
    	JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
    	JsonObject rootobj = root.getAsJsonObject(); // may be Json Array if it's an array, or other type if a primitive
    	
    	// Get some data elements and print them
    	System.out.println("You are currently in: ");
    	String state = rootobj.get("location").getAsJsonObject().get("state").getAsString();
		System.out.println("State: " + state);
		int zip = rootobj.get("location").getAsJsonObject().get("zip").getAsInt();
		System.out.println("Zipcode: " + zip);
		String city = rootobj.get("location").getAsJsonObject().get("city").getAsString();
		System.out.println("City: " + city);
		
		sURL = "http://api.wunderground.com/api/7940be4d28156ec3/hourly/q/" + zip + ".json";
		
		URL url1 = new URL(sURL);
		request = (HttpURLConnection) url1.openConnection();
		request.connect();
		
		JsonParser jp1 = new JsonParser();
    	root = jp1.parse(new InputStreamReader((InputStream) request.getContent()));
    	rootobj = root.getAsJsonObject();
    	//System.out.println("test");
    	JsonArray rArray = rootobj.get("hourly_forecast").getAsJsonArray();
    	int i;
    	System.out.println("Hourly weather forecast are:");
    	for(i=0; i<rArray.size(); i++){
    		System.out.print(rArray.get(i).getAsJsonObject().get("FCTTIME").getAsJsonObject().get("hour").getAsInt());
    		System.out.print(":00 --> " + rArray.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("english").getAsInt());
    		System.out.println("F --> " + rArray.get(i).getAsJsonObject().get("condition").getAsString());
    	}
	}

}