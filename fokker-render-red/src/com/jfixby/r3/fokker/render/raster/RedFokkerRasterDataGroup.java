
package com.jfixby.r3.fokker.render.raster;

import java.io.IOException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.jfixby.r3.fokker.api.FokkerEngineParams;
import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.adopted.gdx.atlas.legacy.GdxTextureAtlas;
import com.jfixby.scarabei.adopted.gdx.fs.ToGdxFileAdaptor;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedFokkerRasterDataGroup implements AssetsGroup {

	private Texture texture;
	private GdxTextureAtlas atlas;
	private final PackageReaderInput input;

	public RedFokkerRasterDataGroup (final PackageReaderInput input, final FokkerTextureLoader reader) throws IOException {
		this.input = input;

		final File package_root_file = this.getPackageInput().packageRootFile;
// final PackageReaderListener reader_listener = this.getPackageInput().getPackageReaderListener();

		if (!package_root_file.exists()) {
			final String msg = "Texture file not found: " + package_root_file;
			L.e(msg);
			final IOException e = new IOException(msg);
// if (reader_listener != null) {
// reader_listener.onError(e);
// } else
			{
				e.printStackTrace();
			}
			return;
		}

		final PackageFormat format = this.getPackageInput().packageInfo.packageFormat;

		if (FokkerTextureLoader.TEXTURE.equals(format)) {
			this.readTexture();
		}

		else if (FokkerTextureLoader.ATLAS.equals(format)) {
			this.readAtlas();
		} else {
			Err.reportError("Unknown format " + format);
		}
	}

	public void readTexture () {
		final File package_root_file = this.getPackageInput().packageRootFile;
// final PackageHandler handler = this.getPackageInput().getPackageHandler();
		final ToGdxFileAdaptor gdx_file = new ToGdxFileAdaptor(package_root_file);
		final ID raster_id = this.getPackageInput().packageInfo.packedAssets.getLast();
		final Collection<ID> assets = this.getPackageInput().packageInfo.packedAssets;

		this.texture = new Texture(gdx_file);
		this.atlas = null;

		final com.badlogic.gdx.graphics.g2d.Sprite gdx_sprite = new com.badlogic.gdx.graphics.g2d.Sprite(this.texture);
		final RedFokkerRasterData data = new RedFokkerRasterData(raster_id, gdx_sprite, this);

		final AssetsContainer container = this.input.assetsContainer;
		container.addAsset(raster_id, data);

	}

	private PackageReaderInput getPackageInput () {
		return this.input;
	}

	public void readAtlas () {
		final File package_root_file = this.getPackageInput().packageRootFile;
// final PackageHandler handler = this.getPackageInput().getPackageHandler();
		final ToGdxFileAdaptor gdx_file = new ToGdxFileAdaptor(package_root_file);
		final Collection<ID> assets = this.getPackageInput().packageInfo.packedAssets;
		final AssetsContainer container = this.input.assetsContainer;
		this.texture = null;
		this.atlas = new GdxTextureAtlas(gdx_file);

		final Array<AtlasRegion> regions = this.atlas.getRegions();
		final List<ID> missing = Collections.newList();
		missing.addAll(assets);
		for (final AtlasRegion region : regions) {
			// #FLIP
			// region.flip(false, true);

			TextureFilter minFilter = TextureFilter.MipMapLinearLinear;
			TextureFilter magFilter = TextureFilter.MipMapLinearLinear;
			{
				final String magString = SystemSettings.getStringParameter(FokkerEngineParams.TextureFilter.Mag);
				if (magString != null) {
					magFilter = TextureFilter.valueOf(magString);
				}
			}
			{
				final String minString = SystemSettings.getStringParameter(FokkerEngineParams.TextureFilter.Min);
				if (minString != null) {
					minFilter = TextureFilter.valueOf(minString);
				}
			}

			// L.d("FILTERING", minFilter + " " + magFilter);

			// TextureFilter minFilter = TextureFilter.MipMapLinearLinear;
			// TextureFilter magFilter = TextureFilter.Linear;
			region.getTexture().setFilter(minFilter, magFilter);
			final String asset_id_string = region.name;
			final Sprite gdx_sprite = this.atlas.createSprite(asset_id_string);
			final ID raster_id = Names.newID(asset_id_string);
			missing.remove(raster_id);
			final RedFokkerRasterData data = new RedFokkerRasterData(raster_id, gdx_sprite, this);
			container.addAsset(raster_id, data);

			// L.d(" ", rester_id);
		}

		if (missing.size() > 0) {
// missing.print("missing");
// Sys.exit();

			for (final ID raster_id : missing) {
				final Sprite gdx_sprite = new Sprite();
				final RedFokkerRasterData data = new RedFokkerRasterData(raster_id, gdx_sprite, this);
				container.addAsset(raster_id, data);
			}
		}

	}

	@Override
	public String toString () {
		if (this.atlas != null) {
			return this.atlas.toString(this.input.packageRootFile);
		}
		if (this.texture != null) {
			return this.texture.toString();
		}

		return "<disposed>";

	}

	@Override
	public void dispose () {
		if (this.atlas != null) {
			this.atlas.dispose();
			this.atlas = null;
		}
		if (this.texture != null) {
			this.texture.dispose();
			this.texture = null;
		}
	}

}
