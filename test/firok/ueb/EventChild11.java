package firok.ueb;

import firok.ueb.event.Event;

public class EventChild11 extends Event
{
	String value;
	public EventChild11(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
