
package com.jfixby.r3.engine.core;

import com.jfixby.r3.fokker.api.FokkerEngineAssembler;

public class FokkerStarterSpecs {

	FokkerStarterSpecs () {
	}

	private FokkerEngineAssembler engine_assembler;

	public void setEngineAssembler (FokkerEngineAssembler engine_assembler) {
		this.engine_assembler = engine_assembler;
	}

	public FokkerEngineAssembler getEngineAssembler () {
		return this.engine_assembler;
	}
}
