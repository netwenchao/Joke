package com.netwc.DataProvider;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.netwc.Entities.JokeInfo;

import android.util.Log;

public class JokeJi_Provider{// extends AbsJokeProvider implements IJokeProvider{
	/*
	public String PageUrl = "http://www.jokeji.cn/";
    public String HostAddress = "http://www.jokeji.cn/";
    private String PageCoding="gb2312";	
	
	@Override
	public ArrayList<CategoryInfo> GetCategorys() {
		
        <div class="joketype l_left">
           <ul>
               <li> <a href="/list29_1.htm">爆笑男女(945)</a></li>
               <li> <a href="/list13_1.htm">社会(722)</a></li>
               <li> <a href="/list43_1.htm">冷笑话(656)</a></li>
               <li> <a href="/list5_1.htm">校园(537)</a></li>
               <li> <a href="/list27_1.htm">综合(445)</a></li>
               <li> <a href="/list7_1.htm">儿童(400)</a></li>
               <li> <a href="/list1_1.htm">夫妻(372)</a></li>
               <li> <a href="/list12_1.htm">动物(296)</a></li>
               <li> <a href="/list4_1.htm">家庭(265)</a></li>
               <li> <a href="/list16_1.htm">职场(254)</a></li>
               <li> <a href="/list40_1.htm">爆笑签名(250)</a></li>
               <li> <a href="/list36_1.htm">短信笑话(245)</a></li>
               <li> <a href="/list39_1.htm">幽默网文(187)</a></li>
           </ul>
       </div>

		ArrayList<CategoryInfo> jokes=new ArrayList<CategoryInfo>();
		Parser htmlParse;
		try {
			htmlParse = new Parser(PageUrl);
			htmlParse.setEncoding(PageCoding);
			
			NodeList categoryContainer=htmlParse.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "joketype l_left")));
			if(categoryContainer!=null && categoryContainer.size()>0){
				NodeList lis=categoryContainer.elementAt(0).getChildren().extractAllNodesThatMatch(new TagNameFilter("li"));
				if(lis!=null && lis.size()>0){
					Node liNode;
					NodeList liSubNode;
					for(int i=0;i<lis.size();i++){
						liNode=lis.elementAt(i);
						liSubNode=liNode.getChildren().extractAllNodesThatMatch(new TagNameFilter("a"));
						if(liSubNode!=null && liSubNode.size()>0){
							CategoryInfo catInfo=new CategoryInfo();
							String catName=((LinkTag)liSubNode.elementAt(0)).getLink();
							catInfo.Name=catName.indexOf("(")>0?catName.substring(0,catName.indexOf("(")):catName;
							String url= HostAddress+((TagNode)liSubNode.elementAt(0)).getAttribute("href");
							catInfo.PageUrl=UrlEncodeChina(url);
						}
					}
				}
			}
		}catch(ParserException e){
			
		}
		return null;
	}

	/*
    <div class="newcontent l_left">
        <ul>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/fq/20130820000335.htm" target="_blank">被二货老婆涮的超郁闷</a>(5974)<span>2013-8-20</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/ert/20130820000245.htm" target="_blank">机灵又霸气的熊孩子</a>(4410)<span>2013-8-20</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/gj/20130820000225.htm" target="_blank">美国、苏联和穆斯林的冷幽默</a>(2707)<span>2013-8-20</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/bxqm/20130820000125.htm" target="_blank">我不脑残,只是想法奇特而已</a>(3097)<span>2013-8-20</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/xy/20130819000425.htm" target="_blank">笑呆你的校园二货</a>(25373)<span>2013-8-19</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/bxnn/20130819000241.htm" target="_blank">甘拜下风的奇葩妹子</a>(17797)<span>2013-8-19</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/bxqm/20130819000201.htm" target="_blank">逗乐囧语妙段子</a>(15385)<span>2013-8-19</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/jsxh/20130819000057.htm" target="_blank">经典的军旅生活爆笑</a>(9841)<span>2013-8-19</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/冷笑话/20130818000359.htm" target="_blank">0度笑话,刚从冰箱里拿出来</a>(33788)<span>2013-8-18</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
            <li><img src="/images/d02.gif" width="8" height="10" border="0"><a href="/jokehtml/bxnn/20130818000240.htm" target="_blank">没有热度的情侣冷段子</a>(21237)<span>2013-8-18</span><span><img src="/images/new.gif" width="28" height="11" border="0"></span></li>
        </ul>
    </div> 

	@Override
	public ArrayList<JokeInfo> GetDailyJokes() {		
		ArrayList<JokeInfo> jokes=new ArrayList<JokeInfo>();
		Parser htmlParse;
		try {
			htmlParse = new Parser(PageUrl);
			htmlParse.setEncoding(PageCoding);
	
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
							String url;
							url = HostAddress+((TagNode)aTags.elementAt(0)).getAttribute("href");
							joke.Url=UrlEncodeChina(url);								
							if(spanTags!=null && spanTags.size()>0){
								joke.SiteDate=((Span)spanTags.elementAt(0)).getStringText();	
							}else{
								joke.SiteDate="";
							}
							joke.DateAdd=(new Date()).getTime();
							joke.IsDownLoad=false;
							joke.IsNew=true;
							joke.IsFavourite=false;
							if(joke.Url!="") jokes.add(joke);
						}							
					}
				}				
			}		
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return jokes;
	}

	@Override
	public ArrayList<JokeInfo> GetCategoryJokes() {
		//Page Regex list29_7.htm
        /*
        <div class="list_title">
        <ul>
            <li><b><a href="/jokehtml/bxnn/20130819000241.htm" target="_blank">甘拜下风的奇葩妹子</a></b><span>浏览：69次</span><i>2013-8-19</i></li>
            <li><b><a href="/jokehtml/bxnn/20130818000240.htm" target="_blank">没有热度的情侣冷段子</a></b><span>浏览：11190次</span><i>2013-8-18</i></li>
            <li><b><a href="/jokehtml/bxnn/20130813000313.htm" target="_blank">哈哈,我越来越佩服自己了</a></b><span>浏览：44470次</span><i>2013-8-13</i></li>
        </ul>
        </div>

		return null;
	}

	@Override
	public JokeInfo GetJokeContent(JokeInfo joke) {
		/*
        <div class="mob_txt">
            <div class="ad360_280">
            <script type="text/javascript">BAIDU_CLB_fillSlot("31558");</script><!--336-280-->
            </div>
            <span><b>阅读技巧</b>：键盘 ←左 右→ 翻页，Ctrl+D 收藏本篇笑话</span><br>
            <span id="text110"><p>1、被妹子问“有男朋友吗？”<br>我惊讶了下回答“诶？还没有”<br>她笑着说“太好了！” <br>难道我久违的爱情要来了？！！只听接下来妹子说道：“我刚还在想，如果像你这样儿的都有女朋友这世界就要毁灭了啊，幸好你没有。”</p>
            <p>2、今天相亲总算见到个传说中的女汉子，一起吃的牛排，她说用不惯刀叉，一直愤愤叨叨的嫌麻烦。<br>后来实在忍不住了霸气的来了一句：“服务员！来双筷子！”<br>在服务员解释吃牛排不好用筷子后，她又大声来了句：“一次性手套也行！”</p>
            <p>3、带着现任女友走在路上，碰到了前女友，很尴尬啊！<br>我急忙给前女友介绍说：“这是你嫂子。”<br>接着女友说：“这位是？”<br>前女友赶话说：“我以前是你嫂子！”</p>
            <p>4、两同事聊天，一同事说：“我要不要告诉我女朋友，我爸有3套别墅，几百万存折。”<br>一同事说：“千万不能说啊，说了她就成你后妈了！”</p></span><br>

            <font color="#009900">看笑话就上：笑话集 www.jokeji.cn</font> 
            <br>
            <br>
            <div class="clear"></div>


            <div class="pl_ad">
            <ul>
            <li><b><a href="#bbs">发表评论</a></b></li>
            <li><span id="topelite" onclick="javascript:check('../../inc/topelite.asp?nid=8070','topelite')" alt="推荐">票数:20</span></li>
            </ul>

            <div class="clear"></div>

            <div class="ad468_date">
            <b><script type="text/javascript">BAIDU_CLB_fillSlot("133131");</script> <!-- GG-2 --></b>
            <i>发布时间：2013-8-19 0:03:00</i>
            </div>
            </div>
            <div class="clear"></div>
        </div> 
      
		try {
			Parser htmlParse = new Parser(joke.Url);
			htmlParse.setEncoding("gb2312");
			NodeList jkContent=htmlParse.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("id", "text110")));
			if(jkContent!=null && jkContent.size()>0){
				joke.Content=RemoveHtmlCode(jkContent.toHtml());
			}			
			joke.Content="";
			joke.IsDownLoad=true;
			joke.IsNew=true;
		}
		catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("ErrorMsg",e.getMessage());	
			joke.IsDownLoad=false;
		}
		return joke;
	}
  */
}
