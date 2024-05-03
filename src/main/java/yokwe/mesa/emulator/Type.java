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
	// virtual page
	public static int toPageOffset(int vp) {
		return vp & PAGE_MASK;
	}
	public static int toPageAddress(int vp) {
		return vp << PAGE_BITS;
	}
	// virtual address
	public static int toPageBase(int va) {
		return va & ~PAGE_MASK;
	}
	public static int toPageNumber(int va) {
		return va >>> PAGE_BITS;
	}
	public static boolean isSamePage(int va, int vb) {
		return toPageBase(va) == toPageBase(vb);
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
