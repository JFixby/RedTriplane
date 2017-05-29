
package com.jfixby.r3.engine.core.unit.layers;

import java.util.Comparator;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.LayerUtilsComponent;
import com.jfixby.r3.api.ui.unit.layer.TreeLayer;

public class RedLayerUtils implements LayerUtilsComponent {

	@Override
	public <T> TreeLayer<T> newTree (ComponentsFactory factory, Comparator<T> comparator) {
		return new RedBinaryLayerTree<T>(factory, comparator);
	}

}
