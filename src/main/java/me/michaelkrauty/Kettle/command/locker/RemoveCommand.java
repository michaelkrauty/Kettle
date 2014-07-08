package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Locker;
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
public class RemoveCommand {

	public RemoveCommand(CommandSender sender, Kettle kettle) {
		Objects objects = kettle.objects;
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 10);
		Location targetBlockLocation = targetBlock.getLocation();
		if (targetBlock.getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		Locker locker = objects.getLocker(targetBlockLocation);
		if (locker == null) {
			player.sendMessage(ChatColor.GRAY + "That chest isn't locked!");
			return;
		}
		if (!locker.userIsOwner(player.getUniqueId())) {
			player.sendMessage(ChatColor.GRAY + "You don't own that chest!");
			return;
		}
		locker.delete();

		World w = targetBlockLocation.getWorld();
		int x = targetBlockLocation.getBlockX();
		int y = targetBlockLocation.getBlockY();
		int z = targetBlockLocation.getBlockZ();
		if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x + 1, y, z);
			Locker lock;
			if ((lock = objects.getLocker(loc)) != null) {
				lock.delete();
			}
		}
		if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x - 1, y, z);
			Locker lock;
			if ((lock = objects.getLocker(loc)) != null) {
				lock.delete();
			}
		}
		if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z + 1);
			Locker lock;
			if ((lock = objects.getLocker(loc)) != null) {
				lock.delete();
			}
		}
		if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z - 1);
			Locker lock;
			if ((lock = objects.getLocker(loc)) != null) {
				lock.delete();
			}
		}
		player.sendMessage(ChatColor.GRAY + "You successfully removed the lock from that chest.");
		return;
	}
}
