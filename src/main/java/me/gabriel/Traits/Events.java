package me.gabriel.Traits;

import me.gabriel.Traits.data.TraitsData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
	//used in MainPlugin's Events.respawn();
	public void reapply(Player player){
		TraitsData data = TraitsData.get(player);
		int hBonus = TraitsData.bonus(data.getHealthLevel());
		int sBonus = TraitsData.bonus(data.getSpeedLevel());
		player.setMaxHealth(TraitsData.HEALTH_TICK * hBonus + 20);
		player.setHealth(player.getMaxHealth());
		player.setWalkSpeed(TraitsData.SPEED_TICK * sBonus + 0.2f);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void forDamageAndDefense(EntityDamageByEntityEvent event) {
		 if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			 Player damager = (Player) event.getDamager();
			 Player damagee = (Player) event.getEntity();
			 TraitsData damagerData = TraitsData.get(damager);
			 TraitsData damageeData = TraitsData.get(damagee);
			 event.setDamage(((event.getDamage()/100) * TraitsData.bonus(damagerData.getAttackLevel()) + event.getDamage())
					 - ((event.getDamage()/100) * TraitsData.bonus(damageeData.getDefenseLevel())));
		 }
	}
}
