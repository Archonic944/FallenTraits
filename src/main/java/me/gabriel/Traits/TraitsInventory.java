package me.gabriel.Traits;

import me.gabriel.Traits.data.TraitsData;
import me.zach.DesertMC.Utils.Config.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TraitsInventory implements Listener {

	public static ItemStack createTrait(String displayname, Material material) {
		ItemStack trait = new ItemStack(material);
		ItemMeta meta = trait.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + displayname);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.YELLOW + "Click to open the trait menu for " + displayname);
		meta.setLore(lore);
		trait.setItemMeta(meta);
		return trait;
	}

	public static void openTraitInventory(Player player) {
		Inventory i = Bukkit.getPluginManager().getPlugin("Traits").getServer().createInventory(null, 27, "Traits Menu");
		for (int j = 0; j < 27; j++) {
			ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
			ItemMeta em = empty.getItemMeta();
			em.setDisplayName(" ");
			empty.setItemMeta(em);
			i.setItem(j, empty);

		}

		i.setItem(10, createTrait("Health", Material.REDSTONE_BLOCK));
		i.setItem(12, createTrait("Speed", Material.NETHER_STAR));
		i.setItem(14, createTrait("Defense", Material.DIAMOND_CHESTPLATE));
		i.setItem(16, createTrait("Attack", Material.DIAMOND_SWORD));

		player.openInventory(i);
	}

	public static void openSpecificTraitInventory(String trait, Player player, Material material) {
		TraitsData data = TraitsData.get(player);
		int Level = data.levelGet(trait);
		int NextLevel = Level + 1;
		int Bonus = TraitsData.bonus(Level);
		int NextBonus = TraitsData.bonus(NextLevel);

		// Stats item
		ItemStack statsitem = new ItemStack(material);
		ItemMeta statsmeta = statsitem.getItemMeta();
		statsmeta.setDisplayName(
				ChatColor.GREEN + trait.substring(0, 1).toUpperCase() + trait.substring(1) + " Trait Stats");
		statsmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		ArrayList<String> statslore = new ArrayList<String>();
		statslore.add(ChatColor.YELLOW + "Current trait bonus: " + ChatColor.BLUE + Bonus + "% " + ChatColor.YELLOW
				+ "better " + trait);
		statslore.add(ChatColor.YELLOW + "Level: " + ChatColor.BLUE + Level);
		statslore.add(ChatColor.YELLOW + "Max level: " + ChatColor.BLUE + Level + "/20");
		statsmeta.setLore(statslore);
		statsitem.setItemMeta(statsmeta);

		// Upgrade item

		ItemStack upgradeitem = new ItemStack(Material.DIAMOND);
		ItemMeta upgrademeta = upgradeitem.getItemMeta();
		upgrademeta.setDisplayName(ChatColor.GREEN + "Train Trait");
		upgrademeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		ArrayList<String> upgradelore = new ArrayList<>();
		int gems = ConfigUtils.getGems(player);

		if (data.levelGet(trait) == 20) {
			upgrademeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
			upgrademeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			upgrademeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			upgradelore.add(ChatColor.GOLD + "You have maxed out this trait!");
			data.canUpgrade(trait, gems);
		} else {
			upgradelore.add(ChatColor.YELLOW + "Materials required to train this trait to level " + ChatColor.BLUE
					+ NextLevel + ChatColor.YELLOW + ": ");
			upgradelore.add(ChatColor.BLUE + ""
					+ TraitsData.ttsToNext(data.levelGet(trait))
					+ ChatColor.YELLOW + " Trait Tokens, " + ChatColor.BLUE
					+ TraitsData.gemsToNext(data.levelGet(trait))
					+ ChatColor.GREEN + " Gems");
			upgradelore.add(ChatColor.YELLOW + "Level " + ChatColor.BLUE + NextLevel + ChatColor.YELLOW
					+ " TOTAL rewards: " + NextBonus + "% " + ChatColor.YELLOW + "better " + trait + ".");

			if (data.canUpgrade(trait, gems)) {
				upgradelore
						.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "You have enough materials to train this trait!");
				upgradelore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click to upgrade!");
			} else {
				upgradelore.add(ChatColor.RED + "You do not have the required materials!");
			}
		}
		upgrademeta.setLore(upgradelore);
		upgradeitem.setItemMeta(upgrademeta);
		// TODO Autotrait

		ItemStack autotraititem = new ItemStack(Material.BARRIER);
		ItemMeta autotraitmeta = autotraititem.getItemMeta();
		autotraitmeta.setDisplayName(ChatColor.RED + "Auto Trait");
		autotraitmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		ArrayList<String> autotraitlore = new ArrayList<String>();
		autotraitlore.add(ChatColor.YELLOW + "This feature is coming in a later update!");
		autotraitmeta.setLore(autotraitlore);
		autotraititem.setItemMeta(autotraitmeta);

		// Trait Token Item
		ItemStack traittokenitem = new ItemStack(Material.DOUBLE_PLANT);
		ItemMeta traittokenmeta = traittokenitem.getItemMeta();
		traittokenmeta.setDisplayName(ChatColor.GREEN + "Trait Tokens: " + ChatColor.GREEN + data.getTraitTokens());
		traittokenitem.setItemMeta(traittokenmeta);

		// Gems item
		ItemStack gemsitem = new ItemStack(Material.EMERALD);
		ItemMeta gemsmeta = traittokenitem.getItemMeta();
		gemsmeta.setDisplayName(ChatColor.GREEN + "Gems: " + ChatColor.GREEN
				+ ConfigUtils.getGems(player));
		gemsitem.setItemMeta(gemsmeta);

		Inventory i = Bukkit.getServer().createInventory(null, 36,
				trait.substring(0, 1).toUpperCase() + trait.substring(1) + " Trait");

		for (int j = 0; j < 36; j++) {
			ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
			ItemMeta em = empty.getItemMeta();
			em.setDisplayName(" ");
			empty.setItemMeta(em);
			i.setItem(j, empty);

		}

		i.setItem(11, autotraititem);
		i.setItem(13, statsitem);
		i.setItem(35, traittokenitem);
		i.setItem(15, upgradeitem);
		i.setItem(27, gemsitem);

		player.openInventory(i);

	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory i = event.getClickedInventory();
		ItemStack item = event.getCurrentItem();
		TraitsData data = TraitsData.get(player);
		if (i == null) {
			return;
		}

		if (i.getTitle().equalsIgnoreCase("Traits Menu")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.NETHER_STAR)) {
				openSpecificTraitInventory("speed", player, Material.NETHER_STAR);
				return;
			}

			if (item.getType().equals(Material.REDSTONE_BLOCK)) {
				openSpecificTraitInventory("health", player, Material.REDSTONE_BLOCK);
				return;
			}

			if (item.getType().equals(Material.DIAMOND_CHESTPLATE)) {
				openSpecificTraitInventory("defense", player, Material.DIAMOND_CHESTPLATE);
				return;
			}

			if (item.getType().equals(Material.DIAMOND_SWORD)) {
				openSpecificTraitInventory("attack", player, Material.DIAMOND_SWORD);

				return;
			}
		}
		if (i.getTitle().equalsIgnoreCase("Health Trait") || i.getTitle().equalsIgnoreCase("Speed Trait")
				|| i.getTitle().equalsIgnoreCase("Defense Trait") || i.getTitle().equalsIgnoreCase("Attack Trait")) {
			event.setCancelled(true);
			String traitNoLowercase = i.getTitle().replaceAll(" Trait", "");
			String trait = traitNoLowercase.substring(0, 1).toLowerCase() + traitNoLowercase.substring(1);
			int Level = data.levelGet(trait);
			if (item.getType().equals(Material.DIAMOND)) {
				int gems = ConfigUtils.getGems(player);
				if (data.canUpgrade(trait, gems)) {
					ConfigUtils.deductGems(player, TraitsData.gemsToNext(data.levelGet(trait)));
					Level = Level + 1;
					data.levelSet(trait, Level);
					if(trait.equals("health") || trait.equals("speed")) new Events().forHSetAndSset(player);
					player.sendMessage(ChatColor.GREEN + "Trait trained to level " + Level + "!");
					if (Level >= 16) {
						if (trait.equalsIgnoreCase("health")) {
							openSpecificTraitInventory(trait, player, Material.REDSTONE_BLOCK);
							if (Level == 20) {
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 1);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 2);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 3);
								player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "TRAIT MAXED!");
							} else {
								player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);
							}
						}
						if (trait.equalsIgnoreCase("speed")) {
							openSpecificTraitInventory(trait, player, Material.NETHER_STAR);
							if (Level == 20) {
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 1);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 2);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 3);
								player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "TRAIT MAXED!");
							} else {
								player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

							}
						}
						if (trait.equalsIgnoreCase("attack")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_SWORD);
							if (Level == 20) {
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 1);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 2);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 3);
								player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "TRAIT MAXED!");
							} else {
								player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

							}
						}
						if (trait.equalsIgnoreCase("defense")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_CHESTPLATE);
							if (Level == 20) {
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 1);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 2);
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 10, 3);
								player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "TRAIT MAXED!");
							} else {
								player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

							}
						}
						return;
					}
					if (Level >= 12) {
						if (trait.equalsIgnoreCase("health")) {
							openSpecificTraitInventory(trait, player, Material.REDSTONE_BLOCK);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("speed")) {
							openSpecificTraitInventory(trait, player, Material.NETHER_STAR);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("attack")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_SWORD);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("defense")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_CHESTPLATE);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						return;
					}
					if (Level >= 8) {
						if (trait.equalsIgnoreCase("health")) {
							openSpecificTraitInventory(trait, player, Material.REDSTONE_BLOCK);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("speed")) {
							openSpecificTraitInventory(trait, player, Material.NETHER_STAR);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("attack")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_SWORD);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("defense")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_CHESTPLATE);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						return;
					}
					if (Level >= 4) {
						if (trait.equalsIgnoreCase("health")) {
							openSpecificTraitInventory(trait, player, Material.REDSTONE_BLOCK);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("speed")) {
							openSpecificTraitInventory(trait, player, Material.NETHER_STAR);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("attack")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_SWORD);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						if (trait.equalsIgnoreCase("defense")) {
							openSpecificTraitInventory(trait, player, Material.DIAMOND_CHESTPLATE);
							player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

						}
						return;
					}
					if (trait.equalsIgnoreCase("health")) {
						openSpecificTraitInventory(trait, player, Material.REDSTONE_BLOCK);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

					}
					if (trait.equalsIgnoreCase("speed")) {
						openSpecificTraitInventory(trait, player, Material.NETHER_STAR);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

					}
					if (trait.equalsIgnoreCase("attack")) {
						openSpecificTraitInventory(trait, player, Material.DIAMOND_SWORD);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);

					}
					if (trait.equalsIgnoreCase("defense")) {
						openSpecificTraitInventory(trait, player, Material.DIAMOND_CHESTPLATE);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);
					}
				} else {
					player.playSound(player.getLocation(), Sound.ANVIL_LAND, 10, 3);
				}
			}
		}
	}
}