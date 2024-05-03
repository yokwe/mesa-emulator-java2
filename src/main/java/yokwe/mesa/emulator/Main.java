package yokwe.mesa.emulator;

public class Main {
	private static final org.slf4j.Logger logger = yokwe.util.LoggerUtil.getLogger();

	public static void main(String[] args) {
		logger.info("START");
		
		int vmBits   = 24;
		int rmBits   = 22;
		int ioRegion = 0x80;
		
		var memory = new Memory(vmBits, rmBits, ioRegion);
		logger.info("memory  {}", memory);
		
		logger.info("STOP");
	}
}
