package firok.ueb;

import firok.ueb.event.Event;

public class EventChild12 extends Event
{
	String value;
	public EventChild12(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
