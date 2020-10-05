package firok.ueb.example;

import firok.ueb.event.Event;

public class EventChild111 extends Event<Object>
{
	public EventChild111(Object o)
	{
		super(o);
		this.setCanCancel(false);
	}
}
