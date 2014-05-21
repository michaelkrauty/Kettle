package me.michaelkrauty.Kettle;

import me.michaelkrauty.Kettle.command.factions.FactionsCommand;
import me.michaelkrauty.Kettle.command.kettle.HelpCommand;
import me.michaelkrauty.Kettle.command.kettle.KettleCommand;
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

		try {
			enabledPlugins = config.getEnabledModules();
			for (String name : enabledPlugins) {
				log.info("[Kettle] Enabled Plugin: " + name);
			}
		} catch (Exception e) {
			log.info("[Kettle] No plugins enabled!");
		}

		PluginManager pm = getServer().getPluginManager();
		// TODO: register events

		registerCommands();

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("Kettle version " + pdfFile.getVersion() + " enabled!");


	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
	}

	private void registerCommands() {

		/** Default kettle commands */
		new KettleCommand("kettle", "/<command> [args]", "The kettle command", this).register();
		new HelpCommand("help", "/<command> [args]", "Help command", this).register();


		/** Factions commands */
		if (enabledPlugins.contains("factions")) {
			new FactionsCommand("factions", "/<command> [args]", "The factions command", this).register();
		}
	}
}
