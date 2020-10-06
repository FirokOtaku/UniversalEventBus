package firok.ueb.bus;

import firok.ueb.event.Event;
import firok.ueb.exception.DuplicatedChildException;
import firok.ueb.exception.NodeNotFoundException;
import firok.ueb.listener.Listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusBuilder
{
	public static BusBuilder create()
	{
		return new BusBuilder();
	}
	public Bus build()
	{
		for(Map.Entry<Class<? extends Event<?>>,EventNode<? extends Event<?>>> entry:this.nodes.entrySet())
		{
			Collections.sort((List)entry.getValue().listeners);
		}
		bus.nodes=this.nodes;
		return bus;
	}

	private final Bus bus;
	private final Map<Class<? extends Event<?>>,EventNode<? extends Event<?>>> nodes;
	private BusBuilder()
	{
		this.bus=new Bus();
		this.nodes=new HashMap<>();
		this.nodes.put(bus.root.typeSelf,bus.root);
	}

	public void registerEventType(Class<? extends Event<?>> typeParent, Class<? extends Event<?>> typeChild)
			throws NodeNotFoundException,DuplicatedChildException
	{
		EventNode<? extends Event<?>> nodeParent = nodes.get(typeParent); // 检查父类节点是否已经注册
		if(nodeParent==null) throw new NodeNotFoundException(typeParent); // 如果父类节点还没有注册, 则抛出异常
		EventNode<? extends Event<?>> nodeChild = nodes.get(typeChild); // 检查子类节点是否已经注册
		if(nodeChild!=null) throw new DuplicatedChildException(typeChild); // 如果已经注册, 则抛出异常

		nodeChild = nodeParent.registerChild((Class)typeChild);

		this.nodes.put(typeChild,nodeChild);
	}

	public void registerEventListener(Class<? extends Event> type, Listener listener)
			throws NodeNotFoundException
	{
		EventNode<? extends Event<?>> node = this.nodes.get(type);
		if(node==null) throw new NodeNotFoundException(type);
		node.registerListener(listener);
	}
}
