
package com.jfixby.r3.fokker.core.unit.raster;

import java.util.ArrayList;

import com.jfixby.rana.api.asset.AssetsGroup;

public class TileSetGroup implements AssetsGroup {

	final ArrayList<TileSet> sets = new ArrayList<TileSet>();

	@Override
	public void dispose () {
		this.sets.clear();
	}

	public void add (final TileSet tileSet) {
		this.sets.add(tileSet);
	}

}
