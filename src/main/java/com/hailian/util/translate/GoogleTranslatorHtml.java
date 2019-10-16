package com.hailian.util.translate;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class GoogleTranslatorHtml extends AbstractTranslator
{
  private static final String url = "https://translate.googleapis.com/translate_a/t";
  private static int s = 0;

  public GoogleTranslatorHtml() { super("https://translate.googleapis.com/translate_a/t"); }


  public void setLangSupport()
  {
    this.langMap.put(LANG.AUTO, "auto");
    this.langMap.put(LANG.ZH, "zh-CN");
    this.langMap.put(LANG.EN, "en");
    this.langMap.put(LANG.JP, "ja");
    this.langMap.put(LANG.KOR, "ko");
    this.langMap.put(LANG.FRA, "fr");
    this.langMap.put(LANG.RU, "ru");
    this.langMap.put(LANG.DE, "de");
    this.langMap.put(LANG.ZHTW, "zh-TW");
  }

  public void setFormData(LANG from, LANG to, String text)
  {
    this.formData.put("anno", "3");

    this.formData.put("client", "te_lib");

    this.formData.put("format", "html");
    this.formData.put("v", "1.0");
    this.formData.put("key", "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
    this.formData.put("logld", "vTE_20180625_00");
    this.formData.put("sl", this.langMap.get(from));
    this.formData.put("tl", this.langMap.get(to));
    this.formData.put("sp", "nmt");
    this.formData.put("tc", "1");
    this.formData.put("sr", "1");
    this.formData.put("mode", "1");
    this.formData.put("tk", generateToken(text));
    this.formData.put("q", text);
  }

  public String query()
    throws Exception
  {
    URIBuilder uri = new URIBuilder("https://translate.googleapis.com/translate_a/t");
    for (String key : this.formData.keySet()) {
      if (!"q".equals(key))
      {
        if ("tc".equals(key)) {
          this.formData.put(key, String.valueOf(s++));
        }
        String value = (String)this.formData.get(key);
        uri.addParameter(key, value);
      }
    }
    Object request = new HttpPost(uri.toString());
    ((HttpUriRequest)request).setHeader("Content-Type", "application/x-www-form-urlencoded");
    ((HttpUriRequest)request).setHeader("DNT", "1");
    ((HttpUriRequest)request).setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
    ((HttpUriRequest)request).setHeader("Origin", "https://en.wikipedia.org");
    ((HttpUriRequest)request).setHeader("Referer", "https://en.wikipedia.org/");

    List params = new ArrayList();
    params.add(new BasicNameValuePair("q", (String)this.formData.get("q")));
    ((HttpPost)request).setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
    CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)request);
    HttpEntity entity = response.getEntity();

    String result = EntityUtils.toString(entity, "utf-8");

    EntityUtils.consume(entity);
    response.getEntity().getContent().close();
    response.close();

    return result;
  }

  public String parses(String text) throws IOException
  {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonNode = mapper.readTree(text);
    JsonNode jsonNode1 = jsonNode.get(0);
    String replaceText = jsonNode1.asText();
    replaceText = replaceText.replaceAll("<i>[\\s\\S]*?</i>", "").replaceAll("<b>", "").replaceAll("</b>", "");
    replaceText=replaceText.replaceAll("&#39;", "'");
    return replaceText;
  }

  private String token(String text) {
    String tk = "";
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
    try {
      FileReader reader = new FileReader("./tk/Google.js");
      engine.eval(reader);
      if ((engine instanceof Invocable)) {
        Invocable invoke = (Invocable)engine;
        tk = String.valueOf(invoke.invokeFunction("token", new Object[] { text }));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tk;
  }
  private static int[] TKK() {
    return new int[] { 406398, 2087938574 };
  }
  private static int shr32(int x, int bits) {
    if (x < 0) {
      long x_l = 4294967295L + x + 1L;
      return (int)(x_l >> bits);
    }
    return x >> bits;
  }
  private static int RL(int a, String b) {
    for (int c = 0; c < b.length() - 2; c += 3) {
      int d = b.charAt(c + 2);
      d = d >= 65 ? d - 87 : d - 48;
      d = b.charAt(c + 1) == '+' ? shr32(a, d) : a << d;
      a = b.charAt(c) == '+' ? a + (d & 0xFFFFFFFF) : a ^ d;
    }
    return a;
  }
  private static String generateToken(String text) {
    int[] tkk = TKK();
    int b = tkk[0];
    int e = 0;
    int f = 0;
    List d = new ArrayList();
    for (; f < text.length(); f++) {
      int g = text.charAt(f);
      if (128 > g) {
        d.add(e++, Integer.valueOf(g));
      } else {
        if (2048 > g) {
          d.add(e++, Integer.valueOf(g >> 6 | 0xC0));
        }
        else if ((55296 == (g & 0xFC00)) && (f + 1 < text.length()) && (56320 == (text.charAt(f + 1) & 0xFC00))) {
          g = 65536 + ((g & 0x3FF) << 10) + (text.charAt(++f) & 0x3FF);
          d.add(e++, Integer.valueOf(g >> 18 | 0xF0));
          d.add(e++, Integer.valueOf(g >> 12 & 0x3F | 0x80));
        } else {
          d.add(e++, Integer.valueOf(g >> 12 | 0xE0));
          d.add(e++, Integer.valueOf(g >> 6 & 0x3F | 0x80));
        }

        d.add(e++, Integer.valueOf(g & 0x3F | 0x80));
      }
    }

    int a_i = b;
    for (e = 0; e < d.size(); e++) {
      a_i += ((Integer)d.get(e)).intValue();
      a_i = RL(a_i, "+-a^+6");
    }
    a_i = RL(a_i, "+-3^+b+-f");
    a_i ^= tkk[1];
    long a_l;
    if (0 > a_i)
      a_l = 2147483648L + (a_i & 0x7FFFFFFF);
    else {
      a_l = a_i;
    }
    a_l = (long) (a_l % Math.pow(10.0D, 6.0D));
    return String.format(Locale.US, "%d.%d", new Object[] { Long.valueOf(a_l), Long.valueOf(a_l ^ b) });
  }
}