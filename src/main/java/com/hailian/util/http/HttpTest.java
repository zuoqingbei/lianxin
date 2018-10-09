package com.hailian.util.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpTest {
	public static void main(String[] args) {
        // TODO Auto-generated method stub
		String url="http://zhixing.court.gov.cn/search/";
        String captchaIdUrl="http://zhixing.court.gov.cn/search/index_form.do";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        HttpGet captachaGet = new HttpGet(captchaIdUrl);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        CloseableHttpResponse captchaResponse = null;
        try {
            //3.执行请求，获取响应
            response = client.execute(httpGet);
            captchaResponse = client.execute(captachaGet);
            //看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            HttpEntity captchaEntity = response.getEntity();
            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
            if(captchaEntity != null){
            	System.out.println(EntityUtils.toString(captchaEntity, "utf-8"));
            }
            EntityUtils.consume(entity);
            EntityUtils.consume(captchaEntity);
            //方法二  :使用inputStream
           /* if (entity != null) {
                inputStream = entity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            }*/
        } catch (UnsupportedOperationException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
