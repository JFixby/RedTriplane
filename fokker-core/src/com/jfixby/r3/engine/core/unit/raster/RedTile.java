
package com.jfixby.r3.engine.core.unit.raster;

import com.jfixby.r3.api.render.BLEND_MODE;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.raster.Tile;
import com.jfixby.r3.engine.core.unit.RedComponentsFactory;
import com.jfixby.r3.engine.core.unit.RedRectangularComponent;
import com.jfixby.r3.fokker.api.RasterData;
import com.jfixby.r3.fokker.api.render.RenderMachine;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Rectangle;

public class RedTile extends RedRectangularComponent implements Tile {

	@Override
	public String toString () {
		return "Tile[" + this.asset_id + "] name=" + this.getName() + " shape=" + this.shape;
	}

	// private AssetID spriteAssetID;

	private float opacity = 1f;

	// private AssetHandler obtain_sprite;
	private final ID asset_id;

	private final RedComponentsFactory master;

	private BLEND_MODE mode = BLEND_MODE.GDX_DEFAULT;

	public RedTile (final RedComponentsFactory master, final RasterData data) {
		super(master);
		this.master = master;
		this.asset_id = data.getAssetID();
		Debug.checkNull("asset_id", this.asset_id);

	}

	@Override
	public void setSize (final Rectangle rectangle) {
		this.setSize(rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public Raster copy () {
		final Raster copy = this.getComponentsFactory().getRasterDepartment().newRaster(this.getAssetID());
		copy.setupShape(this.shape());
		copy.setOpacity(this.getOpacity());
		return copy;
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.master;
	}

	@Override
	public void doDraw () {
		RenderMachine.beginDrawComponent(this);
		RenderMachine.beginRasterMode(this.mode, this.opacity);

		RenderMachine.drawRaster(this.asset_id, this.shape());

		RenderMachine.endRasterMode(this.mode);
		RenderMachine.endDrawComponent(this);
		if (this.getDebugRenderFlag()) {
			this.debug_rectangle.doDraw();
		}
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	@Override
	public void setOpacity (final float alpha) {
		this.opacity = alpha;
	}

	@Override
	public float getOpacity () {
		return this.opacity;
	}

	@Override
	public BLEND_MODE getBlendMode () {
		return this.mode;
	}

	@Override
	public BLEND_MODE setBlendMode (BLEND_MODE mode) {
		if (mode == null) {
			mode = BLEND_MODE.GDX_DEFAULT;
		}
		final BLEND_MODE old = this.mode;
		this.mode = mode;
		return old;
	}

	@Override
	public void setOriginAbsolute (final ReadOnlyFloat2 point) {
		this.setOriginAbsoluteX(point.getX());
		this.setOriginAbsoluteY(point.getY());
	}

	@Override
	public void setOriginRelative () {
		this.shape.setOriginRelative();
	}

}
