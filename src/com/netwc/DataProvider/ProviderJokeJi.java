package com.netwc.DataProvider;

import java.util.ArrayList;
import java.util.Date;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.netwc.Entities.CategoryInfo;
import com.netwc.Entities.DataFrom;
import com.netwc.Entities.JokeInfo;
import android.util.Log;

public class ProviderJokeJi extends AbsJokeProvider{
	private final String siteUrl="http://www.jokeji.cn";
	private String pageCoding="gb2312";
	
	@Override
	public ArrayList<JokeInfo> GetNewJokeInfos() {
		// TODO Auto-generated method stub
		ArrayList<JokeInfo> jokes=new ArrayList<JokeInfo>();
		Parser htmlParse;
		try {
			htmlParse = new Parser(siteUrl);
			htmlParse.setEncoding(pageCoding);
	
			NodeList newsContainer=htmlParse.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "newcontent l_left")));
			if(newsContainer!=null && newsContainer.size()>0){
				NodeList lis=newsContainer.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("ul"));
				if(lis!=null && lis.size()>0){
					lis=lis.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("li"));
				}
				if(lis!=null && lis.size()>0){
					NodeList liSubNodes;
					NodeList aTags;
					NodeList spanTags;
					Node liNode;
					for(int i=0;i<lis.size();i++){
						liNode=lis.elementAt(i);
						liSubNodes=liNode.getChildren();
						aTags=liSubNodes.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("target", "_blank")));
						spanTags=liSubNodes.extractAllNodesThatMatch(new TagNameFilter("span"));
						if(aTags!=null && aTags.size()>0){
							JokeInfo joke=new JokeInfo();
							joke.Title=((LinkTag)aTags.elementAt(0)).getLinkText();
							joke.DataFrom=DataFrom.QIUSHI;
							String url;
							url = siteUrl+((TagNode)aTags.elementAt(0)).getAttribute("href");
							joke.Url=UrlEncodeChina(url);								
							if(spanTags!=null && spanTags.size()>0){
								joke.SiteDate=((Span)spanTags.elementAt(0)).getStringText();	
							}else{
								joke.SiteDate="";
							}
							jokes.add(joke);
						}							
					}
				}
				for(int i=0;i<jokes.size();i++){
					JokeInfo joke=jokes.get(i);
					String url=joke.Url;
					if(url!=null && url.length()>0){							
						String content=GetContent(url);
						joke.Content=content;
					}									
					joke.DateAdd=(new Date()).getTime();
					//db.AddJoke(joke);
				}
			}		
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return jokes;
	}

	private String GetContent(String url){
		try {
			Parser htmlParse = new Parser(url);
			htmlParse.setEncoding("gb2312");
			NodeList jkContent=htmlParse.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("id", "text110")));
			if(jkContent!=null && jkContent.size()>0){
				return RemoveHtmlCode(jkContent.toHtml());
			}
			Log.v("Content", jkContent.toHtml());
			return "";
		}
		catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("ErrorMsg",e.getMessage());
			return "";
		}
	}

	@Override
	public ArrayList<CategoryInfo> GetJokeCategorys() {
		// TODO Auto-generated method stub
		ArrayList<CategoryInfo> jokes=new ArrayList<CategoryInfo>();
		Parser htmlParse;
		try {
			htmlParse = new Parser(siteUrl);
			htmlParse.setEncoding(pageCoding);
			
			NodeList categoryContainer=htmlParse.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "joketype l_left")));
			if(categoryContainer!=null && categoryContainer.size()>0){
				NodeList lisUl=categoryContainer.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("ul"));
				Log.v("GetJokeCategorys","Ul Size:"+String.valueOf(lisUl.size()));
				if(null!=lisUl && lisUl.size()>0){
					NodeList lis=lisUl.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("li"));
					Log.v("GetJokeCategorys","Li Size:"+String.valueOf(lis.size()));	
					if(lis!=null && lis.size()>0){
						Node liNode;
						NodeList liSubNode;
						for(int i=0;i<lis.size();i++){
							liNode=lis.elementAt(i);
							liSubNode=liNode.getChildren().extractAllNodesThatMatch(new TagNameFilter("a"));
							if(liSubNode!=null && liSubNode.size()>0){
								CategoryInfo catInfo=new CategoryInfo();
								String catName=RemoveHtmlCode(((LinkTag)liSubNode.elementAt(0)).getLinkText());
								catInfo.Name=catName.indexOf("(")>0?catName.substring(0,catName.indexOf("(")):catName;
								String url= siteUrl+((TagNode)liSubNode.elementAt(0)).getAttribute("href");
								catInfo.PageUrl=UrlEncodeChina(url);
								jokes.add(catInfo);
							}
						}
					}
				}
			}
		}catch(ParserException e){
			e.printStackTrace();
		}
		Log.v("GetJokeCategorys-JokeSize:",String.valueOf(jokes.size()));
		return jokes;
	}

	@Override
	public JokeInfo GetJokeInfo(JokeInfo jokeUrl) {
		// TODO Auto-generated method stub
		
		return null;
	}
}
