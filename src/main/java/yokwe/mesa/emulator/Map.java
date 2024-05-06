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
	private static final int REALPAGE_MASK   = ((1 << REALPAGE_BITS) - 1) << REALPAGE_OFFSET;
	
	private static final int FLAG_PROTECT    = 0x04;
	private static final int FLAG_DIRTY      = 0x02;
	private static final int FLAG_REFERENCED = 0x01;
	private static final int FLAG_MASK       = FLAG_PROTECT | FLAG_DIRTY | FLAG_REFERENCED;
	// composite flag
	private static final int FLAG_VACANT           = FLAG_PROTECT | FLAG_DIRTY;
	private static final int FLAG_REFERENCED_DIRTY = FLAG_DIRTY | FLAG_REFERENCED;
	
	private int rawValue;
	
	public Map() {
		rawValue = 0;
	}
	public Map(short mapFlags, int realPage) {
		set(mapFlags, realPage);
	}
	public void set(short mapFlags, int realPage) {
		flag(mapFlags);
		realPage(realPage);
	}
	// write operation
	public void realPage(int realPage) {
		rawValue = (rawValue & FLAG_MASK) | ((realPage << REALPAGE_OFFSET) & REALPAGE_MASK);
	}
	// flag(int value) accept MapFlags compatible value
	public void flag(int value) {
		rawValue = (rawValue & REALPAGE_MASK) | (value & FLAG_MASK);
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
	public void setVacant() {
		rawValue = FLAG_VACANT;
	}
	public boolean testVacant() {
		return (rawValue & FLAG_MASK) == FLAG_VACANT;
	}

	// simple flag
	public boolean testProtect() {
		return (rawValue & FLAG_PROTECT) != 0;
	}
	public boolean testDirty() {
		return (rawValue & FLAG_DIRTY) != 0;
	}
	public boolean testReferenced() {
		return (rawValue & FLAG_REFERENCED) != 0;
	}
	// composite flag
	public boolean testReferencedDirty() {
		return (rawValue & FLAG_REFERENCED_DIRTY) == FLAG_REFERENCED_DIRTY;
	}
	
	public void setProtect() {
		if (!testProtect()) {
			rawValue = (rawValue & ~FLAG_PROTECT) | FLAG_PROTECT;
		}
	}
	public void setDirty() {
		if (!testDirty()) {
			rawValue = (rawValue & ~FLAG_DIRTY) | FLAG_DIRTY;
		}
	}
	public void setReferenced() {
		if (!testReferenced()) {
			rawValue = (rawValue & ~FLAG_REFERENCED) | FLAG_REFERENCED;
		}
	}
	// composite flag
	public void setReferencedDirty() {
		if (!testReferencedDirty()) {
			rawValue = (rawValue & ~FLAG_REFERENCED_DIRTY) | FLAG_REFERENCED_DIRTY;
		}
	}
	
	@Override
	public String toString() {
		List<String> flags = new ArrayList<>();
		if (testVacant()) {
			 flags.add("VACANT");
		} else {
			if (testProtect())    flags.add("PROTECT");
			if (testDirty())      flags.add("DIRTY");
			if (testReferenced()) flags.add("REFERENCED");
		}
		
		return String.format("{%d {%s}}", offset(), String.join(" ", flags));
	}
}