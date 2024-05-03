package yokwe.util;

public final class LoggerUtil {
	public static org.slf4j.Logger getLogger() {
//		Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
		Class<?> callerClass = StackUtil.getCallerStackFrame(StackUtil.OFFSET_CALLER).getDeclaringClass();
		
		return org.slf4j.LoggerFactory.getLogger(callerClass);
	}
}
