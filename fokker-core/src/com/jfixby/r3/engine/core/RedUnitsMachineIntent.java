
package com.jfixby.r3.engine.core;

import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.api.ui.unit.IntentStack;
import com.jfixby.r3.api.ui.unit.UnitListener;
import com.jfixby.scarabei.api.assets.ID;

public class RedUnitsMachineIntent implements Intent {

	private final ID intent_id;
	private UnitListener listener;
	private final IntentStack stack;

	public RedUnitsMachineIntent (final ID intent_id, final IntentStack stack) {
		this.intent_id = intent_id;
		this.stack = stack;
	}

	@Override
	public ID getUnitClassID () {
		return this.intent_id;
	}

	@Override
	public String toString () {
		return "Intent[" + this.intent_id + "]";
	}

	@Override
	public void setUnitListener (final UnitListener listener) {
		this.listener = listener;
	}

	@Override
	public UnitListener getUnitListener () {
		return this.listener;
	}

	@Override
	public IntentStack getStack () {
		return this.stack;
	}

}
