package firok.ueb.exception;

import firok.ueb.event.Event;

/**
 * 异常 - 错误的取消<br>
 * 当尝试取消一个不可取消事件时抛出
 */
public class CancelException extends RuntimeException
{
	public CancelException(Event event)
	{
		super(String.format("Trying to cancel a uncanceled event [%s]\n",event));
	}
}
