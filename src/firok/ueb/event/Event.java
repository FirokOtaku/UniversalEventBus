package firok.ueb.event;

import firok.ueb.exception.CancelException;

/**
 * 事件实例基类
 * @param <TypeSource> 事件源类型
 */
public abstract class Event
{
	protected Event() { }

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
		if(this.isCanCancel())
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
