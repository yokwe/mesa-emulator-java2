package yokwe.mesa.emulator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TypeTest extends Base {
//	private static final yokwe.util.FormatLogger logger = yokwe.util.FormatLogger.getLogger();
	
	@Test
	public void toInt_A() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		short value    = 0x1234;
		int   expected = 0x1234;
		// prepare
		// execute
		var actual = Type.toInt(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toInt_B() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		short value    = (short)0xFFFF;
		int   expected = 0xFFFF;
		// prepare
		// execute
		var actual = Type.toInt(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void highHalf_A() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int   value    = 0x12345678;
		short expected = 0x1234;
		// prepare
		// execute
		var actual = Type.highHalf(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void highHalf_B() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int   value    = 0xFEDCBA98;
		short expected = (short)0xFEDC;
		// prepare
		// execute
		var actual = Type.highHalf(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void lowHalf_A() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int   value    = 0x12345678;
		short expected = 0x5678;
		// prepare
		// execute
		var actual = Type.lowHalf(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void lowHalf_B() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int   value    = 0xFEDCBA98;
		short expected = (short)0xBA98;
		// prepare
		// execute
		var actual = Type.lowHalf(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toInt32_A() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		short high     = 0x1234;
		short low      = 0x5678;
		int   expected = 0x12345678;
		// prepare
		// execute
		var actual = Type.toInt(high, low);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toInt32_B() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		short high     = (short)0xFEDC;
		short low      = (short)0xBA98;
		int   expected = 0xFEDCBA98;
		// prepare
		// execute
		var actual = Type.toInt(high, low);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toPageAddress() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int value    = 0xFEDC;
		int expected = 0xFEDC00;
		// prepare
		// execute
		var actual = Type.toPageAddress(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toPageOffset() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int value    = 0xFEDCBA98;
		int expected = 0x98;
		// prepare
		// execute
		var actual = Type.toPageOffset(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toPageBase() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int value    = 0xFEDCBA98;
		int expected = 0xFEDCBA00;
		// prepare
		// execute
		var actual = Type.toPageBase(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void toPageNumber() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int value    = 0xFEDCBA98;
		int expected = 0xFEDCBA;
		// prepare
		// execute
		var actual = Type.toPageNumber(value);
		
		// check result
		assertEquals(expected, actual);
		
		// check side effect
	}
	@Test
	public void isSamePage_A() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int valueA   = 0xFEDCBA98;
		int valueB   = 0xFEDCBA00;
		// prepare
		// execute
		var actual = Type.isSamePage(valueA, valueB);
		
		// check result
		assertEquals(true, actual);
		
		// check side effect
	}
	@Test
	public void isSamePage_B() {
//		logger.info(yokwe.util.StackUtil.getCallerMethodName());
		
		int valueA   = 0xFEDCBA98;
		int valueB   = 0xFEDCBB00;
		// prepare
		// execute
		var actual = Type.isSamePage(valueA, valueB);
		
		// check result
		assertEquals(false, actual);
		
		// check side effect
	}

}
