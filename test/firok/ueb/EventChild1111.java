package firok.ueb;

import firok.ueb.event.Event;

public class EventChild1111 extends Event
{
	String value;
	public EventChild1111(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
