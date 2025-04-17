package ru.otus.pandina.helpers

import org.aeonbits.owner.ConfigFactory

class PropertiesReader {

    val testProperties : TestProperties = ConfigFactory.create(TestProperties::class.java, System.getProperties())
}