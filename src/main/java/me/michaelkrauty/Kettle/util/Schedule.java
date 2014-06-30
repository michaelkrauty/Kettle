package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 6/29/2014.
 *
 * @author michaelkrauty
 */
public class Schedule {

	private Kettle kettle;

	public Schedule(Kettle instance) {
		kettle = instance;
	/*	kettle.getServer().getScheduler().scheduleSyncRepeatingTask(kettle, new Runnable() {
			@Override
			public void run() {
				checkExpiry();
			}
		}, 0, 1200);*/
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
}
