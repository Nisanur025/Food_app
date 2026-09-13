package com.example.food_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.food_app.data.repository.UserRepository
import com.example.food_app.ui.auth.SignUpFragment
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mail_et = view.findViewById<EditText>(R.id.mail_et)
        val password_et = view.findViewById<EditText>(R.id.password_et)
        val loginBtn = view.findViewById<Button>(R.id.login_btn)
        val registerBtn = view.findViewById<TextView>(R.id.btnRegister)

        val db = AppDatabase.getInstance(requireContext())
        val repository = UserRepository(db.userDao())

        // 1. GİRİŞ YAP (LOGIN) BUTONU
        loginBtn.setOnClickListener {
            val mail = mail_et.text.toString().trim()
            val password = password_et.text.toString().trim()

            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Hata: E-posta veya şifre boş bırakılamaz",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                lifecycleScope.launch {
                    val result = repository.login(email = mail, password = password)

                    when (result) {
                        is UserRepository.LoginResult.Success -> {
                            Toast.makeText(requireContext(), "Giriş Başarılı!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish() // Giriş sonrası login ekranını kapatır
                        }
                        is UserRepository.LoginResult.InvalidCredentials -> {
                            Toast.makeText(
                                requireContext(),
                                "Hata: E-posta veya şifre yanlış!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UserRepository.LoginResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Hata: ${result.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // 2. KAYIT OL (REGISTER) BUTONU
        registerBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }

        // 3. Şifre Açık mı Kapalı mı
        // Şifrenin şu an açık mı kapalı mı olduğunu takip etmek için değişken
             var isPasswordVisible = false

            val password = view.findViewById<EditText>(R.id.password_et)
            val togglePasswordIv = view.findViewById<ImageView>(R.id.togglePasswordIv)

            togglePasswordIv.setOnClickListener {
                isPasswordVisible = !isPasswordVisible

                if (isPasswordVisible) {
                    // Şifreyi GÖRÜNÜR yap
                    password.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                            android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    // İkonu ic_eye_open ile değiştir
                    togglePasswordIv.setImageResource(R.drawable.ic_eye_open)
                } else {
                    // Şifreyi GİZLİ yap
                    password.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                            android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                    // İkonu ic_eye_close ile değiştir
                    togglePasswordIv.setImageResource(R.drawable.ic_eye_hide)
                }

                // İmleci (cursor) metnin en sonuna taşı
                password.setSelection(password.text.length)
            }

        // 4. Şifremi unuttum BUTONU
        val forgot_psw = view.findViewById<TextView>(R.id.forgot_pw)
        forgot_psw.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ForgotPasswordFragment())
                .addToBackStack(null)
                .commit()
        }

    }
}