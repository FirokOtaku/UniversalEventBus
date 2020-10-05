package firok.ueb.bus;

import firok.ueb.event.Event;
import firok.ueb.exception.DuplicatedChildException;
import firok.ueb.exception.NodeNotFoundException;
import firok.ueb.listener.Listener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class BusFactory
{
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

		return builder.build();
	}
}
