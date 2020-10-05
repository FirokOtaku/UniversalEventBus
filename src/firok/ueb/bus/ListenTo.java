package firok.ueb.bus;

import firok.ueb.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ListenTo
{
	Class<? extends Event>[] value() default { Event.class };
}
