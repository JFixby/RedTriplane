
package com.jfixby.r3.fokker.core;

import com.jfixby.r3.api.ui.unit.Intent;
import com.jfixby.r3.fokker.api.EngineState;
import com.jfixby.r3.fokker.core.unit.UnitContainer;
import com.jfixby.r3.fokker.core.unit.UnitContainerProperties;

public class UnitsManager {
	private UnitContainer current_unit_container;

	public void loadNext (final Intent intent) {
		if (this.current_unit_container != null) {
			this.current_unit_container.doDispose();
			this.current_unit_container = null;
		}
		final UnitContainerProperties unit_container_propertis = new UnitContainerProperties();
		unit_container_propertis.setIntent(intent);
		this.current_unit_container = UnitContainer.newUnitContainer(unit_container_propertis);
		this.current_unit_container.doDeploy();

	}

	public boolean isIdle () {
		return this.current_unit_container == null;
	}

	public void suspend () {
		this.current_unit_container.doSuspend();
	}

	public void resume () {
		this.current_unit_container.doResume();
	}

	public void update (final EngineState engine_state) {

		this.current_unit_container.doUpdate(engine_state);
	}

	public void render (final EngineState engine_state) {
		this.current_unit_container.doRender(engine_state);
	}

}
