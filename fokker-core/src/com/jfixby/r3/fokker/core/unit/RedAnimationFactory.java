
package com.jfixby.r3.fokker.core.unit;

import com.jfixby.r3.api.ui.unit.animation.AnimationFactory;
import com.jfixby.r3.api.ui.unit.animation.EventsGroupSpecs;
import com.jfixby.r3.api.ui.unit.animation.EventsSequence;
import com.jfixby.r3.api.ui.unit.animation.EventsSequenceSpecs;
import com.jfixby.r3.api.ui.unit.animation.LayersAnimation;
import com.jfixby.r3.api.ui.unit.animation.LayersAnimationSpecs;
import com.jfixby.r3.api.ui.unit.animation.PositionAnchor;
import com.jfixby.r3.api.ui.unit.animation.PositionsSequence;
import com.jfixby.r3.api.ui.unit.animation.PositionsSequenceSpecs;
import com.jfixby.r3.fokker.core.unit.anim.RedAnimationAnchor;
import com.jfixby.r3.fokker.core.unit.anim.RedAnimationSequence;
import com.jfixby.r3.fokker.core.unit.anim.RedAnimationSequenceSpecs;
import com.jfixby.r3.fokker.core.unit.anim.RedEventsGroupSpecs;
import com.jfixby.r3.fokker.core.unit.anim.RedEventsSequence;
import com.jfixby.r3.fokker.core.unit.anim.RedEventsSequenceSpecs;
import com.jfixby.r3.fokker.core.unit.anim.RedLayerAnimation;
import com.jfixby.r3.fokker.core.unit.anim.RedLayerAnimationSpecs;

public class RedAnimationFactory implements AnimationFactory {

	private final RedComponentsFactory master;

	public RedAnimationFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public LayersAnimationSpecs newLayersAnimationSpecs () {
		return new RedLayerAnimationSpecs();
	}

	@Override
	public LayersAnimation newLayerAnimation (final LayersAnimationSpecs specs) {
		return new RedLayerAnimation(specs, this.master);
	}

	@Override
	public PositionAnchor newAnchor (final long time_stamp) {
		return new RedAnimationAnchor(time_stamp);
	}

	@Override
	public PositionsSequenceSpecs newPositionsSequenceSpecs () {
		return new RedAnimationSequenceSpecs();
	}

	@Override
	public PositionsSequence newPositionsSequence (final PositionsSequenceSpecs specs) {
		return new RedAnimationSequence(specs, this.master);
	}

	@Override
	public EventsSequenceSpecs newEventsSequenceSpecs () {
		return new RedEventsSequenceSpecs();
	}

	@Override
	public EventsSequence newEventsSequence (final EventsSequenceSpecs specs) {
		return new RedEventsSequence(specs, this.master);
	}

	@Override
	public EventsGroupSpecs newEventsGroupSpecs () {
		return new RedEventsGroupSpecs();
	}

}
