package com.example.food_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food_app.databinding.BottomAddCategoryBinding
import com.example.food_app.databinding.FragmentRecipeDetailBinding
import com.example.food_app.databinding.FragmentShopListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentShopListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



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
        //// 1. Dialog oluştur (Fragment içinde olduğumuz için 'requireContext()' kullanıyoruz)
        val dialog = BottomSheetDialog(requireContext())

        // 2. Hazırladığın XML'i bağla (Örn: XML dosya adın bottom_filter.xml ise)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShopListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShopListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}