package me.michaelkrauty.Kettle.util;

/**
 * Created on 7/22/2014.
 *
 * @author michaelkrauty
 */
public class Groups {

	public static String getPrefix(Group group) {
		if (group == Group.LAME)
			return "";
		else if (group == Group.MEMBER)
			return "";
		else if (group == Group.NOBLE)
			return "";
		else if (group == Group.ELITE)
			return "";
		else if (group == Group.ULTIMATE)
			return "";
		else if (group == Group.MODERATOR)
			return "";
		else if (group == Group.ADMIN)
			return "";
		else if (group == Group.OWNER)
			return "";
		else
			return "ERROR";
	}

	public static String getSuffix(Group group) {
		if (group == Group.LAME)
			return "";
		else if (group == Group.MEMBER)
			return "";
		else if (group == Group.NOBLE)
			return "";
		else if (group == Group.ELITE)
			return "";
		else if (group == Group.ULTIMATE)
			return "";
		else if (group == Group.MODERATOR)
			return "";
		else if (group == Group.ADMIN)
			return "";
		else if (group == Group.OWNER)
			return "";
		else
			return "ERROR";
	}

	public static double getRequiredPlaytime(Group group) {
		if (group == Group.LAME)
			return 0;
		else if (group == Group.MEMBER)
			return 300;
		else if (group == Group.NOBLE)
			return 1000;
		else if (group == Group.ELITE)
			return 2500;
		else if (group == Group.ULTIMATE)
			return 10000;
		else if (group == Group.MODERATOR)
			return -1;
		else if (group == Group.ADMIN)
			return -1;
		else if (group == Group.OWNER)
			return -1;
		else
			return -2;
	}
}
