package firok.ueb.bus;

import firok.ueb.event.Event;
import firok.ueb.exception.CancelException;
import firok.ueb.exception.EventInitializationException;
import firok.ueb.exception.NodeNotFoundException;
import firok.ueb.listener.Listener;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Stack;

public class Bus
{
	/**
	 * 总线根节点
	 */
	final EventNode<? extends Event> root;

	/**
	 * 所有的节点
	 */
	Map<Class<? extends Event<?>>,EventNode<?>> nodes;

	public Bus()
	{
		this.root=new EventNode<>(null,Event.class);
	}

//	/**
//	 * @return 事件是否被取消
//	 */
//	public boolean trigger(Object source)
//	{
//		return false;
//	}

	/**
	 * 根据事件类型自动创建事件实例并触发监听器
	 * @param type 事件类型
	 * @return 事件是否被取消
	 */
	@SuppressWarnings({"unchecked","ResultOfMethodCallIgnored","RawUseOfParameterizedType"})
	public <TypeEvent extends Event<TypeSource>,TypeSource> boolean trigger(Class<TypeEvent> type,TypeSource source)
			throws NodeNotFoundException, EventInitializationException, CancelException
	{
		EventNode<?> nodeCurrent = nodes.get(type);
		if(nodeCurrent == null) throw new NodeNotFoundException(type);

		Event<TypeSource> event = createEvent(type,source);

		Stack<EventNode<?>>  link = new Stack<>();
		link.push(nodeCurrent);

		int sizeListeners = 0;

		while(nodeCurrent.parent!=null) // 将节点链入栈
		{
			nodeCurrent=nodeCurrent.parent;
			link.push(nodeCurrent);
			sizeListeners += nodeCurrent.listeners.size();
		}

		while(sizeListeners>0 && !link.empty()) // 遍历节点链和监听器列表
		{
			EventNode<?> node = link.pop();
			for(Object objListener : node.listeners)
			{
				Listener<Event<TypeSource>,TypeSource> listener = (Listener) objListener;

				listener.handle(event,source);

				if(event.isCanceled()) return true;

				sizeListeners --;
			}
		}

		return false;
	}


	/**
	 * 创建一个事件实例<br>
	 * 此方法仅会创建事件实例, 不会触发监听器
	 * @param type 事件类型
	 * @param source 事件源
	 * @param <TypeSource> 事件源类型
	 * @return 事件实例
	 */
	public static <TypeEvent extends Event<TypeSource>,TypeSource> Event<TypeSource> createEvent(Class<TypeEvent> type,TypeSource source)
	{
		try
		{
			Constructor<TypeEvent> cons = type.getConstructor(Object.class);
			return cons.newInstance(source);
		}
		catch (Exception e)
		{
			throw new EventInitializationException(type);
		}
	}
}
