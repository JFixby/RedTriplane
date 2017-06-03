
package com.jfixby.r3.fokker.core.unit.raster.log;

import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.api.screen.ScreenDimentions;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.GraphicalConsole;
import com.jfixby.r3.api.ui.unit.raster.GraphicalConsoleSpecs;
import com.jfixby.r3.api.ui.unit.user.ScreenChangeListener;
import com.jfixby.r3.fokker.core.unit.RedComponentsFactory;
import com.jfixby.r3.fokker.core.unit.layers.RedLayer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.log.LoggerComponent;

public class RedGraphicalConsole implements GraphicalConsole, LayerBasedComponent {

	final LoggerComponent slog;
	final RedGraphicalConsoleLogger logger = new RedGraphicalConsoleLogger(this);
	private final RedComponentsFactory factory;
	private final RedLayer root;
	final List<ConsoleLine> lines = Collections.newList();
	private final RedLayer lineslayer;
	int currentLine = 0;
	private final Camera cam;

	public RedGraphicalConsole (final RedComponentsFactory master, final GraphicalConsoleSpecs gspec) {
		this.slog = Debug.checkNull("subsequentLogger", gspec.getSubsequentLogger());
		this.factory = master;

		this.root = this.factory.newLayer();
		this.root.closeInputValve();
		this.root.attachComponent(this.screenListener);
		this.lineslayer = this.factory.newLayer();
		this.root.attachComponent(this.lineslayer);
		this.height = Screen.getScreenHeight();
		this.width = Screen.getScreenWidth();

		final CameraSpecs cams = this.factory.getCameraDepartment().newCameraSpecs();
		cams.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);

		this.cam = this.factory.getCameraDepartment().newCamera(cams);
		this.root.setCamera(this.cam);

		this.update();

	}

	public void update () {
		this.lineslayer.detatchAllComponents();
		final int size = 8;
		final int N = this.height / size;
		for (int i = 0; i < N; i++) {
			final ConsoleLine line = new ConsoleLine("123456789", size, this.factory);
			this.lines.add(line);
			this.lineslayer.attachComponent(line);
			line.setPosition(this.lines.size() - 1);
		}

	}

	private void shift () {
		for (int i = 1; i < this.lines.size(); i++) {
			final ConsoleLine l0 = this.lines.getElementAt(i - 1);
			final ConsoleLine l1 = this.lines.getElementAt(i);
			l0.setValue(l1.getValue());
		}
	}

	@Override
	public LoggerComponent getLogger () {
		return this.logger;
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	private int width;
	private int height;

	final ScreenChangeListener screenListener = new ScreenChangeListener() {

		@Override
		public void onScreenChanged (final ScreenDimentions viewport_update) {
			L.d("onScreenChanged", viewport_update);
			RedGraphicalConsole.this.width = viewport_update.getScreenHeight();
			RedGraphicalConsole.this.height = viewport_update.getScreenHeight();
			RedGraphicalConsole.this.update();
		}
	};

	public void printLineE () {
		this.lines.getElementAt(this.currentLine).setValue("");
		this.currentLine++;
	}

	public void printLineD () {
		this.lines.getElementAt(this.currentLine).setValue("");
		this.currentLine++;
	}

	public void printLineD (final Object string) {
		this.lines.getElementAt(this.currentLine).setValue("" + string);
		this.currentLine++;
		if (this.currentLine >= this.lines.size()) {
			this.currentLine--;
			this.shift();
		}

	}

	public void printLineE (final Object string) {
		this.lines.getElementAt(this.currentLine).setValue("" + string);
		this.currentLine++;

	}

	public void printCharsD (final Object string) {
		this.lines.getElementAt(this.currentLine).addValue("" + string);
	}

}
