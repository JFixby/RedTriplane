
package com.jfixby.r3.fokker.core.unit;

import com.jfixby.r3.api.ui.unit.user.ScreenChangeListener;
import com.jfixby.r3.fokker.core.unit.cam.RedCamera;
import com.jfixby.r3.fokker.core.unit.layers.RedLayer;
import com.jfixby.r3.fokker.core.unit.layers.ScreenDimentionsUpdate;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;

public class RedLayerWindowViewportProcessor {

	final private RedLayer root_layer;

	public RedLayerWindowViewportProcessor (final RedLayer root_layer) {
		this.root_layer = root_layer;
	}

	public void deliverScreenDimentions () {
		this.deliverScreenDimentions(this.root_layer);
	}

	private void deliverScreenDimentions (final RedLayer layer) {
		if (!layer.isVisible()) {
			return;
		}
		final RedCamera camera = layer.getCamera();
		this.checkCamera(camera);

		final Map<Object, ScreenDimentionsUpdate> children = layer.listViewportListeners();
		for (int i = 0; i < children.size(); i++) {
			final ScreenDimentionsUpdate child = children.getValueAt(i);
			// child.updateViewport();
			final RedLayer child_layer = child.getLayer();
			// children.print("children");

			final ScreenChangeListener listener = child.getListener();
			if (child_layer != null) {
				this.deliverScreenDimentions(child_layer);
			} else if (listener != null) {
				child.updateListener();
			} else {

				Err.throwNotImplementedYet();
			}
		}
	}

	public void checkCamera (final RedCamera camera) {
		if (camera != null) {
			camera.checkScreenDimentions();
		}
	}

}
