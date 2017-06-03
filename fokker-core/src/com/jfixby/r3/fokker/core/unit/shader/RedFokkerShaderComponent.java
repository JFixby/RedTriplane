
package com.jfixby.r3.fokker.core.unit.shader;

import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.api.ui.unit.shader.ShaderComponent;
import com.jfixby.r3.api.ui.unit.shader.ShaderSpecs;
import com.jfixby.r3.fokker.api.render.RenderMachine;
import com.jfixby.r3.fokker.core.unit.RedComponentsFactory;
import com.jfixby.r3.fokker.core.unit.RedDrawableComponent;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.log.L;

public class RedFokkerShaderComponent extends RedDrawableComponent implements ShaderComponent {

	private final Rectangle INFINITE = this.setupINFINITESHApe();
	private final RedFokkerShader fokkerShader;
	private final boolean draw_shaders = true;
	private final ID id;
	private final Rectangle shape;

	@Override
	public String toString () {
		return "ShaderComponent[" + this.id + "]";
	}

	private Rectangle setupINFINITESHApe () {
		final Rectangle rect = Geometry.newRectangle();
		rect.setWidth(10000);
		rect.setHeight(10000);

		return rect;
	}

	public RedFokkerShaderComponent (final String vertexProgramRawString, final String fragmentProgramRawString,
		final RedComponentsFactory redComponentsFactory, final ShaderSpecs specs) {
		this.id = null;
		this.fokkerShader = new RedFokkerShader(vertexProgramRawString, fragmentProgramRawString);
		if (!this.draw_shaders) {
			L.e("Ignoring shader: " + this.toString());
		}
		this.shape = Geometry.newRectangle(specs.getShape());
		this.fokkerShader.setShape(this.shape);
	}

	public RedFokkerShaderComponent (final ShaderAsset asset, final AssetsConsumer consumer, final ShaderSpecs specs) {
		this.id = null;
		L.d("warning, null shader id");
		this.fokkerShader = new RedFokkerShader(asset, consumer);
		if (!this.draw_shaders) {
			L.e("Ignoring shader: " + this.toString());
		}
		final Rectangle targetShape = specs.getShape();
		if (targetShape == null) {
			this.shape = null;
		} else {
			this.shape = Geometry.newRectangle(targetShape);
		}
		this.fokkerShader.setShape(this.shape);
	}

	@Override
	final public void doDraw () {
		if (!this.draw_shaders) {
			return;
		}

		RenderMachine.beginDrawComponent(this);
		this.fokkerShader.setupValues();
		RenderMachine.beginShaderMode(this.fokkerShader);
		RenderMachine.applyShader();
		RenderMachine.endShaderMode(this.fokkerShader);
		RenderMachine.endDrawComponent(this);
	}

	@Override
	public void setFloatParameterValue (final String name, final double value) {
		this.fokkerShader.setFloatParameterValue(name, value);
	}

	@Override
	public Rectangle getShape () {
		return this.shape;
	}

}
