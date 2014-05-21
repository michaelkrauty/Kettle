package me.michaelkrauty.Kettle.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class KettleCommand implements CommandExecutor {

	private final me.michaelkrauty.Kettle.Kettle kettle;

	public KettleCommand(me.michaelkrauty.Kettle.Kettle instance) {
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage("Kettle version " + kettle.getDescription().getVersion());
		sender.sendMessage("Enabled Plugins: " + kettle.enabledPlugins.toString());
		sender.sendMessage("Config Version: " + kettle.config.getString("config_version"));
		return true;
	}
}
