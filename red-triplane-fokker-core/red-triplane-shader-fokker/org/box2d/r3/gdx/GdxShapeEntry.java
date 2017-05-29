package org.box2d.r3.gdx;



import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;

public class GdxShapeEntry implements Asset {

	private final Box2DEditorShapeData data;

	public GdxShapeEntry (final Box2DEditorShapeData data) {
		this.data = data;
	}

	@Override
	public ID getAssetID () {
		return this.data.getAssetID();
	}

	@Override
	public AssetsGroup getGroup () {
		Err.throwNotImplementedYet();
		return null;
	}

}
