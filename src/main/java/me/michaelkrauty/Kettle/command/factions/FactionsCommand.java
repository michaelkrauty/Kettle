package me.michaelkrauty.Kettle.command.factions;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class FactionsCommand extends AbstractCommand {

	private final Kettle kettle;

	public FactionsCommand(String command, String usage, String description, List<String> aliases, Kettle instance) {
		super(command, usage, description, aliases);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (args[0].equalsIgnoreCase("create")) {
			new CreateCommand(kettle, player, args);
			return true;
		}
		if (args[0].equalsIgnoreCase("disband")) {
			new DisbandCommand(kettle, player, args);
			return true;
		}
		if (args[0].equalsIgnoreCase("info")) {
			new InfoCommand(kettle, player, args);
			return true;
		}
		player.sendMessage(ChatColor.RED + "unknown command");
		return true;
	}
}
