
package com.jfixby.r3.fokker.api.starter;

import java.util.HashMap;

import com.jfixby.scarabei.api.collections.Collections;

public class FokkerStarterConfig {

	public static final String TITLE = "title";
	public static final String useGL30 = "false";
	public static final String width = "width";
	public static final String height = "height";

	public static final String PACKAGE_FORMAT_STRING = "RedTriplane.Fokker.StarterConfig";
	public static final String FILE_NAME = "r3-fokker-starter-config.json";

	public HashMap<String, String> params = new HashMap<String, String>();

	public String getValue (final String key) {
		return this.params.get(key);
	}

	public void print () {
		Collections.newMap(this.params).print("FokkerStarterConfig");
		;
	}

}
