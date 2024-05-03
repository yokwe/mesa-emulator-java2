package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static yokwe.mesa.emulator.Type.*;

public class MemoryTest extends Base {
//	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();

	@Test
	public void readMapA() {
//		logger.info(StackUtil.getCallerMethodName());
		
		int va = 0x0020_0123;
		
		// prepare
		// execute
		var map = memory.readMap(toPageNumber(va));
		
		// check result
		assertEquals((short)0, map.flag());
		assertEquals(false, map.testVacant());
		
		assertEquals(toPageBase(va), map.offset());
		
		// check side effect
	}
	@Test
	public void readMapB() {
		int va = (memory.raSize) + 100;
		
		// prepare
		// execute
		var map = memory.readMap(toPageNumber(va));
		
		// check result
		assertEquals(true, map.testVacant());
		assertEquals(0, map.offset());
		
		// check side effect
	}
	@Test
	public void writeMap() {
//		logger.info(StackUtil.getCallerMethodName());
		
		int   vp       = 1000;
		short mapFlags = 0xFF;
		int   rp       = 2000;
		// prepare
		// execute
		memory.writeMap(vp, mapFlags, rp);
		
		// check result
		var map = memory.readMap(vp);
		assertEquals(mapFlags, map.flag());
		assertEquals(toPageOffset(rp), map.offset());
		
		// check side effect
	}
	@Test
	public void fetch() {
//		logger.info(StackUtil.getCallerMethodName());

		int va = 0x0020_0123;
		
		// prepare
		// execute
		int ra = memory.fetch(va);
		
		// check result
		assertEquals(va, ra);
		
		// check side effect
		Map map = memory.readMap(toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(true,  map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void store() {
//		logger.info(StackUtil.getCallerMethodName());

		int va = 0x0020_0123;
		
		// prepare
		// execute
		int ra = memory.store(va);
		
		// check result
		assertEquals(va, ra);
		
		// check side effect
		Map map = memory.readMap(toPageNumber(va));
		assertEquals(true,  map.testDirty());
		assertEquals(true,  map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void peek() {
//		logger.info(StackUtil.getCallerMethodName());

		int va = 0x0020_0123;
		
		// prepare
		// execute
		int ra = memory.peek(va);
		
		// check result
		assertEquals(va, ra);
		
		// check side effect
		Map map = memory.readMap(toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(false, map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void rawRead16() {
//		logger.info(StackUtil.getCallerMethodName());

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
		Map map = memory.readMap(toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(false, map.testReferenced());
		assertEquals(false, map.testProtect());
	}
	@Test
	public void rawWrite16() {
//		logger.info(StackUtil.getCallerMethodName());

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
		Map map = memory.readMap(toPageNumber(va));
		assertEquals(false, map.testDirty());
		assertEquals(false, map.testReferenced());
		assertEquals(false, map.testProtect());
	}
}
