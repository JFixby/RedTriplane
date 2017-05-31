
package com.jfixby.r3.engine.core.unit.text;

import java.io.IOException;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jfixby.r3.api.render.BLEND_MODE;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.r3.ext.font.gdx.ft.GdxR3FontCache;
import com.jfixby.r3.ext.font.gdx.ft.GdxR3FontGenerator;
import com.jfixby.r3.ext.font.gdx.ft.GdxR3FontParameters;
import com.jfixby.r3.ext.font.gdx.ft.RedBitmapFont;
import com.jfixby.r3.fokker.api.render.FokkerDrawable;
import com.jfixby.r3.fokker.api.render.FokkerString;
import com.jfixby.r3.fokker.api.render.RenderMachine;
import com.jfixby.r3.fokker.render.raster.VerticesCache;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.strings.api.io.font.TTFFontInfo;

public class FokkerRedRasterizedString implements FokkerDrawable, FokkerString, VisibleComponent {
	final RedRasterizedString master;
	private final boolean render_strings;
	private final boolean gdx_render = true;
	private final double opacity = 1;
	private final CanvasPosition canvas_position;
	private RedBitmapFont font;
	private AssetHandler font_aset;
	final TTFFontInfo font_data;
	final File gdx_font_file;
	final Set<Character> chars = Collections.newSet();
	String string_value;
	final VerticesCache verticesCache = new VerticesCache();

	public FokkerRedRasterizedString (final RedRasterizedString redRasterizedString) {
		this.master = redRasterizedString;
		this.render_strings = true;
		this.canvas_position = Geometry.newCanvasPosition();
		this.string_value = null;
		try {
			this.font_aset = this.obtain(this.master.fontID, redRasterizedString.componentsFactory);
		} catch (final IOException e) {
			e.printStackTrace();
			Err.reportError(e);
			this.font_aset = null;
		}
		this.font_data = this.font_aset.asset();
		this.gdx_font_file = this.font_data.getFontFile();
		this.setChars("");
	}

	public void dispose (final AssetsConsumer componentsFactory) {
		LoadedAssets.releaseAsset(this.font_aset, componentsFactory);
		L.e("RedRasterizedString disposal required: https://github.com/JFixby/RedTriplane/issues/3");
	}

	public void setChars (final String string) {
		if (JUtils.equalObjects(string, this.string_value)) {
			return;
		}
		this.string_value = string;
		this.updateString();
	}

	public String getChars () {
		return this.string_value;
	}

	@Override
	public void doDraw () {
		if (!this.render_strings) {
			return;

		}
		RenderMachine.component().beginDrawComponent(this);
		RenderMachine.component().beginRasterMode(BLEND_MODE.GDX_DEFAULT, this.opacity);

		if (this.gdx_render) {
			RenderMachine.component().drawString(this, this.canvas_position);
		} else {
			Err.reportError("Unsupported mode");
		}

		RenderMachine.component().endRasterMode(BLEND_MODE.GDX_DEFAULT);

		if (this.master.getDebugRenderFlag()) {
			RenderMachine.component().beginShapesMode();
			RenderMachine.endShapesMode();
		}

		RenderMachine.endDrawComponent(this);
	}

	private String charset () {
		this.chars.clear();
		for (int i = 0; i < this.string_value.length(); i++) {
			this.chars.add(this.string_value.charAt(i));
		}

		return charset(this.chars);
	}

	private AssetHandler obtain (final ID newAssetID, final AssetsConsumer componentsFactory) throws IOException {
		AssetsManager.autoResolveAsset(newAssetID);
		final AssetHandler asset_handler = LoadedAssets.obtainAsset(newAssetID, componentsFactory);
		if (asset_handler == null) {
			LoadedAssets.printAllLoadedAssets();
// PackagesLoader.printAllPackageReaders();
// ResourcesManager.printAllPackages();
			Err.reportError("Font<" + newAssetID + "> not found.");
		}
		return asset_handler;
	}

	static private String charset (final Collection<Character> set) {
		final StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < set.size(); i++) {
			final Character c = set.getElementAt(i);
			tmp.append(c);
		}
		tmp.reverse();// check for instability
		return tmp.toString();
	}

	private void updateString () {
		if (this.font != null) {
			this.font.dispose();
		}

		final GdxR3FontGenerator generator = new GdxR3FontGenerator(this.gdx_font_file);
		final GdxR3FontParameters params = generator.newFontParameters();
		params.setSize((int)FloatMath.round(this.master.size / this.master.rescale));
		params.setBorderSize((int)FloatMath.round(this.master.borderSize / this.master.rescale));
		params.setColor(this.master.color);
		params.setBorderColor(this.master.border_color);
		params.setCharacters(this.charset());
		this.font = generator.generateFont(params);
		Debug.checkNull("font", this.font);
		generator.dispose();

		final GdxR3FontCache cache = this.font.getCache();
		cache.setText(this.string_value);
		final int n = this.getRegions().size;
		this.verticesCache.updateNumberOfRegions(n);

	}

	@Override
	public boolean isVisible () {
		return this.master.isVisible();
	}

	public Array<TextureRegion> getRegions () {
		return this//
			.font//
				.getGdxFont()//
				.getRegions();
	}

	public int getVertexCount (final int region) {
		return this.font.getCache().getVertexCount(region);
	}

	public float[] getVertices (final int region) {
// return this.font.getCache().getVertices(region);
		this.verticesCache.updateRegion(region, this.font.getCache().getVertices(region));
		return this.verticesCache.getVertices(region);
	}
// HashMap<>

	public int getNumberOfSprites (final int region) {
// return this.font.getCache().getVertices(region).length / SpritesRenderer.SPRITE_SIZE;
		return this.verticesCache.getNumberOfSprites(region);
	}

	@Override
	public void setName (final String name) {
		this.master.setName(name);
	}

	@Override
	public String getName () {
		return this.master.getName();
	}

	@Override
	public void hide () {
		this.master.hide();
	}

	@Override
	public void show () {
		this.master.show();
	}

	@Override
	public void setVisible (final boolean b) {
		this.master.setVisible(b);
	}

	@Override
	public String toString () {
		return this.string_value;
	}

}
