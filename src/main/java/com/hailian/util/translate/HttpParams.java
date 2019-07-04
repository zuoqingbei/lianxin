package com.hailian.util.translate;


public abstract interface HttpParams
{
  public abstract void setFormData(LANG paramLANG, String paramString);

  public abstract void setFormData(LANG paramLANG1, LANG paramLANG2, String paramString);
}