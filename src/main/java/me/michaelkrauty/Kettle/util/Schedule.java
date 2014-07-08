package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;
import me.michaelkrauty.Kettle.Objects.Locker;
import me.michaelkrauty.Kettle.Objects.Objects;
import org.bukkit.Material;

/**
 * Created on 6/29/2014.
 *
 * @author michaelkrauty
 */
public class Schedule {

	private Kettle kettle;
	private Objects objects;

	public Schedule(Kettle instance) {
		kettle = instance;
		objects = kettle.objects;
		kettle.getServer().getScheduler().scheduleSyncRepeatingTask(kettle, new Runnable() {
			@Override
			public void run() {
				checkChests();
			}
		}, 0, 1);
	}

	/*public void checkExpiry() {
		for (HashMap hash : kettle.lockers) {
			String[] loc0 = ((String) hash.get("location")).split(",");
			Location loc = new Location(kettle.getServer().getWorld(loc0[0]), Integer.parseInt(loc0[1]), Integer.parseInt(loc0[2]), Integer.parseInt(loc0[3]));
			long expiry = Long.parseLong((String) hash.get("expiry"));
			if (expiry == 0) {
				kettle.sql.removeLocker(loc);
			} else {
				if (!(expiry < 0)) {
					kettle.sql.updateLockerExpiry(loc, expiry - 1);
				}
			}
		}
	}*/

	public void checkChests() {
		try {
			for (Locker locker : objects.lockers) {
				if (locker.getLocation().getWorld().getBlockAt(locker.getLocation()).getType() != Material.CHEST) {
					locker.delete();
				}
			}
		} catch (Exception ignored) {
		}
	}
}
