package me.michaelkrauty.Kettle;

import me.michaelkrauty.Kettle.Objects.Objects;
import me.michaelkrauty.Kettle.command.chat.PrefixCommand;
import me.michaelkrauty.Kettle.command.essential.*;
import me.michaelkrauty.Kettle.command.factions.FactionsCommand;
import me.michaelkrauty.Kettle.command.groups.GroupCommand;
import me.michaelkrauty.Kettle.command.kettle.KettleCommand;
import me.michaelkrauty.Kettle.command.kettle.TestCommand;
import me.michaelkrauty.Kettle.command.locker.LockerCommand;
import me.michaelkrauty.Kettle.command.permissions.PermissionCommand;
import me.michaelkrauty.Kettle.command.worlds.WorldCommand;
import me.michaelkrauty.Kettle.file.ConfigFile;
import me.michaelkrauty.Kettle.file.DataFile;
import me.michaelkrauty.Kettle.file.MotdFile;
import me.michaelkrauty.Kettle.listener.BlockListener;
import me.michaelkrauty.Kettle.listener.PlayerListener;
import me.michaelkrauty.Kettle.util.Error;
import me.michaelkrauty.Kettle.util.Schedule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
	public MotdFile motdFile;
	public DataFile dataFile;

	public Schedule schedule;

	public Objects objects;

	public static ArrayList<String> admins = new ArrayList<String>();

	public static ArrayList<String> defaultPermissions = new ArrayList<String>();

	public static HashMap<Material, Integer> itemPrices = new HashMap<Material, Integer>();

	public final me.michaelkrauty.Kettle.util.Error error = new Error(this);

	private final PlayerListener playerListener = new PlayerListener(this);
	private final BlockListener blockListener = new BlockListener(this);

	public void onEnable() {
		kettle = this;
		checkDirectories();
		configFile = new ConfigFile(this);
		dataFile = new DataFile(this);
		loadWorlds();

		objects = new Objects(this);

		objects.loadObjects();

		registerEvents();
		registerCommands();
		motdFile = new MotdFile(this);
		schedule = new Schedule(this);

		log.info("Kettle version " + getDescription().getVersion() + " enabled!");
	}

	public void onDisable() {
		objects.unloadObjects();
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
		// new HelpCommand("help", "/<command> [args]", "Help Command", this).register();
		new TestCommand("test", "/<command> [args]", "Test Command", this).register();

		/** Essential commands */
		new MotdCommand("motd", "/motd", "MOTD Command", this).register();
		new TeleportCommand("teleport", "/teleport <player> [player]", "Teleport Command", Arrays.asList("tp", "tele"), this).register();
		new TeleportHereCommand("teleporthere", "/teleporthere <player>", "Teleport Here Command", Arrays.asList("tph", "tphere"), this).register();
		new GamemodeCommand("gamemode", "/gamemode <0|1|2> [player]", "Gamemode Command", Arrays.asList("gm"), this).register();
		new MuteCommand("mute", "/mute <player>", "Mute Command", this).register();
		new AdminLoginCommand("admin", "/admin <password>", "Login as adin", this).register();
		new SpawnCommand("spawn", "/spawn", "Spawn Command", this).register();
		new SetspawnCommand("setspawn", "/setspawn", "SetSpawn Command", this).register();
		new WorldCommand("world", "/world <create|delete> <name> <type>", "World Command", this).register();
		new LockerCommand("locker", "/locker", "Locker Command", this).register();
		new BackCommand("back", "/back", "Back Command", this).register();
		new NicknameCommand("nickname", "/nick <nickname>", "Nickname Command", Arrays.asList("nick"), this).register();
		new PrefixCommand("prefix", "/prefix <player> <prefix>", "Prefix Command", this).register();
		new HealCommand("heal", "/heal [player]", "Heal Command", this).register();
		new FeedCommand("feed", "/feed [player]", "Feed Command", this).register();
		new GroupCommand("group", "/group <player> <group>", "Group Command", this).register();
		new PermissionCommand("permission", "/permission <player> <add/remove/list> [permission]", "Group Command", this).register();
		new RealnameCommand("realname", "/realname <player>", "Realname Command", this).register();

		/** Factions commands */
		new FactionsCommand("factions", "/<command> [args]", "The factions command", Arrays.asList("f", "faction", "fac"), this).register();
	}

	public void checkDirectories() {
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
		File playerFolder = new File(getDataFolder() + "/players");
		File factionFolder = new File(getDataFolder() + "/factions");
		File lockerFolder = new File(getDataFolder() + "/lockers");
		if (!playerFolder.exists())
			playerFolder.mkdir();
		if (!factionFolder.exists())
			factionFolder.mkdir();
		if (!lockerFolder.exists())
			lockerFolder.mkdir();
	}

	public String locationToString(Location loc) {
		String world = loc.getWorld().getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		return world + "," + x + "," + y + "," + z;
	}

	public Location stringToLocation(String str) {
		String[] loc = str.split(",");
		return new Location(getServer().getWorld(loc[0]), Integer.parseInt(loc[1]), Integer.parseInt(loc[2]), Integer.parseInt(loc[3]));
	}

	public Location getSpawnLocation() {
		if (dataFile.getLocation("spawn") != null)
			return dataFile.getLocation("spawn");
		else
			return getServer().getWorlds().get(0).getSpawnLocation();
	}

	private void loadWorlds() {
		for (String worldName : dataFile.getArrayList("worlds"))
			new WorldCreator(worldName).createWorld();
	}
}
