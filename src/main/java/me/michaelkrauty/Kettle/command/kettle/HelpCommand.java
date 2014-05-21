package me.michaelkrauty.Kettle.command.kettle;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.util.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class HelpCommand extends AbstractCommand {

	private final Kettle kettle;

	public HelpCommand(String command, String usage, String description, Kettle instance) {
		super(command, usage, description);
		kettle = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		ArrayList<String> commands = new ArrayList<String>();
		for (HelpTopic helpTopic : kettle.getServer().getHelpMap().getHelpTopics()) {
			commands.add(helpTopic.getName());
		}
		ArrayList<String> descriptions = new ArrayList<String>();
		for (HelpTopic helpTopic : kettle.getServer().getHelpMap().getHelpTopics()) {
			descriptions.add(helpTopic.getShortText());
		}
		int pages = 8;
		if (args.length == 0) {
			for (int i = 0; i < commands.size(); i++) {
				if (i < pages) {
					sender.sendMessage(ChatColor.RED + commands.get(i) + ": " + ChatColor.GRAY + descriptions.get(i));
				}
			}
		} else {
			for (int i = 0; i < commands.size(); i++) {
				if ((i < (Integer.parseInt(args[0]) * pages)) && (i >= (Integer.parseInt(args[0]) * pages) - pages)) {
					sender.sendMessage(ChatColor.RED + commands.get(i) + ": " + ChatColor.GRAY + descriptions.get(i));
				}
			}
		}
		return true;
	}
}