package com.zhan.framework.support.adapter;

public interface FragmentPagerChangeListener {

	public void instantiate(String fragmentName);
	
	public void destroy(String fragmentName);
	
}
