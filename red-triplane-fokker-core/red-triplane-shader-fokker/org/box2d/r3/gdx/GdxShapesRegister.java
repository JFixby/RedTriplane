
package org.box2d.r3.gdx;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;

public class GdxShapesRegister {

	final Map<ID, GdxShapeEntry> register = Collections.newMap();

	public void registerRaster (ID rester_id, GdxShapeEntry data) {
		Debug.checkNull("rester_id", rester_id);
		Debug.checkNull("data", data);
		register.put(rester_id, data);
	}

	public GdxShapeEntry getShapeData (ID asset_id) {
		Debug.checkNull("asset_id", asset_id);
		return register.get(asset_id);
	}

	public void printAll (String tag) {
		register.print(tag);
	}

}
