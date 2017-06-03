
package com.jfixby.r3.fokker.unit.text;

import com.jfixby.r3.api.ui.unit.txt.TextBarMargin;

public class RedTextBarMargin implements TextBarMargin {

	@Override
	public boolean isPixels () {
		return false;
	}

	@Override
	public boolean isPercentage () {
		return false;
	}

	@Override
	public float getValue () {
		return 0;
	}

}
