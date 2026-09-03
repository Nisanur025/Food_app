package com.example.food_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food_app.databinding.BottomDialogFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomDialogFilterBinding? = null
    private val binding get() = _binding!!

    // Filtre sonuçlarını ana Fragment'a bildirmek için callback
    var onApplyFilterListener: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomDialogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Uygula butonuna tıklandığında
        binding.btnApply.setOnClickListener {
            // Seçilen filtre değerlerini alıp ana ekrana gönder
            onApplyFilterListener?.invoke("Filtreler Uygulandı")
            dismiss() // BottomSheet'i kapatır
        }

        // Temizle butonuna tıklandığında
        binding.tvClear.setOnClickListener {
            // Filtreleri sıfırlama mantığı
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FilterBottomSheetFragment"
    }
}