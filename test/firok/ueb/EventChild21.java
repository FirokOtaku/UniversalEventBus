package firok.ueb;

import firok.ueb.event.Event;

public class EventChild21 extends Event
{
	String value;
	public EventChild21(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
