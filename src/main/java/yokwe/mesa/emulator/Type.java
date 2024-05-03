package yokwe.mesa.emulator;

import yokwe.util.UnexpectedException;

public final class Type {
	public static final int SHORT_BITS = 16;
	public static final int SHORT_SIZE = 1 << SHORT_BITS;
	public static final int SHORT_MASK = SHORT_SIZE - 1;
	
	public static int toInt(short value) {
		return value & SHORT_MASK;
	}
	public static int toInt(short high, short low) {
		return (high << SHORT_BITS) | (low & SHORT_MASK);
	}
	
	public static final int PAGE_BITS = 8;
	public static final int PAGE_SIZE = 1 << PAGE_BITS;
	public static final int PAGE_MASK = PAGE_SIZE - 1;
	public static int toPageNumber(int va) {
		return va >>> PAGE_BITS;
	}
	public static boolean isSamePage(int va, int vb) {
		return (va & ~PAGE_MASK) == (vb & ~PAGE_MASK);
	}


	public static final int MAX_REALMEMORY_PAGE_SIZE = /* RealMemoryImplGuam::largestArraySize */ 4086 * SHORT_BITS;
	public static final int MAX_REALMEMORY_SIZE = MAX_REALMEMORY_PAGE_SIZE << PAGE_BITS;
	
	public static final int DEFAULT_VM_BITS   = 24;
	public static final int DEFAULT_RM_BITS   = 22;
	public static final int DEFAULT_IO_REGION = 0x80;
	
	
	public static void error() {
		// FIXME
		throw new UnexpectedException("error");
	}
	
}
