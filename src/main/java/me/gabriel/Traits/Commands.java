package me.gabriel.Traits;

import me.zach.DesertMC.Utils.MiscUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Commands implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("traits") && MiscUtils.isAdmin(player)) {
			TraitsInventory.openTraitInventory(player);
		}
		return true;
	}
}
