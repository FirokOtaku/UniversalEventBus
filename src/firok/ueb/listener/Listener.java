package firok.ueb.listener;

import firok.ueb.event.Event;
import firok.ueb.exception.CancelException;

/**
 * 监听器基类
 */
public interface Listener<TypeEvent extends Event<TypeSource>,TypeSource> extends Comparable<Listener<TypeEvent,TypeSource>>
{
	/**
	 * 对事件实例做出反应
	 * @param event 事件实例
	 */
	void handle(TypeEvent event,TypeSource source) throws CancelException;

	int getPriority();

	@Override
	default int compareTo(Listener<TypeEvent,TypeSource> o)
	{
		return Integer.compare(this.getPriority(),o.getPriority());
	}
}
