package com.test.bll;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 序列化工具类JSON
 * */
public class JsonUtility {
	public static JSONArray resultSet2JSONArray(ResultSet rs) throws SQLException{
		JSONArray result = new JSONArray();
		
		//getMetaData()--->������ ResultSet������е����������ͺ����ԡ�
		ResultSetMetaData metaData = rs.getMetaData();
		
		//getColumnCount()--->���ش� ResultSet�����е�����
		int columnCount = metaData.getColumnCount();
		while(rs.next()){
			JSONObject object = new JSONObject();
			//ͨ���������������ResultSet
			for (int i = 0; i < columnCount; i++) {
				//getColumnLabel()--->��ȡָ���еĽ���������ڴ�ӡ�������ʾ��
				String columnName = metaData.getColumnLabel(i);
				//ͨ��columnName�����ĵ�ǰ����ָ���е�ֵ
				String value = rs.getString(columnName);
				//�ü�ֵ�Եķ�ʽ�����ŵ�JSON������
				object.put(columnName, value);
			}
			//������ŵ�JSONArray������
			result.add(object);
		}
		return result;
	}
	public static JSONArray filterList(String[] configs,Object source)
	{
		JsonFilter filter=new JsonFilter(configs);
		JsonConfig config=new JsonConfig();
		config.setJsonPropertyFilter(filter);
	
		return JSONArray.fromObject(source,config);	
	}
}
