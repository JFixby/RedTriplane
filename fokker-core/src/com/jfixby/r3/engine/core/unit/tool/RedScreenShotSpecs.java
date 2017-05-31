
package com.jfixby.r3.engine.core.unit.tool;

import com.badlogic.gdx.Gdx;
import com.jfixby.r3.api.ui.unit.ScreenShotSpecs;
import com.jfixby.r3.api.ui.unit.camera.Camera;

public class RedScreenShotSpecs implements ScreenShotSpecs {

	private int w = Gdx.graphics.getWidth();
	private int y;
	private int x;
	private int h = Gdx.graphics.getHeight();
	private Camera camera;

	@Override
	public int getAreaWidth () {
		return w;
	}

	@Override
	public int getAreaHeight () {
		return h;
	}

	@Override
	public int getAreaY () {
		return y;
	}

	@Override
	public int getAreaX () {
		return x;
	}

	@Override
	public void setAreaX (int x) {
		this.x = x;
	}

	@Override
	public void setAreaY (int y) {
		this.y = y;
	}

	@Override
	public void setAreaWidth (int w) {
		this.w = w;

	}

	@Override
	public void setAreaHeight (int h) {
		this.h = h;
	}

	@Override
	public void setCamera (Camera camera) {
		this.camera = camera;
	}

	@Override
	public Camera getCamera () {
		return camera;
	}

}
