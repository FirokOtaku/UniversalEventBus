package firok.ueb;

import firok.ueb.bus.Bus;
import firok.ueb.bus.BusBuilder;
import org.junit.jupiter.api.Test;

public class TestExample
{
	@Test
	public void test() throws Exception
	{
		Bus bus = BusBuilder.create(ConfigExample.class);
		bus.trigger(EventChild11.class,"source11");
		bus.trigger(EventChild12.class,"source12");
		bus.trigger(EventChild21.class,"source21");
		bus.trigger(EventChild22.class,"source22");
		bus.trigger(EventChild1111.class,"source1111");
		bus.trigger(EventChild111.class,"source111");
	}
}
