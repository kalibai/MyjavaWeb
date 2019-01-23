package com.test.bll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 获取链接并遍历出来
 * 
 * **/
public class Get_PC_URL {
	private final static String BASEURL = "http://news.baidu.com/guonei";//站源
	private List<Article> dataurl=new ArrayList<Article>();
	int count=8;  //加入一个计数器
	String URL[]=new String[count];
	
	public String[] GetURL() throws IOException{
			//Connection连接
			Connection conn = Jsoup.connect(BASEURL).maxBodySize(0).timeout(3000);//忽视内容检测ignoreContentType(true)
			//设置头信息
			conn.header("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
			Response rs = conn.ignoreContentType(true).ignoreHttpErrors(true).method(Method.POST).execute();
			Document doc = Jsoup.parse(rs.body());
			Elements contents = doc.select("div#internal-aside-video");
			//查找出自己需要的内容
			for(Element el:contents){//Element元素  contents内容,只有一个div#internal-aside-video所以执行一次
				for(int x=0;x<count;x++){//
				Article temp=new Article();
				temp.setTitle(el.select("a").eq(x).attr("abs:href"));//链接
				temp.setDescription(el.select("a.title").eq(x).text());//内容
				dataurl.add(temp);
				}
			}
			//输出爬来的数据
			for(int i=0;i<dataurl.size();i++){
				//System.out.println("第"+(i+1)+"篇--路径:"+dataurl.get(i).getTitle());
				//System.out.println(dataurl.get(i).getDescription());
				//System.out.println("================================================");
				URL[i]=dataurl.get(i).getTitle();
			}
			return URL;
	}
}
