package me.michaelkrauty.Kettle.listener;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class BlockListener implements Listener {

	private final Kettle kettle;

	public BlockListener(Kettle instance) {
		kettle = instance;
	}

	public void onBlockPhysics(BlockPhysicsEvent event) {

	}
}
