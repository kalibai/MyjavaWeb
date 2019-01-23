package com.test.bll;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PC_go extends HttpHandlerContext{//extends HttpHandlerContext
	private final static String BASEURL = "http://finance.ifeng.com/a/20180730/16413638_0.shtml";
	private static int page=1;
	private List<Newss> datas=new ArrayList<Newss>();
	Get_PC_URL f=new Get_PC_URL();
	String Res="";//保存编码
	public  PC_go(HttpServletRequest request, HttpServletResponse response) throws IOException{
		super(request, response);
	}
	public void PCT_2() throws IOException{
		System.out.println("启动爬虫.....");
		String html="";//初始化json值
		int d=1;//计数器
		for(String a:f.GetURL()){//获取链接并遍历赋值
			System.out.println("第"+d+"个，获取的url："+a);
			//Connection连接
			Connection conn = Jsoup.connect(a).maxBodySize(0).timeout(6000);//忽视内容检测ignoreContentType(true)
			//伪装成浏览器抓取
			conn.header("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
			Response rs = conn.ignoreHttpErrors(true).ignoreContentType(true).method(Method.GET).execute();
			rs.body();
			Document doc = Jsoup.parse(rs.body());//解析页面  查询编码
			//获取页面编码并且修改
			String eles = doc.select("meta[charset]").toString();//页面类型1
			String eles2 = doc.select("meta[content]").toString();//页面类型2
			if(!eles.equals(""))//如果页面类型1获取的编码为空
			{
				String ss=eles.substring(eles.indexOf("charset="));
				Res=ss.substring(9, ss.indexOf("\" />"));
			}
			else 
			{
				if(eles2.indexOf("charset")>0){				
				String ss=eles2.substring(eles2.indexOf("charset="));
				Res=ss.substring(8, ss.indexOf("\" />"));
				}
			}
			//设置页面编码为对应值
			Document docs = Jsoup.parse(new URL(a).openStream(),Res,a);
			Elements contents = docs.select("body");
				for(Element el:contents){//Element元素  contents内容
					Newss temp=new Newss();
					temp.setJpgs(el.select("body > img").attr("abs:src"));
					if(a.indexOf("ifeng.com")>0){//1凤凰网
						temp.setBt(el.select("h1#artical_topic").text());//前标题
						temp.setLy(el.select("#artical_sth > p > span:nth-child(4) > span > a").text());//来源
						String bj=el.select("#main_content > span").text(); 
						if(bj.contains("作者")){
							bj=bj.substring(bj.indexOf("作者"));
						}else if(bj.contains("编辑")){
							bj=bj.substring(bj.indexOf("编辑"));
						}else if(el.select("#artical_sth2 > p.iphone_none").text().contains("编辑")){
							bj=el.select("#artical_sth2 > p.iphone_none").text().substring(el.select("#artical_sth2 > p.iphone_none").text().indexOf("编辑"));
						}
						temp.setHf(bj);//作者
						temp.setNr(el.select("#main_content").text());//内容
						temp.setSj(el.select("#artical_sth > p > span.ss01").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("163.com")>0){//2网易
						temp.setBt(el.select("#epContentLeft > h1").text());//前标题
						temp.setLy(el.select("#endText > div.ep-source.cDGray > span.left").eq(0).text());//来源
						temp.setHf(el.select("#endText > div.ep-source.cDGray > span.ep-editor").text());//作者
						temp.setNr(el.select("div#endText").text());//内容
						temp.setSj(el.select("#epContentLeft > div.post_time_source").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("news.sina.com")>0){//3新浪
						temp.setBt(el.select("body > div.main-content.w1240 > h1").text());//前标题
						temp.setLy(el.select("#top_bar > div > div.date-source > a").eq(0).text());//来源
						String jz=el.select("#article > p").text();
						if(jz.indexOf("记者 ")>0){
							jz=jz.substring(jz.indexOf("记者 "),jz.length()-4);
						}else if(jz.indexOf("责任编辑")>0){
							jz=jz.substring(jz.indexOf("责任编辑"));
						}else if(el.select("#keywords > a:nth-child(4)").text().length()>0){
							jz=el.select("#keywords > a:nth-child(4)").text();
						}
						temp.setHf(jz);//作者
						temp.setNr(el.select("#article").text());//内容
						temp.setSj(el.select("#top_bar > div > div.date-source > span").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("thepaper.cn")>0){//4澎湃新闻
						temp.setBt(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > h1").text());//前标题
						temp.setLy(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_about > p:nth-child(2) > span").eq(0).text());//来源
						temp.setHf(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_about > p:nth-child(1)").text());//作者
						temp.setNr(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_txt").text());//内容
						temp.setSj(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_about > p:nth-child(2)").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("chinanews.com")>0){//5中国新闻网
						temp.setBt(el.select("#cont_1_1_2 > h1").text());//前标题
						temp.setLy(el.select("#cont_1_1_2 > div.left-time > div.left-t > a:nth-child(1)").eq(0).text());//来源
						temp.setHf(el.select("#cont_1_1_2 > div.left_name > div.left_name").text());//作者
						temp.setNr(el.select("#cont_1_1_2 > div.left_zw").text());//内容
						temp.setSj(el.select("#cont_1_1_2 > div.left-time > div.left-t").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("people.com")>0){//6人民网
						temp.setBt(el.select("body > div.clearfix.w1000_320.text_title > h1").text());//前标题
						temp.setLy(el.select("body > div.clearfix.w1000_320.text_title > div > div.fl > a").text());//来源
						temp.setHf(el.select("body > div.clearfix.w1000_320.text_con > div.fl.text_con_left > div.box_con > div.edit.clearfix").text());//作者
						temp.setNr(el.select("body > div.clearfix.w1000_320.text_con > div.fl.text_con_left > div.box_con").text());//内容
						temp.setSj(el.select("body > div.clearfix.w1000_320.text_title > div > div.fl").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("m2.people.cn")>0){//7人民日报
						temp.setBt(el.select("body > article > h1").text());//前标题
						temp.setLy(el.select("body > article > h2 > span.origin").text());//来源
						temp.setHf(el.select("body > article > div.content > p:nth-last-child(1)").text());//作者
						temp.setNr(el.select("body > article > div.content").text());//内容
						temp.setSj(el.select("body > article > h2 > span.time").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("qq.com")>0){//8腾讯浙江网
						temp.setBt(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > h1").text());//前标题
						temp.setLy(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > div > div.a_Info > span.a_source").text());//来源
						temp.setHf(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > div > div.a_Info > span.a_author").text());//作者
						temp.setNr(el.select("#Cnt-Main-Article-QQ > p").text());//内容
						temp.setSj(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > div > div.a_Info > span.a_time").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("news.timedg.com")>0){//9央视新闻
						temp.setBt(el.select("#content > h1").text());//前标题
						temp.setLy(el.select("#source_baidu").text());//来源
						temp.setHf(el.select("#content > p").eq(0).text());//作者
						temp.setNr(el.select("#content > div.mainContent.article-content").text());//内容
						temp.setSj(el.select("#pubtime_baidu").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("business.sohu.com")>0){//10搜狐
						temp.setBt(el.select("#article-container > div.left.main > div.text > div.text-title > h1").text());//前标题
						temp.setLy(el.select("#user-info > h4 > a").text());//来源
						temp.setHf(el.select("#mp-editor > p:nth-last-child(4) > span").eq(0).text());//作者
						temp.setNr(el.select("#mp-editor").text());//内容
						temp.setSj(el.select("#news-time").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("sh.eastday.com")>0){//11东方网
						temp.setBt(el.select("#biaoti").text());//前标题
						temp.setLy(el.select("#sectionTop > div.lm1 > div.grey12.weizhi1b.lh22.fl").text());//来源
						temp.setHf(el.select("#sectionleft > div.time.grey12a.fc.lh22 > p:nth-child(2)").eq(0).text());//作者
						temp.setNr(el.select("#zw > p").text());//内容
						temp.setSj(el.select("#pubtime_baidu").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("shobserver.com")>0){//12上观新闻
						System.out.println("sg");
						temp.setBt(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.wz_contents").text());//前标题
						temp.setLy(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.fenxiang > div.fenxiang_zz > span:nth-child(1)").text());//来源
						temp.setHf(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.wz_contents1 > div.news-edit-info > span:nth-child(1)").text());//作者
						temp.setNr(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.wz_contents1").text());//内容
						temp.setSj(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.fenxiang > div.fenxiang_zz > span:nth-child(2)").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("news.cctv.com")>0){//13央视网
						temp.setBt(el.select("h1").eq(0).text());//前标题
						temp.setLy(el.select("#mbxtext").text());//来源
						temp.setHf(el.select("div.cnt_share > span > a").text());//作者
						temp.setNr(el.select("div.col_w660 > div.cnt_bd > p").text());//内容
						temp.setSj(el.select("div.col_w660 > div.cnt_bd > div > span.info > i").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("ce.cn")>0){//14中国经济网
						temp.setBt(el.select("#articleTitle").eq(0).text());//前标题
						temp.setLy(el.select("#articleSource").text());//来源
						temp.setHf(el.select("#articleText > p").text());//作者
						temp.setNr(el.select("#articleText > div").text());//内容
						temp.setSj(el.select("#articleTime").eq(0).text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("hunan.voc.com.cn")>0){//15华声新闻
						temp.setBt(el.select("body > div.main > div.main_l > h1").text());//前标题
						temp.setLy(el.select("#source_baidu > a").text());//来源
						temp.setHf(el.select("#editor_baidu").text());//作者
						temp.setNr(el.select("#content").text());//内容
						temp.setSj(el.select("#pubtime_baidu").text());//时间
						temp.setNURLS(a);//链接
					}else if(a.indexOf("sina.com.cn")>0){//16新浪
						temp.setBt(el.select("#artibody > div.article-header.clearfix > h1").text());//前标题
						temp.setLy(el.select("#art_source").text());//来源
						String sub=el.select("#artibody > div.article-body.main-body > p").eq(0).text();
						if(sub.contains("（")){
							sub=sub.substring(sub.indexOf("（"));
						}
						temp.setHf(sub);//作者
						temp.setNr(el.select("#artibody > div.article-body.main-body").text());//内容
						temp.setSj(el.select("#artibody > div.article-header.clearfix > p > span:nth-child(1)").text());//时间
						temp.setNURLS(a);//链接
					}
					datas.add(temp);
					d++;
			}//每个页面判断一次
	    }//每个网址循环一次
		//输出爬来的数据
		for(int i=0;i<datas.size();i++){
			System.out.println("第"+(i+1)+"个页面,编码："+Res+"，获取的url："+datas.get(i).getNURLS());
			System.out.println("标题:"+datas.get(i).getBt()+"---");
			System.out.print("内容:"+datas.get(i).getNr());
			System.out.println("========时间:"+datas.get(i).getSj());
			System.out.println("来源："+datas.get(i).getLy()+"----编辑："+datas.get(i).getHf());
			System.out.println("图片地址："+datas.get(i).getJpgs());
			System.out.println("================================================");
			String b="{'bt':'"+datas.get(i).getBt()+"','ly':'"+datas.get(i).getLy()+"','nr':'"+datas.get(i).getNr()+"','sj':'"+datas.get(i).getSj()
					+"','hf':'"+datas.get(i).getHf()+"','url':'"+datas.get(i).getNURLS()+"'},";
			html+=b;
		}
		this.output(JsonUtility.filterList(new String[]{"bt","ly","","nr","sj","hf","url"},"["+html+"]"));
	}
}
