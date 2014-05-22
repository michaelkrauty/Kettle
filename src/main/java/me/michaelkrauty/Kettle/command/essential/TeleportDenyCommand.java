package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class TeleportDenyCommand extends AbstractCommand {

	private final Kettle kettle;

	public TeleportDenyCommand(String command, String usage, String description, List<String> args, Kettle instance) {
		super(command, usage, description, args);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		return true;
	}
}