package firok.ueb.bus;

import firok.ueb.event.Event;
import firok.ueb.exception.DuplicatedChildException;
import firok.ueb.exception.NodeNotFoundException;
import firok.ueb.listener.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BusBuilder
{
	public static BusBuilder create()
	{
		return new BusBuilder();
	}
	public Bus build()
	{
		for(Map.Entry<Class<? extends Event>,EventNode<? extends Event>> entry:this.nodes.entrySet())
		{
			Collections.sort(entry.getValue().listeners);
		}
		bus.nodes= Collections.unmodifiableMap(this.nodes);
		return bus;
	}

	private final Bus bus;
	private final Map<Class<? extends Event>,EventNode<? extends Event>> nodes;
	private BusBuilder()
	{
		this.bus=new Bus();
		this.nodes=new HashMap<>();
		this.nodes.put(bus.root.typeSelf,bus.root);
	}

	public void registerEventType(Class<? extends Event> typeParent, Class<? extends Event> typeChild)
			throws NodeNotFoundException,DuplicatedChildException
	{
		EventNode<? extends Event> nodeParent = nodes.get(typeParent); // 检查父类节点是否已经注册
		if(nodeParent==null) throw new NodeNotFoundException(typeParent); // 如果父类节点还没有注册, 则抛出异常
		EventNode<? extends Event> nodeChild = nodes.get(typeChild); // 检查子类节点是否已经注册
		if(nodeChild!=null) throw new DuplicatedChildException(typeChild); // 如果已经注册, 则抛出异常

		nodeChild = nodeParent.registerChild((Class)typeChild);

		this.nodes.put(typeChild,nodeChild);
	}

	public void registerEventListener(Class<? extends Event> type, Listener listener)
			throws NodeNotFoundException
	{
		EventNode<? extends Event> node = this.nodes.get(type);
		if(node==null) throw new NodeNotFoundException(type);
		node.registerListener(listener);
	}

	/**
	 * 对储存的数据进行优化
	 */
	public void optimize()
	{
		this.nodes.values().parallelStream().forEach(EventNode::optimize);
	}

	/**
	 * 根据给定的配置类, 直接构造一个事件总线
	 * @param configs 配置类
	 * @return 构建后的事件总线
	 * @throws NodeNotFoundException
	 * @throws DuplicatedChildException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings({"unchecked","RawUseOfParameterizedType"})
	public static Bus create(Class<?>... configs) throws NodeNotFoundException, DuplicatedChildException, IllegalAccessException
	{
		Objects.requireNonNull(configs);

		BusBuilder builder = BusBuilder.create();

		for(Class<?> config:configs)
		{
			Field[] fields=config.getDeclaredFields();
			for(Field field:fields)
			{
				if(!Modifier.isStatic(field.getModifiers())) continue; // 如果不是静态字段, 直接跳过
				EventType annoEventType = field.getAnnotation(EventType.class);
				ListenTo annoListenTo = field.getAnnotation(ListenTo.class);
				if(annoEventType==null && annoListenTo==null) continue; // 如果没有注解, 直接掉过

				Class<?> typeField = field.getType();
				if(annoEventType!=null && Event.class.isAssignableFrom(typeField)) // 注册事件类型
				{
					builder.registerEventType(annoEventType.parent(),(Class<? extends Event>)typeField);
				}
				else if(annoListenTo!=null && Listener.class.isAssignableFrom(typeField)) // 注册事件监听器
				{
					Listener listener = (Listener) field.get(null);
					for(Class<? extends Event> typeParent : annoListenTo.value())
					{
						builder.registerEventListener(typeParent, listener);
					}
				}

//				System.out.printf("%s isFromEvent %s  isEventFrom %s\n", typeField, typeField.isAssignableFrom(Event.class), Event.class.isAssignableFrom(typeField) );
			}
		}

		builder.optimize();

		return builder.build();
	}
}
