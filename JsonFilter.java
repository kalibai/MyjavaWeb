package com.test.bll;

import net.sf.json.util.PropertyFilter;

public class JsonFilter implements PropertyFilter {
	private String[] _filterfield;
	public JsonFilter(String[] filterfield)
	{
		this._filterfield=filterfield;
	}
	@Override
	public boolean apply(Object arg0, String arg1, Object arg2) {
		// TODO Auto-generated method stub
		boolean result=true;
		if(this._filterfield!=null&&this._filterfield.length>0)
		{
			int i;
			for(i=0;i<this._filterfield.length;i++)
			{
				if(this._filterfield[i].equals(arg1))
				{
					result=false;
					break;
				}
			}
			if(i==this._filterfield.length)
			{
				result=true;
			}
			
		}
		return result;
	}
	
}
