
package com.jfixby.r3.fokker.core.shader;

import java.io.IOException;

import com.jfixby.r3.api.shader.FragmentProgram;
import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.api.shader.ShaderParameter;
import com.jfixby.r3.api.shader.VertexProgram;
import com.jfixby.r3.api.shader.srlz.R3_SHADER_SETTINGS;
import com.jfixby.r3.api.shader.srlz.ShaderInfo;
import com.jfixby.r3.api.shader.srlz.ShaderParameterInfo;
import com.jfixby.r3.api.shader.srlz.ShadersContainer;
import com.jfixby.rana.api.asset.Asset;
import com.jfixby.rana.api.asset.AssetsGroup;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;

public class ShaderEntry implements Asset, ShaderAsset {
	ID asset_id;
	ShaderInfo shader;
	File shader_folder;
	ShadersContainer container;
	private final File frag_file;
	private final File vertex_file;
	private final RedShaderProgram vertex_program;
	private final RedShaderProgram fragment_program;
	Map<String, ShaderParameter> params = Collections.newMap();

	public ShaderEntry (final ID asset_id, final ShaderInfo shader, final File shader_folder, final ShadersContainer container)
		throws IOException {
		super();
		this.asset_id = asset_id;
		this.shader = shader;
		this.shader_folder = shader_folder;
		this.container = container;

		for (final ShaderParameterInfo param : shader.parameters_list) {
			final String name = param.name;
			final ShaderParameter value = new Parameter(param);
			this.params.put(name, value);
		}
		;

		this.frag_file = shader_folder.child(R3_SHADER_SETTINGS.FRAG_FILE_NAME);
		this.vertex_file = shader_folder.child(R3_SHADER_SETTINGS.VERT_FILE_NAME);

		this.vertex_program = new RedShaderProgram(this.vertex_file);
		this.fragment_program = new RedShaderProgram(this.frag_file);
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	@Override
	public VertexProgram getVertexProgram () {
		return this.vertex_program;
	}

	@Override
	public FragmentProgram getFragmentProgram () {
		return this.fragment_program;
	}

	@Override
	public Map<String, ShaderParameter> listParameters () {
		return this.params;
	}

	@Override
	public AssetsGroup getGroup () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public boolean isOverlay () {
		return this.shader.isOverlay;
	}

}
