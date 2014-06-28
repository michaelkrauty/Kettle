package me.michaelkrauty.Kettle.util;

import me.michaelkrauty.Kettle.Kettle;

import java.util.UUID;

/**
 * Created on 6/28/2014.
 *
 * @author michaelkrauty
 */
public class AsyncLoginStuff implements Runnable {

	Thread t;

	private Kettle kettle;
	private final UUID uuid;

	public AsyncLoginStuff(me.michaelkrauty.Kettle.Kettle instance, UUID uuid) {
		kettle = instance;
		this.uuid = uuid;
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	@Override
	public void run() {
		kettle.getServer().broadcastMessage("async stuff");
		kettle.sql.updateUsername(uuid);
		kettle.sql.updateIP(uuid);
		kettle.sql.updateLastLogin((uuid));
		kettle.getServer().broadcastMessage("async stuff done");
	}
}
