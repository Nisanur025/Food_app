package com.example.food_app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * "users" tablosu üzerinde SQL sorgularını çalıştıran DAO.
 * Room, bu arayüzün implementasyonunu derleme zamanında otomatik üretir.
 */
@Dao
interface UserDAO {

    /**
     * Yeni kullanıcı ekler. E-posta zaten varsa (UNIQUE index sayesinde)
     * çakışma olur ve insert başarısız olur -> -1 döner.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    /**
     * Girilen e-postanın veritabanında olup olmadığını kontrol eder.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email LIMIT 1)")
    suspend fun isEmailRegistered(email: String): Boolean

    /**
     * E-posta ve şifreye göre kullanıcıyı bulur (login için).
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    /**
     * Tek bir kullanıcıyı e-postaya göre getirir.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    /**
     * Tüm kullanıcıları listeler (debug/admin amaçlı).
     */
    @Query("SELECT * FROM users ORDER BY created_at DESC")
    suspend fun getAllUsers(): List<UserEntity>
}