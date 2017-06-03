
package com.jfixby.r3.fokker.render;

import java.io.IOException;

import com.jfixby.r3.api.EngineParams;
import com.jfixby.r3.fokker.api.FOKKER_SYSTEM_ASSETS;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.rana.api.pkg.PackagesBank;
import com.jfixby.rana.api.pkg.PackagesManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedFokkerRasterManager implements AssetsConsumer {

	// private

// private final PackageReaderListener listener = new PackageReaderListener() {
//
// @Override
// public void onError (final IOException e) {
// Err.reportError(e);
// }
//
// @Override
// public void onDependenciesRequired (final PackageHandler requiredBy, final Collection<ID> dependencies) {
// if (dependencies.size() == 0) {
// return;
// }
// L.d("requiredBy", requiredBy);
// dependencies.print("dependencies");
// Err.throwNotImplementedYet();
// }
//
// @Override
// public void onPackageDataDispose (final SealedAssetsContainer data) {
// data.printAll();
// Err.reportError("Shouldn't dispose system assets");
// }
//
// @Override
// public void onPackageDataLoaded (final SealedAssetsContainer data) {
// AssetsManager.registerAssetsContainer(data);
// }
//
// @Override
// public void onFailedToInstall (final IOException e) {
// Err.reportError(e);
// }
// };
// private final ResourceRebuildIndexListener reader = new ResourceRebuildIndexListener() {
//
// @Override
// public void onError (final Throwable e) {
// e.printStackTrace();
// }
// };

	public RedFokkerRasterManager () {

		final ID bank = FOKKER_SYSTEM_ASSETS.LOCAL_R3_BANK_NAME;
		final PackagesBank group = PackagesManager.getBank(bank);
		if (group == null) {
			PackagesManager.printAllResources();
			Err.reportError("Resource not found: " + bank);
		}
// resource.rebuildIndex(this.reader);

		FOKKER_SYSTEM_ASSETS.init();

		this.loadSystemAsset(FOKKER_SYSTEM_ASSETS.SHADER_GDX_DEFAULT);

		this.loadSystemAsset(FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING);
		this.loadSystemAsset(FOKKER_SYSTEM_ASSETS.BLACK);
		this.loadSystemAsset(FOKKER_SYSTEM_ASSETS.DEBUG_BLACK);
		this.loadSystemAsset(FOKKER_SYSTEM_ASSETS.LOGO);

// this.loadSystemAsset(FOKKER_SYSTEM_ASSETS.SHADER_TEST);

		SystemSettings.setSystemAssetID(EngineParams.Assets.RASTER_IS_MISING, FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING);
		SystemSettings.setSystemAssetID(EngineParams.Assets.SPRITE_BLACK, FOKKER_SYSTEM_ASSETS.BLACK);
		SystemSettings.setSystemAssetID(EngineParams.Assets.SPRITE_BLACK_DEBUG, FOKKER_SYSTEM_ASSETS.DEBUG_BLACK);

	}

	private void loadSystemAsset (final ID asset_id) {
		try {
			AssetsManager.autoResolveAsset(asset_id);
		} catch (final IOException e) {
			e.printStackTrace();
			Err.reportError(e);
		}
		LoadedAssets.obtainAsset(asset_id, this);
	}

}
