package com.myretail

import com.google.common.io.Resources
import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper

class JsonSpecification extends Specification {
    static final ObjectMapper objectMapper = new ObjectMapperBuilder().build()

    static <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz)
    }

    static String jsonFromFixture(String fixture) {
        String path = "fixtures/${fixture}.json"
        return jsonFromResource(path)
    }

    static String jsonFromResource(String resourcePath) {
        InputStream inputStream = Resources.getResource(resourcePath)?.openStream()
        if (inputStream) {
            StringBuffer objContent = new StringBuffer()
            inputStream.eachLine {
                objContent << it.replaceFirst(/": /, '":')
                        .replaceFirst(/" : /, '":')
                        .trim()
            }
            return objContent.toString()
        }
        throw new FileNotFoundException(resourcePath)
    }
}
