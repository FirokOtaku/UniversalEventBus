package firok.ueb.exception;

import firok.ueb.event.Event;

/**
 * 异常 - 未找到事件节点<br>
 */
public class NodeNotFoundException extends Exception
{
	public NodeNotFoundException(Class<? extends Event> type)
	{
		super("Event node not found: "+type.getCanonicalName());
	}
}
