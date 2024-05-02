package yokwe.mesa.emulator;

import java.util.ArrayList;
import java.util.List;

public final class MapFlag {
	private static final int MASK_REAL_PAGE   = 0x0000_FFFF; // real page number
	private static final int MASK_FLAG        = 0xFFFF_0000;
	
	private static final int MASK_PROTECT     = 0x0001_0000;
	private static final int MASK_DIRTY       = 0x0002_0000;
	private static final int MASK_REFERENCED  = 0x0004_0000;
	
	private static final int FLAG_VACANT = MASK_DIRTY | MASK_PROTECT; // not referenced and dirty and protect
	
	private int rawValue;
	
	public MapFlag() {
		rawValue = FLAG_VACANT;
	}
	
	// read operation
	public int realPage() {
		return rawValue & MASK_REAL_PAGE;
	}
	public boolean protect() {
		return (rawValue & MASK_PROTECT) != 0;
	}
	public boolean dirty() {
		return (rawValue & MASK_DIRTY) != 0;
	}
	public boolean referenced() {
		return (rawValue & MASK_REFERENCED) != 0;
	}
	public boolean vacant() {
		return (rawValue & MASK_FLAG) == FLAG_VACANT;
	}
	
	// write operation
	public void realPage(int value) {
		rawValue = (rawValue & MASK_FLAG) | (value & MASK_REAL_PAGE);
	}
	public void protect(boolean value) {
		rawValue = (rawValue & ~MASK_PROTECT) | (value ? MASK_PROTECT : 0);
	}
	public void vacant(boolean value) {
		rawValue = (rawValue & ~MASK_FLAG) | (value ? FLAG_VACANT : 0);
	}
	
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
	public void setVacant() {
		rawValue = FLAG_VACANT;
	}
	
	@Override
	public String toString() {
		List<String> flags = new ArrayList<>();
		if (vacant()) {
			 flags.add("VACANT");
		} else {
			if (protect()) flags.add("PROTECT");
			if (dirty()) flags.add("DIRTY");
			if (referenced()) flags.add("REFERENCED");
		}
		
		return String.format("{%X {%s}}", realPage(), String.join(" ", flags));
	}
}