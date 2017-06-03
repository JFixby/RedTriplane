
package com.jfixby.r3.fokker.core;

import com.jfixby.r3.api.EngineVersion;

public class FokkerVersion implements EngineVersion {

	private String name;
	private String build_id;
	private String homepage;

	@Override
	public String toString () {
		return "FokkerVersion [name=" + name + ", build_id=" + build_id + ", homepage=" + homepage + "]";
	}

	public FokkerVersion (String name, String buildID, String homepage) {
		this.name = name;
		this.build_id = buildID;
		this.homepage = homepage;
	}

	@Override
	public String getName () {
		return name;
	}

	@Override
	public String getBuildID () {
		return build_id;
	}

	@Override
	public String getHomePage () {
		return homepage;
	}

}
