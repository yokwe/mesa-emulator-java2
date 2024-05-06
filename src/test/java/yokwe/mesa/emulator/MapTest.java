package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MapTest extends Base {
//	private static final yokwe.util.FormatLogger logger = yokwe.util.FormatLogger.getLogger();

	@Test
	public void constructor() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		// prepare
		// execute
		var map = new Map();
		
		// check result
		assertEquals((short)0, map.flag());
		assertEquals(0, map.offset());
		
		// check side effect
	}

}
