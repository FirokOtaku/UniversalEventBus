# Universal Event Bus

> 此文档适用于`[0.1.0,)`版本的**UEB**

> 阅读文档时请注意区分"**个**"和"**种**"

**Universal Event Bus**仿照Minecraft Event Bus编写, 目标是提供一套简易的事件总线系统.

> 请注意, UEB的节点树和建造器并不是线程安全的
> 且节点树将会在总线建造完成后锁定为只读状态

## 约定

* 触发子类事件会触发父类事件, 并且节点的调用顺序是由父类到子类
* 当某个可取消事件实例被某个事件监听器取消时, 不会再调用后续事件监听器

## 使用方式

### 声明新的事件类型

若需要创建一种新的事件类型, 只需要声明一个`firok.ueb.event.Event`的子类, 并且在后续步骤中注册至某个总线.

在构造方法内调用`firok.ueb.event.Event#setCanCancel`即可调整某个事件类型的实例是否可以被取消.

### 创建事件监听器

若需要在某个总线上监听某种事件, 您需要创建一个实现了`firok.ueb.listener.Listener`接口的实例对象, 并且在后续步骤中注册至某个总线.

由于一个事件监听器实例可以监听多种事件类型, 或者某种事件含有子类型事件, 所以您需要明确每个监听器的泛型参数类型来明确监听器示例可处理的事件类型, 否则在触发事件时可能遇到`java.lang.ClassCastException`.

### 创建总线

您可以调用总线建造器手动创建一个事件总线, 或是使用总线工厂读取配置类来创建总线.

#### 向总线注册事件类型

相关方法为**firok.ueb.bus.BusBuilder#registerEventType**

注册事件时, 您需要指定父类事件类型. Java类继承关系并不影响注册过程. 如, 现有`EventA1 extends EventA`和`EventA2 extends EventA`, 但是您可以将`EventA1`注册为`EventA2`的子事件类型.

但是, 同一种事件类型在一个总线只能注册一次, 您不能将一个事件类型注册为多个事件的子类事件.

#### 向总线注册事件监听器

相关方法为**firok.ueb.bus.BusBuilder#registerEventListener**

事件监听器列表会在总线创建结束时排序, 其顺序取决于`firok.ueb.listener.Listener#getPriority`的返回值.

### 自动创建

相关方法为`firok.ueb.bus.BusFactory#create`

您可以提供一个配置类, 使用工厂扫描并自动创建总线.

配置类中, 您需要将相关字段写为**公有静态字段**.

### 使用示例

**UEB**详细的用例请查看`firok.ueb.TestExample#main`

正常运行后将会得到如下日志

```log
listener [Child11] triggered with [EventChild11:source11]
listener [Base1] triggered with [EventChild11:source11]
listener [Base] triggered with [EventChild11:source11]
listener [Child12] triggered with [EventChild12:source12]
listener [Base1] triggered with [EventChild12:source12]
listener [Base] triggered with [EventChild12:source12]
listener [Child21] triggered with [EventChild21:source21]
listener [Base2] triggered with [EventChild21:source21]
listener [Base] triggered with [EventChild21:source21]
listener [Child22] triggered with [EventChild22:source22]
listener [Base2] triggered with [EventChild22:source22]
listener [Base] triggered with [EventChild22:source22]
listener [Child1111] triggered with [EventChild1111:source1111]
listener 111 triggered by [EventChild1111:source1111]
listener [Child111] canceled [EventChild1111:source1111]
listener 111 triggered by [EventChild111:source111]
listener [Child111] canceled [EventChild111:source111]

firok.ueb.exception.CancelException: Trying to cancel a uncanceled event [EventChild111:source111]


	at firok.ueb.event.Event.setCancel(Event.java:43)
	at firok.ueb.ConfigExample$ListenerCanceler.handle(ConfigExample.java:80)
	at firok.ueb.bus.EventNode.trigger(EventNode.java:86)
	at firok.ueb.bus.Bus.trigger(Bus.java:42)
	at firok.ueb.TestExample.test(TestExample.java:18)
```
