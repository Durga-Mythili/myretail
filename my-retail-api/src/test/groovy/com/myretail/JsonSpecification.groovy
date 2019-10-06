package com.myretail

import spock.lang.Specification
import com.fasterxml.jackson.databind.ObjectMapper

import javax.annotation.Resources

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
            return stripWhiteSpace(inputStream.text)
        }
        throw new FileNotFoundException(resourcePath)
    }

    private static String stripWhiteSpace(String str) {
        StringBuffer out = new StringBuffer()
        str.eachLine {
            out << it.replaceFirst(/": /, '":').replaceFirst(/" : /, '":').trim()
        }
        return out.toString()
    }


}
