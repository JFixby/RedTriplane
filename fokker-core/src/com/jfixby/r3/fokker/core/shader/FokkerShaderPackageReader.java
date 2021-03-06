
package com.jfixby.r3.fokker.core.shader;

import java.io.IOException;

import com.jfixby.r3.fokker.api.StandardPackageFormats;
import com.jfixby.rana.api.format.PackageFormat;
import com.jfixby.rana.api.loader.PackageReader;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class FokkerShaderPackageReader implements PackageReader {

	final List<PackageFormat> acceptablePackageFormats;

	public FokkerShaderPackageReader () {
		this.acceptablePackageFormats = Collections.newList(new PackageFormat(StandardPackageFormats.RedTriplane.Shader));
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public void doReadPackage (final PackageReaderInput input) throws IOException {
// final PackageHandler handler = input.getPackageHandler();
// listener.onDependenciesRequired(handler, handler.listDependencies());

		final ShadersGroup group = new ShadersGroup(input);

	}

}
