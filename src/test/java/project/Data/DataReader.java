package project.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String,String>> jsonDataToMap() throws IOException {
		
		//In the below step,we are converting the json file to a string object with name jsonContent with the help of FileUtils.
		String jsonContent=FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\project\\Data\\PurchaseOrder.json"),StandardCharsets.UTF_8);
		
		//By the help of Jackson Databind,we are converting the string to Hashmap which we have added in pom.xml.
		//Now from the above step,we got the string.Now the string is being converted to Hashmap by the help of readValue and stores it in list. 
		ObjectMapper mapper=new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});
		
		return data;
	}

}
