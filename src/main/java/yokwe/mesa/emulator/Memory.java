package yokwe.mesa.emulator;

import java.util.Arrays;

public class Memory {
	public static final int DEFAULT_VM_BITS   = 24;
	public static final int DEFAULT_RM_BITS   = 20;
	public static final int DEFAULT_IO_REGION = 0x80;
	
	public static final int PAGE_BITS = 8;
	public static final int PAGE_SIZE = 1 << PAGE_BITS;
	public static final int PAGE_MASK = PAGE_SIZE - 1;
		
	public final int vmBits;
	public final int rmBits;
	public final int ioRegion;
	
	public final int vmSize;
	public final int rmSize;
	
	private final MapFlag[] mapFlag;
	private final short[][] realPage;
	
	
	public Memory(int vmBits_, int rmBits_, int ioRegion_) {
		vmBits   = vmBits_;
		rmBits   = rmBits_;
		ioRegion = ioRegion_;
		
		vmSize   = 1 << vmBits;
		rmSize   = 1 << rmBits;
		
		mapFlag  = new MapFlag[vmSize];
		for(int i = 0; i < vmSize; i++) {
			mapFlag[i] = new MapFlag();
		}
		realPage = new short[rmSize][PAGE_SIZE];
		for(int i = 0; i < rmSize; i++) {
			Arrays.fill(realPage[i], (short)0);
		}
	}
	public Memory() {
		this(DEFAULT_VM_BITS, DEFAULT_RM_BITS, DEFAULT_IO_REGION);
	}
	
	@Override
	public String toString() {
		return String.format("{vmBits %d  rmBits  %d  ioRegion  %d}", vmBits, rmBits, ioRegion);
	}
	
	
	public static final int SHORT_BITS = 16;
	public static final int SHORT_MASK = (1 << SHORT_BITS) - 1;
	public static int toInt(short value) {
		return value & SHORT_MASK;
	}
	public static int toInt(short high, short low) {
		return (high << SHORT_BITS) | (low & SHORT_MASK);
	}
	
	
	public short[] fetchPage(int va) {
		var flag = mapFlag[va >>> PAGE_BITS];
		
		if (flag.vacant()) Process.pageFault(va);
		
		// maintain flag
		flag.setReferenced();
		
		return realPage[flag.realPage()];
	}
	public short[] storePage(int va) {
		var flag = mapFlag[va >>> PAGE_BITS];
		
		if (flag.vacant()) Process.pageFault(va);
		if (flag.protect()) Process.writeProtectFault(va);
		
		// maintain flag
		flag.setReferenced();
		flag.setDirty();
		
		return realPage[flag.realPage()];
	}
	
	public int read16(int va) {
		return toInt(fetchPage(va)[va & PAGE_MASK]);
	}
	public void writ16(int va, int value) {
		storePage(va)[va & PAGE_MASK] = (short)value;
	}
	
	
	public static boolean isSamePage(int va, int vb) {
		return (va & ~PAGE_MASK) == (vb & ~PAGE_MASK);
	}
	public int read32(int va) {
		int va0 = va + 0;
		int va1 = va + 1;
		
		short[] ra0 = fetchPage(va0);
		short[] ra1 = isSamePage(va0, va1) ? ra0 : fetchPage(va1);
		
		return toInt(ra0[va0 & PAGE_MASK], ra1[va0 & PAGE_MASK]);
	}
	public void write32(int va, int newValue) {
		int va0 = va + 0;
		int va1 = va + 1;
		
		short[] ra0 = fetchPage(va0);
		short[] ra1 = isSamePage(va0, va1) ? ra0 : fetchPage(va1);
		
		ra0[va0 & PAGE_MASK] = (short)(newValue >> SHORT_BITS);
		ra1[va1 & PAGE_MASK] = (short)(newValue);
	}

	
}
