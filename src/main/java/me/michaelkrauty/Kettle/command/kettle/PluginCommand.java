package me.michaelkrauty.Kettle.command.kettle;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class PluginCommand extends AbstractCommand {

	private final Kettle kettle;

	public PluginCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length < 2 || args.length > 2) {
			sender.sendMessage("incorrect usage");
			return true;
		}
		if (args[0].equalsIgnoreCase("enable")) {
			if (kettle.isValidPlugin(args[1])) {
				ArrayList<String> enabled = new ArrayList<String>();
				for (String line : kettle.configFile.getList("enabled_plugins")) {
					enabled.add(line);
				}
				enabled.add(args[1]);
				kettle.configFile.set("enabled_plugins", enabled);
				sender.sendMessage("enabled plugin: " + args[1]);
				return true;
			} else {
				sender.sendMessage("no such plugin: " + args[1]);
				return true;
			}
		}
		if (args[0].equalsIgnoreCase("disable")) {
			if (kettle.enabledPlugins.contains(args[1])) {
				ArrayList<String> enabled = new ArrayList<String>();
				for (String line : kettle.configFile.getList("enabled_plugins")) {
					if (!line.equalsIgnoreCase(args[1])) {
						enabled.add(line);
					}
				}
				kettle.configFile.set("enabled_plugins", enabled);
				sender.sendMessage("disabled plugin: " + args[1]);
				return true;
			} else {
				sender.sendMessage("no such plugin: " + args[1]);
				return true;
			}
		}
		sender.sendMessage("incorrect usage");
		return true;
	}
}
