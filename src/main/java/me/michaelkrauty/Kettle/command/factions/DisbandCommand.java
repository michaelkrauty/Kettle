package me.michaelkrauty.Kettle.command.factions;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Faction;
import me.michaelkrauty.Kettle.Objects.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created on 7/9/2014.
 *
 * @author michaelkrauty
 */
public class DisbandCommand {

	public DisbandCommand(Kettle kettle, Player player, String[] args) {
		User user = kettle.objects.getUser(player);
		if (user.getFaction() == null) {
			player.sendMessage(ChatColor.RED + "You don't belong to any faction!");
			return;
		}
		Faction faction = kettle.objects.getFaction(user.getFaction());
		if (faction.getOwner() != player.getUniqueId()) {
			player.sendMessage(ChatColor.RED + "You don't own the faction you're in!");
			return;
		}
		kettle.objects.getFaction(kettle.objects.getUser(player).getFaction()).deleteFactionFile();
		kettle.objects.factions.remove(kettle.objects.getFaction(kettle.objects.getUser(player).getFaction()));
		kettle.objects.getUser(player).setFaction(null);
		player.sendMessage(ChatColor.GRAY + "Disbanded the faction.");
	}
}
