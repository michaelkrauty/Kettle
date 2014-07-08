package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Objects;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/3/2014.
 *
 * @author michaelkrauty
 */
public class BackCommand extends AbstractCommand {

	private final Kettle kettle;
	private Objects objects;

	public BackCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
		objects = kettle.objects;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = objects.getUser(player);
		if (user.getLastLocation() != null)
			user.teleport(user.getLastLocation());
		else
			player.sendMessage(ChatColor.RED + "no location to go back to!");
		return true;
	}
}