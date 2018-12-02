package net.nexus.utils;

import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileConfig extends YamlConfiguration {

	public String path;
	public String seperator;
	
	public FileConfig(String folder, String fileName) {
		seperator = System.getProperty("file.seperator");
		
		if(seperator == null) {
			seperator = "/";
		}
		
		path = "plugins" + seperator + folder + seperator + fileName;
		
		try {
			load(path);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void saveConfig() {
		try {
			save(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
