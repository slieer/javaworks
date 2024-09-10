package org.slieer.http.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by L.cm
 * Date: 2015-25-12 17:57
 */
public final class JsonUtils {
    private JsonUtils() {}

    /**
     * 将对象序列化成json字符串
     * @param object javaBean
     * @return jsonString json字符串
     */
    public static String toJson(Object object) {
        try {
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     *  
     */
    public static String toFormatedJson(Object object) {
    	ObjectWriter writer = getInstance().writerWithDefaultPrettyPrinter();
        try {
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 将json反序列化成对象
     * @param jsonString jsonString
     * @param valueType class
     * @param <T> T 泛型标记
     * @return Bean
     */
    public static <T> T parse(String jsonString, Class<T> valueType) {
        try {
            return getInstance().readValue(jsonString, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final static JacksonObjectMapper mapper;
    static {
    	mapper = new JacksonObjectMapper();
    }
    public static ObjectMapper getInstance() {
        return mapper;
    }

    public static ObjectNode getObject() {
        return mapper.createObjectNode();
    }

    public static ArrayNode getArray() {
        return mapper.createArrayNode();
    }
    
    private static class JacksonObjectMapper extends ObjectMapper {
        private static final long serialVersionUID = 4288193147502386170L;
        private static final Locale CHINA = Locale.CHINA;
        
        public JacksonObjectMapper() {
            this.setLocale(CHINA);
            this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", CHINA));
            this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
    }
}
