package firok.ueb.listener;

import firok.ueb.event.Event;
import firok.ueb.exception.CancelException;

/**
 * 监听器基类
 */
public interface Listener extends Comparable<Listener>
{
	/**
	 * 对事件实例做出反应
	 * @param event 事件实例
	 */
	void handle(Event event) throws CancelException;

	int getPriority();

	@Override
	default int compareTo(Listener o)
	{
		return Integer.compare(this.getPriority(),o.getPriority());
	}
}
