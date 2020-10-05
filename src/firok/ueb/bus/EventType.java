package firok.ueb.bus;

import firok.ueb.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件类型注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EventType
{
	/**
	 * 父类事件类型
	 */
	Class<? extends Event> parent() default Event.class;
}
