
package com.jfixby.r3.fokker.utils;

import com.badlogic.gdx.utils.Array;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.collections.List;

public class GdxUtils {

	static private ComponentInstaller<GdxUtilsComponent> componentInstaller = new ComponentInstaller<GdxUtilsComponent>(
		"GdxUtils");

	public static final void installComponent (GdxUtilsComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final GdxUtilsComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final GdxUtilsComponent component () {
		return componentInstaller.getComponent();
	}

	public static <T> List<T> newList (Array<T> gdx_array) {
		return invoke().newList(gdx_array);
	}

}
