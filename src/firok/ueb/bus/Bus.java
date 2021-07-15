package firok.ueb.bus;

import firok.ueb.event.Event;
import firok.ueb.exception.CancelException;
import firok.ueb.exception.EventInitializationException;
import firok.ueb.exception.NodeNotFoundException;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Optional;

public class Bus
{
	/**
	 * 总线根节点
	 */
	final EventNode<? extends Event> root;

	/**
	 * 所有的节点
	 */
	Map<Class<? extends Event>,EventNode<?>> nodes;

	/**
	 * 构造一个空事件总线
	 */
	Bus() { this.root=new EventNode<>(null,Event.class); }

	/**
	 * 根据事件类型自动创建事件实例并触发监听器
	 * @param type 事件类型
	 * @return 事件是否被取消
	 */
	@SuppressWarnings({"unchecked","ResultOfMethodCallIgnored","RawUseOfParameterizedType"})
	public <TypeEvent extends Event> boolean trigger(Class<TypeEvent> type,Object... params)
			throws NodeNotFoundException, EventInitializationException, CancelException
	{
		EventNode<?> node = Optional.ofNullable(nodes.get(type)).orElseThrow(()->new NodeNotFoundException(type));
		Event event = createEvent(node.typeSelf,params);
		return node.trigger(event);
	}

	/**
	 * 创建一个事件实例<br>
	 * 此方法仅会创建事件实例, 不会触发监听器
	 * @param type 事件类型
	 * @param params 创建事件实例所需的形参
	 * @return 事件实例
	 * @throws EventInitializationException 构造事件实例错误
	 */
	public static <TypeEvent extends Event> Event createEvent(Class<TypeEvent> type,Object... params)
	{
		Class<?>[] _params = new Class[ params != null ? params.length : 0 ];
		for(int step = 0; step < _params.length; step++)
		{
			_params[step] = params[step] != null ? params[step].getClass() : Object.class;
		}

		try
		{
			Constructor<TypeEvent> cons = type.getConstructor(_params);
			return cons.newInstance(params);
		}
		catch (Exception e)
		{
			throw new EventInitializationException(type);
		}
	}
}
