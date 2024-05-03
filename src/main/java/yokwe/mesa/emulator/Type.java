package yokwe.mesa.emulator;

import yokwe.util.UnexpectedException;

public final class Type {
	//
	// 16 bit word
	//
	private static final int WORD_BITS = 16;
	private static final int WORD_SIZE = 1 << WORD_BITS;
	private static final int WORD_MASK = WORD_SIZE - 1;
	
	public static int toInt(short value) {
		return value & WORD_MASK;
	}
	
	
	//
	// 32 bit word
	//
	public static short highHalf(int value32) {
		return (short)(value32 >> WORD_BITS);
	}
	public static short lowHalf(int value32) {
		return (short)value32;
	}
	public static int toInt(short high, short low) {
		return (high << WORD_BITS) | (low & WORD_MASK);
	}

	
	//
	// PAGE
	//
	private static final int PAGE_BITS = 8;
	private static final int PAGE_SIZE = 1 << PAGE_BITS;
	private static final int PAGE_MASK = PAGE_SIZE - 1;
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
	
	
	//
	// Memory
	//
	public static final int MAX_REALMEMORY_PAGE_SIZE = /* RealMemoryImplGuam::largestArraySize */ 4086 * WORD_BITS;
	public static final int MAX_REALMEMORY_SIZE = MAX_REALMEMORY_PAGE_SIZE << PAGE_BITS;
	
	public static final int DEFAULT_VM_BITS   = 24;
	public static final int DEFAULT_RM_BITS   = 22;
	public static final int DEFAULT_IO_REGION = 0x80;
	
	
	//
	// Misc
	//
	public static void error() {
		// FIXME
		throw new UnexpectedException("error");
	}
	
}
