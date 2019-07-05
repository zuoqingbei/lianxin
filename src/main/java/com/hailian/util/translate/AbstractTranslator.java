package com.hailian.util.translate;
import java.io.IOException;

public abstract class AbstractTranslator extends AbstractHttpAttribute
  implements HttpParams
{
  public AbstractTranslator(String url)
  {
    super(url);
    setLangSupport();
  }

  public String run(LANG source, String text)
  {
    return null;
  }

  public String run(LANG from, LANG to, String text)
  {
    String result = "";
    setFormData(from, to, text);
    try {
      result = parses(query());
    } catch (Exception e) {
      e.printStackTrace();
    }
    close();
    return result;
  }

  public abstract void setLangSupport();

  public void setFormData(LANG source, String text)
  {
  }

  public abstract void setFormData(LANG paramLANG1, LANG paramLANG2, String paramString);

  public abstract String query()
    throws Exception;

  public abstract String parses(String paramString)
    throws IOException;
}