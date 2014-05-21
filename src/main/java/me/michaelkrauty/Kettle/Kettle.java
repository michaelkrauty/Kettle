package me.michaelkrauty.Kettle;

import me.michaelkrauty.Kettle.command.HelpCommand;
import me.michaelkrauty.Kettle.command.KettleCommand;
import me.michaelkrauty.Kettle.yml.Config;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Kettle extends JavaPlugin {

	public static final Logger log = Logger.getLogger("Minecraft");

	public final Config config = new Config(this);

	public static ArrayList<String> enabledPlugins;

	public void onEnable() {

		enabledPlugins = config.getEnabledModules();

		for(String name : enabledPlugins) {
			log.info("[Kettle] Enabled Plugin: " + name);
		}

		PluginManager pm = getServer().getPluginManager();
		// TODO: register events

		getCommand("kettle").setExecutor(new KettleCommand(this));
		getCommand("help").setExecutor(new HelpCommand(this));

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("Kettle version " + pdfFile.getVersion() + " enabled!");


	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
	}
}
