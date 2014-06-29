package me.michaelkrauty.Kettle.command.kettle;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		if (!(sender instanceof Player)) {
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "Kettle version " + kettle.getDescription().getVersion());
			return true;
		}
		return true;
	}
}
