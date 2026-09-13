package com.example.food_app.data.repository

import com.example.food_app.UserDAO
import com.example.food_app.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Kayıt / giriş işlemlerinin veritabanı ile arasındaki köprü.
 * Fragment/ViewModel bu sınıfı çağırır, DAO'yu doğrudan bilmez.
 */
class UserRepository(private val userDao: UserDAO) {

    sealed class RegisterResult {
        data class Success(val userId: Long) : RegisterResult()
        object EmailAlreadyExists : RegisterResult()
        data class Error(val message: String) : RegisterResult()
    }

    sealed class LoginResult {
        data class Success(val user: UserEntity) : LoginResult()
        object InvalidCredentials : LoginResult()
        data class Error(val message: String) : LoginResult()
    }

    // NOT: "phone" parametresi kaldırıldı çünkü UserEntity'de böyle bir alan yok.
    suspend fun register(
        fullName: String,
        email: String,
        password: String
    ): RegisterResult = withContext(Dispatchers.IO) {
        try {
            val normalizedEmail = email.trim().lowercase()

            if (userDao.isEmailRegistered(normalizedEmail)) {
                return@withContext RegisterResult.EmailAlreadyExists
            }

            val user = UserEntity(
                fullName = fullName.trim(),
                email = normalizedEmail,
                password = password
            )

            val newId = userDao.insertUser(user)
            RegisterResult.Success(newId)
        } catch (e: Exception) {
            RegisterResult.Error(e.message ?: "Kayıt sırasında bir hata oluştu")
        }
    }

    suspend fun login(email: String, password: String): LoginResult = withContext(Dispatchers.IO) {
        try {
            val normalizedEmail = email.trim().lowercase()
            val user = userDao.login(normalizedEmail, password)

            if (user != null) {
                LoginResult.Success(user)
            } else {
                LoginResult.InvalidCredentials
            }
        } catch (e: Exception) {
            LoginResult.Error(e.message ?: "Giriş sırasında bir hata oluştu")
        }
    }
}