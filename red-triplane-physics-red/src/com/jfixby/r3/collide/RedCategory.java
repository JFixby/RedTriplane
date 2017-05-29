
package com.jfixby.r3.collide;

import com.jfixby.scarabei.api.collisions.AtomicCollisionCategory;

public class RedCategory implements AtomicCollisionCategory {

	private static final RedCategory ALL = new RedCategory((short)0xffff);
	private static final RedCategory NONE = new RedCategory((short)0x0000);

	// private final static short DEFAULT_CATEGORY = (short) 0xffff;

	// private static final CollisionCategory DEFAULT =

	public static RedCategory ALL () {
		return ALL;
	}

	public static RedCategory NONE () {
		return NONE;
	}

	final short bits;
	final String string_represantation;
	private RedAtomicCategoriesList owner;

	public RedCategory (final short category_id) {
		this.bits = category_id;
		this.string_represantation = shortToBitsString(this.bits);
	}

	public static final String shortToBitsString (final short bits) {
		String binarized = Integer.toBinaryString(bits);
		int len = binarized.length();
		String sixteenZeroes = "00000000000000000";
		if (len < 16) {
			binarized = sixteenZeroes.substring(0, 16 - len).concat(binarized);
		} else {
			binarized = binarized.substring(len - 16);
		}
		return binarized;
	}

	public RedCategory (final int index, RedAtomicCategoriesList redAtomicCategoriesList) {
		this.bits = (short)index_to_binary(index);
		this.string_represantation = shortToBitsString(this.bits);
		this.owner = redAtomicCategoriesList;
	}

	static private int index_to_binary (final int index) {
		final int result = (int)(pow(2, index));
		return result;
	}

	private static int pow (int A, int B) {
		int result = 1;
		for (int i = 0; i < B; i++) {
			result = result * A;
		}
		return result;
	}

	public static RedCategory NOT (final RedCategory a) {
		return new RedCategory((short)~a.bits);
	}

	public static RedCategory OR (final RedCategory a, final RedCategory b) {
		return new RedCategory((short)(a.bits | b.bits));
	}

	public static RedCategory AND (final RedCategory a, final RedCategory b) {
		return new RedCategory((short)(a.bits & b.bits));
	}

	@Override
	public String toString () {
		return "CollisionCategory [" + string_represantation + "]";
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + bits;
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final RedCategory other = (RedCategory)obj;
		if (bits != other.bits) return false;
		return true;
	}

	public short getBits () {
		return this.bits;
	}
}
