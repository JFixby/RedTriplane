
package com.jfixby.r3.api.fokker;

import java.util.HashMap;

public class FokkerStarterConfig {

	public static final String TITLE = "title";
	public static final String useGL30 = "false";
	public static final String width = "width";
	public static final String height = "height";

	public HashMap<String, String> params = new HashMap<String, String>();

	public String getValue (final String key) {
		return this.params.get(key);
	}

}
