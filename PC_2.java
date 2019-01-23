package com.test.bll;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * 测试类
 * **/
public class PC_2 {
	public PC_2(){	
	}
	public static void main(String[] args) throws IOException {
		List<Article> datas;
		Get_PC_URL f=new Get_PC_URL();
		int d=1;
		for(String a:f.GetURL()){
			System.out.println("第"+d+"获取的url："+a);
			d++;
			//Connection连接
			Connection conn = Jsoup.connect(a).timeout(8000);//忽视内容检测ignoreContentType(true)
			//伪装成浏览器抓取
			conn.header("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
			Response rs = conn.ignoreHttpErrors(true).ignoreContentType(true).method(Method.GET).execute();
			rs.body();
			Document doc = Jsoup.parse(rs.body());
			//获取页面编码并且修改
			String Res="";//保存编码
			String eles = doc.select("meta[charset]").toString();//页面编码类型1
			String eles2 = doc.select("meta[content]").toString();//页面编码类型2
			if(!eles.equals(""))
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
			Document docs = Jsoup.parse(new URL(a).openStream(),Res,a);
			//查找出自己需要的内容
			datas=new ArrayList<Article>();
				Elements contents = docs.select("body");
				for(Element el:contents){//Element元素  contents内容
					Article temp=new Article();
					String img= el.select("body > img").attr("abs:src");
						temp.setJpgs(img);
					if(a.indexOf("ifeng.com")>0){//1凤凰网
						temp.setTag(el.select("h1#artical_topic").text());//前标题
						temp.setTitle(el.select("#artical_sth > p > span:nth-child(4) > span > a").text());//来源
						temp.setAuthor(el.select("#main_content > span").text());//作者
						temp.setDescription(el.select("#main_content").text());//内容
						temp.setComments(el.select("#artical_sth > p > span.ss01").eq(0).text());//时间
						
					}else if(a.indexOf("money.163")>0){//2网易
						temp.setTag(el.select("#epContentLeft > h1").text());//前标题
						temp.setTitle(el.select("#endText > div.ep-source.cDGray > span.left").eq(0).text());//来源
						temp.setAuthor(el.select("#endText > div.ep-source.cDGray > span.ep-editor").text());//作者
						temp.setDescription(el.select("div#endText").text());//内容
						temp.setComments(el.select("#epContentLeft > div.post_time_source").eq(0).text());//时间
						
					}else if(a.indexOf("news.sina.com")>0){//3新浪
						temp.setTag(el.select("body > div.main-content.w1240 > h1").text());//前标题
						temp.setTitle(el.select("#top_bar > div > div.date-source > a").eq(0).text());//来源
						String jz=el.select("#article > p").text();
						if(jz.indexOf("记者")>0){
							jz=jz.substring(jz.indexOf("记者"),jz.length()-4);
						}else if(jz.indexOf("责任编辑")>0){
							jz=jz.substring(jz.indexOf("责任编辑"));
						}else if(el.select("#keywords > a:nth-child(4)").text().length()>0){
							jz=el.select("#keywords > a:nth-child(4)").text();
						}
						temp.setAuthor(jz);//作者						
						temp.setDescription(el.select("#article").text());//内容
						temp.setComments(el.select("#top_bar > div > div.date-source > span").eq(0).text());//时间
						
					}else if(a.indexOf("thepaper.cn")>0){//4澎湃新闻
						temp.setTag(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > h1").text());//前标题
						temp.setTitle(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_about > p:nth-child(2) > span").eq(0).text());//来源
						temp.setAuthor(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_about > p:nth-child(1)").text());//作者
						temp.setDescription(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_txt").text());//内容
						temp.setComments(el.select("body > div.bdwd.main.clearfix > div.main_lt > div.newscontent > div.news_about > p:nth-child(2)").eq(0).text());//时间
						
					}else if(a.indexOf("chinanews.com")>0){//5中国新闻网
						temp.setTag(el.select("#cont_1_1_2 > h1").text());//前标题
						temp.setTitle(el.select("#cont_1_1_2 > div.left-time > div.left-t > a:nth-child(1)").eq(0).text());//来源
						temp.setAuthor(el.select("#cont_1_1_2 > div.left_name > div.left_name").text());//作者
						temp.setDescription(el.select("#cont_1_1_2 > div.left_zw").text());//内容
						temp.setComments(el.select("#cont_1_1_2 > div.left-time > div.left-t").eq(0).text());//时间
					
					}else if(a.indexOf("people.com")>0){//6人民网
						temp.setTag(el.select("body > div.clearfix.w1000_320.text_title > h1").text());//前标题
						temp.setTitle(el.select("body > div.clearfix.w1000_320.text_title > div > div.fl > a").text());//来源
						temp.setAuthor(el.select("body > div.clearfix.w1000_320.text_con > div.fl.text_con_left > div.box_con > div.edit.clearfix").text());//作者
						temp.setDescription(el.select("body > div.clearfix.w1000_320.text_con > div.fl.text_con_left > div.box_con").text());//内容
						temp.setComments(el.select("body > div.clearfix.w1000_320.text_title > div > div.fl").eq(0).text());//时间
					
					}else if(a.indexOf("m2.people.cn")>0){//7人民日报
						temp.setTag(el.select("body > article > h1").text());//前标题
						temp.setTitle(el.select("body > article > h2 > span.origin").text());//来源
						temp.setAuthor(el.select("body > article > div.content > p:nth-last-child(1)").text());//作者
						temp.setDescription(el.select("body > article > div.content").text());//内容
						temp.setComments(el.select("body > article > h2 > span.time.br").eq(0).text());//时间
					
					}else if(a.indexOf("zj.qq.com")>0){//8腾讯浙江网
						temp.setTag(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > h1").text());//前标题
						temp.setTitle(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > div > div.a_Info > span.a_source").text());//来源
						temp.setAuthor(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > div > div.a_Info > span.a_author").eq(0).text());//作者
						temp.setDescription(el.select("#Cnt-Main-Article-QQ > p").text());//内容
						temp.setComments(el.select("#Main-Article-QQ > div.qq_innerMain.clearfix > div.qq_main > div.qq_article > div.hd > div > div.a_Info > span.a_time").eq(0).text());//时间
					
					}else if(a.indexOf("news.timedg.com")>0){//9央视新闻
						temp.setTag(el.select("#content > h1").text());//前标题
						temp.setTitle(el.select("#source_baidu").text());//来源
						temp.setAuthor(el.select("#content > p").eq(0).text());//作者
						temp.setDescription(el.select("#content > div.mainContent.article-content").text());//内容
						temp.setComments(el.select("#pubtime_baidu").eq(0).text());//时间
					
					}else if(a.indexOf("business.sohu.com")>0){//10搜狐
						temp.setTag(el.select("#article-container > div.left.main > div.text > div.text-title > h1").text());//前标题
						temp.setTitle(el.select("#user-info > h4 > a").text());//来源
						temp.setAuthor(el.select("#mp-editor > p:nth-last-child(4) > span").text());//作者
						temp.setDescription(el.select("#mp-editor").text());//内容
						temp.setComments(el.select("#news-time").eq(0).text());//时间
					
					}else if(a.indexOf("sh.eastday.com")>0){//11东方网
						temp.setTag(el.select("#biaoti").text());//前标题
						temp.setTitle(el.select("#sectionleft > div.time.grey12a.fc.lh22 > p:nth-child(2) > a").text());//来源
						temp.setAuthor(el.select("#author_baidu").text());//作者
						temp.setDescription(el.select("#zw > p").text());//内容
						temp.setComments(el.select("#pubtime_baidu").eq(0).text());//时间
					
					}else if(a.indexOf("shobserver.com")>0){//12上观新闻
						System.out.println("sg");
						temp.setTag(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.wz_contents").text());//前标题
						temp.setTitle(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.fenxiang > div.fenxiang_zz > span:nth-child(1)").text());//来源
						temp.setAuthor(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.wz_contents1 > div.news-edit-info > span:nth-child(1)").text());//作者
						temp.setDescription(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.wz_contents1").text());//内容
						temp.setComments(el.select("body > div:nth-child(3) > div:nth-child(2) > div.center > div.left > div.fenxiang > div.fenxiang_zz > span:nth-child(2)").eq(0).text());//时间
					
					}else if(a.indexOf("news.cctv.com")>0){//13央视网
						temp.setTag(el.select("h1").eq(0).text());//前标题
						temp.setTitle(el.select("#mbxtext").text());//来源
						temp.setAuthor(el.select("div.cnt_share > span > a").text());//作者
						temp.setDescription(el.select("div.col_w660 > div.cnt_bd > p").text());//内容
						temp.setComments(el.select("div.col_w660 > div.cnt_bd > div > span.info > i").eq(0).text());//时间
					
					}else if(a.indexOf("ce.cn")>0){//14中国经济网
						temp.setTag(el.select("#articleTitle").eq(0).text());//前标题
						temp.setTitle(el.select("#articleSource").text());//来源
						temp.setAuthor(el.select("#articleText > p").text());//作者
						temp.setDescription(el.select("#articleText > div").text());//内容
						temp.setComments(el.select("#articleTime").eq(0).text());//时间
					
					}else if(a.indexOf("hunan.voc.com.cn")>0){//15华声新闻
						temp.setTag(el.select("body > div.main > div.main_l > h1").text());//前标题
						temp.setTitle(el.select("#source_baidu > a").text());//来源
						temp.setAuthor(el.select("#editor_baidu").text());//作者
						temp.setDescription(el.select("#content").text());//内容
						temp.setComments(el.select("#pubtime_baidu").text());//时间
					
					}else if(a.indexOf("sina.com.cn")>0){//16新浪
						temp.setTag(el.select("#artibody > div.article-header.clearfix > h1").text());//前标题
						temp.setTitle(el.select("#art_source").text());//来源
						String sub=el.select("#artibody > div.article-body.main-body > p").eq(0).text();
						if(sub.contains("（")){
							sub=sub.substring(sub.indexOf("（"));
						}
						temp.setAuthor(sub);//作者
						temp.setDescription(el.select("#artibody > div.article-body.main-body").text());//内容
						temp.setComments(el.select("#artibody > div.article-header.clearfix > p > span:nth-child(1)").text());//时间
						
					}
					datas.add(temp);
			}
			//输出爬来的数据
			for(int i=0;i<datas.size();i++){
				System.out.println("该页面编码："+Res);
				System.out.println("标题:"+datas.get(i).getTag()+" ---来源:"+datas.get(i).getTitle());
				System.out.println("内容："+datas.get(i).getDescription());
				System.out.println("作者："+datas.get(i).getAuthor()+" ----时间："+datas.get(i).getComments());
				System.out.println("图片地址:"+datas.get(i).getJpgs());
				System.out.println("================================================");
			}
		}
	}

}
