package net.nexus.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.nexus.NexusJava;
import net.nexus.enums.TimeValue;
import net.nexus.manager.MojangManager;
import net.nexus.utils.LocationUtils;

public class SQLManager {

	/**
	 * Create all the necessary Databases in our SQLite
	 */
	public static void createNecessaryDatabases() {
		SQL.onUpdate("CREATE TABLE IF NOT EXISTS bans(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(40), grund VARCHAR(30), time DATETIME DEFAULT CURRENT_TIMESTAMP, until DATETIME, teammitglied VARCHAR(30))");
		SQL.onUpdate("CREATE TABLE IF NOT EXISTS mutes(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, uuid VARCHAR(40), grund VARCHAR(30), time DATETIME DEFAULT CURRENT_TIMESTAMP, until DATETIME, teammitglied VARCHAR(30))");
	
		SQL.onUpdate("CREATE TABLE IF NOT EXISTS wichteln(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, location VARCHAR(80), uuid VARCHAR(40), partner VARCHAR(40), helper VARCHAR(40))");
	}
	
	/**
	 * Catching Usernames
	 */
	public static void catchUsernames() {
		ResultSet set = SQL.onQuery("SELECT uuid, teammitglied FROM bans");
		
		try {
			while(set.next()) {
				MojangManager.addUsernameToCatch(UUID.fromString(set.getString("uuid")));
				MojangManager.addUsernameToCatch(UUID.fromString(set.getString("teammitglied")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		set = SQL.onQuery("SELECT uuid, teammitglied FROM mutes");
		
		try {
			while(set.next()) {
				MojangManager.addUsernameToCatch(UUID.fromString(set.getString("uuid")));
				MojangManager.addUsernameToCatch(UUID.fromString(set.getString("teammitglied")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		set = SQL.onQuery("SELECT * FROM wichteln");
		
		try {
			while(set.next()) {
				String location = set.getString("location");
				if(!(location == null) && !location.isEmpty() && !location.equals("")) {
					NexusJava.INSTANCE.getRegManager().add(LocationUtils.Str2Region(location));
				}
				
				String self = set.getString("uuid");
				if(!(self == null) && !self.isEmpty() && !self.equals("")) {
					MojangManager.addUsernameToCatch(UUID.fromString(self));
				}
				
				
				String partner = set.getString("partner");
				if(!(partner == null) && !partner.isEmpty() && !partner.equals("")) {
					MojangManager.addUsernameToCatch(UUID.fromString(partner));
				}
				
				partner = set.getString("helper");
				if(!(partner == null) && !partner.isEmpty() && !partner.equals("")) {
					MojangManager.addUsernameToCatch(UUID.fromString(partner));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Bukkit.getConsoleSender().sendMessage("§eNexus§7 > §oCatched §a" + MojangManager.size() + " §7§oUsernames and §a" + NexusJava.INSTANCE.getRegManager().size() + " §7§oWichtelboxes.");
	}
	
	/**
	 * Checking if UUID is banned
	 * @param uuid
	 * @return isBanned
	 */
	public static boolean isBanned(UUID uuid) {
		
		return false;
	}
	
	/**
	 * Bans the specified UUID
	 * @param uuid
	 * @param grund
	 * @param timevalue
	 * @param teammitglied
	 */
	public static void banUUID(UUID uuid, String grund, TimeValue timevalue, UUID teammitglied) {
		MojangManager.addUsernameToCatch(uuid);
		MojangManager.addUsernameToCatch(teammitglied);
		
		if(!isBanned(uuid)) {
			int add = timevalue.duration;
			
			SQL.onUpdate("INSERT INTO bans(uuid, grund, until, teammitglied) VALUES('" + uuid.toString() + "', '" + grund + "', DATETIME(CURRENT_TIMESTAMP, '+" + add + " day'), '" + teammitglied.toString() + "')");
			
			Player banned;
			if((banned = Bukkit.getPlayer(uuid)) != null) {
				banned.kickPlayer("§cYou have been banned.");
			}
		}
		else {
			//Whats wrong with ya?
			
			
		}
	}
}
