
package com.jfixby.r3.fokker.core.unit.tool;

import com.jfixby.r3.api.ui.unit.ScreenShot;
import com.jfixby.r3.api.ui.unit.ScreenShotSpecs;
import com.jfixby.r3.api.ui.unit.UnitToolkit;
import com.jfixby.r3.fokker.core.unit.RedUnitExecutor;

public class RedUnitTools implements UnitToolkit {

	private RedUnitExecutor master;

	public RedUnitTools (RedUnitExecutor redUnitExecutor) {
		this.master = redUnitExecutor;
	}

	@Override
	public ScreenShotSpecs newScreenShotSpecs () {
		return new RedScreenShotSpecs();
	}

	@Override
	public ScreenShot newScreenShot (ScreenShotSpecs sh_spec) {
		return new RedScreenShot(sh_spec);
	}

}
