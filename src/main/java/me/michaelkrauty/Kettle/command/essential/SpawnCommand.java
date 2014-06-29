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
public class SpawnCommand extends AbstractCommand {

	private final Kettle kettle;

	public SpawnCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		if (kettle.dataFile.getLocation("spawn") != null ) {
			player.teleport(kettle.dataFile.getLocation("spawn"));
		} else {
			player.teleport(kettle.getServer().getWorlds().get(0).getSpawnLocation());
		}
		return true;
	}
}