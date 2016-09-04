package com.chenxun.component.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/** 
 * @author：chenxun
 * 创建时间：2016年5月24日 下午11:32:48 
 * 参考：
 * 说明：
 */
public class Excuter {
	
	private static CloseableHttpClient httpclient = HttpClients.createDefault();
	
    public static void createPostRequest() throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
    	HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/post/");
//	    RequestConfig defaultRequestConfig = RequestConfig.custom()
//	            .setCookieSpec(CookieSpecs.DEFAULT)
//	            .setExpectContinueEnabled(true)
//	            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
//	            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
//	            .build();
//    	RequestConfig config = RequestConfig.copy(defaultRequestConfig)
//                .setSocketTimeout(5000)
//                .setConnectTimeout(5000)
//                .setConnectionRequestTimeout(5000)
//                .setProxy(new HttpHost("myotherproxy", 8080))
//                .build();
//		httpPost.setConfig(config);
    	//提交的是中文字符，需要加上相应的编码格式 ;如果GetMethod提交的参数有中文字符，需要先转换成utf-8格式：  URLEncoder.encode("杭州", "utf-8");
    	//httpPost.addHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");  //??中文无效
    	httpPost.addHeader("Connection", HTTP.CONN_KEEP_ALIVE);
    	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    	nvps.add(new BasicNameValuePair("username", "陈浚"));
    	nvps.add(new BasicNameValuePair("password", "123"));
    	
    	httpPost.setEntity(new UrlEncodedFormEntity(nvps));
    	httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8)); //设置中文   放到 上一行的下面设置才有效
    	
    	CloseableHttpResponse postResponse = httpclient.execute(httpPost);
    	

    	try {
    	    System.out.println(postResponse.getStatusLine());
    	    HttpEntity entity = postResponse.getEntity();
    	    // do something useful with the response body
    	    // and ensure it is fully consumed
    	    InputStream in = entity.getContent();
    	    if(in!=null){
    	    	InputStreamReader inputStreamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
    	    	char[] chars = new char[1024];
    	    	int len;
    	    	while ((len = inputStreamReader.read(chars))!=-1) {
    	    		System.out.println(chars);
    	    	}
    	    }
    	    EntityUtils.consume(entity);
    	} finally {
    	    postResponse.close();
    	}
	}
	public static void createGetRequest() throws IOException{
    	HttpGet httpGet = new HttpGet("https://123.sogou.com/");
    	CloseableHttpResponse getResponse = httpclient.execute(httpGet);
    	// The underlying HTTP connection is still held by the response object
    	// to allow the response content to be streamed directly from the network socket.
    	// In order to ensure correct deallocation of system resources
    	// the user MUST call CloseableHttpResponse#close() from a finally clause.
    	// Please note that if response content is not fully consumed the underlying
    	// connection cannot be safely re-used and will be shut down and discarded
    	// by the connection manager. 
    	try {
    	    System.out.println(getResponse.getStatusLine());
    	    HttpEntity entity = getResponse.getEntity();
    	    // do something useful with the response body
    	    // and ensure it is fully consumed
    	    EntityUtils.consume(entity);
    	} finally {
    	    getResponse.close();
    	}
    }
	/**
	 * 得到页面返回的html
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
//	private static void createGetRequest2() throws ClientProtocolException, IOException {
//		// TODO Auto-generated method stub
//		Content returnContent = Request.Get("https://123.sogou.com/").execute().returnContent();
//		String asString = returnContent.asString();
//	    System.out.println(asString);
//	}
	/**
	 * 得到页面返回的html
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
//	private static void createPostRequest2() throws ClientProtocolException, IOException {
//		// TODO Auto-generated method stub
//		Content returnContent = Request.Post("https://123.sogou.com/")
//	    .bodyForm(Form.form().add("username",  "vip").add("password",  "secret").build())
//	    .execute().returnContent();
//		String asString = returnContent.asString();
//	    System.out.println(asString);
//	}
	public static void main(String[] args) {
		try {
			//createGetRequest();
			//createGetRequest2();
			createPostRequest();
			//createPostRequest2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
