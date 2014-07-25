package me.michaelkrauty.Kettle.util;

/**
 * Created on 7/22/2014.
 *
 * @author michaelkrauty
 */
public class Groups {

	public static String getPrefix(String group) {
		if (group.equalsIgnoreCase("lame"))
			return "&8[&7Lame&8] &7";
		else if (group.equalsIgnoreCase("member"))
			return "&8[&aMember&8] &7";
		else if (group.equalsIgnoreCase("noble"))
			return "noble ";
		else if (group.equalsIgnoreCase("elite"))
			return "elite ";
		else if (group.equalsIgnoreCase("ultimate"))
			return "ultimate ";
		else if (group.equalsIgnoreCase("moderator"))
			return "moderator ";
		else if (group.equalsIgnoreCase("admin"))
			return "admin ";
		else if (group.equalsIgnoreCase("owner"))
			return "owner ";
		else
			return "ERROR";
	}

	public static String getSuffix(String group) {
		if (group.equalsIgnoreCase("lame"))
			return "";
		else if (group.equalsIgnoreCase("member"))
			return "";
		else if (group.equalsIgnoreCase("noble"))
			return "";
		else if (group.equalsIgnoreCase("elite"))
			return "";
		else if (group.equalsIgnoreCase("ultimate"))
			return "";
		else if (group.equalsIgnoreCase("moderator"))
			return "";
		else if (group.equalsIgnoreCase("admin"))
			return "";
		else if (group.equalsIgnoreCase("owner"))
			return "";
		else
			return "ERROR";
	}

	public static double getRequiredPlaytime(String group) {
		if (group.equalsIgnoreCase("lame"))
			return 0;
		else if (group.equalsIgnoreCase("member"))
			return 300;
		else if (group.equalsIgnoreCase("noble"))
			return 1000;
		else if (group.equalsIgnoreCase("elite"))
			return 2500;
		else if (group.equalsIgnoreCase("ultimate"))
			return 10000;
		else if (group.equalsIgnoreCase("moderator"))
			return -1;
		else if (group.equalsIgnoreCase("admin"))
			return -1;
		else if (group.equalsIgnoreCase("owner"))
			return -1;
		else
			return -2;
	}

	public static boolean groupExists(String group) {
		return group.equals("lame")
				|| group.equals("member")
				|| group.equals("noble")
				|| group.equals("ultimate")
				|| group.equals("moderator")
				|| group.equals("admin")
				|| group.equals("owner");
	}
}
