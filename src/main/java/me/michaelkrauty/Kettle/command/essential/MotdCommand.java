package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class MotdCommand extends AbstractCommand {

	private final Kettle kettle;

	public MotdCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		for (String line : kettle.motdFile.getMOTD()) {
			sender.sendMessage(line);
		}
		return true;
	}
}
