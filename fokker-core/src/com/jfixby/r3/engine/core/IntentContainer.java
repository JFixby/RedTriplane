
package com.jfixby.r3.engine.core;

import com.jfixby.r3.api.ui.unit.Intent;

public class IntentContainer {

	private final Intent intent;

	public IntentContainer (final Intent intent) {
		this.intent = intent;
	}

	public Intent intent () {
		return this.intent;
	}

}
