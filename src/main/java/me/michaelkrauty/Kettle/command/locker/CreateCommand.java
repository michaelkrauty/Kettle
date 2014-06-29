package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class CreateCommand {

	public CreateCommand(CommandSender sender, Kettle kettle) {
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 10);
		Location targetBlockLocation = targetBlock.getLocation();
		if (targetBlock.getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		if (kettle.sql.lockerExists(targetBlockLocation)) {
			player.sendMessage(ChatColor.GRAY + "That chest is already locked.");
			return;
		}
		kettle.sql.addLocker(targetBlockLocation, player.getUniqueId().toString(), kettle.getConfigFile().getLong("chest_expiry"));
		World w = targetBlockLocation.getWorld();
		int x = targetBlockLocation.getBlockX();
		int y = targetBlockLocation.getBlockY();
		int z = targetBlockLocation.getBlockZ();
		if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x + 1, y, z);
			kettle.sql.addLocker(loc, player.getUniqueId().toString(), kettle.getConfigFile().getLong("chest_expiry"));
		}
		if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x - 1, y, z);
			kettle.sql.addLocker(loc, player.getUniqueId().toString(), kettle.getConfigFile().getLong("chest_expiry"));
		}
		if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z + 1);
			kettle.sql.addLocker(loc, player.getUniqueId().toString(), kettle.getConfigFile().getLong("chest_expiry"));
		}
		if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z - 1);
			kettle.sql.addLocker(loc, player.getUniqueId().toString(), kettle.getConfigFile().getLong("chest_expiry"));
		}
		player.sendMessage(ChatColor.GRAY + "You successfully locked that chest.");
		return;
	}
}
