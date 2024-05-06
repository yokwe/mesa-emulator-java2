package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MemoryTest extends Base {
//	private static final yokwe.util.FormatLogger logger = yokwe.util.FormatLogger.getLogger();

	@Test
	public void readMap_A() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va = 0x0020_0123;
		
		// prepare
		// execute
		var map = memory.readMap(Type.toPageNumber(va));
		
		// check result
		assertEquals((short)0, map.flag());
		assertEquals(false, map.testVacant());
		
		assertEquals(Type.toPageBase(va), map.offset());
		
		// check side effect
	}
	@Test
	public void readMap_B() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va = (memory.raSize) + 100;
		
		// prepare
		// execute
		var map = memory.readMap(Type.toPageNumber(va));
		
		// check result
		assertEquals(true, map.testVacant());
		assertEquals(0, map.offset());
		
		// check side effect
	}
	@Test
	public void writeMap() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int   vp       = 1000;
		short mapFlags = 0xFF;
		int   rp       = 2000;
		short expected = 0x07;
		// prepare
		// execute
		memory.writeMap(vp, mapFlags, rp);
		
		// check result
		var map = memory.readMap(vp);
		assertEquals(expected, map.flag());
		assertEquals(Type.toPageAddress(rp), map.offset());
		
		// check side effect
	}
	@Test
	public void fetch() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va = 0x0020_0123;
		
		// prepare
		// execute
		int ra = memory.fetch(va);
		
		// check result
		assertEquals(va, ra);
		
		// check side effect
		Map map = memory.readMap(Type.toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(true,  map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void store() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va = 0x0020_0123;
		
		// prepare
		// execute
		int ra = memory.store(va);
		
		// check result
		assertEquals(va, ra);
		
		// check side effect
		Map map = memory.readMap(Type.toPageNumber(va));
		assertEquals(true,  map.testDirty());
		assertEquals(true,  map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void peek() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va = 0x0020_0123;
		
		// prepare
		// execute
		int ra = memory.peek(va);
		
		// check result
		assertEquals(va, ra);
		
		// check side effect
		Map map = memory.readMap(Type.toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(false, map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void rawRead16() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va       = 0x0020_0123;
		int expected = 0xCAFE;
		
		// prepare
		int ra = memory.peek(va);
		memory.rawWrite16(ra, expected);
		
		// execute
		int actual = memory.rawRead16(ra);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
		Map map = memory.readMap(Type.toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(false, map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void rawWrite16() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int va       = 0x0020_CAFE;
		int expected = 0xCAFE;
		
		// prepare
		int ra = memory.peek(va);
		memory.rawWrite16(ra, expected);
		
		// execute
		int actual = memory.rawRead16(ra);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
		Map map = memory.readMap(Type.toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(false, map.testReferenced());
		assertEquals(false, map.testProtect());
	}
}
