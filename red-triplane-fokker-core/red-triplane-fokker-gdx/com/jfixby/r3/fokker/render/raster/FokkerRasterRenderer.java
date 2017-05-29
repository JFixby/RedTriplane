
package com.jfixby.r3.fokker.render.raster;

import com.badlogic.gdx.graphics.Texture;
import com.jfixby.r3.api.shader.R3Shader;
import com.jfixby.r3.api.shader.Shader;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;
import com.jfixby.r3.fokker.api.render.FokkerShader;
import com.jfixby.r3.fokker.api.render.FokkerString;
import com.jfixby.r3.fokker.render.FokkerRenderMachine;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.r3.fokker.render.RenderBuffer;
import com.jfixby.r3.fokker.render.geo.Renderer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.api.util.StateSwitcher;

public class FokkerRasterRenderer extends Renderer {

	private SpritesRenderer sprites_renderer;
	private RenderBuffer primary_buffer;
	private SecondaryRenderBuffer secondary_buffer;
	// private Matrix4 combined;
	private StateSwitcher<BLEND_MODE> blend_state;

	final CurrentShaderContainer current_shader = new CurrentShaderContainer();
	private BLEND_MODE mode;
	private double current_opacity = 1f;

	// private static StateSwitcher<RASTER_MODE> raster_mode;

	final public void init (final FokkerRenderMachine fokkerRenderMachine, final RenderBuffer primary_buffer) {
		super.init(fokkerRenderMachine);
		this.sprites_renderer = new SpritesRenderer(this);
		GdxRender.activateRasterRenderer();
		this.primary_buffer = primary_buffer;

		this.blend_state = JUtils.newStateSwitcher(BLEND_MODE.GDX_DEFAULT);
		this.current_shader.init();
	}

	@Override
	public void onFrameBegin () {
		// blend_state.expectState(BLEND_MODE.NOT_SET);
		this.blend_state.switchState(BLEND_MODE.GDX_DEFAULT);
		GdxRender.setShader(null);
	}

	@Override
	final public void doOpen () {
		GdxRender.openRasterRenderer(this.getGdxCamera().combined);
	}

	boolean shaderIsOverlay = false;

	public void open (final BLEND_MODE blend_mode, final double opacity, final FokkerShader shader) {
		this.mode = blend_mode;
		this.current_opacity = opacity;
		if (blend_mode == BLEND_MODE.GDX_DEFAULT) {
			GdxRender.setShader(null);
		} else {
			this.shaderIsOverlay = shader.isOverlay();
			if (this.shaderIsOverlay) {
				this.loadOverlayShader(blend_mode, opacity, shader);
			} else {
				this.loadTextureShader(blend_mode, opacity, shader);
			}
		}
		super.open();
	}

	public void close (final BLEND_MODE blend_mode, final FokkerShader shader) {
		this.current_opacity = 1;
		if (this.mode != blend_mode) {
			Err.reportError("Unexpected BLEND_MODE: " + blend_mode);
		}
		super.close();
		if (blend_mode == BLEND_MODE.GDX_DEFAULT) {
			GdxRender.setShader(null);
		} else {
			if (this.shaderIsOverlay) {
				this.unloadOverlayShader(blend_mode);
			} else {
				this.unloadTextureShader(blend_mode);
			}
		}
	}

	@Override
	final public void doClose () {
		// blend_state.doesNotExpectState(BLEND_MODE.NOT_SET);
		GdxRender.closeRasterRenderer();
	}

	@Override
	public void onFrameEnd () {
		// blend_state.doesNotExpectState(BLEND_MODE.NOT_SET);
		// blend_state.switchState(BLEND_MODE.NOT_SET);
		GdxRender.setShader(null);
		this.blend_state.switchState(BLEND_MODE.GDX_DEFAULT);
	}

	// @Override
	// final public void setGdxProjectionMatrix(final Matrix4 combined) {
	// GdxRender.setRasterRendererProjectionMatrix(combined);
	// this.combined = combined;
	// }

	public final void drawSprite (final ID spriteAssetID, final Rectangle shape) {
		final Texture blend_texture = this.current_shader.getBlendTexture();
		this.sprites_renderer.drawSprite(spriteAssetID, shape, this.current_opacity, blend_texture);
	}

	public final void drawAperture (final ID spriteAssetID, final double ax, final double ay, final double bx, final double by) {
		this.sprites_renderer.drawAperture(spriteAssetID, ax, ay, bx, by, this.current_opacity);
	}

	public final void drawString (final FokkerString string_value, final CanvasPosition shape) {
		// blend_state.doesNotExpectState(BLEND_MODE.NOT_SET);
		final Texture blend_texture = this.current_shader.getBlendTexture();
		this.sprites_renderer.drawString(string_value, shape, this.current_opacity, blend_texture);
	}

	final void loadOverlayShader (final BLEND_MODE next_blend_mode, final double opacity, final FokkerShader customShader) {
		if (this.secondary_buffer == null) {
			this.secondary_buffer = new SecondaryRenderBuffer();
		}

		this.primary_buffer.pause();

		//
		this.secondary_buffer.begin(this.getGdxCamera().combined);
		final Texture tmp_texture = this.primary_buffer.getResult();
		this.secondary_buffer.saveTexture(tmp_texture);
		this.secondary_buffer.end();
		this.primary_buffer.resume();

		final Shader shader = this.shaderFor(next_blend_mode, customShader);
		final Texture blend_texture = this.secondary_buffer.getResult();
		this.current_shader.setShader(next_blend_mode, shader, blend_texture);

		// texture.bind(1);

		this.current_shader.activateShader(opacity);

	}

	private void unloadOverlayShader (final BLEND_MODE blend_mode) {
		this.current_shader.deactivateShader();
	}

	final void loadTextureShader (final BLEND_MODE next_blend_mode, final double opacity, final FokkerShader customShader) {
		final Shader shader = this.shaderFor(next_blend_mode, customShader);
		this.current_shader.setShader(next_blend_mode, shader, null);

		// texture.bind(1);

		this.current_shader.activateShader(opacity);
	}

	private void unloadTextureShader (final BLEND_MODE blend_mode) {
		this.current_shader.deactivateShader();
	}

	private Shader shaderFor (final BLEND_MODE blend_mode, final FokkerShader customShader) {
		if (blend_mode == null) {
			return (Shader)customShader;
		}

		if (blend_mode == BLEND_MODE.GDX_DEFAULT) {
			Err.reportError("Wrong BLEND_MODE = " + blend_mode);
		}
		if (blend_mode == BLEND_MODE.TEST) {
			return R3Shader.PHOTOSHOP().TEST();

		}
		if (blend_mode == BLEND_MODE.Normal) {
			return R3Shader.PHOTOSHOP().NORMAL();

		}

		if (blend_mode == BLEND_MODE.Multiply) {
			return R3Shader.PHOTOSHOP().MULTIPLY();

		}

		if (blend_mode == BLEND_MODE.Grayscale) {
			return R3Shader.PHOTOSHOP().GRAYSCALE();

		}

		Err.reportError("Unknown BLEND_MODE=" + blend_mode);
		return null;
	}

	public Shader getDefaultGdxShader () {
		return R3Shader.PHOTOSHOP().GDX_DEFAULT();
	}

}
