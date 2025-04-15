package ml.docilealligator.infinityforreddit.post

import io.qameta.allure.Description
import io.qameta.allure.Feature
import ml.docilealligator.infinityforreddit.account.Account
import ml.docilealligator.infinityforreddit.message.Message
import ml.docilealligator.infinityforreddit.readpost.ReadPost
import ml.docilealligator.infinityforreddit.user.UserData
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@Feature("Unit tests")
@ExtendWith(MockitoExtension::class)
class PostTests {

    private lateinit var post: Post
    private lateinit var message: Message
    private lateinit var readPost: ReadPost
    private lateinit var account: Account

    @Description("Post test with mock")
    @Test
    fun testPostWithMock() {
        post = mock(Post::class.java)
        `when`(post.author).thenReturn("Test author")
        `when`(post.title).thenReturn("Test title")
        assert(post.author.equals("Test author"))
        assert(post.title.equals("Test title"))
        assert(!post.isRead)
        `when`(post.isRead).thenReturn(true)
        assert(post.isRead)
    }

    @Description("Message test with mock")
    @Test
    fun testMessageWithMock() {
        message = mock(Message::class.java)
        `when`(message.title).thenReturn("Message title")
        `when`(message.author).thenReturn("message author")
        `when`(message.body).thenReturn("message body")
        assert(message.author.equals("message author"))
        assert(message.title.equals("Message title"))
        assert(message.body.equals("message body"))
    }

    @Description("Read post test with mock")
    @Test
    fun testReadPostWithMock() {
        readPost = mock(ReadPost::class.java)
        `when`(readPost.id).thenReturn("123")
        assert(readPost.id.equals("123"))
    }

    @Description("Account test with mock")
    @Test
    fun testAccountWithMock() {
        account = mock(Account::class.java)
        `when`(account.accountName).thenReturn("Test account name")
        `when`(account.describeContents()).thenReturn(10)
        assert(account.accountName.equals("Test account name"))
        assert(account.describeContents() == 10)
    }

    @Description("User data test with mock")
    @Test
    fun testUserDataWithMock() {
        val userData = mock(UserData::class.java)
        `when`(userData.name).thenReturn("user test name")
        assert(userData.name.equals("user test name"))
    }

}

