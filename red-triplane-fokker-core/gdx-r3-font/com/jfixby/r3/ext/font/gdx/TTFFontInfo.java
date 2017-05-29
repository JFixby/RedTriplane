
package com.jfixby.r3.ext.font.gdx;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;

public class TTFFontInfo implements Asset {

	final private ID asset_id;
	final private File gdx_font_file;

	@Override
	public String toString () {
		return "TTFFontInfo[" + this.asset_id + "] " + this.gdx_font_file + "";
	}

	public TTFFontInfo (final ID asset_id, final File original_font_file) {
		this.asset_id = asset_id;
		this.gdx_font_file = original_font_file;
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	public File getFontFile () {
		return this.gdx_font_file;
	}

	@Override
	public AssetsGroup getGroup () {
		Err.throwNotImplementedYet();
		return null;
	}

}
