package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class GamemodeCommand extends AbstractCommand {

	private final Kettle kettle;

	public GamemodeCommand(String command, String usage, String description, List<String> aliases, Kettle instance) {
		super(command, usage, description, aliases);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(cmd.getUsage());
			return true;
		}
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
			if (user.isAdminLoggedIn()) {
				player.setGameMode(GameMode.SURVIVAL);
			}
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
			return true;
		}
		if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
			if (user.isAdminLoggedIn()) {
				player.setGameMode(GameMode.CREATIVE);
			}
			sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
			return true;
		}
		sender.sendMessage(cmd.getUsage());
		return true;
	}
}
