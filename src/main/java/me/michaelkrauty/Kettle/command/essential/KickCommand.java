package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/23/2014.
 *
 * @author michaelkrauty
 */
public class KickCommand extends AbstractCommand {

	private final Kettle kettle;

	public KickCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (args.length == 0) {
			player.sendMessage(cmd.getUsage());
			return true;
		}
		if (user.isAdminLoggedIn()) {
			if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
				Player target = kettle.getServer().getPlayer(args[0]);
				String reason = "You were kicked from the server.";
				if (args.length > 1) {
					reason = "";
					for (int i = 1; i < args.length; i++) {
						reason = reason + args[i] + " ";
					}
				}
				target.kickPlayer(reason);
				return true;
			}
			return true;
		}
		player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
		return true;
	}
}
