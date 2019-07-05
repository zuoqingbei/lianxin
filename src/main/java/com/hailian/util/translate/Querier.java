package com.hailian.util.translate;

import java.util.ArrayList;
import java.util.List;


public final class Querier<T extends AbstractHttpAttribute>
{
  private LANG from;
  private LANG to;
  private String text;
  private List<T> collection;

  public Querier()
  {
    this.collection = new ArrayList();
  }

  public List<String> execute() {
    List result = new ArrayList();

    for (AbstractHttpAttribute element : this.collection) {
      if (element.getClass().getName().contains("Translator"))
        result.add(element.run(this.from, this.to, this.text));
      else if (element.getClass().getName().contains("TTS")) {
        result.add(element.run(this.from, this.text));
      }
    }
    return result;
  }

  public void setParams(LANG from, LANG to, String text) {
    this.from = from;
    this.to = to;
    this.text = text;
  }

  public void setParams(LANG source, String text) {
    this.from = source;
    this.text = text;
  }

  public void attach(T element) {
    this.collection.add(element);
  }

  public void detach(T element) {
    this.collection.remove(element);
  }

  public Integer getSize() {
    return Integer.valueOf(this.collection.size());
  }
}