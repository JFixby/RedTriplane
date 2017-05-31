
package com.jfixby.r3.fokker.render.raster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.jfixby.r3.api.render.BLEND_MODE;
import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.api.shader.FOKKER_SHADER_PARAMS;
import com.jfixby.r3.api.shader.Shader;
import com.jfixby.r3.api.shader.ShaderParameter;
import com.jfixby.r3.fokker.api.render.FokkerShader;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.err.Err;

public class CurrentShaderContainer {
	private ShaderProgram gdx_shader_program;
	private Texture blend_texture;
	// private Texture test_blend_texture;
	private Shader shader;
	private FokkerShader fokker_shader;
	private Mapping<String, ShaderParameter> params;
	private BLEND_MODE current_mode;

	void init () {
		// File file = LocalFileSystem
		// .newFile("D:\\[DATA]\\[RED-ASSETS]\\Art-Private\\tinto-assets\\content\\data\\libgdx.png");
		// FileHandle gdx_file = new ToGdxFileAdaptor(file);
		// test_blend_texture = new Texture(gdx_file);
	}

	public void setShader (final BLEND_MODE next_blend_mode, final Shader shader, final Texture blend_texture) {
		this.blend_texture = blend_texture;
		// this.blend_texture = this.test_blend_texture;
		this.shader = shader;
		this.current_mode = next_blend_mode;

		if (!(shader instanceof FokkerShader)) {
			Err.reportError("FokkerShader required. This is not a FokkerShader: " + shader);
		}
		this.fokker_shader = (FokkerShader)shader;

		this.gdx_shader_program = this.fokker_shader.getGdxShaderProgram();
		this.params = shader.listParameters();

// this.setupShaderValues(1.0f);
	}

	public void activateShader (final double opacity) {
		// params.print("params");
// Debug.checkTrue("" + this.params, this.params.size() >= 6);
		this.setupShaderValues(opacity);
		GdxRender.setShader(this.gdx_shader_program);

	}

	private void setupShaderValues (final double opacity) {
		this.shader.setFloatParameterValue(FOKKER_SHADER_PARAMS.SCREEN_WIDTH.name, Screen.getScreenWidth());
		this.shader.setFloatParameterValue(FOKKER_SHADER_PARAMS.SCREEN_HEIGHT.name, Screen.getScreenHeight());
		this.shader.setFloatParameterValue(FOKKER_SHADER_PARAMS.ALPHA_BLEND.name, opacity);
		this.shader.setIntParameterValue(FOKKER_SHADER_PARAMS.U_TEXTURE_0_CURRENT.name, 0);
		this.shader.setIntParameterValue(FOKKER_SHADER_PARAMS.U_TEXTURE_1_ORIGINAL.name, 1);
		this.shader.setIntParameterValue(FOKKER_SHADER_PARAMS.U_TEXTURE_2_ALPHA.name, 2);

		this.shader.setupValues();
	}

	public void deactivateShader () {
		GdxRender.setShader(null);
	}

	public Texture getBlendTexture () {
		return this.blend_texture;
	}

	public BLEND_MODE getCurrentMode () {
		return this.current_mode;
	}

}
