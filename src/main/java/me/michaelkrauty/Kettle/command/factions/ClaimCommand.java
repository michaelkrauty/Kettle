package me.michaelkrauty.Kettle.command.factions;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Faction;
import me.michaelkrauty.Kettle.Objects.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created on 7/24/2014.
 *
 * @author michaelkrauty
 */
public class ClaimCommand {

	public ClaimCommand(Kettle kettle, Player player, String[] args) {
		if (kettle.objects.getUser(player).getFaction() == null) {
			player.sendMessage(ChatColor.RED + "You don't belong to a faction!");
			return;
		}
		User user = kettle.objects.getUser(player);
		Faction faction = kettle.objects.getFaction(user.getFaction());
		faction.addLand(player.getLocation().getChunk());
		player.sendMessage(ChatColor.GRAY + "Claimed this land for your faction.");
		return;
	}
}
