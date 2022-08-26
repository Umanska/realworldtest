package org.realworld.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManagerUtils {

    private static final String propertiesFileName = "env.properties";

    public static String getProperty(String propertyName) {

        try (InputStream input = new FileInputStream(propertiesFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(propertyName);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
