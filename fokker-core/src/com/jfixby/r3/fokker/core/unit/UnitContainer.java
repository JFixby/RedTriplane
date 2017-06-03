
package com.jfixby.r3.fokker.core.unit;

import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.api.ui.UnitsSpawner;
import com.jfixby.r3.api.ui.UnitsSpawningException;
import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.UnitListener;
import com.jfixby.r3.fokker.api.EngineState;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.err.Err;

public class UnitContainer {

	public static UnitContainer newUnitContainer (final UnitContainerProperties unit_container_propertis) {
		return new UnitContainer(unit_container_propertis);
	}

	private final Intent intent;
	private Unit unit;
	private final RedUnitExecutor unit_executor;

	public UnitContainer (final UnitContainerProperties unit_container_propertis) {
		this.intent = unit_container_propertis.getIntent();
		this.unit_executor = new RedUnitExecutor(this);
	}

	public void doDispose () {
		this.unit.onPause();
		this.inspector.onPause();
		LoadedAssets.releaseAllAssets((AssetsConsumer)this.unit_executor.getComponentsFactory());
		this.unit.onDestroy();
		this.inspector.onDestroy();

	}

	final RedUnitStateInspector inspector = new RedUnitStateInspector();

	public void doDeploy () {
		try {
			this.unit = UnitsSpawner.spawnUnit(this.intent);
		} catch (final UnitsSpawningException e) {
			e.printStackTrace();
			Err.reportError(e);
		}
		final UnitListener listener = this.intent.getUnitListener();
		this.inspector.setListener(listener);
		this.inspector.setUnit(this.unit);
		this.inspector.onInit();
		this.unit.onCreate(this.unit_executor);
		this.inspector.onCreate();
		this.unit.onStart();
		this.inspector.onStart();
	}

	public void doExecute (final EngineState engine_state) {

	}

	public void doSuspend () {
		this.unit_executor.suspend();
		this.unit.onPause();
		this.inspector.onPause();
	}

	public void doResume () {
		this.unit_executor.resume();
		this.unit.onResume();
		this.inspector.onResume();
	}

	public void doUpdate (final EngineState engine_state) {
		this.unit_executor.update(engine_state);
	}

	public void doRender (final EngineState engine_state) {
		if (!Screen.isInValidState()) {
			return;
		}
		this.unit_executor.render(engine_state);
	}

	public String getUnitDebugName () {
		return this.unit.toString();
	}

}
