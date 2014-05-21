package me.michaelkrauty.Kettle;

import me.michaelkrauty.Kettle.command.Help;
import me.michaelkrauty.Kettle.yml.Config;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Kettle extends JavaPlugin {

	public static final Logger log = Logger.getLogger("Minecraft");

	public final Config config = new Config(this);

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		// TODO: register events

		getCommand("help").setExecutor(new Help(this));

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled!");


	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
	}
}
