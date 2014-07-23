package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/22/2014.
 *
 * @author michaelkrauty
 */
public class SellCommand extends AbstractCommand {

	private final Kettle kettle;

	public SellCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0) {
			if (player.getItemInHand().getType() != Material.AIR) {
				// TODO: sell item
			}
		}
		return true;
	}
}