package com.example.food_app

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.food_app.databinding.BottomGuestMenuFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class FilterBottomGuestMenu : BottomSheetDialogFragment() {
    private var _binding: BottomGuestMenuFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomGuestMenuFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipGroupCategories.isSingleSelection = true

        // Kendi kategorini oluştur -> Gönder butonu
        binding.btnSendCategory.setOnClickListener {
            val customCategory = binding.etYourCategory.text.toString().trim()
            if (customCategory.isNotEmpty()) {
                addNewCategoryChip(customCategory)
                binding.etYourCategory.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Kategori adı giriniz", Toast.LENGTH_SHORT).show()
            }
        }

        // Kendi süreni yaz -> Gönder butonu
        binding.btnSendTime.setOnClickListener {
            val customTime = binding.etYourTime.text.toString().trim()
            if (customTime.isNotEmpty()) {
                addNewPrepTimeRadioButton(customTime)
                binding.etYourTime.text?.clear()
            } else {
                Toast.makeText(requireContext(), "Süre giriniz", Toast.LENGTH_SHORT).show()
            }
        }

        // Sıfırla butonu
        binding.btnReset.setOnClickListener {
            binding.chipGroupCategories.clearCheck()
            binding.radioGroupPrepTime.clearCheck()
            binding.etYourCategory.text?.clear()
            binding.etYourTime.text?.clear()
            binding.etDishName.text?.clear()
        }

        binding.btnApplyFilter.setOnClickListener {
            //  Girilen yemek adı kontrolü
            val dishNameInput = binding.etDishName.text.toString().trim()
            if (dishNameInput.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Lütfen yemek adı giriniz",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener // Yemek adı boşsa işlemi durdur, kapatma
            }

            //Kategori Seçimi (Özel metin girildiyse onu, yoksa seçili Chip'i al)
            val customCategory = binding.etYourCategory.text.toString().trim()
            val selectedCategoryId = binding.chipGroupCategories.checkedChipId
            val selectedCategory = when {
                customCategory.isNotEmpty() -> customCategory
                selectedCategoryId != View.NO_ID -> {
                    val selectedView = binding.chipGroupCategories.findViewById<View>(selectedCategoryId)
                    (selectedView as? Chip)?.text?.toString() ?: "Genel"
                }
                else -> "Genel"
            }

            // Süre Seçimi (Özel metin girildiyse onu, yoksa seçili RadioButton'u al)
            val customTime = binding.etYourTime.text.toString().trim()
            val selectedRadioId = binding.radioGroupPrepTime.checkedRadioButtonId
            val selectedTime = when {
                customTime.isNotEmpty() -> customTime
                selectedRadioId != View.NO_ID -> {
                    binding.radioGroupPrepTime.findViewById<RadioButton>(selectedRadioId)?.text?.toString() ?: ""
                }
                else -> ""
            }

            // Verileri Ana Ekran'a aktar
            setFragmentResult(
                "REQUEST_KEY_FILTER",
                bundleOf(
                    "SELECTED_DISH_NAME" to dishNameInput,
                    "SELECTED_CATEGORY" to selectedCategory,
                    "SELECTED_TIME" to selectedTime
                )
            )

            // BottomSheet'i kapat
            dismiss()
        }
    }

    private fun addNewCategoryChip(categoryName: String) {
        val chipContext = androidx.appcompat.view.ContextThemeWrapper(
            requireContext(),
            com.google.android.material.R.style.Widget_Material3_Chip_Filter
        )
        val newChip = Chip(chipContext).apply {
            id = View.generateViewId()
            text = categoryName
            isCheckable = true
            isCheckedIconVisible = true

            setCheckedIconResource(com.google.android.material.R.drawable.ic_m3_chip_check)

            // Metin ve Arka plan renkleri
            setTextColor(Color.parseColor("#F2F2F0"))
            chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#3D1F1F"))
            chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#E0523F"))
            chipStrokeWidth = 1.5f * resources.displayMetrics.density

            // Tik İkonunun Rengi
            checkedIconTint = ColorStateList.valueOf(Color.parseColor("#E5A024"))
        }
        binding.chipGroupCategories.addView(newChip)
        newChip.isChecked = true
    }

    private fun addNewPrepTimeRadioButton(timeText: String) {
        val density = resources.displayMetrics.density

        val newRadioButton = com.google.android.material.radiobutton.MaterialRadioButton(requireContext()).apply {
            id = View.generateViewId()
            text = timeText
            textSize = 14f
            gravity = android.view.Gravity.CENTER_VERTICAL
            setTextColor(Color.parseColor("#F2F2F0"))

            // Arka Plan: gri kart arka planı
            setBackgroundColor(Color.parseColor("#2B2B2B"))

            // halka rengi
            val purpleColorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(android.R.attr.state_checked),  // Seçili durum (Mor)
                    intArrayOf(-android.R.attr.state_checked) // Seçili değilken (Koyu gri)
                ),
                intArrayOf(
                    Color.parseColor("#9C88FF"),
                    Color.parseColor("#666666")
                )
            )
            buttonTintList = purpleColorStateList

            //  İç boşluklar (Padding) ve Dış Boşluklar (Margin)
            setPadding(
                (14 * density).toInt(), // Sol
                (8 * density).toInt(),  // Üst
                (14 * density).toInt(), // Sağ
                (8 * density).toInt()   // Alt
            )

            layoutParams = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = (8 * density).toInt()
            }
        }

        binding.radioGroupPrepTime.addView(newRadioButton)
        newRadioButton.isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}