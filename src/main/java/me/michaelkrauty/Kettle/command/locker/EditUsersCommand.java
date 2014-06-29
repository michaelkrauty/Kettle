package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class EditUsersCommand {

	public EditUsersCommand(CommandSender sender, String[] args, Kettle kettle) {
		Player player = (Player) sender;
		if (player.getTargetBlock(null, 10).getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		Location targetBlockLocation = player.getTargetBlock(null, 10).getLocation();
		if (!kettle.sql.lockerExists(targetBlockLocation)) {
			player.sendMessage(ChatColor.GRAY + "That chest isn't locked.");
			return;
		}
		HashMap lockerInfo = kettle.sql.getLocker(targetBlockLocation);
		ArrayList<String> userNames = new ArrayList<String>();
		ArrayList<String> userUUIDS = new ArrayList<String>();
		for (String uuid : ((String) lockerInfo.get("users")).split(",")) {
			UUID uuid1 = UUID.fromString(uuid);
			String name = kettle.getServer().getOfflinePlayer(uuid1).getName();
			userNames.add(name);
			userUUIDS.add(uuid1.toString());
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.GRAY + "Expires in: " + lockerInfo.get("expiry") + " minutes.");
			player.sendMessage(ChatColor.GRAY + "Users: " + userNames.toString());
			player.sendMessage(ChatColor.GRAY + "Add/Remove players with " + ChatColor.RED + "/locker users " + ChatColor.GRAY + "<" + ChatColor.GREEN + "add" + ChatColor.GRAY + "/" + ChatColor.GREEN + "remove" + ChatColor.GRAY + "> <" + ChatColor.GREEN + "user" + ChatColor.GRAY + ">" + ChatColor.GRAY + ".");
			return;
		}
		if (!lockerInfo.get("owner").equals(player.getUniqueId().toString())) {
			player.sendMessage(ChatColor.GRAY + "You don't own that locker!");
			return;
		}
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("add")) {
				for (int i = 0; i < userUUIDS.size(); i++) {
					if (userUUIDS.contains(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString())) {
						player.sendMessage(ChatColor.GRAY + "That player already has access to that chest.");
						return;
					}
				}
				kettle.sql.updateLockerUsers(targetBlockLocation, lockerInfo.get("users") + "," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());


				World w = targetBlockLocation.getWorld();
				int x = targetBlockLocation.getBlockX();
				int y = targetBlockLocation.getBlockY();
				int z = targetBlockLocation.getBlockZ();
				if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x + 1, y, z);
					kettle.sql.updateLockerUsers(loc, lockerInfo.get("users") + "," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x - 1, y, z);
					kettle.sql.updateLockerUsers(loc, lockerInfo.get("users") + "," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z + 1);
					kettle.sql.updateLockerUsers(loc, lockerInfo.get("users") + "," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z - 1);
					kettle.sql.updateLockerUsers(loc, lockerInfo.get("users") + "," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}


				player.sendMessage(ChatColor.GRAY + "Added the player " + args[2] + " to the locker.");
				return;
			}
			if (args[1].equalsIgnoreCase("remove")) {
				for (int i = 0; i < userUUIDS.size(); i++) {
					if (!userUUIDS.contains(kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString())) {
						player.sendMessage(ChatColor.GRAY + "That player isn't added to that chest.");
						return;
					}
				}
				kettle.sql.updateLockerUsers(targetBlockLocation, ((String) lockerInfo.get("users")).replace("," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));


				World w = targetBlockLocation.getWorld();
				int x = targetBlockLocation.getBlockX();
				int y = targetBlockLocation.getBlockY();
				int z = targetBlockLocation.getBlockZ();
				if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x + 1, y, z);
					kettle.sql.updateLockerUsers(loc, ((String) lockerInfo.get("users")).replace("," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x - 1, y, z);
					kettle.sql.updateLockerUsers(loc, ((String) lockerInfo.get("users")).replace("," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z + 1);
					kettle.sql.updateLockerUsers(loc, ((String) lockerInfo.get("users")).replace("," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z - 1);
					kettle.sql.updateLockerUsers(loc, ((String) lockerInfo.get("users")).replace("," + kettle.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}


				player.sendMessage(ChatColor.GRAY + "Removed the player " + args[2] + " from the locker.");
				return;
			}
			return;
		}
		return;
	}
}
