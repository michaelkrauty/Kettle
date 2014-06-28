package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class MuteCommand extends AbstractCommand {

	private final Kettle kettle;

	public MuteCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (kettle.getServer().getPlayer(args[0]) instanceof Player) {
			if (!kettle.mutedPlayers.contains(sender))
			kettle.mutedPlayers.add((Player) sender);
			else
				kettle.mutedPlayers.remove(sender);
		}
		return true;
	}
}
