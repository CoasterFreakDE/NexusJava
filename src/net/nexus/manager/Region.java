package net.nexus.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.nexus.sql.SQL;
import net.nexus.utils.LocationUtils;

public class Region {

	private World world;
	private int x1, x2, z1, z2;
	
	/**
	 * @param world
	 * @param x1
	 * @param x2
	 * @param z1
	 * @param z2
	 */
	public Region(World world, int x1, int z1, int x2, int z2) {
		super();
		this.world = world;
		this.x1 = x1;
		this.x2 = x2;
		this.z1 = z1;
		this.z2 = z2;
	}
	
	@Override
	public String toString() {
		String str = getWorld().getName();
		
		str += ";" + getX1() + ";" + getZ1() + ";" + getX2() + ";" + getZ2();
		
		return str;
	}

	public boolean isInRegion(Location loc) {
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		
		if(((x > x1 && x < x2)) && ((z > z1 && z < z2))) {
			return true;
		}
		
		
		return false;
	}
	
	
	public String getOwner() {
		String owner = "Niemand";
		
		ResultSet set = SQL.onQuery("SELECT * FROM wichteln WHERE location = '" + LocationUtils.Region2Str(this) + "'");
		
		try {
			while(set.next()) {
				String self = set.getString("uuid");
				if(!(self == null) && !self.isEmpty() && !self.equals("")) {
					owner = self;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return owner;
	}
	
	public String getHelper() {
		String owner = "Niemand";
		
		ResultSet set = SQL.onQuery("SELECT * FROM wichteln WHERE location = '" + LocationUtils.Region2Str(this) + "'");
		
		try {
			while(set.next()) {
				String self = set.getString("helper");
				if(!(self == null) && !self.isEmpty() && !self.equals("")) {
					owner = self;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return owner;
	}
	
	public void announcePartner() {
		Player p;
		String owner = getOwner();
		
		if(owner.equalsIgnoreCase("Niemand"))
			return;
		
		if((p = Bukkit.getPlayer(UUID.fromString(owner))) != null) {
			String partner = getPartner();
			
			if(partner.equalsIgnoreCase("Niemand"))
				return;
			
			p.sendTitle("§7§oWeihnachtswichteln", "§eDein Partner ist §9" + MojangManager.getUsernameFromCatch(UUID.fromString(partner)), 20, 200, 20);
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 20f, 20f);
		}
	}
	
	public String getPartner() {
		String owner = "Niemand";
		
		ResultSet set = SQL.onQuery("SELECT * FROM wichteln WHERE location = '" + LocationUtils.Region2Str(this) + "'");
		
		try {
			while(set.next()) {
				String self = set.getString("partner");
				if(!(self == null) && !self.isEmpty() && !self.equals("")) {
					owner = self;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return owner;
	}
	
	public void teleport(Player p) {
		int midX = (this.x1 + this.x2) / 2;
		int midZ = (this.z1 + this.z2) / 2;
		int midY = 1 + world.getHighestBlockAt(midX, midZ).getLocation().getBlockY();
		
		p.teleport(new Location(getWorld(), midX, midY, midZ));
	}
	
	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @return the x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * @param x1 the x1 to set
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * @return the x2
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * @return the z1
	 */
	public int getZ1() {
		return z1;
	}

	/**
	 * @param z1 the z1 to set
	 */
	public void setZ1(int z1) {
		this.z1 = z1;
	}

	/**
	 * @return the z2
	 */
	public int getZ2() {
		return z2;
	}

	/**
	 * @param z2 the z2 to set
	 */
	public void setZ2(int z2) {
		this.z2 = z2;
	}


}
