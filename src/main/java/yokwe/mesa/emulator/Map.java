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
	
	// real page  00FF FF00
	// flag       0000 00FF
	private static final int REALPAGE_BITS   = 16;
	private static final int REALPAGE_OFFSET =  8;
	private static final int FLAG_BITS       =  8;
	private static final int FLAG_OFFSET     =  0;
	
	private static final int REALPAGE_MASK  = ((1 << REALPAGE_BITS) - 1) << REALPAGE_OFFSET;
	private static final int FLAG_MASK      = ((1 << FLAG_BITS) - 1) << FLAG_OFFSET;
		
	private static final int BIT_PROTECT    = 0x04;
	private static final int BIT_DIRTY      = 0x02;
	private static final int BIT_REFERENCED = 0x01;
	
	private static final int BIT_VACANT = BIT_DIRTY | BIT_PROTECT; // not referenced and dirty and protect
	
	private int rawValue;
	
	public Map() {
		rawValue  = 0;
	}
	public Map(short mapFlags, int realPage) {
		set(mapFlags, realPage);
	}
	public void set(short mapFlags, int realPage) {
		rawValue  = ((realPage << REALPAGE_OFFSET) & REALPAGE_MASK) | ((mapFlags << FLAG_OFFSET) & FLAG_MASK);
	}
	// write operation
	public void realPage(int realPage) {
		rawValue = (rawValue & FLAG_MASK) | ((realPage << REALPAGE_OFFSET) & REALPAGE_MASK);
	}
	// flag(int value) accept MapFlags compatible value
	public void flag(int value) {
		rawValue = (rawValue & REALPAGE_MASK) | ((value << FLAG_OFFSET) & FLAG_MASK);
	}

	// read operation
	public int offset() {
		return rawValue & REALPAGE_MASK;
	}
	// flag() returns MapFlags compatible value
	public short flag() {
		return (short)(rawValue & FLAG_MASK);
	}
	
	// vacant
	public boolean testVacant() {
		return (rawValue & FLAG_MASK) == BIT_VACANT;
	}
	public void setVacant() {
		rawValue = BIT_VACANT;
	}
	
	public boolean testProtect() {
		return (rawValue & BIT_PROTECT) != 0;
	}
	public boolean testDirty() {
		return (rawValue & BIT_DIRTY) != 0;
	}
	public boolean testReferenced() {
		return (rawValue & BIT_REFERENCED) != 0;
	}
	
	public void setProtect() {
		if ((rawValue & BIT_PROTECT) == 0) {
			rawValue = (rawValue & ~BIT_PROTECT) | BIT_PROTECT;
		}
	}
	public void setDirty() {
		if ((rawValue & BIT_DIRTY) == 0) {
			rawValue = (rawValue & ~BIT_DIRTY) | BIT_DIRTY;
		}
	}
	public void setReferenced() {
		if ((rawValue & BIT_REFERENCED) == 0) {
			rawValue = (rawValue & ~BIT_REFERENCED) | BIT_REFERENCED;
		}
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
		
		return String.format("{%d {%s}}", offset(), String.join(" ", flags));
	}
}