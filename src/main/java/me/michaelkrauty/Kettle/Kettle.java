package me.michaelkrauty.Kettle;

import me.michaelkrauty.Kettle.command.essential.*;
import me.michaelkrauty.Kettle.command.factions.FactionsCommand;
import me.michaelkrauty.Kettle.command.kettle.HelpCommand;
import me.michaelkrauty.Kettle.command.kettle.KettleCommand;
import me.michaelkrauty.Kettle.command.kettle.TestCommand;
import me.michaelkrauty.Kettle.file.ConfigFile;
import me.michaelkrauty.Kettle.file.LangFile;
import me.michaelkrauty.Kettle.file.MotdFile;
import me.michaelkrauty.Kettle.file.PlayerFile;
import me.michaelkrauty.Kettle.listener.BlockListener;
import me.michaelkrauty.Kettle.listener.PlayerListener;
import me.michaelkrauty.Kettle.util.Error;
import me.michaelkrauty.Kettle.util.SQL;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created on 5/20/2014.
 *
 * @author michaelkrauty
 */
public class Kettle extends JavaPlugin {

	public static Kettle kettle;

	public static final Logger log = Logger.getLogger("MC");
	public ConfigFile configFile;
	public PlayerFile playerFile;
	public MotdFile motdFile;
	public LangFile langFile;

	public SQL sql;

	public static ArrayList<String> enabledPlugins;

	public static ArrayList<Player> mutedPlayers = new ArrayList<Player>();

	public final me.michaelkrauty.Kettle.util.Error error = new Error(this);

	private final PlayerListener playerListener = new PlayerListener(this);
	private final BlockListener blockListener = new BlockListener(this);

	public void onEnable() {
		kettle = this;
		checkDirectories();
		registerEvents();
		registerCommands();

		configFile = new ConfigFile(this);
		playerFile = new PlayerFile(this);
		motdFile = new MotdFile(this);
		langFile = new LangFile(this);
		sql = new SQL(this);

		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("Kettle version " + pdfFile.getVersion() + " enabled!");
	}

	public void onDisable() {
		kettle.sql.closeConnection();
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(playerListener, this);
		pm.registerEvents(blockListener, this);
	}

	private void registerCommands() {

		/** Default kettle commands */
		new KettleCommand("kettle", "/<command> [args]", "The Kettle Command", this).register();
		new HelpCommand("help", "/<command> [args]", "Help Command", this).register();
		new TestCommand("test", "/<command> [args]", "Test Command", this).register();

		/** Essential commands */
		new MotdCommand("motd", "/<command> [args]", "MOTD Command", this).register();
		new TeleportCommand("teleport", "/<command> [args]", "Teleport Command", Arrays.asList("tp", "tele"), this).register();
		new TeleportHereCommand("teleporthere", "/<command> [args]", "Teleport Here Command", Arrays.asList("tph", "tphere"), this).register();
		new GamemodeCommand("gamemode", "/<command> [args]", "Gamemode Command", Arrays.asList("gm"), this).register();
		new MuteCommand("mute", "/<command> [args]", "Mute Command", this).register();

		/** Factions commands */
		new FactionsCommand("factions", "/<command> [args]", "The factions command", Arrays.asList("f", "faction", "fac"), this).register();
	}

	public void checkDirectories() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File playerDir = new File(getDataFolder() + "/players");
		if (!playerDir.exists()) {
			playerDir.mkdir();
		}
	}

	public static final String format(String str) {
		return str
				.replace("&0", "§0")
				.replace("&1", "§1")
				.replace("&2", "§2")
				.replace("&3", "§3")
				.replace("&4", "§4")
				.replace("&5", "§5")
				.replace("&6", "§6")
				.replace("&7", "§7")
				.replace("&8", "§8")
				.replace("&9", "§9")
				.replace("&a", "§a")
				.replace("&b", "§b")
				.replace("&c", "§c")
				.replace("&d", "§d")
				.replace("&e", "§e")
				.replace("&f", "§f")
				.replace("&k", "§k")
				.replace("&l", "§l")
				.replace("&m", "§m")
				.replace("&n", "§n")
				.replace("&o", "§o")
				.replace("&r", "§r");
	}
}
