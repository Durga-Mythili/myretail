package com.myretail.repository

import com.mongodb.MongoClient
import com.myretail.dto.CurrentProductPrice
import com.myretail.util.CurrencyCode
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.IMongoConfig
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import spock.lang.Specification

@DataMongoTest
class CustomPriceRepoImplSpec extends Specification {
    MongodExecutable mongodExecutable
    MongoTemplate mongoTemplate
    CustomProductPriceRepoImpl productPriceRepo
    CurrentProductPrice currentProductPrice

    void setup() {
        String ip = "localhost"
        int port = 27017
        IMongoConfig mongoConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION).net(new Net(ip, port, Network.localhostIsIPv6())).build()
        MongodStarter starter = MongodStarter.getDefaultInstance()
        mongodExecutable = starter.prepare(mongoConfig)
        mongodExecutable.start()
        mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "myretail_test")
        productPriceRepo = new CustomProductPriceRepoImpl(mongoTemplate)

        currentProductPrice = new CurrentProductPrice(
                productId: 12345,
                price: 23,
                currencyCode: CurrencyCode.USD
        )
        mongoTemplate.save(currentProductPrice)

    }

    void cleanup() {
        mongodExecutable.stop()
    }

    void 'I can update currentProduct-price'() {
        when:
        int result = productPriceRepo.updatePrice(12345, 30)
        then:
        result == 1

    }

    void 'I cannot update currentProduct-price'() {
        when:
        int result = productPriceRepo.updatePrice(12346, 30)
        then:
        result == 0

    }
}
