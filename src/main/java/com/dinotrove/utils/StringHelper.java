package com.dinotrove.utils;

public class StringHelper {
	public static String makeSuffixWildCardString(String inputString) {
		return inputString.toLowerCase() + "%";
	}
	public static String makeWildCardString(String inputString) {
		return "%" + inputString.toLowerCase() + "%";
	}
	public static String escapeSQL(String s) {
		int length = s.length();
		int newLength = length;
		// first check for characters that might
		// be dangerous and calculate a length
		// of the string that has escapes.
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\':
			case '\"':
			case '\'':
			case '\0': {
				newLength += 1;
			}
				break;
			}
		}
		if (length == newLength) {
			// nothing to escape in the string
			return s;
		}
		StringBuffer sb = new StringBuffer(newLength);
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\\': {
				sb.append("\\\\");
			}
				break;
			case '\"': {
				sb.append("\\\"");
			}
				break;
			case '\'': {
				sb.append("\\\'");
			}
				break;
			case '\0': {
				sb.append("\\0");
			}
				break;
			default: {
				sb.append(c);
			}
			}
		}
		return sb.toString();
	}
}