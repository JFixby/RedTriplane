
package com.jfixby.r3.fokker.starter;

import com.jfixby.r3.fokker.api.starter.FokkerStarterConfig;
import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.scarabei.api.assets.ID;

public class FokkerStarterConfigAsset implements Asset, AssetsGroup {

	private final FokkerStarterConfig configData;
	private final ID assetID;

	public FokkerStarterConfigAsset (final ID assetID, final FokkerStarterConfig configData) {
		this.assetID = assetID;
		this.configData = configData;
	}

	@Override
	public ID getAssetID () {
		return this.assetID;
	}

	@Override
	public String toString () {
		return "FokkerStarterConfigAsset[" + this.assetID + "]";
	}

	public void print () {
		this.configData.print();
	}

	public String getValue (final String arg0) {
		return this.configData.params.get(arg0);
	}

	@Override
	public AssetsGroup getGroup () {
		return this;
	}

	@Override
	public void dispose () {
	}

}
