package com.myretail.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
class AppConfig {
     RedSkyConfig redsky = new RedSkyConfig()
}
