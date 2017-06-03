
package com.jfixby.r3.fokker.unit.shader;

import com.jfixby.r3.api.EngineParams.Settings;
import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.shader.ShaderComponent;
import com.jfixby.r3.api.ui.unit.shader.ShaderFactory;
import com.jfixby.r3.api.ui.unit.shader.ShaderSpecs;
import com.jfixby.r3.fokker.api.FOKKER_SYSTEM_ASSETS;
import com.jfixby.r3.fokker.unit.RedComponentsFactory;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedShadersFactory implements ShaderFactory {

	private final RedComponentsFactory redComponentsFactory;

	public RedShadersFactory (final RedComponentsFactory redComponentsFactory) {
		this.redComponentsFactory = redComponentsFactory;
	}

	@Override
	public ShaderSpecs newShaderSpecs () {
		return new RedShaderSpecs();
	}

	@Override
	public ShaderComponent newShader (final ShaderSpecs specs) {

		final String vertexProgramRawString = specs.getVertexProgramRawString();
		final String fragmentProgramRawString = specs.getFragmentProgramRawString();
		if (vertexProgramRawString == null && fragmentProgramRawString == null) {
			final ID newAssetID = Debug.checkNull("specs.getShaderAssetID()", specs.getShaderAssetID());
			final AssetHandler asset_handler = this.obtainShader(newAssetID);
			final ShaderAsset asset = (ShaderAsset)asset_handler.asset();
			return new RedFokkerShaderComponent(asset, this.redComponentsFactory, specs);
		} else {
			return new RedFokkerShaderComponent(vertexProgramRawString, fragmentProgramRawString, this.redComponentsFactory, specs);
		}
	}

	private AssetHandler obtainShader (final ID newAssetID) {
		return this.redComponentsFactory.obtainAsset(newAssetID, SystemSettings.getFlag(Settings.AllowMissingShader),
			FOKKER_SYSTEM_ASSETS.SHADER_GRAYSCALE, SystemSettings.getFlag(Settings.PrintLogMessageOnMissingShader));
	}

	@Override
	public ComponentsFactory parent () {
		return this.redComponentsFactory;
	}

}
