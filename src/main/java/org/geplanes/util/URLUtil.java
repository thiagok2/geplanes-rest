package org.geplanes.util;

import java.text.Normalizer;

public class URLUtil {
	public static String toURL(String fromURL){
		return  Normalizer.normalize(fromURL, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "")
				.replace(".", "")
				.replace(" ", "-")
				.replace("/", "-")
				.toLowerCase();
	}
}
