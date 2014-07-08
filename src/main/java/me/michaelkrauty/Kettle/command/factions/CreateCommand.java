package me.michaelkrauty.Kettle.command.factions;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Objects;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created on 7/2/2014.
 *
 * @author michaelkrauty
 */
public class CreateCommand {

	public CreateCommand(Kettle kettle, Player player, String[] args) {
		Objects objects = kettle.objects;
		if (objects.getFaction(args[1]) != null) {
			player.sendMessage(ChatColor.RED + "The faction " + args[1] + " already exists.");
			return;
		}
		if (objects.getUser(player).getFaction() != null) {
			player.sendMessage(ChatColor.RED + "You already belong to a faction!");
			return;
		}
		objects.createFaction(args[1], player.getUniqueId());
		objects.getUser(player).setFaction(args[1]);
	}
}
