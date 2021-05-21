package com.harinder.JSONParser;

import java.util.List;

public class KeyPathMetadata {
  //todo: Add type and array index
  private List<String> key = null;
  
  private String value = null;

  public List<String> getKey() {
    return key;
  }
  public void setKey(List<String> key) {
    this.key = key;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }
  @Override
  public String toString() {
    return  "key=" + key + ", value=" + value;
  }

}
