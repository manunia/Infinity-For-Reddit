package ru.otus.pandina.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.qameta.allure.Allure
import io.qameta.allure.Allure.step
import io.qameta.allure.Description
import io.qameta.allure.Feature
import io.qameta.allure.Severity
import io.qameta.allure.SeverityLevel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import ru.otus.pandina.helpers.PropertiesReader


@Feature("Тестирование API")
class NumbersApiTests {

    lateinit var client: HttpClient
    lateinit var response: HttpResponse

    @BeforeEach
    fun setUp() {
        client = HttpClient(engineFactory = io.ktor.client.engine.cio.CIO) {
            install(DefaultRequest) {
                url(PropertiesReader().testProperties.numbersApiPath())
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }


    @DisplayName("Факт о целом числе")
    @Description("Факт о целом числе")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun mathTest() {
        step("Получить факт о целом числе")
        runBlocking {
            response = client.get("45/math")
            assertEquals(response.status, HttpStatusCode.OK)
            assertNotNull(response.body())
            assertTrue(response.bodyAsText().startsWith("45"))
        }
    }

    @DisplayName("Список фактов о ряде чисел")
    @Description("Список фактов о ряде чисел")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun rangesMathTest() {
        step("Получить список фактов о рядечисел")
        runBlocking {
            response = client.get("45,32,9/math")
            assertNotNull(response.body())
            assertEquals(response.status, HttpStatusCode.OK)
        }
    }

    @DisplayName("Число с плавающей точкой негативный сценарий")
    @Description("Число с плавающей точкой негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun unhappyMathTest() {
        step("Получить факт о числе с плавающей точкой")
        runBlocking {
            response = client.get("42.2/math")
            assertTrue(response.status.equals(HttpStatusCode.BadRequest))
            assertEquals(response.bodyAsText(), "Invalid url")
        }
    }

    @DisplayName("Список фактов о дате")
    @Description("Список фактов о дате")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun dateTest() {
        step("Получить список фактов о дате")
        runBlocking {
            response = client.get("5/date")
            assertNotNull(response.body())
            assertTrue(response.status.equals(HttpStatusCode.OK))
        }
    }

    @DisplayName("Список фактов о месяце негативный")
    @Description("Список фактов о месяце негативный")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun monthNegativeTest() {
        step("Получить факты о месяце")
        runBlocking {
            response = client.get("0/month")
            assertTrue(response.bodyAsText().contains("Cannot GET /0/month"))
            assertTrue(response.status.equals(HttpStatusCode.NotFound))
        }
    }

    @DisplayName("Округлить в меньшую сторону до ближайшего числа, с которым связан факт, и вернуть этот факт")
    @Description("Округлить в меньшую сторону до ближайшего числа, с которым связан факт, и вернуть этот факт")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun roundTest() {
        step("Округлить в меньшую сторону")
        runBlocking {
            response = client.get("35353?notFound=floor")
            assertNotNull(response.body())
            assertTrue(response.status.equals(HttpStatusCode.OK))
        }
    }

    @DisplayName("Факт в виде фрагмента предложения")
    @Description("Факт в виде фрагмента предложения")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    fun fragmentTest() {
        step("Получить факт в виде фрагмента")
        runBlocking {
            response = client.get("1969/year?fragment")
            assertTrue(response.status.equals(HttpStatusCode.OK))
            assertNotNull(response.body())
            assertFalse(response.bodyAsText().startsWith("1969"))
        }
    }

    @AfterEach
    fun tearDown() {
        Allure.addAttachment("Результат", "text/plain", response.toString())
    }
}