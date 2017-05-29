
package com.jfixby.r3.ext.font.gdx;

import com.jfixby.scarabei.api.debug.Debug;

public class FontPage {

	private float[] vertices;

	public FontPage (float[] fs) {
		this.vertices = Debug.checkNull(fs);
	}

	public float[] getVertices () {
		return vertices;
	}

}