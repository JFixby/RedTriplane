
package com.jfixby.r3.ext.font.gdx;

import java.io.IOException;

import com.jfixby.r3.engine.core.StandardPackageFormats;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class TrueTypeFontFontPackageLoader implements PackageReader {

	final List<PackageFormat> acceptablePackageFormats;

	public TrueTypeFontFontPackageLoader () {
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(StandardPackageFormats.libGDX.TTFFont);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
// final PackageHandler handler = input.getPackageHandler();

		final TTFFontGroup group = new TTFFontGroup(input);

	}

}
