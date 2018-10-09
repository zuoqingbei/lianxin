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
		String url="http://wsjs.saic.gov.cn/txnDetail.do?y7bRbp=qmMO3Lgy8ZceH5voom7vtF9rp7nDdH4IjVzYtskB_CXlhyBc5J0GS3jXLuJsMYxO3uiCank5I71bELGyIjMHoX_LSYS3kCAdcRVp0DFqjq0_ZDcM.fDmqY0XzmlrIlPNJ9R8V.RqpGKjlMe2.eEM5UdMWjb.SO6IxwFPMQCC8ajegQrC&c1K5tw0w6_=2loG94.Rm05pi1IMWp8gr3QGkwxdArXhxqBdEjach9ArqZhD3dcm0ZOhJ5bhp8IabV5Kv8vWzXO.7ByRNLp1LerUnExb3Y9KQGaEJ9MgafOqf2Bl6rzfTfKV5rWdS5oWDOTSRcRw6_oP.LIg0rSIQsV.uONn28W.tw6Eqw0UkAhp8jE.ptCZbxeDIw96redGKzNWJ.9SjGMNM6Le32XN5JlS91rDMK9O64nT3l9UPbIipc85v7ESDiNnOkP6rWHZR";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            //3.执行请求，获取响应
            response = client.execute(httpGet);
            //看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();
            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
            EntityUtils.consume(entity);
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
