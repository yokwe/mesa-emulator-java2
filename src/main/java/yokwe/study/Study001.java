package yokwe.study;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class Study001 {
	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();

	public static void main(String[] args) {
		logger.info("START");
		
		MemorySegment segment = Arena.global().allocate(100, 1);
		logger.info("segment {}", segment);
		
		logger.info("STOP");
	}
}
