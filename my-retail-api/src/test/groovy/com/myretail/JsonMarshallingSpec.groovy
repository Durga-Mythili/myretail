package com.myretail

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Unroll

class JsonMarshallingSpec extends JsonSpecification {
    ObjectMapper objectMapper = new ObjectMapperBuilder().build()

  /*  @Unroll
    void 'can marshall #fixture to and from Json'() {
        given:
        String json = jsonFromFixture(fixture)

        when:
        def object = objectMapper.readValue(json, clazz)
        String backToJson = objectMapper.writeValueAsString(object)

        then:
        json == backToJson

        where:
        fixture                      | clazz
    }*/

}
