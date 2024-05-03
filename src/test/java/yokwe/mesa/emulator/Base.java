package yokwe.mesa.emulator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class Base {
	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();
	
	private static final int VM_BITS = 24;
	private static final int RM_BITS = 22;
	private static final int IO_REGION_PAGE = 0x80;
	
	
	protected static Memory memory;
	
	@BeforeAll
	protected static void beforeAll() {
		logger.info("beforeAll");
		memory = new Memory(VM_BITS, RM_BITS, IO_REGION_PAGE);
	}
	
	@AfterAll
	protected static void afterAll() {
		logger.info("afterAll");
		System.gc();
	}
	
	@BeforeEach
	protected void beforeEach() {
		logger.info("beforeEach");
		memoryInit();
	}
	
	@AfterEach
	protected void afterEach() {
		logger.info("afterEach");
		System.gc();
	}
	
	
	private static void memoryInit() {
		memory.init();
	}
}
