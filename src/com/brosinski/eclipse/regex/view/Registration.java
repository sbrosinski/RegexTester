package com.brosinski.eclipse.regex.view;

public class Registration {

	public static boolean isUserValid(User user) {
	    if (user.getName() == null ||
	        user.getCode() == null ||
	        user.getEmail() == null)
	        	return false;
	    
		System.out.println(makeKey(user.getName(), user.getEmail()));
		return isKeyValid(user.getName(), user.getEmail(), user.getCode());
	}
	
	public static boolean isKeyValid(String name, String email,
			String key) {
	    if (name == null || "".equals(name))
	        return false;
	    if (email == null || "".equals(email))
	        return false;
	    if (key == null || "".equals(key))
	        return false;
	    
		if (key.length() <= 5)
			return false;
		return key.substring(5).equalsIgnoreCase(
				makeKey(name, email).substring(5));
	}

	public static String makeKey(String name, String email) {
	    if (name == null || "".equals(name))
	        return "invalid";
	    if (email == null || "".equals(email))
	        return "invalid";	    
	    
		return "1300-" + numberForString(name, 7, 3, 4, 6, 5, 324) + "-"
				+ numberForString(email, 10, 8, 5, 7, 2, 245);
	}

	private static String numberForString(String text, int minLen, int c1, int c2,
			int c3, int c4, int addUp) {
		if (text.length() < minLen) {
			text = makeStringLonger(text, minLen);
		}
		int num = ((text.charAt(c1) + text.charAt(c2) * 2) * text.charAt(c3) + 3)
				* text.charAt(c4);
		num = ((num % 10000) + 3791) % 10000 + addUp;
		return String.valueOf(num);
	}

	private static String makeStringLonger(String text, int minLen) {
		while (text.length() <= minLen) {
			text += text;
		}
		return text;
	}

}