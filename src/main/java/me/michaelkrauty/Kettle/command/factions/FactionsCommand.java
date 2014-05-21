package me.michaelkrauty.Kettle.command.factions;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class FactionsCommand extends AbstractCommand {

	private final Kettle kettle;

	public FactionsCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage("factions command");
		return true;
	}
}
