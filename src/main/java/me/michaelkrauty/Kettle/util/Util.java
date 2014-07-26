package me.michaelkrauty.Kettle.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created on 7/24/2014.
 *
 * @author michaelkrauty
 */
public class Util {

	public static String format(String str) {
		return str.replace("&0", "§0")
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

	public static String stripColor(String str) {
		return str.replace("&0", "§0")
				.replace("&1", "")
				.replace("&2", "")
				.replace("&3", "")
				.replace("&4", "")
				.replace("&5", "")
				.replace("&6", "")
				.replace("&7", "")
				.replace("&8", "")
				.replace("&9", "")
				.replace("&a", "")
				.replace("&b", "")
				.replace("&c", "")
				.replace("&d", "")
				.replace("&e", "")
				.replace("&f", "")
				.replace("&k", "")
				.replace("&l", "")
				.replace("&m", "")
				.replace("&n", "")
				.replace("&o", "")
				.replace("&r", "")
				.replace("§0", "")
				.replace("§1", "")
				.replace("§2", "")
				.replace("§3", "")
				.replace("§4", "")
				.replace("§5", "")
				.replace("§6", "")
				.replace("§7", "")
				.replace("§8", "")
				.replace("§9", "")
				.replace("§a", "")
				.replace("§b", "")
				.replace("§c", "")
				.replace("§d", "")
				.replace("§e", "")
				.replace("§f", "")
				.replace("§k", "")
				.replace("§l", "")
				.replace("§m", "")
				.replace("§n", "")
				.replace("§o", "")
				.replace("§r", "");
	}

	public static String toBase64(Inventory inventory) throws IllegalStateException {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			// Write the size of the inventory
			dataOutput.writeInt(inventory.getSize());

			// Save every element in the list
			for (int i = 0; i < inventory.getSize(); i++) {
				dataOutput.writeObject(inventory.getItem(i));
			}

			// Serialize that array
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}

	public static Inventory fromBase64(String data) throws IOException {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

			// Read the serialized inventory
			for (int i = 0; i < inventory.getSize(); i++) {
				inventory.setItem(i, (ItemStack) dataInput.readObject());
			}

			dataInput.close();
			return inventory;
		} catch (ClassNotFoundException e) {
			throw new IOException("Unable to decode class type.", e);
		}
	}
}
