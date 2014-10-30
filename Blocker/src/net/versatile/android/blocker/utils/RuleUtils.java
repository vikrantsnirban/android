package net.versatile.android.blocker.utils;

public class RuleUtils {
	public static String getRegexByFormat(String format){
		return format.replaceAll("\\?", "\\\\d{1}").replaceAll("\\*", "\\\\d+");

	}
}
