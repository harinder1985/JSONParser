package com.harinder.JSONParser;

import java.util.ArrayList;


public interface StructureParser {

  public ArrayList<KeyPathMetadata> parse(String data);
  public boolean isValidStructure(String data);

}