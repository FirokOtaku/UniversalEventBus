package firok.ueb.example;

import firok.ueb.bus.Bus;
import firok.ueb.bus.BusFactory;

public class TestExample
{
	public static void main(String[] args) throws Exception
	{
		Bus bus = BusFactory.create(ConfigExample.class);
		bus.trigger(EventChild11.class,"source11");
		bus.trigger(EventChild12.class,"source12");
		bus.trigger(EventChild21.class,"source21");
		bus.trigger(EventChild22.class,"source22");
		bus.trigger(EventChild1111.class,"source1111");
	}
}
