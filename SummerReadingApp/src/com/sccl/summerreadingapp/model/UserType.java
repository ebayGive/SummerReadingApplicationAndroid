package com.sccl.summerreadingapp.model;

import java.util.HashMap;
import java.util.Map;

public class UserType {

	static Map<String, String> idToDescription = new HashMap<String, String>();
	
	static 
	{
		idToDescription.put("C2747BE4-E0C9-45AC-9E50-549F43B49D31", "Reader");
		idToDescription.put("3E7CAEB5-90BF-4109-8A1E-5703D8FDC063", "Pre-Reader");
		idToDescription.put("FA97845D-7C8D-4C2C-8B80-C46B5DB6A03C", "Teen");
		idToDescription.put("49DABF22-C5BE-48F3-9119-5C8DDFF781B8", "Adult");
		idToDescription.put(null, "Reader");
	}
	
	static public String getDescription(String id)
	{
		return idToDescription.get(id);
	}
}