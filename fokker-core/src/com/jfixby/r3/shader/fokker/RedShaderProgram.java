
package com.jfixby.r3.shader.fokker;

import java.io.IOException;

import com.jfixby.r3.api.shader.FragmentProgram;
import com.jfixby.r3.api.shader.ShaderProgram;
import com.jfixby.r3.api.shader.VertexProgram;
import com.jfixby.scarabei.api.file.File;

public class RedShaderProgram implements VertexProgram, FragmentProgram, ShaderProgram {

	private final String data;
	private final File source_code_file;

	public RedShaderProgram (final File source_code_file) throws IOException {
		this.source_code_file = source_code_file;
		this.data = source_code_file.readToString();
// final ByteArray bytes = source_code_file.readBytes();
// L.d("bytes", Arrays.toString(bytes.toArray()));
// L.d();
	}

	public RedShaderProgram (final String data) {
		this.data = data;
		this.source_code_file = null;
	}

	@Override
	public String getSourceCode () {
		return this.data;
	}

	@Override
	public String toString () {
		return "RedShaderProgram [source_code_file=" + this.source_code_file + ", data=" + this.data + "]";
	}

}
