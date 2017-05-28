
package com.jfixby.r3.fokker.starter;

import java.io.IOException;

import com.jfixby.r3.fokker.api.starter.FokkerStarterConfig;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageInfo;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.json.Json;

public class FokkerStarterConfigReader implements PackageReader {

	private final List<PackageFormat> acceptablePackageFormats;

	public FokkerStarterConfigReader () {
		super();
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(FokkerStarterConfig.PACKAGE_FORMAT_STRING);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
		final String str = input.packageRootFile.readToString();
		final FokkerStarterConfig configData = Json.deserializeFromString(FokkerStarterConfig.class, str);
		final PackageInfo info = input.packageInfo;
		final String name = info.packageName;
		final ID assetID = Names.newID(name);
		final FokkerStarterConfigAsset asset = new FokkerStarterConfigAsset(assetID, configData);
		input.assetsContainer.addAsset(assetID, asset);
	}

}
