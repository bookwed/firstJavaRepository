package com.wed.componentLibrary.httpClient;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientSample {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		//url 前面加上HTTP协议头，标明该请求为http请求
		String url = "http://www.baidu.com";
		//HttpGet请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse resp = httpClient.execute(httpGet);
		try {
			System.out.println(resp.getStatusLine().getStatusCode());
			if (resp.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + resp.getStatusLine().getStatusCode());
			}
			String content = IOUtils.toString(resp.getEntity().getContent());
			System.out.println(content);
			HttpEntity entity = resp.getEntity();
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resp.close();
		}
		
		/*HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse resp2 = httpClient.execute(httpPost);
		try {
			System.out.println(resp2.getStatusLine().getStatusCode());
			if (resp2.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + resp2.getStatusLine().getStatusCode());
			}
			String content2 = IOUtils.toString(resp2.getEntity().getContent());
			System.out.println(content2);
			HttpEntity entity2 = resp2.getEntity();
			EntityUtils.consume(entity2);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
