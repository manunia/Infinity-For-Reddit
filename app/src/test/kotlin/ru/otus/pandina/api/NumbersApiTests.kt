package ru.otus.pandina.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.qameta.allure.Description
import io.qameta.allure.Feature
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


@Feature("Тестирование API")
class NumbersApiTests {

    val client = HttpClient(engineFactory = io.ktor.client.engine.cio.CIO) {
        install(DefaultRequest) {
            url("http://numbersapi.com/")
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    @Description("Факт о целом числе")
    @Test
    fun mathTest() {
        runBlocking {
            val response = client.get("45/math")

            assertEquals(response.status, HttpStatusCode.OK)
            assertNotNull(response.body())
            assertTrue(response.bodyAsText().startsWith("45"))
        }
    }

    @Description("Список фактов о ряде чисел")
    @Test
    fun rangesMathTest() {
        runBlocking {
            val response = client.get("45,32,9/math")
            assertNotNull(response.body())
            assertEquals(response.status, HttpStatusCode.OK)
        }
    }

    @Description("Число с плавающей точкой негативный сценарий")
    @Test
    fun unhappyMathTest() {
        runBlocking {
            val response = client.get("42.2/math")
            assertTrue(response.status.equals(HttpStatusCode.BadRequest))
            assertEquals(response.body(), "Invalid url")
        }
    }

    @Description("Список фактов о дате")
    @Test
    fun dateTest() {
        runBlocking {
            val response = client.get("5/date")
            assertNotNull(response.body())
            assertTrue(response.status.equals(HttpStatusCode.OK))
        }
    }

    @Description("Округлить в меньшую сторону до ближайшего числа, с которым связан факт, и вернуть этот факт")
    @Test
    fun roundTest() {
        runBlocking {
            val response = client.get("35353?notFound=floor")
            assertNotNull(response.body())
            assertTrue(response.status.equals(HttpStatusCode.OK))
        }
    }

    @Description("Факт в виде фрагмента предложения")
    @Test
    fun fragmentTest() {
        runBlocking {
            val response = client.get("1969/year?fragment")
            assertTrue(response.status.equals(HttpStatusCode.OK))
            assertNotNull(response.body())
            assertFalse(response.bodyAsText().startsWith("1969"))
        }
    }
}