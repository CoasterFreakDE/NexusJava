package net.nexus.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.nexus.enums.TimeValue;
import net.nexus.sql.SQLManager;

public class TestCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String alias, String[] args) {
		
		SQLManager.banUUID(UUID.fromString("03094b13-35f6-43de-8d13-c37c3a9b941a"), "Hacking [LIVE]", TimeValue.MONTH, UUID.fromString("03094b13-35f6-43de-8d13-c37c3a9b941a"));
		
		return true;
	}

}
