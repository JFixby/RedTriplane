
package com.jfixby.r3.engine.core.unit.shader;

import com.jfixby.r3.api.ui.unit.shader.ShaderSpecs;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.geometry.Rectangle;

public class RedShaderSpecs implements ShaderSpecs {

	private ID shader_asset;
	private Rectangle shape;
	private String vertexProgramRawString;
	private String fragmentProgramRawString;

	@Override
	public void setShaderAssetID (final ID shader_asset) {
		this.shader_asset = shader_asset;
	}

	@Override
	public ID getShaderAssetID () {
		return this.shader_asset;
	}

	@Override
	public void setShape (final Rectangle shape) {
		this.shape = shape;
	}

	@Override
	public Rectangle getShape () {
		return this.shape;
	}

	@Override
	public void setVertexProgramRawString (final String string) {
		this.vertexProgramRawString = string;
	}

	@Override
	public void setFragmentProgramRawString (final String string) {
		this.fragmentProgramRawString = string;
	}

	@Override
	public String getVertexProgramRawString () {
		return this.vertexProgramRawString;
	}

	@Override
	public String getFragmentProgramRawString () {
		return this.fragmentProgramRawString;
	}

}
