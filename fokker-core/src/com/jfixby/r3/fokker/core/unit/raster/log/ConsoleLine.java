
package com.jfixby.r3.fokker.core.unit.raster.log;

import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.txt.RasterizedString;
import com.jfixby.r3.api.ui.unit.txt.RasterizedStringSpecs;
import com.jfixby.r3.fokker.core.unit.RedComponentsFactory;
import com.jfixby.r3.fokker.core.unit.layers.RedLayer;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;

public class ConsoleLine implements LayerBasedComponent {

	private final RasterizedString rstring;
	private final RedLayer root;
	private final OffsetProjection offset;
	int size = 8;
	private String value;

	public ConsoleLine (final String string, final int size, final RedComponentsFactory factory) {
		this.root = factory.newLayer();
		this.size = size;
		final RasterizedStringSpecs spec = factory.getTextDepartment().newRasterStringSpecs();
		spec.setFontColor(Colors.RED());
		spec.setFontSize(this.size);
		this.rstring = factory.getTextDepartment().newRasterString(spec);
		this.rstring.setChars(string);
		this.root.attachComponent(this.rstring);
		this.offset = Geometry.getProjectionFactory().newOffset();
// this.root.setProjection(this.offset);
		this.rstring.setPositionXY(100, 100);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	public void setPosition (final int index) {
// this.offset.setOffsetY(index * this.size * 0);
	}

	public void addValue (final String string) {
		this.value = this.value + string;
		this.rstring.setChars(this.value);
	}

	public void setValue (final String string) {
		this.value = string;
		this.rstring.setChars(this.value);
	}

	public String getValue () {
		return this.value;
	}

}
