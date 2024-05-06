package yokwe.mesa.emulator;

import java.util.Arrays;

import static yokwe.mesa.emulator.Type.*;

public final class Memory {
	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();
	
	public final int vmBits;
	public final int rmBits;
	public final int ioRegionPage;
	
	public final int vpSize;
	public final int rpSize;
	
	public final int vaSize;
	public final int raSize;
	
	private final Map[]   map;
	private final short[] realMemory;
	
	
	public Memory(int vmBits_, int rmBits_, int ioRegionPage_) {
		vmBits       = vmBits_;
		rmBits       = rmBits_;
		ioRegionPage = ioRegionPage_;
		
		vpSize = toPageNumber(1 << vmBits);
		rpSize = Integer.min(toPageNumber(1 << rmBits), MAX_REALMEMORY_PAGE_SIZE);
		
		vaSize = toPageAddress(vpSize);
		raSize = toPageAddress(rpSize);
		
		map        = new Map[vpSize];
		realMemory = new short[raSize];
		
		// initialize map element
		Arrays.setAll(map, i -> new Map());
		
		init();
	}
	
	@Override
	public String toString() {
		return String.format("{vmBits  %d  vpSize  %X  rmBits %d  rmSize  %X  ioRegionPage  %d}", vmBits, vpSize - 1, rmBits, rpSize - 1, ioRegionPage);
	}
	
	public void init() {
		for(int i = 0; i < map.length; i++) {
			map[i].setVacant();
			map[i].realPage(0);
		}
		Arrays.fill(realMemory, (short)0);
		
		// initialize mapFlags and realPages
		//
		//const int VP_START = pageGerm + countGermVM;
		short mapFlags = 0;
		int   rp   = 0;
		// vp:[ioRegionPage .. 256) <=> rp:[0..256-ioRegionPage)
		for(int vp = ioRegionPage; vp < 256; vp++) {
			map[vp].set(mapFlags, rp++);
		}
		// vp:[0..ioRegionPage) <=> rp: [256-ioRegionPage .. 256)
		for(int vp = 0; vp < ioRegionPage; vp++) {
			map[vp].set(mapFlags, rp++);
		}
		// vp: [256 .. rpSize)
		for(int vp = 256; vp < rpSize; vp++) {
			map[vp].set(mapFlags, rp++);
		}
		if (rp != rpSize) {
			logger.error("rp != rpSize");
			error();
		}
		// vp: [rpSize .. vpSize)
		for(int vp = rpSize; vp < vpSize; vp++) {
			map[vp].setVacant();
		}
	}
	
	
	//	ReadMap PROCEDURE [virtual: VirtualPageNumber]
	//		    RETURNS [flags: MapFlags, real: RealPageNumber];
	public Map readMap(int vp) {
		return map[vp];
	}
	
	//	WriteMap: PROCEDURE [
	//		    virtual: VirtualPageNumber, flags: MapFlags, real: RealPageNumber];
	public void writeMap(int vp, short mapFlags, int realPageNumber) {
		var m = map[vp];
		m.flag(mapFlags);
		m.realPage(realPageNumber);
	}
	
	
	//
	// Low Level Access
	//
	public int peek(int va) {
		var flag = map[toPageNumber(va)];
		if (flag.testVacant()) error();
		
		return flag.offset() | toPageOffset(va);
	}
	public int rawRead16(int ra) {
		return toInt(realMemory[ra]);
	}
	public void rawWrite16(int ra, int value) {
		realMemory[ra] = (short)value;
	}
	public int rawRead32(int ra0, int ra1) {
		return toInt(realMemory[ra0], realMemory[ra1]);
	}
	public void rawWrite32(int ra0, int ra1, int newValue) {
		realMemory[ra0] = highHalf(newValue);
		realMemory[ra1] = lowHalf(newValue);
	}

	
	public int fetch(int va) {
		var flag = map[toPageNumber(va)];
		
		if (flag.testVacant()) Process.pageFault(va);
		
		// maintain flag
		flag.setReferenced();
		
		return flag.offset() | toPageOffset(va);
	}
	public int fetch(int va, int ra, int offset) {
		int vb = va + offset;
		return isSamePage(va, vb) ? ra + offset : fetch(vb);
	}
	public int store(int va) {
		var flag = map[toPageNumber(va)];
		
		if (flag.testVacant())  Process.pageFault(va);
		if (flag.testProtect()) Process.writeProtectFault(va);
		
		// maintain flag
		flag.setReferencedDirty();
		
		return flag.offset() | toPageOffset(va);
	}
	
	
	public int read16(int va) {
		return rawRead16(fetch(va));
	}
	public void writ16(int va, int value) {
		rawWrite16(store(va), value);
	}
	public int read32(int va) {
		int va0 = va + 0;
		int va1 = va + 1;
		
		int ra0 = fetch(va0);
		int ra1 = isSamePage(va0, va1) ? (ra0 + 1) : fetch(va1);
		
		return rawRead32(ra0, ra1);
	}
	public void write32(int va, int newValue) {
		int va0 = va + 0;
		int va1 = va + 1;
		
		int ra0 = store(va0);
		int ra1 = isSamePage(va0, va1) ? (ra0 + 1) : store(va1);
		
		rawWrite32(ra0, ra1, newValue);
	}
}
