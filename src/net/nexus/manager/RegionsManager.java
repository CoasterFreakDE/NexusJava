package net.nexus.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.nexus.NexusJava;
import net.nexus.sql.SQL;

public class RegionsManager implements Listener {

	private List<Region> regions;
	private List<UUID> lostopf;
	
	public RegionsManager() {
		this.regions = new ArrayList<Region>();
		this.lostopf = new ArrayList<UUID>();
		
		Bukkit.getPluginManager().registerEvents(this, NexusJava.INSTANCE);
	}
	
	public void add(Region region) {
		this.regions.add(region);
	}
	
	public int size() {
		return this.regions.size();
	}
	
	@EventHandler
	public void onEnterRegion(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		for(Region region : this.regions) {
			if(region.isInRegion(e.getTo()) && !region.isInRegion(e.getFrom())) {
				//New in Region
				String owner = region.getOwner();
				String helper = region.getHelper();
				boolean help = false;
				
				if(owner.equalsIgnoreCase("Niemand")) {
					p.sendTitle("§7§oWeihnachtswichteln", "§eRegion frei", 5, 20, 5);
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 20f, 20f);
				}
				else {
					if(!helper.equalsIgnoreCase("Niemand")) {
						if(p.getUniqueId().equals(UUID.fromString(helper))) {
							help = true;
						}
					}
					
					
 					if(p.getUniqueId().equals(UUID.fromString(owner)) || help) {
						String partner = region.getPartner();
						String ow = MojangManager.getUsernameFromCatch(UUID.fromString(owner));
						
						
						if(partner.equalsIgnoreCase("Niemand")) {
							p.sendTitle(help ? "§7§oHilfe für §a" + ow : "§7§oWeihnachtswichteln", "§eNoch kein Partner", 5, 20, 5);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 20f, 20f);
						}
						else {
							String puser = MojangManager.getUsernameFromCatch(UUID.fromString(partner));
							
							p.sendTitle(help ? "§7§oHilfe für §a" + ow : "§7§oWeihnachtswichteln", "§7Du baust für §a" + puser, 5, 20, 5);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 20f, 20f);
						}
					}
					else {
						p.sendTitle("§7§oWeihnachtswichteln", "§cDu darfst hier nicht rein.", 5, 20, 5);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 20f, 20f);
						
						p.teleport(e.getFrom());
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		
		for(Region region : this.regions) {
			if(region.isInRegion(e.getTo()) && !region.isInRegion(e.getFrom())) {
				//New in Region
				String owner = region.getOwner();
				String helper = region.getHelper();
				boolean help = false;
				
				if(owner.equalsIgnoreCase("Niemand")) {
					p.sendTitle("§7§oWeihnachtswichteln", "§eRegion frei", 5, 20, 5);
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 20f, 20f);
				}
				else {
					if(!helper.equalsIgnoreCase("Niemand")) {
						if(p.getUniqueId().equals(UUID.fromString(helper))) {
							help = true;
						}
					}
					
					
 					if(p.getUniqueId().equals(UUID.fromString(owner)) || help) {
						String partner = region.getPartner();
						String ow = MojangManager.getUsernameFromCatch(UUID.fromString(owner));
						
						
						if(partner.equalsIgnoreCase("Niemand")) {
							p.sendTitle(help ? "§7§oHilfe für §a" + ow : "§7§oWeihnachtswichteln", "§eNoch kein Partner", 5, 20, 5);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 20f, 20f);
						}
						else {
							String puser = MojangManager.getUsernameFromCatch(UUID.fromString(partner));
							
							p.sendTitle(help ? "§7§oHilfe für §a" + ow : "§7§oWeihnachtswichteln", "§7Du baust für §a" + puser, 5, 20, 5);
							p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 20f, 20f);
						}
					}
					else {
						p.sendTitle("§7§oWeihnachtswichteln", "§cDu darfst hier nicht rein.", 5, 20, 5);
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASS, 20f, 20f);
						
						p.teleport(e.getFrom());
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	/**
	 * Get Region by Location within
	 * @param loc
	 * @return
	 */
	public Region getRegion(Location loc) {
		for(Region region : this.regions) {
			if(region.isInRegion(loc)) return region;
		}
		return null;
	}
	
	/**
	 * Get Region by Creator
	 * @param uuid
	 * @return
	 */
	public Region getRegion(UUID uuid) {
		for(Region region : this.regions) {
			if(region.getOwner().equals(uuid.toString())) return region;
		}
		return null;
	}
	
	public void addToLosTopf(UUID uuid) {
		this.lostopf.add(uuid);
	}
	
	public void sendInfo(Player p) {
		if(this.regions.size() > 0) {
			
			p.sendMessage(MessagesManager.getMesage("Wichteln.Info.Regions", this.regions.size()));
			
			for(Region region : this.regions) {
				String owner = "Niemand";
				
				if(!region.getOwner().equalsIgnoreCase("Niemand")) {
					owner = MojangManager.getUsernameFromCatch(UUID.fromString(region.getOwner()));
				}
				
				p.sendMessage(" §e> §7§o" + owner + " - §f§o" + region.toString());
			}
		}
		else {
			p.sendMessage(MessagesManager.getMesage("Wichteln.Info.NoRegions"));
		}
	}
	
	/**
	 * Partner auslosen
	 */
	public void losen() {
		if((this.lostopf.size()) <= 0) {
			
			System.out.println("Failed");
			return;
		}
		
		ConcurrentHashMap<UUID, UUID> parship = new ConcurrentHashMap<>();
		List<UUID> zugtopf = new ArrayList<>();
		List<UUID> partnertopf = new ArrayList<>();
		
		zugtopf.addAll(this.lostopf);
		partnertopf.addAll(this.lostopf);
		
		Random rand = new Random();
		
		int counter = 0;
		
		UUID next;
		while(!zugtopf.isEmpty()) {
			if(counter < 100) {
				counter++;
			}
			else {
				losen();
				return;
			}
			
			next = zugtopf.remove(0);
			
			if(!partnertopf.isEmpty()) {
				UUID partner = partnertopf.get(rand.nextInt(partnertopf.size()));
				
				if(next.equals(partner)) {
					zugtopf.add(next);
					continue;
				}
				
				parship.put(next, partner);
				partnertopf.remove(partner);
			}
			else {
				
			}
		}
		
		
		for(UUID uuid : parship.keySet()) {
			SQL.onUpdate("UPDATE wichteln SET partner = '" + parship.get(uuid).toString() + "' WHERE uuid = '" + uuid.toString() + "'");
		}
		
		this.regions.forEach(reg -> reg.announcePartner());
	}

	
	
	
	
}
