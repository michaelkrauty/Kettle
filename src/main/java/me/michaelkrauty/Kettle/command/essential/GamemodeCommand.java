package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class GamemodeCommand extends AbstractCommand {

	private final Kettle kettle;

	public GamemodeCommand(String command, String usage, String description, List<String> aliases, Kettle instance) {
		super(command, usage, description, aliases);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(kettle.langFile.getString("gamemode", "incorrectusage"));
			return true;
		}
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
					if (kettle.admins.contains(player.getName())) {
						player.setGameMode(GameMode.SURVIVAL);
						sender.sendMessage(kettle.langFile.getString("gamemode", "success_survival"));
					} else {
						sender.sendMessage(kettle.langFile.getString("gamemode", "nopermission"));
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
					if (kettle.admins.contains(player.getName())) {
						player.setGameMode(GameMode.CREATIVE);
						sender.sendMessage(kettle.langFile.getString("gamemode", "success_creative"));
					} else {
						sender.sendMessage(kettle.langFile.getString("gamemode", "nopermission"));
						return true;
					}
				}
				sender.sendMessage(kettle.langFile.getString("gamemode", "incorrectusage"));
				return true;
			} else {
				sender.sendMessage(kettle.langFile.getString("gamemode", "console"));
				return true;
			}
		}
		sender.sendMessage(kettle.langFile.getString("gamemode", "incorrectusage"));
		return true;
	}
}
