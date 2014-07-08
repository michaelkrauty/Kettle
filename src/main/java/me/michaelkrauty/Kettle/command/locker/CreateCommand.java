package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Objects;
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
		Objects objects = kettle.objects;
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 10);
		Location targetBlockLocation = targetBlock.getLocation();
		if (targetBlock == null || targetBlock.getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		if (objects.getLocker(targetBlockLocation) != null) {
			player.sendMessage(ChatColor.GRAY + "That chest is already locked.");
			return;
		}
		objects.createLocker(targetBlockLocation, player);
		World w = targetBlockLocation.getWorld();
		int x = targetBlockLocation.getBlockX();
		int y = targetBlockLocation.getBlockY();
		int z = targetBlockLocation.getBlockZ();
		if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x + 1, y, z);
			objects.createLocker(loc, player);
		}
		if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x - 1, y, z);
			objects.createLocker(loc, player);
		}
		if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z + 1);
			objects.createLocker(loc, player);
		}
		if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z - 1);
			objects.createLocker(loc, player);
		}
		player.sendMessage(ChatColor.GRAY + "You successfully locked that chest.");
		return;
	}
}
