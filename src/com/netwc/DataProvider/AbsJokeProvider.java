package com.netwc.DataProvider;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.htmlparser.Node;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import com.netwc.Entities.JokeInfo;
import com.netwc.Entities.LinkNodeData;
import com.netwc.Entities.NodeData;

import android.util.Log;

public abstract class AbsJokeProvider {
	public abstract ArrayList<JokeInfo> Execute();
	
	public String UrlEncodeChina(String str){
		Pattern p=Pattern.compile("(?m)(?u)([\u4E00-\u9FA5]+)");
		String outStr=str;
		Matcher m=p.matcher(str);
		while(m.find()){
			Log.v("Match",Integer.toString(m.group(1).length()));
			Log.v("Match",m.group(1));
			outStr=outStr.replace(m.group(1),URLEncoder.encode(m.group(1)));
		}
		return outStr;
	}
	
	public String RemoveHtmlCode(String str){
		return str.replaceAll("(?m)(?u)\\<br[ ]*(/)*\\>","\n").replaceAll("(?m)\\<[^<>]*\\>","")
		.replaceAll("(?m)(?u)&amp;","&")
		.replaceAll("(?m)(?u)&lt;","<")
		.replaceAll("(?m)(?u)&gt;",">")
		.replaceAll("(?m)(?u)&quot;","\"")
		.replaceAll("(?m)(?u)&apos;","'")
		.replaceAll("(?m)(?u)&nbsp[ ]*;"," ");
	}
	
	public LinkNodeData GetLinkNodeUrl(NodeList aTags,String baseUrl){
		if(aTags!=null && aTags.size()>0){			
			return new LinkNodeData(
						baseUrl+((LinkTag)aTags.elementAt(0)).getAttribute("href").toString(),
						((LinkTag)aTags.elementAt(0)).getLinkText()
					);
		}
		return null;
	}

	public NodeData GetNodeData(NodeList tags)
	{
		if(tags!=null && tags.size()>0){
			ArrayList<HashMap<String,String>> attributes=new ArrayList<HashMap<String,String>>();
			NodeData data=new NodeData();
			data.Content=RemoveHtmlCode(tags.elementAt(0).toHtml());
			return data;
		}
		return null;
	}
	
	public NodeData GetNodeData(NodeList tags,String attrName)
	{
		if(tags!=null && tags.size()>0){
			ArrayList<HashMap<String,String>> attributes=new ArrayList<HashMap<String,String>>();
			NodeData data=new NodeData();
			data.Content=RemoveHtmlCode(tags.elementAt(0).toHtml());
			if(attrName!=null && attrName.length()>0){
				TagNode node=(TagNode)tags.elementAt(0);				
				HashMap<String,String> attr=new HashMap<String,String>();
				attr.put(attrName,node.getAttribute(attrName));
				attributes.add(attr);
			}
			return data;
		}
		return null;
	}

	public NodeData GetNodeData(Node tag,String attrName)
	{		
		ArrayList<HashMap<String,String>> attributes=new ArrayList<HashMap<String,String>>();
		NodeData data=new NodeData();
		data.Content=RemoveHtmlCode(tag.toHtml());
		if(attrName!=null && attrName.length()>0){
			TagNode node=(TagNode)tag;
			HashMap<String,String> attr=new HashMap<String,String>();
			attr.put(attrName,node.getAttribute(attrName));
			attributes.add(attr);
		}
		return data;
	}
	
	public NodeData GetNodeData(NodeList tags,String[] attrNames)
	{	
		if(tags!=null && tags.size()>0){
			ArrayList<HashMap<String,String>> attributes=new ArrayList<HashMap<String,String>>();
			NodeData data=new NodeData();
			data.Content=RemoveHtmlCode(tags.elementAt(0).toHtml());
			if(attrNames!=null && attrNames.length>0){
				TagNode node=(TagNode)tags.elementAt(0);
				for(int i=0;i<attrNames.length;i++){						
					HashMap<String,String> attr=new HashMap<String,String>();
					attr.put(attrNames[i],node.getAttribute(attrNames[i]));
					attributes.add(attr);		
				}
			}
			return data;
		}
		return null;	
	}
}
