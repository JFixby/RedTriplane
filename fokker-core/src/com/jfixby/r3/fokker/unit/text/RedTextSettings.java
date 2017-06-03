
package com.jfixby.r3.fokker.unit.text;

import com.jfixby.r3.api.ui.unit.txt.TextSettings;
import com.jfixby.scarabei.api.assets.ID;

public class RedTextSettings implements TextSettings {

	private ID text_id;

	@Override
	public ID getTextID () {
		return text_id;
	}

	@Override
	public void setTextID (ID text_id) {
		this.text_id = text_id;
	}

}
