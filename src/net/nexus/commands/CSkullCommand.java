package net.nexus.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import net.nexus.utils.GuiUtils;

public class CSkullCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String alias, String[] args) {
		
		if(s instanceof Player) {
			Player p = (Player) s;
			
			if(!p.hasPermission(new Permission("nexus.cskull", PermissionDefault.OP))) {
				p.sendMessage("§e[Nexus] [Fehler] §o[FailNoPerms] §f§oDies ist dir anscheinend nicht erlaubt.");
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 80, 80);
				return true;
			}
			
			
			if(args.length == 1) {
				String url = args[0];
				
				p.getInventory().addItem(GuiUtils.getSkull(url, "§7MiniBlock", null, 1));
				p.playSound(p.getLocation(), Sound.ENTITY_WITCH_THROW, 80, 80);
			}
			else {
				p.sendMessage("§cError §6>> §f/cskull <UrlDerTextur>");
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 80, 80);
			}
		}
		else {
			s.sendMessage("Nur ein Spieler kann so einen Befehl ausführen.");
		}
		
		return true;
	}

}
