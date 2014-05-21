package me.michaelkrauty.Kettle.command.kettle;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class HelpCommand extends AbstractCommand {

	private final Kettle kettle;

	public HelpCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage("You have reached the help command.");
		return true;
	}
}
