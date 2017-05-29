
package com.jfixby.r3.engine.core;

import com.jfixby.r3.api.EngineVersion;
import com.jfixby.r3.api.GameStarter;
import com.jfixby.r3.api.RedTriplaneComponent;
import com.jfixby.scarabei.api.debug.Debug;

public class Fokker implements RedTriplaneComponent {

	private static final FokkerVersion VERSION = new FokkerVersion("Fokker", "#160404", "https://github.com/JFixby/Red");
	private GameStarter starter;

	@Override
	public EngineVersion VERSION () {
		return VERSION;
	}

	@Override
	public void setGameStarter (final GameStarter starter) {
		Debug.checkNull("EngineStarter", starter);
		this.starter = starter;
	}

	@Override
	public GameStarter getGameStarter () {
		return this.starter;
	}

}
