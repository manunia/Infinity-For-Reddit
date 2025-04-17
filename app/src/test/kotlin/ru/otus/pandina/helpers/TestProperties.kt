package ru.otus.pandina.helpers

import org.aeonbits.owner.Config

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources(
    "system:properties",
    "file:src/test/resources/test.properties"
)
interface TestProperties : Config {

    @Config.Key("numbersApiPath")
    fun numbersApiPath() : String
}