package me.michaelkrauty.Kettle.command;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Help implements CommandExecutor {

	private final Kettle kettle;

	public Help(Kettle instance) {
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		return true;
	}
}
