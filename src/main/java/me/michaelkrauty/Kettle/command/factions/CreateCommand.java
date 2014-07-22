package me.michaelkrauty.Kettle.command.factions;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created on 7/2/2014.
 *
 * @author michaelkrauty
 */
public class CreateCommand {

	public CreateCommand(Kettle kettle, Player player, String[] args) {
		if (kettle.objects.getFaction(args[1]) != null) {
			player.sendMessage(ChatColor.RED + "The faction " + args[1] + " already exists.");
			return;
		}
		if (kettle.objects.getUser(player).getFaction() != null) {
			player.sendMessage(ChatColor.RED + "You already belong to a faction!");
			return;
		}
		String pattern = "^[a-zA-Z0-9]*$";
		if (!args[1].matches(pattern)) {
			player.sendMessage(ChatColor.RED + "Faction name must be alphanumeric! (numbers and letters)");
			return;
		}
		kettle.objects.createFaction(args[1], player.getUniqueId());
		kettle.objects.getUser(player).setFaction(args[1]);
		player.sendMessage(ChatColor.GRAY + "Founded the faction " + args[1]);
	}
}
