package firok.ueb.example;

import firok.ueb.bus.EventType;
import firok.ueb.bus.ListenTo;
import firok.ueb.event.Event;
import firok.ueb.exception.CancelException;
import firok.ueb.listener.Listener;

/**
 * 示例配置类
 */
public class ConfigExample
{
	@EventType
	public static EventBase1 eventBase1;

	@EventType
	public static EventBase2 eventBase2;

	@EventType(parent = EventBase1.class)
	public static EventChild11 eventChild11;

	@EventType(parent = EventChild11.class)
	public static EventChild111 eventChild111;

	@EventType(parent = EventChild111.class)
	public static EventChild1111 eventChild1111;

	@EventType(parent = EventBase1.class)
	public static EventChild12 eventChild12;

	@EventType(parent = EventBase2.class)
	public static EventChild21 eventChild21;

	@EventType(parent = EventBase2.class)
	public static EventChild22 eventChild22;

	static class ListenerLogger<TypeEvent extends Event<TypeSource>,TypeSource> implements Listener<TypeEvent,TypeSource>
	{
		final String name;
		final int priority;
		ListenerLogger(String name)
		{
			this(name,50);
		}
		ListenerLogger(String name,int priority)
		{
			this.name = name;
			this.priority = priority;
		}
		@Override
		public void handle(TypeEvent event, TypeSource source) throws CancelException
		{
			System.out.printf("listener [%s] triggered with [%s]\n",name,source);
		}

		@Override
		public int getPriority()
		{
			return priority;
		}
	}
	static class ListenerCanceler extends ListenerLogger<Event<Object>,Object>
	{
		ListenerCanceler(String name)
		{
			super(name);
		}

		ListenerCanceler(String name, int priority)
		{
			super(name, priority);
		}

		@Override
		public void handle(Event<Object> event, Object source) throws CancelException
		{
			System.out.printf("listener [%s] canceled [%s]\n",name,source);
			event.setCancel();
		}
	}
	static class Listener111 implements Listener<EventChild111,Object>
	{
		@Override
		public void handle(EventChild111 event, Object source) throws CancelException
		{
			System.out.printf("listener 111 triggered by [%s:%s]",event,source);
		}

		@Override
		public int getPriority()
		{
			return 0;
		}
	}

	@ListenTo
	public static Listener listenerBaseLogger = new ListenerLogger("Base");

	@ListenTo(EventBase1.class)
	public static Listener listenerBase1Logger = new ListenerLogger("Base1");

	@ListenTo(EventChild11.class)
	public static Listener listenerChild11 = new ListenerLogger("Child11");

	@ListenTo(EventChild111.class)
	public static Listener listenerChild111 = new ListenerCanceler("Child111");

	@ListenTo(EventChild1111.class)
	public static Listener listenerChild1111 = new ListenerLogger("Child1111");

	@ListenTo(EventChild12.class)
	public static Listener listenerChild12 = new ListenerLogger("Child12");

	@ListenTo(EventBase2.class)
	public static Listener listenerBase2Logger = new ListenerLogger("Base2");

	@ListenTo(EventChild21.class)
	public static Listener listenerChild21 = new ListenerLogger("Child21");

	@ListenTo(EventChild22.class)
	public static Listener listenerChild22 = new ListenerLogger("Child22");

	@ListenTo(EventChild111.class)
	public static Listener listener111 = new Listener111();
}
