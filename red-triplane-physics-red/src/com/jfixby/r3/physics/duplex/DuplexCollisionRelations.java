
package com.jfixby.r3.physics.duplex;

import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collisions.COLLISION_RELATION;
import com.jfixby.scarabei.api.collisions.CollisionCategory;
import com.jfixby.scarabei.api.collisions.CollisionRelations;

public class DuplexCollisionRelations implements CollisionRelations {

	private List<CollisionRelations> list;

	public DuplexCollisionRelations (List<CollisionRelations> list) {
		this.list = list;

	}

	@Override
	public void setPolicy (COLLISION_RELATION relation, CollisionCategory category) {
		for (int i = 0; i < list.size(); i++) {
			list.getElementAt(i).setPolicy(relation, category);
		}
	}

}
