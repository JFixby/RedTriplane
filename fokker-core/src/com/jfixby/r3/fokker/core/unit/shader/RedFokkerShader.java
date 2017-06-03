
package com.jfixby.r3.fokker.core.unit.shader;

import com.jfixby.r3.api.shader.FragmentProgram;
import com.jfixby.r3.api.shader.Shader;
import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.api.shader.ShaderParameter;
import com.jfixby.r3.api.shader.VertexProgram;
import com.jfixby.r3.fokker.api.render.FokkerShader;
import com.jfixby.r3.fokker.shader.RedShaderProgram;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.java.FloatValue;
import com.jfixby.scarabei.api.java.Int;
import com.jfixby.scarabei.api.log.L;

public class RedFokkerShader implements Shader, FokkerShader {

	private VertexProgram vertex;
	private FragmentProgram fragment;
	private String vertex_source_code;
	private String fragment_source_code;
	private ShaderAsset asset;
	private Mapping<String, ShaderParameter> parameters;
	private final Map<String, FloatValue> float_param_values = Collections.newMap();
	private final Map<String, Int> int_param_values = Collections.newMap();
	private final com.badlogic.gdx.graphics.glutils.ShaderProgram gdx_shader_program;
	private Rectangle shape;

	private RedFokkerShader () {
		this.gdx_shader_program = null;
	}

	RedFokkerShader (final String vertexProgramRawString, final String fragmentProgramRawString) {

		com.badlogic.gdx.graphics.glutils.ShaderProgram.pedantic = false;
		this.vertex = new RedShaderProgram(vertexProgramRawString);
		this.fragment = new RedShaderProgram(fragmentProgramRawString);

		this.vertex_source_code = this.vertex.getSourceCode();
		this.fragment_source_code = this.fragment.getSourceCode();

		this.gdx_shader_program = new com.badlogic.gdx.graphics.glutils.ShaderProgram(this.vertex_source_code,
			this.fragment_source_code);
		if (!this.gdx_shader_program.isCompiled()) {
			throw new IllegalArgumentException("Error compiling shader: " + this.gdx_shader_program.getLog());
		}

	}

	RedFokkerShader (final ShaderAsset asset, final AssetsConsumer consumer) {
		this.asset = asset;

		com.badlogic.gdx.graphics.glutils.ShaderProgram.pedantic = false;
		this.vertex = this.asset.getVertexProgram();
		this.fragment = this.asset.getFragmentProgram();
		this.parameters = this.asset.listParameters();

		this.vertex_source_code = this.vertex.getSourceCode();
		this.fragment_source_code = this.fragment.getSourceCode();

		this.gdx_shader_program = new com.badlogic.gdx.graphics.glutils.ShaderProgram(this.vertex_source_code,
			this.fragment_source_code);
		if (!this.gdx_shader_program.isCompiled()) {
			throw new IllegalArgumentException("Error compiling shader: " + this.gdx_shader_program.getLog());
		}

	}

	private final boolean hasParams () {
		return this.float_param_values.size() > 0 || this.int_param_values.size() > 0;
	}

	@Override
	final public void setFloatParameterValue (final String parameter_name, final double value) {
		FloatValue float_value = this.float_param_values.get(parameter_name);
		if (float_value == null) {
			float_value = new FloatValue();
			this.float_param_values.put(parameter_name, float_value);
		}
		float_value.value = value;
	}

	@Override
	final public void setIntParameterValue (final String parameter_name, final long value) {
		Int float_value = this.int_param_values.get(parameter_name);
		if (float_value == null) {
			float_value = new Int();
			this.int_param_values.put(parameter_name, float_value);
		}
		float_value.value = value;
	}

	@Override
	public double getFloatParameterValue (final String parameter_name) {
		FloatValue float_value = this.float_param_values.get(parameter_name);
		if (float_value == null) {
			float_value = new FloatValue();
			this.float_param_values.put(parameter_name, float_value);
		}
		return float_value.value;
	}

	@Override
	public long getIntParameterValue (final String parameter_name) {
		Int value = this.int_param_values.get(parameter_name);
		if (value == null) {
			value = new Int();
			this.int_param_values.put(parameter_name, value);
		}
		return value.value;
	}

	final private void setFloatParam (final String param_name, final float value) {
		try {
			// L.d("set F " + param_name, value);
			this.gdx_shader_program.setUniformf(param_name, value);
		} catch (final Throwable e) {
			L.e(this.asset.getFragmentProgram());
			L.e(this.asset.getVertexProgram());
			e.printStackTrace();
			Err.reportError("Failed to set parameter " + param_name + "=" + value);

		}
	}

	final private void setIntParam (final String param_name, final int value) {
		try {
			// L.d("set I " + param_name, value);
			this.gdx_shader_program.setUniformi(param_name, value);
		} catch (final Throwable e) {
			L.e(this.asset.getFragmentProgram());
			L.e(this.asset.getVertexProgram());
			e.printStackTrace();
			Err.reportError("Failed to set parameter " + param_name + "=" + value);

		}
	}

	@Override
	public com.badlogic.gdx.graphics.glutils.ShaderProgram getGdxShaderProgram () {
		return this.gdx_shader_program;
	}

	@Override
	final public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.fragment_source_code == null) ? 0 : this.fragment_source_code.hashCode());
		result = prime * result + ((this.vertex_source_code == null) ? 0 : this.vertex_source_code.hashCode());
		return result;
	}

	@Override
	public final boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final RedFokkerShader other = (RedFokkerShader)obj;
		if (this.fragment_source_code == null) {
			if (other.fragment_source_code != null) {
				return false;
			}
		} else if (!this.fragment_source_code.equals(other.fragment_source_code)) {
			return false;
		}
		if (this.vertex_source_code == null) {
			if (other.vertex_source_code != null) {
				return false;
			}
		} else if (!this.vertex_source_code.equals(other.vertex_source_code)) {
			return false;
		}
		return true;
	}

	@Override
	public Mapping<String, ShaderParameter> listParameters () {
		return this.parameters;
	}

	@Override
	public void setupValues () {
		if (this.gdx_shader_program == null) {
			return;
		}
		if (this.hasParams()) {

			this.gdx_shader_program.begin();
			// float_param_values.print("float_param_values");
			for (int i = 0; i < this.float_param_values.size(); i++) {
				final FloatValue value = this.float_param_values.getValueAt(i);
				final String param_name = this.float_param_values.getKeyAt(i);
				this.setFloatParam(param_name, (float)value.value);
			}

			for (int i = 0; i < this.int_param_values.size(); i++) {
				final Int value = this.int_param_values.getValueAt(i);
				final String param_name = this.int_param_values.getKeyAt(i);
				this.setIntParam(param_name, (int)value.value);
			}
			this.gdx_shader_program.end();
		}
	}

	public void printParameterValues () {
		L.d("Shader parameters list:");
		for (int i = 0; i < this.float_param_values.size(); i++) {
			final FloatValue value = this.float_param_values.getValueAt(i);
			final String param_name = this.float_param_values.getKeyAt(i);
			L.d("   float: " + param_name, value);
		}

		for (int i = 0; i < this.int_param_values.size(); i++) {
			final Int value = this.int_param_values.getValueAt(i);
			final String param_name = this.int_param_values.getKeyAt(i);
			L.d("     int: " + param_name, value);
		}
	}

	@Override
	public boolean isOverlay () {
		if (this.asset == null) {
			return false;
		}
		return this.asset.isOverlay();
	}

	@Override
	public Rectangle shape () {
		return this.shape;
	}

	public void setShape (final Rectangle shape) {
		this.shape = shape;
	}

}
