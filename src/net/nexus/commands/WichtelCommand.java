package net.nexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import net.nexus.NexusJava;
import net.nexus.manager.MessagesManager;
import net.nexus.manager.MojangManager;
import net.nexus.manager.Region;
import net.nexus.sql.SQL;
import net.nexus.utils.LocationUtils;

public class WichtelCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String alias, String[] args) {
	
		if(s instanceof Player) {
			Player p = (Player) s;
			
			if(args.length >= 1) {
				String sub = args[0];
				
				if(sub.equalsIgnoreCase("create")) {
					if(p.hasPermission(new Permission("nexus.wichteln.create", PermissionDefault.OP))) {
						WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
						Selection sel = worldEdit.getSelection(p);

						if (sel != null) {
						    Location vec1 = sel.getMinimumPoint();
						    Location vec2 = sel.getMaximumPoint();
							
							String str = LocationUtils.Vecs2Str(p.getWorld().getName(), vec1, vec2);
							
							SQL.onUpdate("INSERT INTO wichteln(location) VALUES('" + str + "')");
							NexusJava.INSTANCE.getRegManager().add(LocationUtils.Str2Region(str));
							p.sendMessage(MessagesManager.getMesage("Wichteln.SuccessCreated"));
						}
						else {
							p.sendMessage(MessagesManager.getMesage("Wichteln.NoWorldEditSelection"));
						}
					}
					else {
						p.sendMessage(MessagesManager.getMesage("NoPerms"));
					}
				}
				else if(sub.equalsIgnoreCase("list")) {
					if(p.hasPermission(new Permission("nexus.wichteln.create", PermissionDefault.OP))) {
						NexusJava.INSTANCE.getRegManager().sendInfo(p);
					}
					else {
						p.sendMessage(MessagesManager.getMesage("NoPerms"));
					}
				}
				else if(sub.equalsIgnoreCase("set")) {
					if(args.length == 2) {
						if(p.hasPermission(new Permission("nexus.wichteln.create", PermissionDefault.OP))) {

							Region reg;
							if((reg = NexusJava.INSTANCE.getRegManager().getRegion(p.getLocation())) != null) {
								reg.teleport(p);
								
								OfflinePlayer of;
								
								if((of = Bukkit.getOfflinePlayer(args[1])) != null) {
									if(of.getUniqueId() != null) {
										SQL.onUpdate("UPDATE wichteln SET uuid = '" + of.getUniqueId().toString() + "' WHERE location = '" + LocationUtils.Region2Str(reg) + "'");
										MojangManager.addUsernameToCatch(of.getUniqueId());
										p.sendMessage(MessagesManager.getMesage("Wichteln.SuccessSet"));
									}
									else {
										p.sendMessage(MessagesManager.getMesage("Wichteln.NotAUser", args[1]));
									}
								}
								else {
									p.sendMessage(MessagesManager.getMesage("Wichteln.NotAUser", args[1]));
								}
							}
							else {
								p.sendMessage(MessagesManager.getMesage("Wichteln.NotInRegion"));
							}
						}
						else {
							p.sendMessage(MessagesManager.getMesage("NoPerms"));
						}
					}
					else {
						sendHelp(s);
					}
				}
				else if(sub.equalsIgnoreCase("helper")) {
					if(args.length == 2) {


							Region reg;
							if((reg = NexusJava.INSTANCE.getRegManager().getRegion(p.getUniqueId())) != null) {
								OfflinePlayer of;
								
								if((of = Bukkit.getOfflinePlayer(args[1])) != null) {
									if(of.getUniqueId() != null) {
										if(reg.getPartner().equalsIgnoreCase(of.getUniqueId().toString()) || reg.getPartner().equalsIgnoreCase("Niemand")) {
											p.sendMessage(MessagesManager.getMesage("Wichteln.NotAValidUser", args[1]));
											return true;
										}
										
										SQL.onUpdate("UPDATE wichteln SET helper = '" + of.getUniqueId().toString() + "' WHERE location = '" + LocationUtils.Region2Str(reg) + "'");
										MojangManager.addUsernameToCatch(of.getUniqueId());
										p.sendMessage(MessagesManager.getMesage("Wichteln.SuccessSet"));
									}
									else {
										p.sendMessage(MessagesManager.getMesage("Wichteln.NotAUser", args[1]));
									}
								}
								else {
									p.sendMessage(MessagesManager.getMesage("Wichteln.NotAUser", args[1]));
								}
							}
							else {
								p.sendMessage(MessagesManager.getMesage("Wichteln.NoRegion"));
							}
					}
					else {
						sendHelp(s);
					}
				}
				else if(sub.equalsIgnoreCase("add")) {
					if(args.length == 2) {
						if(p.hasPermission(new Permission("nexus.wichteln.create", PermissionDefault.OP))) {
							OfflinePlayer of;
							
							if((of = Bukkit.getOfflinePlayer(args[1])) != null) {
								if(of.getUniqueId() != null) {
									MojangManager.addUsernameToCatch(of.getUniqueId());
									NexusJava.INSTANCE.getRegManager().addToLosTopf(of.getUniqueId());
									p.sendMessage(MessagesManager.getMesage("Wichteln.SuccessAdd", args[1]));
								}
								else {
									p.sendMessage(MessagesManager.getMesage("Wichteln.NotAUser", args[1]));
								}
							}
							else {
								p.sendMessage(MessagesManager.getMesage("Wichteln.NotAUser", args[1]));
							}
						}
						else {
							p.sendMessage(MessagesManager.getMesage("NoPerms"));
						}
					}
					else {
						sendHelp(s);
					}
				}
				else if(sub.equalsIgnoreCase("losen")) {
					if(p.hasPermission(new Permission("nexus.wichteln.create", PermissionDefault.OP))) {
						NexusJava.INSTANCE.getRegManager().losen();
					}
					else {
						p.sendMessage(MessagesManager.getMesage("NoPerms"));
					}
				}
				else if(sub.equalsIgnoreCase("tp")) {
					
					Region reg;
					if((reg = NexusJava.INSTANCE.getRegManager().getRegion(p.getUniqueId())) != null) {
						reg.teleport(p);
					}
					else {
						p.sendMessage(MessagesManager.getMesage("Wichteln.NoRegion"));
					}
				}
				else {
					sendHelp(s);
				}
			} else {
				//Send Help xD
				
				sendHelp(s);
			}
		}
		else {
			s.sendMessage("§c§oDies ist kein Kommando für die Konsole.");
		}
		
		return true;
	}

	
	public void sendHelp(CommandSender s) {
		s.sendMessage("  §e§lHelp");
		
		if(s.hasPermission(new Permission("nexus.wichteln.create", PermissionDefault.OP))) {
			s.sendMessage("§7§o/wichteln create §f- Erstelle einen neuen Wichtelbaukasten.");
			s.sendMessage("§7§o/wichteln set [Name] §f- Setze den Creator des Baukastens.");
			s.sendMessage("§7§o/wichteln list §f- Liste alle Wichtelbaukasten auf.");
			s.sendMessage("§7§o/wichteln add [Name] §f- Füge jemanden in den Lostopf hinzu.");
			s.sendMessage("§7§o/wichteln losen §f- Auslosen!");
		}
		s.sendMessage("§7§o/wichteln helper [Name] §f- Helfer hinzufügen.");
	}
}
