
package com.jfixby.r3.fokker.adaptor;

import com.badlogic.gdx.Gdx;
import com.jfixby.r3.api.input.InputQueue;
import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.fokker.api.EngineState;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.r3.fokker.api.render.RenderMachine;
import com.jfixby.r3.fokker.render.FokkerRenderMachine;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.taskman.SysExecutor;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.api.util.StateSwitcher;

public class GdxAdaptor implements com.badlogic.gdx.ApplicationListener, EngineState {

	private final UnitsMachineExecutor executor;
	long cycle = 0;
	final GdxAdaptorViewportState viewport_state = new GdxAdaptorViewportState(this);
	final GdxInputAdaptor input_adaptor = new GdxInputAdaptor(this);

// private FokkerRenderMachine fokker_render_machine;
	private final StateSwitcher<ENGINE_STATE> state;
	private DebugTimer timer;

	public GdxAdaptor (final UnitsMachineExecutor executor) {
		this.executor = executor;
		this.state = JUtils.newStateSwitcher(ENGINE_STATE.NEW);
	}

	public com.badlogic.gdx.ApplicationListener getGDXApplicationListener () {
		return this;
	}

	@Override
	public void create () {

	}

	@Override
	public void resize (final int width, final int height) {
		this.viewport_state.updateViewport(width, height);
		this.viewport_state.flagNeedUpdate();
	}

	@Override
	public void render () {
		if (this.state.currentState() == ENGINE_STATE.OK) {
			this.do_full_cycle();
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.RESTORING) {
			this.do_resume();
			this.setState(ENGINE_STATE.OK);
			this.do_full_cycle();
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.NEW) {
			this.deploy();
			this.setState(ENGINE_STATE.OK);
			this.do_full_cycle();
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.BROKEN) {
			// this.setState(ENGINE_STATE.RESTORING);
			// this.do_resume();
			// this.setState(ENGINE_STATE.OK);
			// this.do_cycle();

			this.do_only_update_cycle();

			return;
		}
	}

	private void do_only_update_cycle () {
		SysExecutor.pushTasks();
		this.input_adaptor.flush();
		this.viewport_state.checkNeedUpdateFlag(this.cycle);
		this.executor.doUpdate(this);
		this.cycle++;
	}

	private void do_resume () {
		// viewport_state.flagNeedUpdate();
		this.executor.doResume();
		this.input_adaptor.enable();
	}

	private void do_full_cycle () {
		// this.timer.reset();
		SysExecutor.pushTasks();
		// this.timer.printTime("SysExecutor.pushTasks()");
		this.viewport_state.checkNeedUpdateFlag(this.cycle);
		// this.timer.reset();
		this.executor.doUpdate(this);
		// this.timer.printTime("doUpdate()");
		// this.timer.reset();
		this.executor.doRender(this);
		// this.timer.printTime("doRender()");
		this.input_adaptor.flush();
		this.cycle++;
	}

	private void deploy () {
		this.state.expectState(ENGINE_STATE.NEW);
		Screen.installComponent(this.viewport_state);
// fokker_render_machine = new FokkerRenderMachine();
		RenderMachine.installComponent(new FokkerRenderMachine());
// ------------------------------
		this.executor.doDeploy();
		Gdx.input.setInputProcessor(this.input_adaptor);
		this.input_adaptor.deploy();
		this.input_adaptor.enable();
		this.timer = Debug.newTimer();

	}

	private void do_pause () {
		this.executor.doPause();
		this.input_adaptor.disable();
	}

	@Override
	public void pause () {
		if (this.state.currentState() == ENGINE_STATE.OK) {
			this.do_pause();
			this.setState(ENGINE_STATE.BROKEN);
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.NEW) {
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.RESTORING) {
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.BROKEN) {
			return;
		}
	}

	private void setState (final ENGINE_STATE next_state) {
		L.d("ENGINE: " + this.state, next_state);
		this.state.switchState(next_state);
	}

	@Override
	public void resume () {
		if (this.state.currentState() == ENGINE_STATE.BROKEN) {
			this.setState(ENGINE_STATE.RESTORING);
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.OK) {
			this.do_pause();
			this.setState(ENGINE_STATE.BROKEN);
			this.setState(ENGINE_STATE.RESTORING);
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.RESTORING) {
			return;
		}
		if (this.state.currentState() == ENGINE_STATE.NEW) {
			return;
		}
	}

	@Override
	public void dispose () {
		this.executor.doDispose();
	}

	@Override
	public long getCurrentCycleNumber () {
		return this.cycle;
	}

	@Override
	public InputQueue getInputQueue () {
		return this.input_adaptor;
	}

	@Override
	public boolean isBroken () {
		return this.state.currentState() == ENGINE_STATE.BROKEN;
	}
}
