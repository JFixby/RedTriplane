
package com.jfixby.r3.fokker.shader;

import java.io.IOException;

import com.jfixby.r3.api.shader.srlz.ShaderInfo;
import com.jfixby.r3.api.shader.srlz.ShadersContainer;
import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.io.IO;

public class ShadersGroup {

	public ShadersGroup (final PackageReaderInput input) throws IOException {
		final File package_root_file = input.packageRootFile;
// final PackageHandler handler = input.getPackageHandler();
		final AssetsContainer container = input.assetsContainer;

		final File file = input.packageRootFile;
		final ShadersContainer file_container = this.deserialize(ShadersContainer.class, file);
		for (final ShaderInfo shader : file_container.shaders) {
			final ID asset_id = Names.newID(shader.shader_id);
			final String shader_folder_name = shader.shader_folder_name;
			final File shader_folder = file.parent().child(shader_folder_name);
			final ShaderEntry entry = new ShaderEntry(asset_id, shader, shader_folder, file_container);
			container.addAsset(asset_id, entry);
		}
	}

	private <T> T deserialize (final Class<T> class1, final File file) throws IOException {
		try {
			return IO.deserialize(class1, file.readBytes());
		} catch (final IOException e) {
			e.printStackTrace();
			throw new IOException("Failed to read " + file, e);
		}
	}

}
