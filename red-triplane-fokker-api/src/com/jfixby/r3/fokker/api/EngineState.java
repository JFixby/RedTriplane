package com.jfixby.r3.fokker.api;

import com.jfixby.r3.api.input.InputQueue;

public interface EngineState {

	long getCurrentCycleNumber();

	InputQueue getInputQueue();

	boolean isBroken();

}
