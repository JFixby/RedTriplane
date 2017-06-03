
package com.jfixby.r3.fokker.core.unit.text;

import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.color.Color;

public class RedRasterizedStringSpecs implements RasterizedStringSpecs {
	private ID font_id;
	private float size;
	private final Set<Character> requiredCharacters = Collections.newSet();
	private Color font_color;
	private float font_scale;
	private float border;
	private Color border_color;

	@Override
	public void setFontName (final ID font_id) {
		this.font_id = font_id;
	}

	@Override
	public ID getFontName () {
		return this.font_id;
	}

	@Override
	public float getFontSize () {
		return this.size;
	}

	@Override
	public void setFontSize (final float size) {
		this.size = size;
	}

	@Override
	public Set<Character> getRequiredCharacters () {
		return this.requiredCharacters;
	}

	@Override
	public void addRequiredCharacters (final Iterable<Character> chars) {
		for (final Character c : chars) {
			this.requiredCharacters.add(c);
		}
	}

	@Override
	public void addRequiredCharacters (final String input) {
		final String chars = input;
		for (int i = 0; i < chars.length(); i++) {
			final Character c = chars.charAt(i);
			this.requiredCharacters.add(c);
		}
	}

	@Override
	public void setFontColor (final Color font_color) {
		this.font_color = font_color;
	}

	@Override
	public Color getFontColor () {
		return this.font_color;
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
	public void setBorderSize (final float border) {
		this.border = border;
	}

	@Override
	public void setBorderColor (final Color border_color) {
		this.border_color = border_color;
	}

	@Override
	public Color getBorderColor () {
		return this.border_color;
	}

	@Override
	public float getBorderSize () {
		return this.border;
	}

}
