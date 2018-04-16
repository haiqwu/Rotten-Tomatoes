package com.peppa.peppamovies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import properties_manager.InvalidXMLFileFormatException;
import properties_manager.PropertiesManager;

@SpringBootApplication
public class PeppamoviesApplication {
	public static void main(String[] args)
	{
		boolean isEnglish = true;
		if( isEnglish  )
			loadProperties("app_properties.xml");
		else{}
		SpringApplication.run(PeppamoviesApplication.class, args);
	}

	public static boolean loadProperties(String propertiesFileName) {
		PropertiesManager props = PropertiesManager.getPropertiesManager();
		try {
			props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, "src/main/java/com/peppa/peppamovies/properties/");
			props.loadProperties(propertiesFileName, "properties_schema.xsd");
			return true;
		} catch (InvalidXMLFileFormatException ixmlffe) {
			System.out.println("init xml failed.");
			return false;
		}
	}
}

