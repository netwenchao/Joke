package com.netwc.DataProvider;

import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.netwc.Entities.DataFrom;
import com.netwc.Entities.JokeInfo;
import com.netwc.Entities.LinkNodeData;
import com.netwc.Entities.NodeData;

public class ProviderQiuShi extends AbsJokeProvider{
	private final String siteUrl="http://www.qiushibaike.com";	
	@Override
	public ArrayList<JokeInfo> Execute() {
		// TODO Auto-generated method stub
		ArrayList<JokeInfo> jokes=new ArrayList<JokeInfo>();
		Parser htmlParse;
		try {
			htmlParse = new Parser(siteUrl);
			htmlParse.setEncoding("utf-8");	
			NodeList newsContainer=htmlParse.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "block untagged")));
			/*
			<div class="block untagged" id="qiushi_tag_32498823">
				<div class="detail">
					<a href="/article/32498823" target="_blank">她对我说我看你是想当黄瓜了是吧</a>
				</div>
				<div class="author">
					<img src="http://www.qiushibaike.com/static/images/thumb/missing.png" alt="mélisse">
					<a href="/users/1271537">mélisse</a>
				</div>
				<div class="content" title="2013-06-08 14:09:56">
					今天我跟女同事开玩笑，她对我说我看你是想当黄瓜了是吧！天啊，她竟然该跟一个看了这么多年糗百的人说这句话。。。
				</div>
				<div class="thumb">
					<a href="/article/46893430" target="_blank" onclick="_hmt.push(['_trackEvent', 'post', 'click', 'signlePost'])">
						<img src="http://pic.qiushibaike.com/system/pictures/4689/46893430/medium/app46893430.jpg" alt="糗事#46893430">
					</a>
				</div>
			</div>
			*/

			if(newsContainer!=null && newsContainer.size()>0){
				String title;
				String Content;
				for(Integer i=0;i<newsContainer.size();i++){
					NodeList childNodes=newsContainer.elementAt(i).getChildren();
					if(childNodes.size()>=3){
						JokeInfo info=new JokeInfo();
						info.DataFrom=DataFrom.JOKEJI;
						NodeList details=childNodes.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("a"));//get Title and url
						NodeList author=childNodes.elementAt(1).getChildren().extractAllNodesThatMatch(new TagNameFilter("a"));//get author						
						Node content=childNodes.elementAt(2);//get time and content
						LinkNodeData detailNodeData=this.GetLinkNodeUrl(details, siteUrl);
						LinkNodeData authorNodeData=this.GetLinkNodeUrl(author, siteUrl);
						NodeData contendNodeData=this.GetNodeData(content,"title");
						if(detailNodeData!=null && contendNodeData!=null){
							info.Title=detailNodeData.Text;
							info.Url=detailNodeData.Url;
							info.Content=contendNodeData.Content;
							if(contendNodeData.Attributes.size()>0){
								info.SiteDate=contendNodeData.Attributes.get(0).get("title");
							}
						}
						jokes.add(info);
					}
				}
			}		
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return jokes;
	}
}
