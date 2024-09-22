package org.example.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager; //singleton, vì k cần nhiều hơn một ConfigurationManager
    private static Configuration myCurrentConfiguration;

    public ConfigurationManager() {
    }

    /**
     * Một số điểm cần cải thiện
     * Synchronized: Nếu lớp này sẽ được truy cập từ nhiều thread,
     * bạn nên đồng bộ hóa phương thức getInstance() để tránh tạo ra nhiều instance trong tình huống đa luồng.
     * */
    public static ConfigurationManager getInstance(){
        if(myConfigurationManager == null){
            myConfigurationManager = new ConfigurationManager();
        }

        return myConfigurationManager;
    }

    /**
     * Used to load a configuration file by the path provided
     * */
    public void loadConfigurationFile(String filePath)  {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new AppException(e);
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        try {
            while ((i = fileReader.read()) != -1){
                stringBuffer.append((char) i); // dùng để lưu lại nội dung của tệp
            }
        }catch (IOException e){
            throw new AppException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(stringBuffer.toString());
        } catch (IOException e) {
            throw new AppException("Error parsing the configuration file", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new AppException("Error parsing the configuration file, internal", e);
        }
    }

    /**
     * Returns the Current loaded Configuration
     * */
    public Configuration getCurrentConfiguration(){
        if(myCurrentConfiguration == null){
            throw new AppException("No current configuration to be set");
        }
        return myCurrentConfiguration;
    }

}
