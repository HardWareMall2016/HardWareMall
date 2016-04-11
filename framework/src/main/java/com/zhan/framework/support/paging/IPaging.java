package com.zhan.framework.support.paging;

import java.io.Serializable;

public interface IPaging<T> extends Serializable {

	/**
	 * 重设参数
	 * 
	 * @return
	 */
    public IPaging<T> newInstance();

	/**
	 * 处理数据
	 * @param firstData
	 *            adapter数据集中的第一条数据
	 * @param lastData
	 *            adapter数据集中的最后一条数据
	 */
    public void processData(T firstData, T lastData);

    public String getPreviousPage();

    public String getNextPage();

    public void setPage(String previousPage, String nextPage);

	/**
	 * 是否还能刷新最新
	 * 
	 * @return
	 */
    public boolean canRefresh();

	/**
	 * 是否还能拉取更多
	 * 
	 * @return
	 */
    public boolean canUpdate();

}
