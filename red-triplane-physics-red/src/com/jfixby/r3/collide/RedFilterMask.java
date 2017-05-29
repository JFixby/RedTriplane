
package com.jfixby.r3.collide;

public class RedFilterMask {
	private static final short DEFAULT_MASK = -1;
	short bits;
	String string_represantation;

	public RedFilterMask (short category_id) {
		this.bits = category_id;
		this.string_represantation = RedCategory.shortToBitsString(category_id);
	}

	public RedFilterMask () {
		this(DEFAULT_MASK);
	}

	public short getBits () {
		return this.bits;
	}

	public void setBits (final short bits) {
		this.bits = bits;
		this.string_represantation = RedCategory.shortToBitsString(bits);
	}

	@Override
	public String toString () {
		return "CollisionFilterMask [" + string_represantation + "]";
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
		RedFilterMask other = (RedFilterMask)obj;
		if (bits != other.bits) return false;
		return true;
	}

	public void rejectCollisionsWith (final RedCategory category) {
		final short category_bits = category.getBits();
		final short current_mask_bits = this.bits;
		this.setBits((short)((current_mask_bits) & (~category_bits)));

	}

	public void acceptCollisionsWith (final RedCategory category) {
		final short category_bits = category.getBits();
		final short current_mask_bits = this.bits;
		this.setBits((short)(current_mask_bits | category_bits));

	}
}
