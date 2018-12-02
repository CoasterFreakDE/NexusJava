package net.nexus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.nexus.manager.Region;

public class LocationUtils {

	
	public static String Vecs2Str(String world, Location vec, Location vec2) {
		String str = world;
		
		str += ";" + vec.getBlockX() + ";" + vec.getBlockZ() + ";" + vec2.getBlockX() + ";" + vec2.getBlockZ();
		
		return str;
	}
	
	public static String Region2Str(Region reg) {
		String str = reg.getWorld().getName();
		
		str += ";" + reg.getX1() + ";" + reg.getZ1() + ";" + reg.getX2() + ";" + reg.getZ2();
		
		return str;
	}
	
	
	public static Region Str2Region(String str) throws NumberFormatException {
		String[] args = str.split(";");
		return new Region(Bukkit.getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
	}
}
