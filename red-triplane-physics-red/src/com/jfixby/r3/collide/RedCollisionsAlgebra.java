
package com.jfixby.r3.collide;

import com.jfixby.scarabei.api.collisions.AtomicCategories;
import com.jfixby.scarabei.api.collisions.CollisionCategory;
import com.jfixby.scarabei.api.collisions.CollisionsComponent;

public class RedCollisionsAlgebra implements CollisionsComponent {

	private final RedAtomicCategoriesList ATOMS = new RedAtomicCategoriesList();

	@Override
	public AtomicCategories AtomicCategories () {
		return ATOMS;
	}

	@Override
	public CollisionCategory produceAND (CollisionCategory A, CollisionCategory B) {
		return RedCategory.AND((RedCategory)A, (RedCategory)B);
	}

	@Override
	public CollisionCategory produceOR (CollisionCategory A, CollisionCategory B) {
		return RedCategory.OR((RedCategory)A, (RedCategory)B);
	}

	@Override
	public CollisionCategory produceNOT (CollisionCategory A) {
		return RedCategory.NOT((RedCategory)A);
	}

	@Override
	public CollisionCategory ALL () {
		return RedCategory.ALL();
	}

	@Override
	public CollisionCategory NONE () {
		return RedCategory.NONE();

	}

	@Override
	public CollisionCategory DEFAULT () {
		return RedCategory.ALL();
	}

}
