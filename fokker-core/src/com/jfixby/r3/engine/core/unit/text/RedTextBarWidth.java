
package com.jfixby.r3.engine.core.unit.text;

import com.jfixby.r3.api.ui.unit.txt.TextBarWidth;

public class RedTextBarWidth implements TextBarWidth {

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
