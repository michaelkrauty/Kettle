package me.michaelkrauty.Kettle.command.essential;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/23/2014.
 *
 * @author michaelkrauty
 */
public class Nickname  extends AbstractCommand {

	private final Kettle kettle;

	public Nickname(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		User user = kettle.objects.getUser(player);
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + cmd.getUsage());
			return true;
		}
		if (args.length == 1) {
			if (isAlphaNumeric(args[0])) {
				user.setNickname(args[0]);
				player.sendMessage(ChatColor.GRAY + "Nickname changed to " + args[0]);
				return true;
			}
			player.sendMessage(ChatColor.RED + "Nickname must only contain letters/numbers!");
		}
		player.sendMessage(ChatColor.RED + cmd.getUsage());
		return true;
	}

	public boolean isAlphaNumeric(String s){
		String pattern= "^[a-zA-Z0-9]*$";
		if(s.matches(pattern)){
			return true;
		}
		return false;
	}
}