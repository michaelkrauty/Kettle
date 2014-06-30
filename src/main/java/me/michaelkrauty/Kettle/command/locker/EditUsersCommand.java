package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Locker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class EditUsersCommand {

	public EditUsersCommand(CommandSender sender, String[] args, Kettle kettle) {
		for (int i = 0; i < kettle.lockers.size(); i++ ) {
			System.out.println(kettle.lockers.get(i).getLocation().toString());
		}
		Player player = (Player) sender;
		if (player.getTargetBlock(null, 10).getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		Location targetBlockLocation = player.getTargetBlock(null, 10).getLocation();
		Locker locker;
		if ((locker = kettle.getLocker(targetBlockLocation)) == null) {
			player.sendMessage(ChatColor.GRAY + "That chest isn't locked.");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.GRAY + "Users: " + locker.getUserNames());
			player.sendMessage(ChatColor.GRAY + "Add/Remove players with " + ChatColor.RED + "/locker users " + ChatColor.GRAY + "<" + ChatColor.GREEN + "add" + ChatColor.GRAY + "/" + ChatColor.GREEN + "remove" + ChatColor.GRAY + "> <" + ChatColor.GREEN + "user" + ChatColor.GRAY + ">" + ChatColor.GRAY + ".");
			return;
		}
		if (locker.getOwner() != player.getUniqueId()) {
			player.sendMessage(ChatColor.GRAY + "You don't own that locker!");
			return;
		}
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("add")) {
				locker.addUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());


				World w = targetBlockLocation.getWorld();
				int x = targetBlockLocation.getBlockX();
				int y = targetBlockLocation.getBlockY();
				int z = targetBlockLocation.getBlockZ();
				if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x + 1, y, z);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).addUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}
				if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x - 1, y, z);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).addUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}
				if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z + 1);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).addUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}
				if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z - 1);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).addUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}


				player.sendMessage(ChatColor.GRAY + "Added the player " + args[2] + " to the locker.");
				return;
			}
			if (args[1].equalsIgnoreCase("remove")) {
				if (!locker.userHasAccess(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId())) {
					player.sendMessage(ChatColor.GRAY + "That player isn't added to that chest.");
					return;
				}
				locker.removeUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());


				World w = targetBlockLocation.getWorld();
				int x = targetBlockLocation.getBlockX();
				int y = targetBlockLocation.getBlockY();
				int z = targetBlockLocation.getBlockZ();
				if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x + 1, y, z);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).removeUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}
				if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x - 1, y, z);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).removeUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}
				if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z + 1);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).removeUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}
				if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z - 1);
					if (kettle.getLocker(loc) != null)
					kettle.getLocker(loc).removeUser(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId());
				}


				player.sendMessage(ChatColor.GRAY + "Removed the player " + args[2] + " from the locker.");
				return;
			}
			return;
		}
		return;
	}
}
