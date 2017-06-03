
package com.jfixby.r3.fokker.core.unit.parallax;

import com.jfixby.r3.api.ui.unit.parallax.Parallax;
import com.jfixby.r3.api.ui.unit.parallax.ParallaxFactory;
import com.jfixby.r3.api.ui.unit.parallax.ParallaxSpecs;
import com.jfixby.r3.fokker.core.unit.RedComponentsFactory;

public class RedParallaxFactory implements ParallaxFactory {

	public final RedComponentsFactory redComponentsFactory;

	public RedParallaxFactory (final RedComponentsFactory redComponentsFactory) {
		this.redComponentsFactory = redComponentsFactory;
	}

	@Override
	public ParallaxSpecs newParallaxSpecs () {
		return new RedParallaxSpecs();
	}

	@Override
	public Parallax newParallax (final ParallaxSpecs specs) {
		return new RedParallax(this, specs);
	}

}
