package me.michaelkrauty.Kettle.command.locker;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class HelpCommand {

	public HelpCommand(CommandSender sender) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.GRAY + "---help & stuff---");
		player.sendMessage(ChatColor.GRAY + "**Use commands looking directly at any chest**");
		player.sendMessage(ChatColor.RED + "/locker lock " + ChatColor.GRAY + "lock a chest");
		player.sendMessage(ChatColor.RED + "/locker unlock " + ChatColor.GRAY + "unlock a chest");
		player.sendMessage(ChatColor.RED + "/locker users " + ChatColor.GRAY + "see who can access a chest");
		player.sendMessage(ChatColor.RED + "/locker users " + ChatColor.RED + "<add/remove> " + ChatColor.GRAY + "manage chest access");
		player.sendMessage(ChatColor.GREEN + "-" + ChatColor.RED + "Breaking a locked chest will also break the lock.");
	}
}
