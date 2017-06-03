
package com.jfixby.r3.fokker.core.unit.text;

import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.txt.TextBarSpecs;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.strings.api.Text;

public class RedTextBarSpecs implements TextBarSpecs {

	private ID font_id;
	private float font_size;
	private Text text;
	private float padding;
	private Raster bg_asset_id;
	private float font_scale;
	private String locale_name;
	private Color font_color;
	private String text_value_raw;

	@Override
	public void setFont (final ID font_id) {
		this.font_id = font_id;
	}

	@Override
	public ID getFont () {
		return this.font_id;
	}

	@Override
	public void setFontSize (final float font_size) {
		this.font_size = font_size;
	}

	@Override
	public float getFontSize () {
		return this.font_size;
	}

	@Override
	public Text getText () {
		return this.text;
	}

	@Override
	public void setText (final Text text) {
		this.text = text;
	}

	@Override
	public void setPadding (final float padding) {
		this.padding = padding;
	}

	@Override
	public float getPadding () {
		return this.padding;
	}

	@Override
	public Raster getBackgroundRaster () {
		return this.bg_asset_id;
	}

	@Override
	public void setBackgroundRaster (final Raster bg_asset_id) {
		this.bg_asset_id = bg_asset_id;
	}

	@Override
	public void setFontScale (final float font_scale) {
		this.font_scale = font_scale;
	}

	@Override
	public float getFontScale () {
		return this.font_scale;
	}

	@Override
	public void setLocaleName (final String locale_name) {
		this.locale_name = locale_name;
	}

	@Override
	public String getLocaleName () {
		return this.locale_name;
	}

	@Override
	public void setFontColor (final Color color) {
		this.font_color = color;
	}

	@Override
	public Color getFontColor () {
		return this.font_color;
	}

	@Override
	public void setRawText (final String text_value_raw) {
		this.text_value_raw = text_value_raw;
	}

	@Override
	public String getRawText () {
		return this.text_value_raw;
	}

}
