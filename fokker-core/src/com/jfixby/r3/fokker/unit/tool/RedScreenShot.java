
package com.jfixby.r3.fokker.unit.tool;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jfixby.r3.api.ui.unit.ScreenShot;
import com.jfixby.r3.api.ui.unit.ScreenShotSpecs;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.fokker.unit.cam.RedCamera;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileOutputStream;
import com.jfixby.scarabei.api.image.ArrayColorMap;
import com.jfixby.scarabei.api.image.ArrayColorMapSpecs;
import com.jfixby.scarabei.api.image.ColorMap;
import com.jfixby.scarabei.api.image.ImageProcessing;
import com.jfixby.scarabei.api.log.L;

public class RedScreenShot implements ScreenShot {

	private int w;
	private int h;
	private int x;
	private int y;
	private final Pixmap gdx_pixmap;
	private final Camera camera;

	public RedScreenShot (final ScreenShotSpecs sh_spec) {
		this.camera = sh_spec.getCamera();
		if (this.camera == null) {
			this.w = sh_spec.getAreaWidth();
			this.h = sh_spec.getAreaHeight();
			this.x = sh_spec.getAreaX();
			this.y = sh_spec.getAreaY();
		} else {
			final RedCamera redcam = (RedCamera)this.camera;

			this.w = redcam.getScreenAperturedWidth();
			this.h = redcam.getScreenAperturedHeight();
			this.x = redcam.getScreenAperturedX();
			this.y = redcam.getScreenAperturedY();

		}
		this.gdx_pixmap = getScreenshot(this.x, this.y, this.w, this.h, true);

	}

	@Override
	public ColorMap toColorMap () {
		final ArrayColorMapSpecs specs = ImageProcessing.newArrayColorMapSpecs();
		specs.setWidth(this.w);
		specs.setHeight(this.h);
		final ArrayColorMap map = ImageProcessing.newArrayColorMap(specs);
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				final Color color_value = toGdxColor(this.gdx_pixmap.getPixel(i, j));
				map.setValue(i, j, color_value);
			}
		}
		return map;
	}

	final static private Color toGdxColor (final int pixel) {
		final com.badlogic.gdx.graphics.Color gdx_color = new com.badlogic.gdx.graphics.Color(pixel);
		final Color color = Colors.newColor(gdx_color.a, gdx_color.r, gdx_color.g, gdx_color.b);
		return color;
	}

	private static Pixmap getScreenshot (final int x, final int y, final int w, final int h, final boolean yDown) {
		final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);

		if (yDown) {
			// Flip the pixmap upside down
			final ByteBuffer pixels = pixmap.getPixels();
			final int numBytes = w * h * 4;
			final byte[] lines = new byte[numBytes];
			final int numBytesPerLine = w * 4;
			for (int i = 0; i < h; i++) {
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
			pixels.clear();
		}

		return pixmap;
	}

	@Override
	public void saveToFile (final File screenSHotFile) {
//
// final FileHandle file = new ToGdxFileAdaptor(screenSHotFile);
		L.d("writing", screenSHotFile);
// PixmapIO.writePNG(file, this.gdx_pixmap);

		final FileOutputStream os = screenSHotFile.newOutputStream();
		os.open();
		final Pixmap pixmap = this.gdx_pixmap;
		try {
			final PNG writer = new PNG((int)(pixmap.getWidth() * pixmap.getHeight() * 1.5f)); // Guess at deflated size.
			try {
				writer.setFlipY(false);

				final OutputStream output = os.toJavaOutputStream();
				writer.write(output, pixmap);
			} finally {
				writer.dispose();
			}
		} catch (final IOException ex) {
			throw new GdxRuntimeException("Error writing PNG: " + screenSHotFile, ex);
		}

		os.close();

	}

}
