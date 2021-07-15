package firok.ueb;

import firok.ueb.event.Event;

public class EventChild111 extends Event
{
	String value;
	public EventChild111(String value)
	{
		this.value = value;
		this.setCanCancel(false);
	}
	public String toString() { return this.getClass().getSimpleName() + ":" + value; }
}
