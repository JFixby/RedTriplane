
package com.jfixby.r3.engine.core.unit.physics;

import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.physics.PhysicsCoreDebugRenderer;
import com.jfixby.r3.api.ui.unit.physics.PhysicsCoreDebugRendererSpecs;
import com.jfixby.r3.engine.core.unit.RedComponentsFactory;

public class Box2DRenderer implements PhysicsCoreDebugRenderer, LayerBasedComponent {

	final Camera camera;

	final Layer root_layer;
	final GDXBox2DDebugRendererComponent box2d_renderer;

	public Box2DRenderer (final PhysicsCoreDebugRendererSpecs debug_renderer_properties, final RedComponentsFactory master) {
		final CameraSpecs cam_properties = master.getCameraDepartment().newCameraSpecs();
		this.camera = master.getCameraDepartment().newCamera(cam_properties);
		this.root_layer = master.newLayer();
		this.root_layer.setCamera(this.camera);

// this.box2d_renderer = new GDXBox2DDebugRendererComponent(debug_renderer_properties.physicsCore);
		this.box2d_renderer = new GDXBox2DDebugRendererComponent(null);
		this.root_layer.attachComponent(this.box2d_renderer);
	}

	@Override
	public Camera getCamera () {
		return this.camera;
	}

	public Layer getRootLayer () {
		return this.root_layer;
	}

	@Override
	public boolean isVisible () {
		return this.is_visible;
	}

	boolean is_visible = true;

	@Override
	public void hide () {
		this.is_visible = false;
	}

	@Override
	public void show () {
		this.is_visible = true;
	}

	@Override
	public Layer getRoot () {
		return this.root_layer;
	}

}
