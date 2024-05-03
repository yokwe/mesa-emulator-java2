package yokwe.mesa.emulator;

import java.util.ArrayList;
import java.util.List;

public final class Map {
	//	MapFlags: TYPE = MACHINE DEPENDENT RECORD [
	//  reserved(0: 0..12): UNSPECIFIED[O..17777B],
	//  protected(0: 13..13): BOOLEAN,
	//  dirty(0: 14..14): BOOLEAN,
	//  referenced(0: 15..15): BOOLEAN];
	
	// NOTE provide MapFlags compatible layout for flag() and flag(int)
	
	private static final int MASK_REAL_PAGE   = 0x0000_FFFF; // real page number
	private static final int MASK_FLAG        = 0xFFFF_0000;
	
	private static final int OFFSET_REAL_PAGE =  0;
	private static final int OFFSET_FLAG      = 16;
	
	private static final int MASK_PROTECT     = 0x0004_0000;
	private static final int MASK_DIRTY       = 0x0002_0000;
	private static final int MASK_REFERENCED  = 0x0001_0000;
	
	private static final int FLAG_VACANT = MASK_DIRTY | MASK_PROTECT; // not referenced and dirty and protect
	
	private int rawValue;
	
	public Map() {
		rawValue  = 0;
	}
	public Map(short mapFlags, int realPage) {
		rawValue  = ((mapFlags << OFFSET_FLAG) & MASK_FLAG) | ((realPage << OFFSET_REAL_PAGE) & MASK_REAL_PAGE);
	}
	
	// read operation
	public int realPage() {
		return (rawValue & MASK_REAL_PAGE) >> OFFSET_REAL_PAGE;
	}
	// flag() returns MapFlags compatible value
	public short flag() {
		return (short)((rawValue & MASK_FLAG) >> OFFSET_FLAG);
	}
	
	// write operation
	public void realPage(int value) {
		rawValue = (rawValue & MASK_FLAG) | ((value << OFFSET_REAL_PAGE) & MASK_REAL_PAGE);
	}
	// flag(int value) accept MapFlags compatible value
	public void flag(int value) {
		rawValue = ((value << OFFSET_FLAG) & MASK_FLAG) | (rawValue & MASK_REAL_PAGE);
	}
	
	// vacant
	public boolean testVacant() {
		return (rawValue & MASK_FLAG) == FLAG_VACANT;
	}
	public void setVacant() {
		rawValue = FLAG_VACANT;
	}
	
	// testXXX
	private boolean testFlag(int mask) {
		return (rawValue & mask) != 0;
	}
	public boolean testProtect() {
		return testFlag(MASK_PROTECT);
	}
	public boolean testDirty() {
		return testFlag(MASK_DIRTY);
	}
	public boolean testReferenced() {
		return testFlag(MASK_REFERENCED);
	}
	
	// setXXX
	private void setFlag(int mask) {
		if ((rawValue & mask) == 0) {
			rawValue = (rawValue & ~mask) | mask;
		}
	}
	public void setProtect() {
		setFlag(MASK_PROTECT);
	}
	public void setDirty() {
		setFlag(MASK_DIRTY);
	}
	public void setReferenced() {
		setFlag(MASK_REFERENCED);
	}
	
	@Override
	public String toString() {
		List<String> flags = new ArrayList<>();
		if (testVacant()) {
			 flags.add("VACANT");
		} else {
			if (testProtect()) flags.add("PROTECT");
			if (testDirty()) flags.add("DIRTY");
			if (testReferenced()) flags.add("REFERENCED");
		}
		
		return String.format("{%X {%s}}", realPage(), String.join(" ", flags));
	}
}