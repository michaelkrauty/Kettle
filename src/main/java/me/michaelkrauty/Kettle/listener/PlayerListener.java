package me.michaelkrauty.Kettle.listener;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Locker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
		kettle.getServer().getScheduler().scheduleSyncDelayedTask(kettle, new Runnable() {
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
		System.out.println("click");
		Block clickedBlock = event.getClickedBlock();
		try {
			if (clickedBlock.getType() == Material.CHEST) {
				System.out.println("chest");
				if (isProtected(clickedBlock.getLocation())) {
					System.out.println("is protected");
					if (!playerHasAccess(event.getPlayer(), clickedBlock.getLocation())) {
						System.out.println("has access");
						event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + kettle.getServer().getOfflinePlayer(kettle.getLocker(clickedBlock.getLocation()).getOwner()));
						event.setCancelled(true);
						return;
					}
					System.out.println("does not have access");
					return;
				}
				System.out.println("is not protected");
				return;
			}
			System.out.println("is not chest");
			// TODO: remove locker if chest is no longer present
			/*
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Block block = event.getClickedBlock().getRelative(event.getBlockFace());
				if (block.getType() == Material.AIR) {
					if (kettle.lockerExists(block.getLocation())) {
						kettle.removeLocker(block.getLocation());
					}
				}
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isProtected(Location loc) {
		return kettle.lockerExists(loc);
	}

	private boolean playerHasAccess(Player player, Location loc) {
		Locker locker;
		if ((locker = kettle.getLocker(loc)) != null) {
			if (locker.getUsers().contains(player.getUniqueId()))
				return true;
		}
		System.out.println("null");
		return false;
	}
}
