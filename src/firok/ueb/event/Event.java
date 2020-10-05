package firok.ueb.event;

import firok.ueb.exception.CancelException;

/**
 * 事件实例基类
 * @param <TypeSource> 事件源类型
 */
public abstract class Event<TypeSource>
{
	/**
	 * 事件源
	 */
	private final TypeSource source;

	/**
	 * 创建一个事件实例
	 * @param source 事件源
	 * @apiNote 所有子类事件必须包含一个相同签名的构造方法
	 */
	public Event(TypeSource source)
	{
		this.source=source;
	}

	/**
	 * 获取事件源
	 * @return 事件源
	 */
	public TypeSource getSource()
	{
		return source;
	}


	private boolean canCancel = true;
	/**
	 * 设定当前事件是否可以被取消
	 */
	protected void setCanCancel(boolean value)
	{
		this.canCancel = value;
	}

	/**
	 * @return 当前事件是否可以被取消
	 */
	public boolean isCanCancel()
	{
		return this.canCancel;
	}

	private boolean canceled = false;
	/**
	 * 取消当前事件示例
	 * @throws CancelException 当当前事件无法被取消时抛出
	 */
	public void setCancel() throws CancelException
	{
		if(this.canCancel)
		{
			this.canceled = true;
		}
		else
		{
			throw new CancelException(this);
		}
	}

	/**
	 * @return 当前事件示例是否可以被取消
	 */
	public boolean isCanceled()
	{
		return this.canceled;
	}
}
