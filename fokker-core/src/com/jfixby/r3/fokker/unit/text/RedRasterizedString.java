
package com.jfixby.r3.fokker.unit.text;

import com.jfixby.r3.api.render.BLEND_MODE;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.r3.fokker.unit.RedComponentsFactory;
import com.jfixby.r3.fokker.unit.layers.RedLayer;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.projections.ComposedProjection;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;
import com.jfixby.scarabei.api.geometry.projections.RotateProjection;
import com.jfixby.scarabei.api.math.Angle;

public class RedRasterizedString implements RasterizedString, LayerBasedComponent {

	final FokkerRedRasterizedString fokkerString;

	final RedComponentsFactory componentsFactory;

	private final CanvasPosition canvas_position;
	private boolean debug = !true;
	private String name;

	final float rescale = 1f;
	final float borderSize;
	final Color border_color;
	final Color color;
	final float size;
	final ID fontID;

	private final Color debug_color = Colors.YELLOW();

	int MAX = 128;

	private final RedLayer root;

	private final OffsetProjection offset;

	private final RotateProjection rotate;

	private final ComposedProjection projection;

	public RedRasterizedString (final RedComponentsFactory componentsFactory, final RasterizedStringSpecs specs) {
		this.fontID = Debug.checkNull("font", specs.getFontName());
		this.componentsFactory = componentsFactory;
		this.root = componentsFactory.newLayer();

		this.color = specs.getFontColor();
		this.border_color = specs.getBorderColor();

		this.borderSize = specs.getBorderSize();
		this.size = specs.getFontSize();
		this.canvas_position = Geometry.newCanvasPosition();
		this.name = "RedRasterizedString";
		this.fokkerString = new FokkerRedRasterizedString(this);
		this.root.attachComponent(this.fokkerString);
		this.offset = Geometry.getProjectionFactory().newOffset();
		this.rotate = Geometry.getProjectionFactory().newRotate();

		this.projection = Geometry.getProjectionFactory().compose(this.offset, this.rotate);
		this.root.setProjection(this.projection);

		this.setChars("");
	}

	private void updateProjections () {
		this.offset.setOffsetX(this.getPositionX());
		this.offset.setOffsetY(this.getPositionY());
		this.rotate.setRotation(this.getRotation());
	}

	@Override
	public void setPositionXY (final double canvas_x, final double canvas_y) {
		this.canvas_position.setXY(canvas_x, canvas_y);
		this.updateProjections();
	}

	private ORIGIN_RELATIVE_HORIZONTAL horizontal_alignment = ORIGIN_RELATIVE_HORIZONTAL.LEFT;
	private ORIGIN_RELATIVE_VERTICAL vertical_alignment = ORIGIN_RELATIVE_VERTICAL.TOP;

	@Override
	public void setOriginRelativeX (final ORIGIN_RELATIVE_HORIZONTAL horizontal_alignment) {
		this.horizontal_alignment = Debug.checkNull(horizontal_alignment);
		this.updateProjections();
	}

	@Override
	public void setOriginRelativeY (final ORIGIN_RELATIVE_VERTICAL vertical_alignment) {
		this.vertical_alignment = Debug.checkNull(vertical_alignment);
		this.updateProjections();
	}

	@Override
	public boolean getDebugRenderFlag () {
		return this.debug;
	}

	@Override
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public double getPositionX () {
		return this.canvas_position.getX();
	}

	@Override
	public double getPositionY () {
		return this.canvas_position.getY();
	}

	@Override
	public void setRotation (final Angle rotation) {
		this.canvas_position.setRotation(rotation);
		this.updateProjections();
	}

	@Override
	public void setRotation (final double rotation) {
		this.canvas_position.setRotation(rotation);
		this.updateProjections();
	}

	@Override
	public Angle getRotation () {
		return this.canvas_position.getRotation();
	}

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public void setName (final String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public void setPosition (final CanvasPosition position) {
		this.canvas_position.setPosition(position);
		this.updateProjections();
	}

	@Override
	public void setDebugRenderFlag (final boolean debug) {
		this.debug = debug;
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

	private BLEND_MODE mode = BLEND_MODE.GDX_DEFAULT;

	@Override
	public void setPosition (final ReadOnlyFloat2 position) {
		this.setPositionXY(position.getX(), position.getY());
	}

	@Override
	public void setPositionX (final double x) {
		this.canvas_position.setX(x);
		this.updateProjections();
	}

	@Override
	public void setPositionY (final double y) {
		this.canvas_position.setY(y);
		this.updateProjections();
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public void setChars (final String string) {
		this.fokkerString.setChars(string);
	}

	@Override
	public String getChars () {
		return this.fokkerString.getChars();
	}

	public void dispose () {
		this.fokkerString.dispose(this.componentsFactory);
	}

	@Override
	public String toString () {
		return "RasterizedString[" + this.fokkerString + "] size=" + this.size + "";
	}

}
