package net.nexus.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MojangManager {

	private static ConcurrentHashMap<UUID, String> usernameCatch = new ConcurrentHashMap<>();


	/**
	 * Fetches the UUID from Web and catches it.
	 * @param uuid
	 */
	public static void addUsernameToCatch(UUID uuid) {
		String username = "Steve";
		
		if(usernameCatch.containsKey(uuid)) return;
		
		String url = "https://ss.gameapis.net/name/" + uuid.toString();
		
		try {
			HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection(); 
			httpcon.addRequestProperty("User-Agent", "Mozilla/4.76"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
			String output = "";
			String out;
			while((out = reader.readLine()) != null) {
				output += " " + out;
			}
			
			JSONParser parser=new JSONParser();
			JSONObject arr = (JSONObject) parser.parse(output);
			
			username = (String) arr.get("name");
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		
		
		usernameCatch.put(uuid, username);
		System.out.println("Username( " + uuid + " , " + username + " ) added to catch.");
	}
	
	/**
	 * 
	 * @param uuid
	 * @return Catched Username
	 */
	public static String getUsernameFromCatch(UUID uuid) {
		return usernameCatch.get(uuid);
	}
	
	/**
	 * Refreshes the catched Username
	 * @param uuid
	 */
	public static void refreshCatchedUsername(UUID uuid) {
		if(usernameCatch.containsKey(uuid)) usernameCatch.remove(uuid);
		
		addUsernameToCatch(uuid);
	}
	
	public static int size() {
		return usernameCatch.size();
	}
}
