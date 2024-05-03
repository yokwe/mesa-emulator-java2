package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import yokwe.util.StackUtil;

public class StackUtilTest extends Base {
//	private static final yokwe.util.FormatLogger logger = yokwe.util.FormatLogger.getLogger();
	
	@Test
	public void getCallerMethodName() {
//		logger.info("%s", yokwe.util.StackUtil.getCallerMethodName());
		
		// prepare
		// execute
		var actual = StackUtil.getCallerMethodName();
		
		// check result
		assertEquals("getCallerMethodName", actual);
		
		// check side effect
	}
	@Test
	public void getCallerClass() {
//		logger.info("%s", yokwe.util.StackUtil.getCallerMethodName());
		
		// prepare
		// execute
		var actual = StackUtil.getCallerClass();
		
		// check result
		assertEquals(this.getClass(), actual);
		
		// check side effect
	}

}
