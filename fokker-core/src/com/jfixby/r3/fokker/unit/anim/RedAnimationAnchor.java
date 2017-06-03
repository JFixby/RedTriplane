
package com.jfixby.r3.fokker.unit.anim;

import com.jfixby.r3.api.ui.unit.animation.PositionAnchor;
import com.jfixby.scarabei.red.geometry.RedPosition;

public class RedAnimationAnchor extends RedPosition implements PositionAnchor {

	final private long timestamp;

	public RedAnimationAnchor (final long time_stamp) {
		this.timestamp = time_stamp;
	}

	@Override
	public long getTime () {
		return this.timestamp;
	}

	@Override
	public String toString () {
		return "RedAnimationAnchor [timestamp=" + this.timestamp + ", rotation=" + this.getRotation() + ", x=" + this.getX()
			+ ", y=" + this.getY() + "]";
	}

}
