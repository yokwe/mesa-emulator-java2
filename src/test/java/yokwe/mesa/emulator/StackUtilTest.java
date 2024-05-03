package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import yokwe.util.StackUtil;

public class StackUtilTest extends Base {
//	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();
	
	@Test
	public void getCallerMethodName() {
//		logger.info(StackUtil.getCallerMethodName());

		// prepare
		// execute
		var actual = StackUtil.getCallerMethodName();
		
		// check result
		assertEquals("getCallerMethodName", actual);
		
		// check side effect
	}
	@Test
	public void getCallerClass() {
//		logger.info(StackUtil.getCallerMethodName());

		// prepare
		// execute
		var actual = StackUtil.getCallerClass();
		
		// check result
		assertEquals(this.getClass(), actual);
		
		// check side effect
	}

}
