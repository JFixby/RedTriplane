
package com.jfixby.r3.fokker.core.unit.text;

import com.jfixby.r3.api.ui.unit.txt.TextBarHeight;

public class RedTextBarHeight implements TextBarHeight {

	@Override
	public boolean isWrapContent () {
		return false;
	}

	@Override
	public boolean isFixed () {
		return false;
	}

	@Override
	public float getValueInPixels () {
		return 0;
	}

}
