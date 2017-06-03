
package com.jfixby.r3.fokker.unit.text;

import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.geometry.CanvasPosition;

class RedTextBarText {
	private final RedTextBar master;
	private final RasterizedString string;
// private final RasterizedFont font;
	private final float size;
	private final String string_value;
	private Layer root;

	// private final Raster bg;
	private final float padding;
	private final float font_scale;
	private final Color font_color;
	private final ID fontID;

	public String getRawText () {
		return this.string_value;
	}

	public RedTextBarText (final RedTextBar redTextBar, final String string_value, final float size, final float padding,
		final float font_scale, final Color font_color, final ID fontID) {
		Debug.checkNull(string_value);
		Debug.checkNull(fontID);
		this.master = redTextBar;
		this.fontID = fontID;
		this.padding = padding;
		this.font_color = font_color;
		// this.bg = Debug.checkNull(bg_raster_id);
		final RasterizedStringSpecs string_specs = this.master.componentsFactory.getTextDepartment().newRasterStringSpecs();

// final RasterizedFontSpecs font_specs = this.master.componentsFactory.getTextDepartment().newFontSpecs();
		string_specs.setFontSize(size);
		string_specs.setFontScale(font_scale);
		string_specs.setFontName(this.fontID);
		string_specs.addRequiredCharacters(string_value);
		string_specs.setFontColor(font_color);

// final RasterizedFont font = this.master.componentsFactory.getTextDepartment().newFont(font_specs);
// string_specs.setFont(font);

		final RasterizedString string = this.master.componentsFactory.getTextDepartment().newRasterString(string_specs);
		string.setChars(string_value);
		this.string = string;
		this.string.setDebugRenderFlag(!true);
// this.font = this.font;
		this.font_scale = font_scale;
		this.size = size;
		this.string_value = string_value;

	}

	public void dispose () {
		this.root.detatchComponent(this.string);
		// root.detatchComponent(this.bg);
		this.master.componentsFactory.getTextDepartment().dispose(this.string);
// this.string.dispose();
// this.font.dispose();
	}

	public void attach (final Layer root) {
		this.root = root;
		// root.attachComponent(this.bg);
		root.attachComponent(this.string);
	}

	public void updatePosition (final CanvasPosition position) {
		// L.d("text" + "_", position);

		// bg.setPosition(position);
		this.string.setPositionXY(position.getX() + this.padding, position.getY() + this.padding);
		this.string.setRotation(position.getRotation());
		// root.print();
		// L.d("text" + "_", bg.getWidth() + " " + bg.getHeight() + " " +
		// bg.getOpacity());
	}

}
