
package com.jfixby.r3.fokker.render.shader;

import com.jfixby.r3.engine.core.unit.raster.FOKKER_SYSTEM_ASSETS;
import com.jfixby.r3.fokker.api.render.FokkerShader;
import com.jfixby.r3.fokker.render.raster.FokkerRasterRenderer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;

public class FokkerShaderRenderer {

	private FokkerRasterRenderer raster_renderer;
	private Rectangle shape;
	private ID fake_asset;

	public void init (final FokkerRasterRenderer raster_renderer) {
		this.raster_renderer = raster_renderer;
		this.shape = Geometry.newRectangle();
		// shape.setPosition(Double.NEGATIVE_INFINITY,
		// Double.POSITIVE_INFINITY);

		this.fake_asset = FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING;
	}

	public void open (final FokkerShader fokkerShader) {
		final Rectangle shape = fokkerShader.shape();
		if (shape != null) {
			this.shape.setup(shape);
		} else {
			this.resetShape();
		}
		this.raster_renderer.open(null, 1f, fokkerShader);
	}

	private void resetShape () {
		this.shape.setOriginRelative();
		this.shape.setPosition();
		this.shape.setSize(10000, 10000);
	}

	public void close (final FokkerShader fokkerShader) {
		this.raster_renderer.close(null, fokkerShader);

	}

	public void applyShader () {
		this.raster_renderer.drawSprite(this.fake_asset, this.shape);
		// final Texture blend_texture = this.current_shader.getBlendTexture();

		// sprites_renderer.drawSprite(spriteAssetID, shape, current_opacity,
		// blend_texture);
	}

}
