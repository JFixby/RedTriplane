
package com.jfixby.r3.api.ui;

import com.jfixby.r3.api.ui.unit.Unit;

public interface UnitSpawnerComponent {

	public Unit spawnUnit (Intent unit_class_name) throws UnitsSpawningException;

}
