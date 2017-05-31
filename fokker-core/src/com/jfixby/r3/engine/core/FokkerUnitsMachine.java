
package com.jfixby.r3.engine.core;

import com.jfixby.r3.api.RedTriplane;
import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.api.ui.unit.IntentStack;
import com.jfixby.r3.api.ui.unit.UnitsMachine;
import com.jfixby.r3.api.ui.unit.UnitsMachineComponent;
import com.jfixby.r3.fokker.api.EngineState;
import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;
import com.jfixby.r3.fokker.api.render.RenderMachine;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Queue;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.taskman.SysExecutor;
import com.jfixby.scarabei.api.ver.Version;

public class FokkerUnitsMachine implements UnitsMachineComponent, UnitsMachineExecutor {

	final private FokkerEngineAssembler engine_assembler;
	private Queue<IntentContainer> queue;
	UnitsManager units_manager;

	FokkerUnitsMachine (final FokkerEngineAssembler engine_assembler) {
		this.engine_assembler = engine_assembler;
	}

	@Override
	public void doDeploy () {
		this.queue = Collections.newQueue();
		UnitsMachine.installComponent(this);

		if (this.engine_assembler != null) {
			this.engine_assembler.assembleEngine();
		}

		final String applicationPackageName = (SystemSettings.getStringParameter(Version.Tags.PackageName));
		final String versionCode = SystemSettings.getStringParameter(Version.Tags.VersionCode);
		final String versionName = SystemSettings.getStringParameter(Version.Tags.VersionName);

		Debug.checkNull("SystemSettings :: " + Version.Tags.PackageName, applicationPackageName);
		Debug.checkEmpty("SystemSettings :: " + Version.Tags.PackageName, applicationPackageName);

		Debug.checkNull("SystemSettings :: " + Version.Tags.VersionCode, versionCode);
		Debug.checkEmpty("SystemSettings :: " + Version.Tags.VersionCode, versionCode);

		Debug.checkNull("SystemSettings :: " + Version.Tags.VersionName, versionName);
		Debug.checkEmpty("SystemSettings :: " + Version.Tags.VersionName, versionName);

		L.d("------[RedTriplane Engine Start]---------------------------------------------------------");
		L.d("            version - " + RedTriplane.VERSION().getName());
		L.d("           build id - " + RedTriplane.VERSION().getBuildID());
		L.d("           homepage - " + RedTriplane.VERSION().getHomePage());
		L.d();
		L.d("        application - " + applicationPackageName);
		L.d("            version - " + versionName);
		L.d("            v. code - " + versionCode);
		L.d("-----------------------------------------------------------------------------------------");

		// Sys.exit();

		this.units_manager = new UnitsManager();

		SysExecutor.onSystemStart();

		PackageLoaders.deploy();

		RenderMachine.init();

		RedTriplane.getGameStarter().onGameStart();

// L.d("Screen dimensions", Screen.getScreenDimensions());

	}

	@Override
	public void doDispose () {
		if (this.units_manager.isIdle()) {
			return;
		}
	}

	@Override
	public void doPause () {
		if (this.units_manager.isIdle()) {
			return;
		}
		this.units_manager.suspend();
	}

	@Override
	public void doRender (final EngineState engine_state) {
// if (this.queue.hasMore()) {
// final Intent intent = this.queue.pop().intent();
// this.units_namager.loadNext(intent);
// return;
// }
		if (this.units_manager.isIdle()) {
			return;
		}
		this.units_manager.render(engine_state);
	}

	@Override
	public void doResume () {
		if (this.units_manager.isIdle()) {
			return;
		}
		this.units_manager.resume();
	}

	@Override
	public void doUpdate (final EngineState engine_state) {
		if (this.queue.hasMore()) {
			final Intent intent = this.queue.dequeue().intent();
			this.units_manager.loadNext(intent);
			return;
		}
		if (this.units_manager.isIdle()) {
			return;
		}

		this.units_manager.update(engine_state);

	}

	@Override
	public Intent newIntent (final ID intent_id) {
		final IntentStack stack = new IntentStack();
		return new RedUnitsMachineIntent(intent_id, stack);
	}

	@Override
	public void nextUnit (final Intent intent) {
		this.queue.enqueue(new IntentContainer(intent));
	}

}
