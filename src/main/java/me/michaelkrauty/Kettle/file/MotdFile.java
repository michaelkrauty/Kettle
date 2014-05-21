package me.michaelkrauty.Kettle.file;

import me.michaelkrauty.Kettle.Kettle;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created on 5/21/2014.
 *
 * @author michaelkrauty
 */
public class MotdFile {

	YamlConfiguration motd = new YamlConfiguration();

	private final Kettle kettle;

	private final String[] defaultMOTD = new String[]{
			"welcome to the server!",
			"this is the default MOTD.",
			"change it in plugins/Kettle/motd.txt"
	};

	public MotdFile(Kettle instance) {
		kettle = instance;
		File motdFile = new File("plugins/Kettle/motd.txt");
		if (!motdFile.exists()) {
			try {
				File kettleDir = new File("plugins/Kettle");
				if (!kettleDir.exists()) {
					kettleDir.mkdir();
				}
				PrintWriter out = new PrintWriter("plugins/Kettle/motd.txt");
				for (String line : defaultMOTD) {
					out.println(line);
				}
				out.close();
			} catch (Exception e) {
				kettle.error.printError("Failed to copy default motd.txt", e.getMessage());
			}
		}
	}

	public ArrayList<String> getMOTD() {
		File motdFile = new File("plugins/Kettle/motd.txt");
		ArrayList<String> motd = new ArrayList<String>();
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(motdFile));
			while ((sCurrentLine = br.readLine()) != null) {
				motd.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return motd;
	}
}
