package com.example.food_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food_app.databinding.BottomAddCategoryBinding
import com.example.food_app.databinding.FragmentShopListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShopListFragment : Fragment() {

    private lateinit var binding: FragmentShopListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentShopListBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNewCategory.setOnClickListener{
            showBottomSheet()
        }
    }


    private fun showBottomSheet(){
        //Dialog oluştur
        val dialog = BottomSheetDialog(requireContext())

        val sheetBinding = BottomAddCategoryBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)


        sheetBinding.btnSaveCategory.setOnClickListener {
            // Kategori ekleme kodları...
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        sheetBinding.btnCancelCategory.setOnClickListener {
            dialog.dismiss()
        }


    }


}