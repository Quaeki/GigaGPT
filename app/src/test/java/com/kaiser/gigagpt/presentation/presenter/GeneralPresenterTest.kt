@ExperimentalCoroutinesApi
class GeneralPresenterTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var presenter: GeneralPresenter
    private lateinit var repository: ChatRepository
    private lateinit var view: FakeGeneralView

    @Before
    fun setup() {
        repository = mock()
        presenter = GeneralPresenter(repository)
        view = FakeGeneralView()
        presenter.attachView(view)
    }

    @Test
    fun `when sending message, should show loading and clear input`() = runTest {
        // Given
        val message = "Test message"
        whenever(repository.sendMessage(message)).thenReturn(Unit)

        // When
        presenter.sendMessage(message)

        // Then
        assertTrue(view.loadingShown)
        assertTrue(view.inputCleared)
        verify(repository).sendMessage(message)
    }

    @Test
    fun `when loading messages fails, should show error`() = runTest {
        // Given
        whenever(repository.getMessages()).thenThrow(IOException())

        // When
        presenter.loadMessages()

        // Then
        assertNotNull(view.lastError)
        assertTrue(view.lastError!!.contains("сети"))
    }

    private class FakeGeneralView : GeneralContract.View {
        var loadingShown = false
        var inputCleared = false
        var lastError: String? = null
        var messages: List<ChatMessage>? = null

        override fun showLoading() { loadingShown = true }
        override fun hideLoading() { loadingShown = false }
        override fun showError(message: String) { lastError = message }
        override fun clearInput() { inputCleared = true }
        override fun showMessages(messages: List<ChatMessage>) { 
            this.messages = messages 
        }
    }
} 