package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created on 6/29/2014.
 *
 * @author michaelkrauty
 */
public class LockerCommand extends AbstractCommand {

	private final Kettle kettle;

	public LockerCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length < 1) {
			new HelpCommand(sender);
			return true;
		}

		if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("lock") || args[0].equalsIgnoreCase("claim")) {
			new CreateCommand(sender, kettle);
			return true;
		}
		if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("unlock") || args[0].equalsIgnoreCase("unclaim")) {
			new RemoveCommand(sender, kettle);
			return true;
		}
		if (args[0].equalsIgnoreCase("users") || args[0].equalsIgnoreCase("user") || args[0].equalsIgnoreCase("owner") || args[0].equalsIgnoreCase("friends") || args[0].equalsIgnoreCase("players") || args[0].equalsIgnoreCase("allowed") || args[0].equalsIgnoreCase("info")) {
			new EditUsersCommand(sender, args, kettle);
			return true;
		}
		if (args[0].equalsIgnoreCase("help")) {
			new HelpCommand(sender);
			return true;
		}

		new HelpCommand(sender);
		return true;
	}
}