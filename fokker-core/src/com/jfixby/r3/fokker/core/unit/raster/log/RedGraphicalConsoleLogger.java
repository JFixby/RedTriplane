
package com.jfixby.r3.fokker.core.unit.raster.log;

import com.jfixby.scarabei.api.log.LoggerComponent;
import com.jfixby.scarabei.red.log.SimpleLogger;

public class RedGraphicalConsoleLogger extends SimpleLogger implements LoggerComponent {

	private final RedGraphicalConsole console;

	public RedGraphicalConsoleLogger (final RedGraphicalConsole redGraphicalConsole) {
		this.console = redGraphicalConsole;
	}

}
