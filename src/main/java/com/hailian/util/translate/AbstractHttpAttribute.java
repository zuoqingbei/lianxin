package com.hailian.util.translate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public abstract class AbstractHttpAttribute
{
  public String url;
  public Map<String, String> formData;
  public Map<LANG, String> langMap;
  public CloseableHttpClient httpClient;
  private PoolingHttpClientConnectionManager pccm;

  public AbstractHttpAttribute(String url)
  {
    this.pccm = new PoolingHttpClientConnectionManager();
    this.url = url;
    this.formData = new HashMap();
    this.langMap = new HashMap();

    this.httpClient = HttpClients.custom().setConnectionManager(this.pccm).setConnectionManagerShared(true).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
  }

  public abstract String query()
    throws Exception;

  public abstract String run(LANG paramLANG, String paramString);

  public abstract String run(LANG paramLANG1, LANG paramLANG2, String paramString);

  public void close(HttpEntity httpEntity, CloseableHttpResponse httpResponse)
  {
    try
    {
      EntityUtils.consume(httpEntity);
      if (null != httpResponse) {
        httpResponse.close();
      }
      this.httpClient.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close()
  {
    try
    {
      if (null != this.httpClient)
        this.httpClient.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}