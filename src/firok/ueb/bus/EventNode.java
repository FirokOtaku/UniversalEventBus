package firok.ueb.bus;

import firok.ueb.event.Event;
import firok.ueb.listener.Listener;

import java.util.*;

/**
 * 事件树节点
 */
@SuppressWarnings({"RawUseOfParameterizedType","ResultOfMethodCallIgnored","unchecked"})
public class EventNode<TypeEvent extends Event<?>>
{
	/**
	 * 父级事件树节点
	 */
	EventNode<?> parent;

	/**
	 * 当前节点所代表的事件类型
	 */
	Class<TypeEvent> typeSelf;

	/**
	 * @param parent 父节点
	 * @param typeSelf 当前节点事件类型
	 */
	EventNode(EventNode<?> parent,Class<TypeEvent> typeSelf)
	{
		this.parent=parent;
		this.typeSelf=typeSelf;
		this.listeners=new ArrayList<>();
		this.childrenNodes=new HashMap<>();
	}

	/**
	 * 当前节点所有监听器列表
	 */
	List<Listener<? extends Event<?>,?>> listeners;

	/**
	 * 当前事件所有子事件节点
	 */
	Map<Class<? extends TypeEvent>,EventNode<? extends TypeEvent>> childrenNodes;

	/**
	 * 向当前节点注册一个子类事件节点
	 * @param typeChild 子事件类型
	 * @return 子事件节点
	 */
	EventNode<? extends TypeEvent> registerChild(Class<TypeEvent> typeChild)
	{
		EventNode<? extends TypeEvent> nodeChild=new EventNode<>(this,typeChild);
		this.childrenNodes.put(typeChild,nodeChild);
		return nodeChild;
	}

	/**
	 * 向当前事件注册一个事件监听器
	 * @param listener 监听器
	 * @return 当前事件节点
	 */
	EventNode<TypeEvent> registerListener(Listener<?,?> listener)
	{
		this.listeners.add(listener);
		return this;
	}

	/**
	 * 重新排列监听器
	 */
	void sort()
	{
		Collections.sort((List)this.listeners);
	}
}
