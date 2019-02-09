package com.ParkingLibrary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.net.PortProber;

public class JavaPropertiesManager {
	final static Logger logger = Logger.getLogger(JavaPropertiesManager.class);

	private String PropertiesFile;
	private Properties Prop;
	private OutputStream output;
	private InputStream input;

	public JavaPropertiesManager(String propertiesFilePath) {

		PropertiesFile = propertiesFilePath;
		Prop = new Properties();

	}

	public String readProperty(String key) {
		String value = null;

		try {
			input = new FileInputStream(PropertiesFile);
			Prop.load(input);
			value = Prop.getProperty(key);

		} catch (Exception e) {
			logger.error("Error:", e);

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					logger.error("Error: ", e);
				}

			}
		}

		return value;
	}

	public void setProperty(String key, String value) {

		try {
			output = new FileOutputStream(PropertiesFile);
			Prop.setProperty(key, value);
			Prop.store(output, null);

		} catch (Exception e) 
		{
			logger.error("Error:",e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			}
		}
	}

	public static void main(String[] args) {
		
		/*JavaPropertiesManager javaManger = new JavaPropertiesManager("src/test/resources/confiq.properties");
	     String value = javaManger.readProperty("browserType");
	     logger.info("property value is: "+ value);
		
		JavaPropertiesManager javaProperty = new JavaPropertiesManager("src/test/resources/confiq.properties");
		javaProperty.setProperty("name", "frank");

		
		JavaPropertiesManager javaproperty = new JavaPropertiesManager("src/test/resources/test.properties");
		javaproperty.setProperty("car", "Dodge");
*/		
	   	}
	
}
