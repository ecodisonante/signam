import com.ecodisonante.signam.data.FirebaseUserService
import com.ecodisonante.signam.model.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.capture
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class FirebaseUserServiceTest {

    @Mock
    private lateinit var database: DatabaseReference

    @Mock
    private lateinit var mockUsersRef: DatabaseReference

    @Mock
    private lateinit var mockQuery: Query

    @Captor
    private lateinit var captor: ArgumentCaptor<ValueEventListener>

    private lateinit var firebaseUserService: FirebaseUserService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        firebaseUserService = FirebaseUserService(database)
    }

    @Test
    fun getByEmail_returnsUser() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.orderByChild("email")).thenReturn(mockQuery)
        `when`(mockQuery.equalTo(testUser.email)).thenReturn(mockQuery)
        doNothing().`when`(mockQuery).addListenerForSingleValueEvent(capture(captor))

        // Act
        firebaseUserService.getByEmail(testUser.email) { user ->
            // Assert
            assert(user != null)
            assert(user?.email == testUser.email)
        }
    }

    @Test
    fun getByEmail_returnsNull() {
        // Arrange
        val testEmail = "notfound@example.com"
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.orderByChild("email")).thenReturn(mockQuery)
        `when`(mockQuery.equalTo(testEmail)).thenReturn(mockQuery)
        doNothing().`when`(mockQuery).addListenerForSingleValueEvent(capture(captor))

        // Act
        firebaseUserService.getByEmail(testEmail) { user ->
            // Assert
            assert(user == null)
        }
    }

    @Test
    fun createUser_returnsTrue() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.push()).thenReturn(mockUsersRef)
        `when`(mockUsersRef.setValue(any())).thenReturn(Tasks.forResult(null))

        // Act
        firebaseUserService.createUser(testUser) { success ->
            // Assert
            assert(success)
        }
    }

    @Test
    fun createUser_returnsFalse() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.push()).thenReturn(mockUsersRef)
        `when`(mockUsersRef.setValue(any())).thenReturn(Tasks.forException(Exception("Test Error")))

        // Act
        firebaseUserService.createUser(testUser) { success ->
            // Assert
            assert(success)
        }
    }


}
