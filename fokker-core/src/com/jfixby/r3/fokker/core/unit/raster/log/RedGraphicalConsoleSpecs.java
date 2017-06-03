
package com.jfixby.r3.fokker.core.unit.raster.log;

import com.jfixby.r3.api.ui.unit.raster.GraphicalConsoleSpecs;
import com.jfixby.scarabei.api.log.LoggerComponent;

public class RedGraphicalConsoleSpecs implements GraphicalConsoleSpecs {

	private LoggerComponent logger;

	@Override
	public void setSubsequentLogger (final LoggerComponent logger) {
		this.logger = logger;
	}

	@Override
	public LoggerComponent getSubsequentLogger () {
		return this.logger;
	}

}
