package ru.otus.cache.configuration;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component("cacheProperties")
public class CacheProperties {

    static String CONFIGURATION_FILE_PATH = "/cache.properties";
    static String CAPACITY_PROPERTY = "capacity";
    static String DATA_LOST_SECONDS_PROPERTY = "data.lost.interval.seconds";
    int dataLostSeconds;

    Properties properties = new Properties();

    public CacheProperties() {
        try (InputStream is = this.getClass().getResourceAsStream(CONFIGURATION_FILE_PATH)){
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataLostSeconds = getDataLostSeconds();
    }

    public Integer getCapacity(){
        return Integer.parseInt(properties.getProperty(CAPACITY_PROPERTY));
    }

    public Integer getDataLostSeconds(){
        return Integer.parseInt(properties.getProperty(DATA_LOST_SECONDS_PROPERTY));
    }
}
