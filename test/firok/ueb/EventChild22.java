package firok.ueb;

import firok.ueb.event.Event;

public class EventChild22 extends Event
{
	String value;
	public EventChild22(String value)
	{
		this.value = value;
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
