package com.ecodisonante.signam.data

import com.ecodisonante.signam.model.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.capture
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class FirebaseUserServiceTest {

    @Mock
    private lateinit var database: DatabaseReference

    @Mock
    private lateinit var mockUsersRef: DatabaseReference

//    @Mock
//    private lateinit var mockQuery: Query

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
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        doNothing().`when`(mockUsersRef).addListenerForSingleValueEvent(capture(captor))

        // Act
        firebaseUserService.getByEmail(testUser.email) { user ->
            // Assert
            assertNull(user != null)
            assertEquals(testUser.email, user?.email)
        }
    }

    @Test
    fun getByEmail_returnsNull() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        doNothing().`when`(mockUsersRef).addListenerForSingleValueEvent(capture(captor))

        // Act
        firebaseUserService.getByEmail(testUser.email) { user ->
            // Assert
            assertNull(user)
        }
    }

    @Test
    fun createUser_returnsTrue() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        `when`(mockUsersRef.setValue(testUser)).thenReturn(Tasks.forResult(null))

        // Act
        firebaseUserService.createUser(testUser) { success ->
            // Assert
            assertTrue(success)
        }
    }

    @Test
    fun createUser_returnsFalse() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        `when`(mockUsersRef.setValue(any())).thenReturn(Tasks.forException(Exception("Test Error")))

        // Act
        firebaseUserService.createUser(testUser) { success ->
            // Assert
            assertFalse(success)
        }
    }

    @Test
    fun updateUser_returnsTrue() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        `when`(mockUsersRef.setValue(testUser)).thenReturn(Tasks.forResult(null))

        // Act
        firebaseUserService.updateUser(testUser) { success ->
            // Assert
            assertTrue(success)
        }
    }

    @Test
    fun updateUser_returnsFalse() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        `when`(mockUsersRef.setValue(any())).thenReturn(Tasks.forException(Exception("Test Error")))

        // Act
        firebaseUserService.updateUser(testUser) { success ->
            // Assert
            assertFalse(success)
        }
    }


    @Test
    fun deleteUser_returnsTrue() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        `when`(mockUsersRef.removeValue()).thenReturn(Tasks.forResult(null))

        // Act
        firebaseUserService.deleteUser(testUser.email) { success ->
            // Assert
            assertTrue(success)
        }
    }

    @Test
    fun deleteUser_returnsFalse() {
        // Arrange
        val testUser = User("user@test.com", "Test User", "123456")
        `when`(database.child("users")).thenReturn(mockUsersRef)
        `when`(mockUsersRef.child(anyString())).thenReturn(mockUsersRef)
        `when`(mockUsersRef.removeValue()).thenReturn(Tasks.forException(Exception("Test Error")))

        // Act
        firebaseUserService.deleteUser(testUser.email) { success ->
            // Assert
            assertFalse(success)
        }
    }
}
