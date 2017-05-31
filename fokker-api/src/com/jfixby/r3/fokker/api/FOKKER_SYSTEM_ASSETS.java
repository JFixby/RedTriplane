
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;

public class FOKKER_SYSTEM_ASSETS {

	public static ID LOCAL_R3_BANK_NAME;
	public static ID SHADER_GDX_DEFAULT;
	public static ID SHADER_GRAYSCALE;
	public static ID RASTER_IS_MISING;
	public static ID BLACK;
	public static ID DEBUG_BLACK;
	public static ID LOGO;

	public static ID GENERIC_FONT;

	public static ID SHADERS;
	public static ID SHADER_TEST;
	public static ID SHADER_NORMAL;
	public static ID SHADER_MULTIPLY;

	static {
		init();
	}

	public static void init () {
		LOCAL_R3_BANK_NAME = Names.newID("com.red-triplane.assets.r3.local");

		RASTER_IS_MISING = Names.newID("com.jfixby.r3.fokker.render.raster_is_missing");
		BLACK = Names.newID("com.jfixby.r3.fokker.render.black");
		DEBUG_BLACK = Names.newID("com.jfixby.r3.fokker.render.black-debug");

		GENERIC_FONT = Names.newID("otf.GenericFont");

		LOGO = Names.newID("com.jfixby.r3.fokker.render.logo");

		SHADERS = Names.newID("com.jfixby.r3.fokker.shader");
		SHADER_TEST = SHADERS.child("test");

		SHADER_NORMAL = SHADERS.child("normal");
		SHADER_MULTIPLY = SHADERS.child("multiply");
		SHADER_GRAYSCALE = SHADERS.child("grayscale");

		SHADER_GDX_DEFAULT = Names.newID("com.badlogic.gdx.graphics.g2d.SpriteBatch.DefaultShader");
	}

}
