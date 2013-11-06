package com.netwc.Tools;

import java.io.IOException;
import java.util.Random;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import android.R.integer;
import android.content.Context;

public class HttpUtlity {
	public static String[] UserAgents=new String[]{
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)",
		    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)",
		    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2)",
		    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
		    "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36"
	};
	
	public static Integer TimeOut=5000;
	
	public class MyHttpClient implements HttpClient{
		private final Context context;
		private final DefaultHttpClient defaultHttpClient;
		
		public MyHttpClient(Context paramContext,ClientConnectionManager connectionManager,HttpParams paramHttpParams){
			context=paramContext;
			defaultHttpClient=new DefaultHttpClient(connectionManager, paramHttpParams);			
			HttpParams localHttpParams1 = defaultHttpClient.getParams();
			
		    if (localHttpParams1.getParameter("http.useragent") == null)
		      localHttpParams1.setParameter("http.useragent",UserAgents[new Random(System.currentTimeMillis()).nextInt(4)]);
		    if (localHttpParams1.getParameter("http.socket.timeout") == null)
		      localHttpParams1.setParameter("http.socket.timeout", Integer.valueOf(TimeOut));
		    if (localHttpParams1.getParameter("http.connection.timeout") == null)
		      localHttpParams1.setParameter("http.connection.timeout", Integer.valueOf(TimeOut));
		}
		
		@Override
		public HttpResponse execute(HttpUriRequest request) throws IOException,
				ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpResponse execute(HttpUriRequest request, HttpContext context)
				throws IOException, ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpResponse execute(HttpHost target, HttpRequest request)
				throws IOException, ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T execute(HttpUriRequest arg0,
				ResponseHandler<? extends T> arg1) throws IOException,
				ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpResponse execute(HttpHost target, HttpRequest request,
				HttpContext context) throws IOException,
				ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T execute(HttpUriRequest arg0,
				ResponseHandler<? extends T> arg1, HttpContext arg2)
				throws IOException, ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T execute(HttpHost arg0, HttpRequest arg1,
				ResponseHandler<? extends T> arg2) throws IOException,
				ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T> T execute(HttpHost arg0, HttpRequest arg1,
				ResponseHandler<? extends T> arg2, HttpContext arg3)
				throws IOException, ClientProtocolException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ClientConnectionManager getConnectionManager() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpParams getParams() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
