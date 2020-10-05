package firok.ueb.exception;

import firok.ueb.event.Event;

/**
 * 异常 - 重复的子节点类型
 */
public class DuplicatedChildException extends Exception
{
	public DuplicatedChildException(Class<? extends Event> type)
	{
		super("Duplicated event node: "+type.getCanonicalName());
	}
}
