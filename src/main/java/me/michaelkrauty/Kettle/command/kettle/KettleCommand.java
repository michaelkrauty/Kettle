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
public class KettleCommand extends AbstractCommand {

	private final me.michaelkrauty.Kettle.Kettle kettle;

	public KettleCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage("Kettle version " + kettle.getDescription().getVersion());
		String plugins;
		try {
			plugins = kettle.enabledPlugins.toString();
		} catch (Exception e) {
			plugins = "none";
		}
		sender.sendMessage("Enabled Plugins: " + plugins);
		sender.sendMessage("Config Version: " + kettle.configFile.getString("config_version"));
		return true;
	}
}
