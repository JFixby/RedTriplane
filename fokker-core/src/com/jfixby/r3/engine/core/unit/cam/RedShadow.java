
package com.jfixby.r3.engine.core.unit.cam;

import com.jfixby.r3.api.EngineParams.Assets;
import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.api.screen.ScreenDimentions;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraManager;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.Shadow;
import com.jfixby.r3.api.ui.unit.camera.ShadowSpecs;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.Tile;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedShadow implements Shadow, LayerBasedComponent, CameraManager {
	private Layer root;
	private Camera camera;

	@Override
	public void onScreenUpdate (final ScreenDimentions viewport_update, final Camera your_camera) {
		// your_camera.setDebugFlag(!true);
		your_camera.setSize(Screen.getScreenWidth(), Screen.getScreenHeight());
		your_camera.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.shadow_sprite.setWidth(your_camera.getWidth() * 1.91);
		this.shadow_sprite.setHeight(your_camera.getHeight() * 1.91);
		this.shadow_sprite.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.shadow_sprite.setPosition();
		your_camera.setPosition();
		// L.d("onScreenUpdate");

	}

	private Tile shadow_sprite;

	public RedShadow (final ShadowSpecs shadow_specs, final ComponentsFactory components_factory) {
		this.setup(components_factory);
		this.reset();
		this.root.closeInputValve();
	}

	public void setup (final ComponentsFactory components_factory) {
		this.root = components_factory.newLayer();
		this.root.setName("ShadowLayer");
		final CameraSpecs cam_specs = components_factory.getCameraDepartment().newCameraSpecs();
		cam_specs.setCameraManager(this);
// cam_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);

		this.camera = components_factory.getCameraDepartment().newCamera(cam_specs);
		this.camera.setDebugName("ShadowCamera");
		// camera.setApertureOpacity(ABSOLUTE_CLEAR);
		this.root.setCamera(this.camera);

		this.shadow_sprite = components_factory.getRasterDepartment().newTile(SystemSettings.getSystemAssetID(Assets.SPRITE_BLACK));
		this.shadow_sprite.setOpacity(1f);
		this.root.attachComponent(this.shadow_sprite);

// this.shadow_sprite.setHeight(1.1);
// this.shadow_sprite.setWidth(1.1);
// this.shadow_sprite.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
// this.shadow_sprite.setPosition(0, 0);
// this.shadow_sprite.setDebugRenderFlag(false);

		this.onScreenUpdate(null, this.camera);

	}

	public Layer getRootLayer () {
		return this.root;
	}

	@Override
	public void setValue (float f) {
		f = FloatMath.limit(ABSOLUTE_CLEAR, f, ABSOLUTE_DARKNESS);
		this.shadow_sprite.setOpacity(f);
	}

	public void reset () {
		this.setValue(ABSOLUTE_DARKNESS);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public float getValue () {
		return this.shadow_sprite.getOpacity();
	}
}
