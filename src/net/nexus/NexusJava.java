package net.nexus;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.nexus.commands.CSkullCommand;
import net.nexus.commands.TestCommand;
import net.nexus.commands.WichtelCommand;
import net.nexus.manager.MessagesManager;
import net.nexus.manager.RegionsManager;
import net.nexus.sql.SQL;
import net.nexus.sql.SQLManager;

public class NexusJava extends JavaPlugin {

	public static NexusJava INSTANCE;
	private RegionsManager regManager;
	
	/**
	 * Main Class of Plugin
	 */
	public NexusJava() {
		INSTANCE = this;
	}
	
	@Override
	public void onEnable() {
		this.setRegManager(new RegionsManager());
		
		SQL.connect();
		SQLManager.createNecessaryDatabases();
		SQLManager.catchUsernames();
		
		MessagesManager.refreshConfigFile();
		
		
		
		
		register();
	}
	
	@Override
	public void onDisable() {
		SQL.disconnect();
	}
	
	public void register() {
		Bukkit.getPluginCommand("cskull").setExecutor(new CSkullCommand());
		Bukkit.getPluginCommand("punish").setExecutor(new TestCommand());
		Bukkit.getPluginCommand("wichteln").setExecutor(new WichtelCommand());
	}

	/**
	 * @return the regManager
	 */
	public RegionsManager getRegManager() {
		return regManager;
	}

	/**
	 * @param regManager the regManager to set
	 */
	public void setRegManager(RegionsManager regManager) {
		this.regManager = regManager;
	}
}
