package firok.ueb;

import firok.ueb.event.Event;

public class EventBase2 extends Event
{
	String value;
	public EventBase2(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
