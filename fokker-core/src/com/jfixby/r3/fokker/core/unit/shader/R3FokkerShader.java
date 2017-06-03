
package com.jfixby.r3.fokker.core.unit.shader;

import com.jfixby.r3.api.shader.PhotoshopShaders;
import com.jfixby.r3.api.shader.R3ShaderComponent;
import com.jfixby.r3.api.shader.Shader;
import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.fokker.api.FOKKER_SYSTEM_ASSETS;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;

public class R3FokkerShader implements R3ShaderComponent, PhotoshopShaders, AssetsConsumer {

	@Override
	public String toString () {
		return "R3FokkerShader";
	}

	public R3FokkerShader () {

	}

	@Override
	public PhotoshopShaders PHOTOSHOP () {
		return this;
	}

	static Shader TEST = null;

	@Override
	public Shader TEST () {
		TEST = resolve(TEST, FOKKER_SYSTEM_ASSETS.SHADER_TEST, this);
		return TEST;
	}

	static Shader NORMAL = null;

	@Override
	public Shader NORMAL () {
		NORMAL = resolve(NORMAL, FOKKER_SYSTEM_ASSETS.SHADER_NORMAL, this);
		return NORMAL;
	}

	static Shader MULTIPLY = null;

	@Override
	public Shader MULTIPLY () {
		MULTIPLY = resolve(MULTIPLY, FOKKER_SYSTEM_ASSETS.SHADER_MULTIPLY, this);
		return MULTIPLY;
	}

	static Shader GRAYSCALE = null;

	@Override
	public Shader GRAYSCALE () {
		GRAYSCALE = resolve(GRAYSCALE, FOKKER_SYSTEM_ASSETS.SHADER_GRAYSCALE, this);
		return GRAYSCALE;
	}

	private static final Shader resolve (final Shader shader, final ID name, final AssetsConsumer consumer) {
		if (shader != null) {
			return shader;
		}
		final AssetHandler asset_handler = LoadedAssets.obtainAsset(name, consumer);
		if (asset_handler == null) {
			Err.reportError("Asset<" + name + "> not found.");
		}
		final ShaderAsset asset = (ShaderAsset)asset_handler.asset();
		return new RedFokkerShader(asset, consumer);
	}

	static Shader GDX_DEFAULT = null;

	@Override
	public Shader GDX_DEFAULT () {
		GDX_DEFAULT = resolve(GDX_DEFAULT, FOKKER_SYSTEM_ASSETS.SHADER_GDX_DEFAULT, this);
		return GDX_DEFAULT;
	}

}
