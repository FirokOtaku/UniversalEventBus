package firok.ueb;

import firok.ueb.event.Event;

public class EventBase1 extends Event
{
	String value;
	public EventBase1(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
