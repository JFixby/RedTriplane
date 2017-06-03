
package com.jfixby.r3.fokker.core.unit.cam;

import com.jfixby.r3.api.ui.unit.camera.CameraManager;
import com.jfixby.r3.api.ui.unit.camera.CameraSpecs;
import com.jfixby.r3.api.ui.unit.camera.SIMPLE_CAMERA_POLICY;

public class RedCameraSpecs implements CameraSpecs {

	private CameraManager cameraManager;

	@Override
	public void setCameraManager (final CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	@Override
	public CameraManager getCameraManager () {
		return this.cameraManager;
	}

	SIMPLE_CAMERA_POLICY policy;

	@Override
	public void setSimpleCameraPolicy (final SIMPLE_CAMERA_POLICY policy) {
		this.policy = policy;
	}

	@Override
	public SIMPLE_CAMERA_POLICY getSimpleCameraPolicy () {
		return this.policy;
	}

}
