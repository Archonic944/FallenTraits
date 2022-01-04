package me.gabriel.Traits;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class Commands implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		Plugin plugin = TraitsMain.getPlugin(TraitsMain.class);

		if (cmd.getName().equalsIgnoreCase("traits") && player.hasPermission("admin")) {

			TraitsInventory.openTraitInventory(player);
		}
		return true;
	}

}
