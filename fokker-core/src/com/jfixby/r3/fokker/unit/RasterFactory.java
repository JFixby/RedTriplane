
package com.jfixby.r3.fokker.unit;

import com.jfixby.r3.api.EngineParams.Settings;
import com.jfixby.r3.api.ui.unit.raster.GraphicalConsole;
import com.jfixby.r3.api.ui.unit.raster.GraphicalConsoleSpecs;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.raster.RasterComponentsFactory;
import com.jfixby.r3.api.ui.unit.raster.RasterPool;
import com.jfixby.r3.fokker.api.FOKKER_SYSTEM_ASSETS;
import com.jfixby.r3.fokker.api.RasterData;
import com.jfixby.r3.fokker.unit.raster.RedTile;
import com.jfixby.r3.fokker.unit.raster.RedTilesComposition;
import com.jfixby.r3.fokker.unit.raster.TileSet;
import com.jfixby.r3.fokker.unit.raster.log.RedGraphicalConsole;
import com.jfixby.r3.fokker.unit.raster.log.RedGraphicalConsoleSpecs;
import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RasterFactory implements RasterComponentsFactory {

	private final RedComponentsFactory master;

	public RasterFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public RedTile newTile (final ID newAssetID) {
		final AssetHandler asset_handler = this.obtainRaster(newAssetID);
		final Asset asset = asset_handler.asset();

		if (asset instanceof RasterData) {
			return new RedTile(this.master, (RasterData)asset);
		}

		if (asset instanceof TileSet) {
			Err.reportError("This is not a tile: " + asset);
		}

		Err.reportError("Unknown asset type: " + asset);
		return null;

	}

	@Override
	public Raster newRaster (final ID newAssetID) {
		final AssetHandler asset_handler = this.obtainRaster(newAssetID);
		final Asset asset = asset_handler.asset();

		if (asset instanceof RasterData) {
			return new RedTile(this.master, (RasterData)asset);
		}

		if (asset instanceof TileSet) {
			final TileSet composition = ((TileSet)asset).copy();
			this.release(asset_handler);
			if (composition.size() == 0) {
				Err.reportError("Bad structure<" + newAssetID + "> - has no tiles");
			}
			final RedTilesComposition compos = new RedTilesComposition(this.master, composition);

			return compos;
		}

		Err.reportError("Unknown asset type: " + asset);
		return null;

	}

	private AssetHandler obtainRaster (final ID newAssetID) {
		return this.master.obtainAsset(newAssetID, SystemSettings.getFlag(Settings.AllowMissingRaster),
			FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING, SystemSettings.getFlag(Settings.PrintLogMessageOnMissingSprite));
	}

	private void release (final AssetHandler asset_handler) {
		LoadedAssets.releaseAsset(asset_handler, this.master);
	}

	@Override
	public RasterPool newRasterPool (final ID assetID) {
		return new RedRasterPool(this.master, assetID);
	}

	@Override
	public GraphicalConsoleSpecs newConsoleSpecs () {
		return new RedGraphicalConsoleSpecs();
	}

	@Override
	public GraphicalConsole newConsole (final GraphicalConsoleSpecs gspec) {
		return new RedGraphicalConsole(this.master, gspec);
	}

}
