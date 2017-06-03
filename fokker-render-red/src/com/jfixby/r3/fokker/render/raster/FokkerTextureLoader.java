
package com.jfixby.r3.fokker.render.raster;

import java.io.IOException;

import com.jfixby.r3.fokker.api.StandardPackageFormats;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public final class FokkerTextureLoader implements PackageReader {
	public static final PackageFormat TEXTURE = new PackageFormat(StandardPackageFormats.libGDX.Texture);
	public static final PackageFormat ATLAS = new PackageFormat(StandardPackageFormats.libGDX.Atlas);

	final List<PackageFormat> acceptablePackageFormats = Collections.newList();

	public FokkerTextureLoader () {
		this.acceptablePackageFormats.add(TEXTURE);
		this.acceptablePackageFormats.add(ATLAS);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
// final PackageHandler handler = input.getPackageHandler();
// listener.onDependenciesRequired(handler, handler.listDependencies());
		final RedFokkerRasterDataGroup group = new RedFokkerRasterDataGroup(input, this);

	}

}
