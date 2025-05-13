/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOT;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author SIO
 */
public class Config {

    Properties properties;
    final private String DIRECTORYPROJECT = System.getProperty("user.dir");

    public Config() throws IOException {
        properties = new Properties();
        try (InputStream input = new FileInputStream(DIRECTORYPROJECT + File.separator + "config.properties")) {
            if (input == null) {
                throw new IOException("config.properties file not found");
            }
            // Load key-value pairs from the config file
            properties.load(input);
        }
    }

    public String getTokenOpenAI() {
        return properties.getProperty("TOKENOPENAI");
                
    }
        
    }
    
    

