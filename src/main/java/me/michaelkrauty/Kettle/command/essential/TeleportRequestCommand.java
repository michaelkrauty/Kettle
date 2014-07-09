package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Objects;
import me.michaelkrauty.Kettle.Objects.User;
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
public class TeleportRequestCommand extends AbstractCommand {

	private final Kettle kettle;

	public TeleportRequestCommand(String command, String usage, String description, List<String> args, Kettle instance) {
		super(command, usage, description, args);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(kettle.langFile.getString("teleportrequest", "incorrectusage"));
			return true;
		}
		if (sender instanceof Player) {
			sender.sendMessage(kettle.langFile.getString("teleportrequest", "console"));
			return true;
		}
		Player player = (Player) sender;
		if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
			Player target = kettle.getServer().getPlayer(args[0]);
			User user = kettle.objects.getUser(target);
			if (user.teleportEnabled()) {
				// TODO: tpa
			} else {
				// target's teleportion is disabled
			}
		}
		sender.sendMessage(kettle.langFile.getString("teleportrequest", "incorrectusage"));
		return true;
	}
}
