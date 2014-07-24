package me.michaelkrauty.Kettle.listener;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Locker;
import me.michaelkrauty.Kettle.Objects.Objects;
import me.michaelkrauty.Kettle.Objects.User;
import me.michaelkrauty.Kettle.util.Permission;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

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
		if (kettle.objects.getUser(event.getPlayer()) == null)
			kettle.objects.users.add(new User(kettle, event.getPlayer()));
		final Player player = event.getPlayer();
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
		if (kettle.objects.getUser(event.getPlayer()).isMuted()) {
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
		Player player = event.getPlayer();
		User user = kettle.objects.getUser(player);
		Block clickedBlock = event.getClickedBlock();
		if (clickedBlock == null)
			return;
		if (clickedBlock.getType() == Material.CHEST) {
			if (kettle.objects.lockerExists(clickedBlock.getLocation())) {
				Locker locker = kettle.objects.getLocker(clickedBlock.getLocation());
				if (!locker.userHasAccess(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + kettle.getServer().getOfflinePlayer(kettle.objects.getLocker(clickedBlock.getLocation()).getOwner()).getName());
					event.setCancelled(true);
					return;
				}
				return;
			}
			return;
		}
		if (clickedBlock.getType() == Material.SIGN || clickedBlock.getType() == Material.WALL_SIGN || clickedBlock.getType() == Material.SIGN_POST) {
			Sign sign = (Sign) clickedBlock;
			if (sign.getLine(0).equalsIgnoreCase("[Permission]")) {
				if (!user.hasPermission(Permission.valueOf(sign.getLine(1)))) {
					if (user.getBalance() >= Integer.parseInt(sign.getLine(3))) {
						user.addPermission(Permission.valueOf(sign.getLine(1)));
						user.setBalance(user.getBalance() - Integer.parseInt(sign.getLine(3)));
						player.sendMessage(ChatColor.GREEN + "Bought access to that command.");
						return;
					}
					player.sendMessage(ChatColor.RED + "You need at least $" + sign.getLine(3) + " to buy access to that command.");
					return;
				}
				player.sendMessage(ChatColor.GREEN + "You already have access to that command.");
				return;
			}
		}


		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
			Location blockLocation = block.getLocation();
			World w = blockLocation.getWorld();
			int x = blockLocation.getBlockX();
			int y = blockLocation.getBlockY();
			int z = blockLocation.getBlockZ();
			if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x + 1, y, z);
				if (kettle.objects.getLocker(loc) != null)
					kettle.objects.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x - 1, y, z);
				if (kettle.objects.getLocker(loc) != null)
					kettle.objects.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z + 1);
				if (kettle.objects.getLocker(loc) != null)
					kettle.objects.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z - 1);
				if (kettle.objects.getLocker(loc) != null)
					kettle.objects.copyLocker(loc, blockLocation);
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		kettle.objects.users.remove(kettle.objects.getUser(event.getPlayer()));
	}
}
