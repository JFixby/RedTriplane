
package com.jfixby.r3.engine.core;

import com.jfixby.r3.engine.core.unit.raster.TiledRasterReader;
import com.jfixby.rana.api.loader.PackagesLoader;

public class PackageLoaders {

	static TiledRasterReader tiledRasterReader;

	public static void deploy () {
		tiledRasterReader = new TiledRasterReader();
		PackagesLoader.registerPackageReader(tiledRasterReader);
	}

}
