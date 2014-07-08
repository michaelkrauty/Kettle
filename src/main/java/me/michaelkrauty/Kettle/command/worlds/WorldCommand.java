package me.michaelkrauty.Kettle.command.worlds;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Objects;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class WorldCommand extends AbstractCommand {

	private final Kettle kettle;
	private Objects objects;

	public WorldCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
		objects = kettle.objects;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = objects.getUser(player);
		if (kettle.admins.contains(player.getName())) {
			if (args.length == 0) {
				String worlds = "";
				List<World> worldsList = kettle.getServer().getWorlds();
				for (int i = 0; i < worldsList.size(); i++) {
					if (i != worldsList.size() - 1) {
						worlds = worlds + worldsList.get(i).getName() + ", ";
					} else {
						worlds = worlds + worldsList.get(i).getName();
					}
				}
				player.sendMessage(ChatColor.GRAY + "Worlds: " + worlds);
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					player.sendMessage(ChatColor.GREEN + "/world <create|delete> <name> <normal|flat|amplified>");
					return true;
				}
				if (kettle.getServer().getWorld(args[0]) != null) {
					user.teleport(kettle.getServer().getWorld(args[0]).getSpawnLocation());
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "Invalid world: " + args[0]);
					return true;
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("delete")) {
					if (kettle.getServer().getWorld(args[1]) != null) {
						player.sendMessage(ChatColor.GREEN + "Deleting...");
						if (kettle.getSpawnLocation().getWorld().getName().equalsIgnoreCase(args[1]))
							kettle.dataFile.set("spawn", kettle.getServer().getWorlds().get(0).getSpawnLocation());
						for (Player p : kettle.getServer().getWorld(args[1]).getPlayers()) {
							p.teleport(kettle.getSpawnLocation());
						}
						kettle.getServer().unloadWorld(kettle.getServer().getWorld(args[1]), true);
						recursiveDelete(new File(kettle.getServer().getWorldContainer(), args[1]));

						ArrayList<String> worlds = kettle.dataFile.getArrayList("worlds");
						worlds.remove(args[1]);
						kettle.dataFile.set("worlds", worlds);

						player.sendMessage(ChatColor.GREEN + "Done.");
						return true;
					}
					player.sendMessage(ChatColor.RED + "Invalid world: " + args[1]);
					return true;
				}
			}
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("create")) {
					if (kettle.getServer().getWorld(args[1]) != null) {
						player.sendMessage(ChatColor.RED + "That world already exists!");
						return true;
					}
					player.sendMessage(ChatColor.GREEN + "Creating...");
					ArrayList<String> worlds = kettle.dataFile.getArrayList("worlds");
					if (args[2].equalsIgnoreCase("normal")) {
						new WorldCreator(args[1]).type(WorldType.NORMAL).createWorld();
						worlds.add(args[1]);
					}
					if (args[2].equalsIgnoreCase("flat")) {
						new WorldCreator(args[1]).type(WorldType.FLAT).createWorld();
						worlds.add(args[1]);
					}
					if (args[2].equalsIgnoreCase("amplified")) {
						new WorldCreator(args[1]).type(WorldType.AMPLIFIED).createWorld();
						worlds.add(args[1]);
					}
					kettle.dataFile.set("worlds", worlds);
					player.sendMessage(ChatColor.GREEN + "Done.");
					return true;
				}
			}
			player.sendMessage(ChatColor.GREEN + "/world <create|delete> <name> <normal|flat|amplified>");
		}
		return true;
	}

	private static void recursiveDelete(File file) {
		if (!file.exists())
			return;

		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				recursiveDelete(f);
			}
		}
		file.delete();
	}
}