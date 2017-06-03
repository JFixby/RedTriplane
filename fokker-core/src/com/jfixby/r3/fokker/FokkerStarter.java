
package com.jfixby.r3.fokker;

import com.jfixby.r3.fokker.api.FokkerEngineAssembler;
import com.jfixby.r3.fokker.api.UnitsMachineExecutor;

public class FokkerStarter {

	FokkerUnitsMachine machine;

	FokkerStarter (final FokkerStarterSpecs config) {
		final FokkerEngineAssembler engine_assembler = config.getEngineAssembler();

		this.machine = new FokkerUnitsMachine(engine_assembler);

	}

	public static FokkerStarterSpecs newRedTriplaneConfig () {
		return new FokkerStarterSpecs();
	}

	public static FokkerStarter newRedTriplane (final FokkerStarterSpecs config) {
		return new FokkerStarter(config);
	}

	public UnitsMachineExecutor getUnitsMachineExecutor () {
		return this.machine;
	}

}
