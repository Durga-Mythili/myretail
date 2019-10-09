package com.retail

import com.fasterxml.jackson.databind.ObjectMapper
import com.myretail.ObjectMapperBuilder
import spock.lang.Specification

class ObjectMapperBuilderSpec extends Specification {
    static class Person {
        String firstName = "John Doe"
        Integer grade = 10
        Section section
    }
    enum Section {
        A
    }

    void 'I can convert object to snakecase'() {
        given:
        ObjectMapper objectMapper = new ObjectMapperBuilder().build()
        Person person = new Person()
        person.section = Section.A
        when:
        String json = objectMapper.writeValueAsString(person)
        then:
        println(json)
        json == '{"first_name":"John Doe","grade":10,"section":"A"}'
    }

    void 'I can convert snakecase json to object'() {
        String json = '{"first_name":"John Doe","grade":10,"section":"A"}'
        ObjectMapper objectMapper = new ObjectMapperBuilder().build()

        when:
        Person person = objectMapper.readValue(json, Person)

        then:
        noExceptionThrown()
        person
    }

}
