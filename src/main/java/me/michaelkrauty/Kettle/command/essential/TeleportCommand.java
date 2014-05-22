package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class TeleportCommand extends AbstractCommand {

	private final Kettle kettle;

	public TeleportCommand(String command, String usage, String description, List<String> aliases, Kettle instance) {
		super(command, usage, description, aliases);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(kettle.langFile.getString("teleport", "incorrectusage"));
			return true;
		}
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
					Player target = kettle.getServer().getPlayer(args[0]);
					if (sender.hasPermission("kettle.teleport")) {
						player.teleport(target);
						sender.sendMessage("Teleported to " + target.getName());
						return true;
					} else {
						sender.sendMessage(kettle.langFile.getString("teleport" ,"nopermission"));
						return true;
					}
				} else {
					sender.sendMessage(kettle.langFile.getString("teleport", "playernotfound", args[0]));
					return true;
				}
			} else {
				sender.sendMessage(kettle.langFile.getString("teleport", "console"));
				return true;
			}
		}
		if (args.length == 2) {
			if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
				Player player1 = kettle.getServer().getPlayer(args[0]);
				if (kettle.getServer().getPlayer(args[1]) instanceof Player) {
					Player player2 = kettle.getServer().getPlayer(args[1]);
					if (sender.hasPermission("kettle.teleport.others")) {
						player1.teleport(player2);
						sender.sendMessage(kettle.langFile.getString("teleport", "teleportplayertoplayersuccess", player1.getName(), player2.getName()));
						return true;
					} else {
						sender.sendMessage(kettle.langFile.getString("teleport", "nopermission"));
						return true;
					}
				} else {
					sender.sendMessage(kettle.langFile.getString("teleport", "playernotfound", args[1]));
					return true;
				}
			} else {
				sender.sendMessage(kettle.langFile.getString("teleport", "playernotfound", args[0]));
				return true;
			}
		}
		sender.sendMessage(kettle.langFile.getString("teleport", "incorrectusage"));
		return true;
	}
}
