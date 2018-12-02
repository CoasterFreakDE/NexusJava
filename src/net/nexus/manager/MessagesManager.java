package net.nexus.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;

import net.nexus.utils.FileConfig;

public class MessagesManager {

	
	private static ConcurrentHashMap<String, String> messages = new ConcurrentHashMap<>();
	
	/**
	 * Refreshes the config file
	 */
	public static void refreshConfigFile() {
		boolean reset = false;
		FileConfig mess = new FileConfig("Nexus", "messages.yml");
		
		if(mess.contains("#.SET_FALSE_TO_RESTORE")) {
			reset = !mess.getBoolean("#.SET_FALSE_TO_RESTORE");
		}
		else {
			reset = true;
		}
		
		mess.set("#.SET_FALSE_TO_RESTORE", true);
		
		
		if(reset) {
			mess.set("NoPerms", "&c&l[Nexus] [Fehler] &o[FailedNoPerms] &r&f&lDu hast offenbar &lkeine Berechtigung &r&f&odiesen Befehl auszuführen. Solltest du dir &lsicher sein&r&f&o, dass dies &leine Störung &r&f&oist, so melde dies bitte einem &lModerator&r&f.");
			
			mess.set("Wichteln.NoWorldEditSelection", "&e&l[Nexus] &o[Info] &r&f&oBitte WorldEdit Selection machen.");
			mess.set("Wichteln.NoRegion", "&e&l[Nexus] &o[Info] &r&f&oDir wurde noch keine Region zugewiesen.");
			mess.set("Wichteln.NotInRegion", "&e&l[Nexus] &o[Info] &r&f&oDu befindest dich in keiner Region.");
			mess.set("Wichteln.NotAUser", "&e&l[Nexus] &o[Info] &r&f&o%s ist kein User auf Nexus.");
			mess.set("Wichteln.NotAValidUser", "&e&l[Nexus] &o[Info] &r&f&o%s kann nicht als Helper hinzugefuegt werden.");
			mess.set("Wichteln.SuccessCreated", "&e&l[Nexus] &o[Info] &r&f&oWichtelBaukasten erstellt. Kein User zugewiesen.");
			mess.set("Wichteln.SuccessSet", "&e&l[Nexus] &o[Info] &r&f&oUser zu WichtelBaukasten zugewiesen.");
			mess.set("Wichteln.SuccessAdd", "&e&l[Nexus] &o[Info] &r&f&o%s zu Lostopf hinzugefügt.");
			
			mess.set("Wichteln.Info.Regions", "&e&l[Nexus] &o[Info] &r&f&oEs wurden bereits &a%o &r&f&oWichtelbaukaesten erstellt.");
			mess.set("Wichteln.Info.NoRegions", "&e&l[Nexus] &o[Info] &r&f&oEs wurden noch keine Wichtelbaukaesten erstellt.");
		}
		
		
		mess.saveConfig();
		
		refreshMessages();
	}
	
	/**
	 * Refreshes the configured messages
	 */
	public static void refreshMessages() {
		FileConfig mess = new FileConfig("Nexus", "messages.yml");
		
		for(String entry : mess.getKeys(true)) {
			messages.put(entry, mess.getString(entry));
		}
	}
	
	
	/**
	 * @param name
	 * @return the Message
	 */
	public static String getMesage(String name, Object... placeholder) {
		String msg = "";
		
		msg = messages.get(name);
		
		if(msg != null) {
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			
			msg = msg.replaceAll("ae", "ä");
			msg = msg.replaceAll("oe", "ö");
			msg = msg.replaceAll("ue", "ü");
			
			
			msg = msg.replaceAll("AE", "Ä");
			msg = msg.replaceAll("OE", "Ö");
			msg = msg.replaceAll("UE", "Ü");
			
			if(placeholder.length > 0) {
				msg = String.format(msg, placeholder);
			}
			
		}
		else {
			msg = "§c§l[Nexus] [Fehler] §r§f§oKeine Nachricht definiert. Bitte wende dich an einen Admin.";
		}
		
		return msg;
	}
}
