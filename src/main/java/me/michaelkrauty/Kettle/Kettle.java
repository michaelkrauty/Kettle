package me.michaelkrauty.Kettle;

import me.michaelkrauty.Kettle.Objects.Locker;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.command.essential.*;
import me.michaelkrauty.Kettle.command.factions.FactionsCommand;
import me.michaelkrauty.Kettle.command.kettle.KettleCommand;
import me.michaelkrauty.Kettle.command.kettle.TestCommand;
import me.michaelkrauty.Kettle.command.locker.LockerCommand;
import me.michaelkrauty.Kettle.command.worlds.WorldCommand;
import me.michaelkrauty.Kettle.file.ConfigFile;
import me.michaelkrauty.Kettle.file.DataFile;
import me.michaelkrauty.Kettle.file.LangFile;
import me.michaelkrauty.Kettle.file.MotdFile;
import me.michaelkrauty.Kettle.listener.BlockListener;
import me.michaelkrauty.Kettle.listener.PlayerListener;
import me.michaelkrauty.Kettle.util.Error;
import me.michaelkrauty.Kettle.util.SQL;
import me.michaelkrauty.Kettle.util.Schedule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
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
	public LangFile langFile;
	public DataFile dataFile;

	public SQL sql;
	public Schedule schedule;

	public ArrayList<User> users = new ArrayList<User>();

	public ArrayList<Locker> lockers = new ArrayList<Locker>();

	public static ArrayList<String> admins = new ArrayList<String>();

	public static ArrayList<String> mutedPlayers = new ArrayList<String>();

	public final me.michaelkrauty.Kettle.util.Error error = new Error(this);

	private final PlayerListener playerListener = new PlayerListener(this);
	private final BlockListener blockListener = new BlockListener(this);

	public void onEnable() {
		kettle = this;
		checkDirectories();
		registerEvents();
		registerCommands();

		configFile = new ConfigFile(this);
		motdFile = new MotdFile(this);
		langFile = new LangFile(this);
		dataFile = new DataFile(this);
		sql = new SQL(this);
		schedule = new Schedule(this);

		loadLockers();
		loadUsers();

		log.info("Kettle version " + getDescription().getVersion() + " enabled!");
	}

	public void onDisable() {
		try {
			for (Locker locker : lockers) {
				locker.unload();
			}
		} catch (Exception ignored) {
		}
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
		// new HelpCommand("help", "/<command> [args]", "Help Command", this).register();
		new TestCommand("test", "/<command> [args]", "Test Command", this).register();

		/** Essential commands */
		new MotdCommand("motd", "/<command> [args]", "MOTD Command", this).register();
		new TeleportCommand("teleport", "/<command> [args]", "Teleport Command", Arrays.asList("tp", "tele"), this).register();
		new TeleportHereCommand("teleporthere", "/<command> [args]", "Teleport Here Command", Arrays.asList("tph", "tphere"), this).register();
		new GamemodeCommand("gamemode", "/<command> [args]", "Gamemode Command", Arrays.asList("gm"), this).register();
		new MuteCommand("mute", "/<command> [args]", "Mute Command", this).register();
		new AdminLoginCommand("admin", "/admin <password>", "Login as adin", this).register();
		new SpawnCommand("spawn", "/<command>", "Spawn Command", this).register();
		new SetspawnCommand("setspawn", "/<command>", "SetSpawn Command", this).register();
		new WorldCommand("world", "/<command> [args]", "World Command", this).register();
		new LockerCommand("locker", "/<command>", "Locker Command", this).register();

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

	public Location getSpawnLocation() {
		if (dataFile.getLocation("spawn") != null)
			return dataFile.getLocation("spawn");
		else
			return getServer().getWorlds().get(0).getSpawnLocation();
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

	public void loadLockers() {
		ArrayList<Location> lock;
		if ((lock = kettle.sql.getAllLockers()) != null) {
			for (Location loc : lock) {
				lockers.add(new Locker(kettle, loc));
			}
		}
	}

	public void loadUsers() {
			for (Player player : kettle.getServer().getOnlinePlayers()) {
				if (kettle.getUser(player) == null)
					kettle.users.add(new User(kettle, player));
			}
	}

	public Locker getLocker(Location loc) {
		if (lockers.size() != 0) {
			for (int i = 0; i < lockers.size(); i++) {
				if (locationToString(lockers.get(i).getLocation()).equals(locationToString(loc))) {
					return lockers.get(i);
				}
			}
			return null;
		}
		return null;
	}

	public void createLocker(Location loc, Player owner) {
		createLocker(loc, owner.getUniqueId());
	}

	public void createLocker(Location loc, UUID owner) {
		if (!lockerExists(loc)) {
			final Location loc1 = loc;
			final UUID owner1 = owner;
			getServer().getScheduler().scheduleAsyncDelayedTask(this, new Runnable() {
				@Override
				public void run() {
					kettle.sql.addLocker(loc1, owner1);
					lockers.add(new Locker(kettle, loc1));
				}
			});
		}
	}

	public boolean lockerExists(Location loc) {
		return getLocker(loc) != null;
	}

	public void copyLocker(Location loc1, Location loc2) {
		createLocker(loc2, getLocker(loc1).getOwner());
	}

	public User getUser(Player player) {
		if (users.size() != 0) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getPlayer() == player) {
					return users.get(i);
				}
			}
		}
		return null;
	}
}
