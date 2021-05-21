package com.harinder.JSONParser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParseJSON implements StructureParser{
  
  private JsonFactory jf ;
  
  public ParseJSON(){
    super();
    jf = new JsonFactory();
  }
  
  @Override
  public ArrayList<KeyPathMetadata> parse(String json) {
    String lastKey = null;
    ArrayList<KeyPathMetadata> listKPM = null;

    try {
      Stack<String> keyNames = new Stack<>();
      listKPM = new ArrayList<KeyPathMetadata> ();
      JsonParser parser = jf.createParser(json);
      while(parser.nextToken() != null){

        if(parser.getCurrentToken() == JsonToken.START_OBJECT){
          if(lastKey != null){
            keyNames.push(lastKey);
            lastKey = null;
          }else{
            keyNames.push(null);
          }
        }
        else if(parser.getCurrentToken() == JsonToken.END_OBJECT){
          lastKey = null;
            keyNames.pop( );
        }
        else if(parser.getCurrentToken() == JsonToken.START_ARRAY){
          if(lastKey != null){
            keyNames.push(lastKey);
            lastKey = null;
          }else{
            keyNames.push(null);
          }
        }
        else if(parser.getCurrentToken() == JsonToken.END_ARRAY){
          lastKey = null;
          keyNames.pop( );
        }
        else if(parser.getCurrentToken() == JsonToken.FIELD_NAME){
          lastKey = parser.getText().toString();
        }else if(parser.getCurrentToken() == JsonToken.VALUE_NULL ){
          populateValue(lastKey, listKPM, keyNames, null);
        }else if(parser.getCurrentToken() == JsonToken.VALUE_STRING ||
          parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT  ||
          parser.getCurrentToken() == JsonToken.VALUE_FALSE||
          parser.getCurrentToken() == JsonToken.VALUE_TRUE ||
          parser.getCurrentToken() == JsonToken.VALUE_NUMBER_FLOAT
          ){
          String value = parser.getText().toString();
          populateValue(lastKey, listKPM, keyNames, value);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return listKPM;
  }

  private void populateValue(String lastKey, ArrayList<KeyPathMetadata> listKPM,
      Stack<String> keyNames, String value) {
    List<String> keyList = new ArrayList<String> ();
    for(String keyN: keyNames){ // to remove the nulls
      if(keyN != null){
        keyList.add(keyN);
      }
    }

    if(lastKey != null){
      keyList.add(lastKey);
    }

    KeyPathMetadata KPM = new KeyPathMetadata();

    KPM.setKey(keyList);
    KPM.setValue(value);
    listKPM.add(KPM);
  }

  @Override
  public boolean isValidStructure(String data) {
    try {
    	data = data.trim();
    	if(data.startsWith("[") && data.endsWith("]")){
    		data = data.substring(1, data.length()-1).trim();      
    	}
    	if((data.startsWith("{") && data.endsWith("}"))){
    		final ObjectMapper mapper = new ObjectMapper();
	        mapper.readTree(data);
	        return true;
	    }
      return false;
    } catch (IOException e) {
       return false;
    }
  }
  
  public static void main(String[] args) {
	  
	  ParseJSON pj = new ParseJSON();
	  
	  List<KeyPathMetadata> harinder =pj.parse("[\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 11:25:49 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 4,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"HIPAA_Hadoop\",\r\n" + 
	  		"        \"policyName\": \"HIPAA_Hadoop\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"NA\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 11:25:49 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 5,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"PCI_Hadoop\",\r\n" + 
	  		"        \"policyName\": \"PCI_Hadoop\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"NA\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 11:25:49 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 6,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"PII_Hadoop\",\r\n" + 
	  		"        \"policyName\": \"PII_Hadoop\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"NA\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 11:25:49 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 7,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"GDPR_Hadoop\",\r\n" + 
	  		"        \"policyName\": \"GDPR_Hadoop\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"NA\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 13:10:58 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10001,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"RestAutomationPolicy\",\r\n" + 
	  		"        \"policyName\": \"HbasePolicy_25873\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"19 Jul 2020 13:10:58 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 13:13:24 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10002,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"RestAutomationPolicy\",\r\n" + 
	  		"        \"policyName\": \"HbasePolicy_16099\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"19 Jul 2020 13:13:24 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 13:14:54 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10003,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"SamplePolicy\",\r\n" + 
	  		"        \"policyName\": \"HARINDER_NEW_POLICY_1\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"19 Jul 2020 13:14:54 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 13:20:50 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10004,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"RestAutomationPolicy\",\r\n" + 
	  		"        \"policyName\": \"HbasePolicy_24557\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"19 Jul 2020 13:20:50 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 13:22:13 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10005,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"SamplePolicy\",\r\n" + 
	  		"        \"policyName\": \"HARINDER_NEW_POLICY_2\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"19 Jul 2020 13:22:13 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"19 Jul 2020 13:23:02 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10006,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"SamplePolicy\",\r\n" + 
	  		"        \"policyName\": \"HARINDER_NEW_POLICY_3\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"19 Jul 2020 13:23:02 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"21 Jul 2020 20:15:43 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10007,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"t\",\r\n" + 
	  		"        \"policyName\": \"t1\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"21 Jul 2020 20:15:43 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"21 Jul 2020 21:12:55 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10008,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutomationPolicyDefaultNAme\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"21 Jul 2020 21:12:55 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"21 Jul 2020 21:24:55 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10009,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_1\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"21 Jul 2020 21:24:55 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"21 Jul 2020 21:25:24 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10010,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_2\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"21 Jul 2020 21:25:24 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"23 Jul 2020 04:50:12 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10019,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_1A\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"23 Jul 2020 04:50:12 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"23 Jul 2020 05:05:04 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10020,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_2a\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"23 Jul 2020 05:05:04 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"23 Jul 2020 06:00:58 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10021,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_2B\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"23 Jul 2020 06:00:58 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"23 Jul 2020 06:01:19 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10023,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_2c\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"23 Jul 2020 06:01:19 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"23 Jul 2020 06:02:11 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10025,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_2D\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"23 Jul 2020 06:02:11 GMT\"\r\n" + 
	  		"    },\r\n" + 
	  		"    {\r\n" + 
	  		"        \"createts\": \"23 Jul 2020 06:06:34 GMT\",\r\n" + 
	  		"        \"encrypted\": false,\r\n" + 
	  		"        \"id\": 10026,\r\n" + 
	  		"        \"isGdprPolicy\": false,\r\n" + 
	  		"        \"isHidden\": false,\r\n" + 
	  		"        \"policyDescription\": \"Auto generated policy\",\r\n" + 
	  		"        \"policyName\": \"AutoPolicy_2E\",\r\n" + 
	  		"        \"source\": \"Unstructured\",\r\n" + 
	  		"        \"updatets\": \"23 Jul 2020 06:06:34 GMT\"\r\n" + 
	  		"    }\r\n" + 
	  		"]");
	  
	  System.out.println(harinder.size());
	  
	int i =6;  
	while (i<harinder.size()) {  
	System.out.println(harinder.get(i).getKey()+":"+harinder.get(i).getValue());
	i = i+9;
	}
}
}