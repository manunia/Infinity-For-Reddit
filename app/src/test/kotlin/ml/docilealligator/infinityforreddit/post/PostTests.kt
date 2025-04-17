package ml.docilealligator.infinityforreddit.post

import io.qameta.allure.Allure.step
import io.qameta.allure.Description
import io.qameta.allure.Feature
import ml.docilealligator.infinityforreddit.account.Account
import ml.docilealligator.infinityforreddit.markdown.MarkdownUtils
import ml.docilealligator.infinityforreddit.message.Message
import ml.docilealligator.infinityforreddit.readpost.ReadPost
import ml.docilealligator.infinityforreddit.user.UserData
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doThrow

@Feature("Unit tests")
@ExtendWith(MockitoExtension::class)
class PostTests {

    private lateinit var post: Post
    private lateinit var message: Message
    private lateinit var readPost: ReadPost
    private lateinit var account: Account

    @DisplayName("Post test with mock")
    @Description("Post test with mock")
    @Test
    fun testPostWithMock() {
        step("Mock class Post")
        post = mock(Post::class.java)
        `when`(post.author).thenReturn("Test author")
        `when`(post.title).thenReturn("Test title")
        step("Check fields")
        assert(post.author.equals("Test author"))
        assert(post.title.equals("Test title"))
        step("Check post is not read")
        assert(!post.isRead)
        step("make post read")
        `when`(post.isRead).thenReturn(true)
        step("Check post is read")
        assert(post.isRead)
    }

    @DisplayName("Message test with mock")
    @Description("Message test with mock")
    @Test
    fun testMessageWithMock() {
        step("Mock class Message")
        message = mock(Message::class.java)
        `when`(message.title).thenReturn("Message title")
        `when`(message.author).thenReturn("message author")
        `when`(message.body).thenReturn("message body")
        step("Check fields")
        assert(message.author.equals("message author"))
        assert(message.title.equals("Message title"))
        assert(message.body.equals("message body"))
    }

    @DisplayName("Read post test with mock")
    @Description("Read post test with mock")
    @Test
    fun testReadPostWithMock() {
        step("Mock class ReadPost")
        readPost = mock(ReadPost::class.java)
        `when`(readPost.id).thenReturn("123")
        step("Check fields")
        assert(readPost.id.equals("123"))
    }

    @DisplayName("Account test with mock")
    @Description("Account test with mock")
    @Test
    fun testAccountWithMock() {
        step("Mock class Account")
        account = mock(Account::class.java)
        `when`(account.accountName).thenReturn("Test account name")
        `when`(account.describeContents()).thenReturn(10)
        step("Check fields")
        assert(account.accountName.equals("Test account name"))
        assert(account.describeContents() == 10)
    }

    @DisplayName("User data test with mock")
    @Description("User data test with mock")
    @Test
    fun testUserDataWithMock() {
        step("Mock class UserData")
        val userData = mock(UserData::class.java)
        `when`(userData.name).thenReturn("user test name")
        step("Check fields")
        assert(userData.name.equals("user test name"))
    }

}

