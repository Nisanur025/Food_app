package com.example.food_app.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.food_app.AppDatabase
import com.example.food_app.LoginFragment
import com.example.food_app.R
import com.example.food_app.data.repository.UserRepository
import com.example.food_app.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch

/**
 * Food app kayıt ol (sign up) ekranı.
 * Kullanıcı bilgilerini doğrular ve Room (SQLite) veritabanına kaydeder.
 * aynı Activity'deki fragmentContainer üzerinden LoginFragment'a dönülüyor.
 */
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getInstance(requireContext())
        userRepository = UserRepository(database.userDao())

        binding.btnRegister.setOnClickListener {
            if (validateInputs()) {
                registerUser()
            }
        }

        binding.tvLoginLink.setOnClickListener {
            goToLoginFragment()
        }

        // Şifre Açık mı Kapalı mı
        var isPasswordVisible = false

        val password = view.findViewById<EditText>(R.id.tillPassword)
        val togglePasswordIv = view.findViewById<ImageView>(R.id.togglePasswordIv)
        val togglePasswordIvConfirm = view.findViewById<ImageView>(R.id.togglePasswordIvConfirm)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
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
        togglePasswordIvConfirm.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {
               etConfirmPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePasswordIvConfirm.setImageResource(R.drawable.ic_eye_open)
            } else {
                etConfirmPassword.inputType = android.text.InputType.TYPE_CLASS_TEXT or
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePasswordIvConfirm.setImageResource(R.drawable.ic_eye_hide)
            }

            etConfirmPassword.setSelection(etConfirmPassword.text.length)
        }


    }

    private fun goToLoginFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, LoginFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val fullName = binding.tillFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.tillPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (fullName.isEmpty()) {
            binding.tillFullName.error = "Ad soyad boş bırakılamaz"
            isValid = false
        } else {
            binding.tillFullName.error = null
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "E-posta boş bırakılamaz"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Geçerli bir e-posta adresi girin"
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tillPassword.error = "Şifre boş bırakılamaz"
            isValid = false
        } else if (password.length < 6) {
            binding.tillPassword.error = "Şifre en az 6 karakter olmalı"
            isValid = false
        } else {
            binding.tillPassword.error = null
        }

        if (confirmPassword != password) {
            binding.etConfirmPassword.error = "Şifreler eşleşmiyor"
            isValid = false
        } else {
            binding.etConfirmPassword.error = null
        }

        if (!binding.cbTerms.isChecked) {
            Toast.makeText(
                requireContext(),
                "Devam etmek için kullanım koşullarını kabul etmelisiniz",
                Toast.LENGTH_SHORT
            ).show()
            isValid = false
        }

        return isValid
    }

    /**
     * Doğrulama başarılıysa kullanıcıyı SQLite (Room 3.0) veritabanına kaydeder.
     * Tüm veritabanı işlemleri (kayıt) burada, SignUpFragment içinde yapılıyor.
     */
    private fun registerUser() {
        val fullName = binding.tillFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.tillPassword.text.toString()

        setLoading(true)

        viewLifecycleOwner.lifecycleScope.launch {
            val result = userRepository.register(fullName, email, password)
            setLoading(false)

            when (result) {
                is UserRepository.RegisterResult.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "Kayıt başarılı! Hoş geldin, $fullName",
                        Toast.LENGTH_LONG
                    ).show()
                    goToLoginFragment()
                }

                is UserRepository.RegisterResult.EmailAlreadyExists -> {
                    binding.etEmail.error = "Bu e-posta zaten kayıtlı"
                }

                is UserRepository.RegisterResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Hata: ${result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}