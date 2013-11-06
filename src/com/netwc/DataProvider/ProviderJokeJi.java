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

import com.netwc.Entities.DataFrom;
import com.netwc.Entities.JokeInfo;
import android.util.Log;

public class ProviderJokeJi extends AbsJokeProvider{
	private final String siteUrl="http://www.jokeji.cn";
	@Override
	public ArrayList<JokeInfo> Execute() {
		// TODO Auto-generated method stub
		ArrayList<JokeInfo> jokes=new ArrayList<JokeInfo>();
		Parser htmlParse;
		try {
			htmlParse = new Parser(siteUrl);
			htmlParse.setEncoding("gb2312");
	
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
	

}
