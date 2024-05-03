package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import yokwe.util.StackUtil;

import static yokwe.mesa.emulator.Type.*;

public class MemoryTest extends Base {
	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();

	@Test
	public void fetch() {
		logger.info(StackUtil.getCallerMethodName());

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
		logger.info(StackUtil.getCallerMethodName());

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
		logger.info(StackUtil.getCallerMethodName());

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
}
