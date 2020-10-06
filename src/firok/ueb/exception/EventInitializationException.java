package firok.ueb.exception;

import firok.ueb.event.Event;

/**
 * 异常 - 事件示例初始化失败
 */
public class EventInitializationException extends RuntimeException
{
	public EventInitializationException(Class<? extends Event> type)
	{
		super("Unable to instantiate event: "+type.getCanonicalName());
	}
}
