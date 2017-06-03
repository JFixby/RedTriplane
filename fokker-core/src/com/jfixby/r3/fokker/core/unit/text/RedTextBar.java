
package com.jfixby.r3.fokker.core.unit.text;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.LayerBasedComponent;
import com.jfixby.r3.api.ui.unit.geometry.RectangleComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.txt.TextBar;
import com.jfixby.r3.api.ui.unit.txt.TextBarSpecs;
import com.jfixby.r3.fokker.core.unit.RedComponentsFactory;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.strings.api.Text;
import com.jfixby.strings.api.TextTranslation;

public class RedTextBar implements TextBar, LayerBasedComponent {

	final ComponentsFactory componentsFactory;
	private final Layer root;
	ID font_id;
	private final CanvasPosition position = Geometry.newCanvasPosition();
	// private Raster bg_raster;
	private final float padding;
	private String locale_name;
	private final Text text;
	private final float font_scale;
	private final float font_size;
	private CanvasComponent background;

	// private final AssetID id;

	private RedTextBarText current_text;
	private Color font_color;
	private final RectangleComponent rectangle;
	private final String rawText;

	@Override
	public String toString () {
// return "TextBar[text=" + this.text + ", font_id=" + this.font_id + ", position=" + this.position + ", padding="
// + this.padding + ", locale_name=" + this.locale_name + ", font_scale=" + this.font_scale + ", font_size="
// + this.font_size + ", background=" + this.background + ", font_color=" + this.font_color + "]";
//
		return "TextBar[" + this.getName() + "]";
	}

	public RedTextBar (final TextBarSpecs text_specs, final RedComponentsFactory componentsFactory) {
		this.componentsFactory = componentsFactory;
		this.root = componentsFactory.newLayer();
		this.font_id = text_specs.getFont();

		this.padding = text_specs.getPadding();
		final Raster bg_raster = text_specs.getBackgroundRaster();
		this.font_scale = text_specs.getFontScale();
		this.font_size = text_specs.getFontSize();
		this.text = text_specs.getText();
		this.rawText = text_specs.getRawText();

		this.locale_name = text_specs.getLocaleName();
		this.font_color = text_specs.getFontColor();
		if (this.font_color == null) {
			this.font_color = Colors.BLACK();
		}

		// root.attachComponent(bg_raster);

		this.rectangle = componentsFactory.getGeometryDepartment().newRectangle();
		this.rectangle.setFillColor(Colors.WHITE().customize().setAlpha(0.5f));
		this.background = this.rectangle;
		this.background = bg_raster;
		if (this.background != null) {
			this.root.attachComponent(this.background);
			this.rectangle.setSize(bg_raster.getWidth(), bg_raster.getHeight());
		}

// Debug.checkNull("getText()", text);

		this.createText();
		if (this.background != null) {
			this.background.setPosition(0, 0);
		}

	}

	@Override
	public void setRawText (final String text_string) {
		Debug.checkNull("text_string", text_string);
		if (text_string.equals(this.rawText)) {
			return;
		}
		if (this.current_text != null) {
			this.disposeText();
		}

// Debug.checkTrue("no children", this.root.listChildren().size() == 0);

		this.current_text = new RedTextBarText(this, text_string, this.font_size, this.padding, this.font_scale, this.font_color,
			this.font_id);
		this.current_text.attach(this.root);

		this.updateTextPosition();
	}

	@Override
	public String getRawText () {

		return this.current_text.getRawText();
	}

	private void createText () {
		String text_string = null;
		if (this.text != null) {
			final TextTranslation translation = this.text.listTranslations().getByLocalization(this.locale_name);
			if (translation == null) {
				this.text.listTranslations().print();
				Err.reportError("Localization not found: " + this.locale_name);
			}
			text_string = translation.getString().getChars();
		} else {
			text_string = "";
			if (this.rawText != null) {
				text_string = this.rawText;
			}
		}

		Debug.checkNull("text_string", text_string);

		if (this.current_text != null) {
			this.disposeText();
		}
// if (this.root.listChildren().size() != 0) {
// this.root.print();
// }
// Debug.checkTrue("no children", this.root.listChildren().size() == 0);

		this.current_text = new RedTextBarText(this, text_string, this.font_size, this.padding, this.font_scale, this.font_color,
			this.font_id);
		this.current_text.attach(this.root);

		this.updateTextPosition();
	}

	private void updateText () {
		this.disposeText();
		this.createText();
	}

	private void disposeText () {
		this.current_text.dispose();
		this.current_text = null;
	}

	@Override
	public void setPosition (final double canvas_x, final double canvas_y) {
		this.position.setXY(canvas_x, canvas_y);
		this.updateTextPosition();
	}

	private void updateTextPosition () {
		this.current_text.updatePosition(this.position);
		if (this.background != null) {
			this.background.setPosition(this.position);
		}
		this.current_text.updatePosition(this.position);
	}

	@Override
	public void setRotation (final Angle rotation) {
		this.position.setRotation(rotation);
		this.updateTextPosition();
	}

	@Override
	public void setRotation (final double rotation) {
		this.position.setRotation(rotation);
		this.updateTextPosition();
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
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public void setName (final String name) {
		this.root.setName(name);
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public double getPositionX () {
		return this.position.getX();

	}

	@Override
	public double getPositionY () {
		return this.position.getY();
	}

	@Override
	public Angle getRotation () {
		return this.position.getRotation();
	}

	@Override
	public void setLocaleName (final String locale_name) {
		final String old_locale = this.locale_name;
		this.locale_name = Debug.checkNull("locale_name", locale_name);
		Debug.checkEmpty("locale_name", locale_name);
		if (!old_locale.equals(locale_name)) {

		}
		this.updateText();
	}

	@Override
	public String getLocaleName () {
		return this.locale_name;
	}

	@Override
	public void setPosition (final CanvasPosition position) {
		this.position.setPosition(position);
		this.updateTextPosition();

	}

	@Override
	public void setPosition (final ReadOnlyFloat2 position) {
		this.position.setPosition(position);
		this.updateTextPosition();
	}

	@Override
	public void offset (final double x, final double y) {
		this.position.add(x, y);
		this.updateTextPosition();
	}

	@Override
	public void setPositionX (final double x) {
		this.position.setX(x);
		this.updateTextPosition();
	}

	@Override
	public void setPositionY (final double y) {
		this.position.setY(y);
		this.updateTextPosition();
	}

// final Rectangle shape = Geometry.newRectangle();
//
// @Override
// public Rectangle shape () {
// this.shape.setPosition(this.position);
// this.shape.setSize(this.rectangle.shape());
// return this.shape;
// }

}
