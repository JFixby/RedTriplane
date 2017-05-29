
package com.jfixby.r3.fokker.utils;

import com.badlogic.gdx.utils.Array;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedGdxUtils implements GdxUtilsComponent {

	@Override
	public <T> List<T> newList (Array<T> gdx_array) {
		final List<T> result = Collections.newList();
		for (int i = 0; i < gdx_array.size; i++) {
			T element = gdx_array.get(i);
			result.add(element);
		}
		return result;
	}

}
