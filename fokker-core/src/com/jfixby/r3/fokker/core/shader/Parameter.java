package com.jfixby.r3.fokker.core.shader;

import com.jfixby.r3.api.shader.ShaderParameter;
import com.jfixby.r3.api.shader.srlz.ShaderParameterInfo;

class Parameter implements ShaderParameter {

	private final String name;
	private final ShaderParameterInfo param;

	public Parameter (final ShaderParameterInfo param) {
		this.name = param.name;
		this.param = param;
	}

	@Override
	public String toString () {
		return "ShaderParameter[name=" + this.name + ", type=" + this.param.type + "]";
	}

	@Override
	public String getName () {
		return this.name;
	}

}