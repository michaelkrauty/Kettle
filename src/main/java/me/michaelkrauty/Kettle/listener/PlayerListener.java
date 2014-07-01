package me.michaelkrauty.Kettle.listener;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Locker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class PlayerListener implements Listener {

	private final Kettle kettle;

	public PlayerListener(Kettle instance) {
		kettle = instance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (!kettle.sql.userExists(player.getUniqueId())) {
			kettle.sql.addUser(player.getUniqueId());
		}
		UUID uuid = player.getUniqueId();
		kettle.sql.updateUsername(uuid);
		kettle.sql.updateIP(uuid, player.getAddress());
		kettle.sql.updateLastLogin(uuid);
		kettle.getServer().getScheduler().scheduleAsyncDelayedTask(kettle, new Runnable() {
			@Override
			public void run() {
				for (String line : kettle.motdFile.getMOTD()) {
					player.sendMessage(line);
				}
			}
		}, 20);
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		if (kettle.mutedPlayers.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You can't talk, you're muted!");
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (kettle.dataFile.getLocation("spawn") != null) {
			event.setRespawnLocation(kettle.dataFile.getLocation("spawn"));
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();
		try {
			if (clickedBlock.getType() == Material.CHEST) {
				if (isProtected(clickedBlock.getLocation())) {
					Locker locker = kettle.getLocker(clickedBlock.getLocation());
					if (!locker.userHasAccess(event.getPlayer().getUniqueId())) {
						event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + kettle.getServer().getOfflinePlayer(kettle.getLocker(clickedBlock.getLocation()).getOwner()).getName());
						event.setCancelled(true);
						return;
					}
					return;
				}
				return;
			}
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				System.out.println("ModX: " + event.getBlockFace().getModX());
				System.out.println("ModY: " + event.getBlockFace().getModY());
				System.out.println("ModZ: " + event.getBlockFace().getModZ());
			}
			Block block = event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
			Location blockLocation = block.getLocation();
			World w = blockLocation.getWorld();
			int x = blockLocation.getBlockX();
			int y = blockLocation.getBlockY();
			int z = blockLocation.getBlockZ();
			if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x + 1, y, z);
				if (kettle.getLocker(loc) != null)
				kettle.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x - 1, y, z);
				if (kettle.getLocker(loc) != null)
				kettle.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z + 1);
				if (kettle.getLocker(loc) != null)
				kettle.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z - 1);
				if (kettle.getLocker(loc) != null)
				kettle.copyLocker(loc, blockLocation);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isProtected(Location loc) {
		return kettle.lockerExists(loc);
	}
}
