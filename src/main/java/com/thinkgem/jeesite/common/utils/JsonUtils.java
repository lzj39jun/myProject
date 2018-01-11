package com.thinkgem.jeesite.common.utils;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);

    public JsonUtils() {
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            Object obj = null;

            try {
                obj = objectMapper.readValue(json, clazz);
            } catch (Exception var5) {
                log.error(var5.getMessage(), var5);
            }

            return (T) obj;
        }
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            List obj = null;

            try {
                obj = (List)objectMapper.readValue(json, (new ObjectMapper()).getTypeFactory().constructParametricType(ArrayList.class, new Class[]{clazz}));
            } catch (Exception var5) {
                log.error(var5.getMessage(), var5);
            }

            return obj;
        }
    }

    public static String object2Json(Object object) {
        if (object == null) {
            return "";
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                return objectMapper.writeValueAsString(object);
            } catch (Exception var3) {
                log.error(var3.getMessage(), var3);
                return "";
            }
        }
    }

    public static String format(String source) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Object obj = mapper.readValue(source, Object.class);
            String formatData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            return formatData;
        } catch (IOException var4) {
            log.error("format json failed,", var4);
            return source;
        }
    }

    public static String compress(String source) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Object obj = mapper.readValue(source, Object.class);
            String formatData = mapper.writeValueAsString(obj);
            return formatData;
        } catch (IOException var4) {
            log.error("compress json failed,", var4);
            return source;
        }
    }

    public static void main(String[] args) {
    }

    @JsonTypeInfo(
            use = Id.CLASS,
            include = As.PROPERTY
    )
    static class UserBean {
        private String username;
        private String pwd;

        UserBean() {
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPwd() {
            return this.pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    }
}

