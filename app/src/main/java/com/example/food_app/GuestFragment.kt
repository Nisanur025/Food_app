package com.example.food_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.databinding.BottomDialogFilterBinding
import com.example.food_app.databinding.BottomGuestMenuFilterBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_guest, container, false)

        val menuListesi = listOf(
            menuItem("Mercimek çorbası", "Çorba", "1 gün önceden hazır", MenuTipi.CORBA),
            menuItem("Güveç", "Ana yemek", "", MenuTipi.ANA_YEMEK),
            menuItem("Sütlaç", "Tatlı", "2 gün önceden hazır", MenuTipi.TATLI),
            menuItem("Tavuklu salata", "Salata", "", MenuTipi.SALATA)
        )

        // 2. RecyclerView'i bul
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_menu)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 3. Adapter'a veriyi ver
        recyclerView.adapter = MenuAdapter(menuListesi)



        return view
    }

    private fun showCategoryBottomSheet() {
        val dialog = BottomSheetDialog(requireContext())

        // Paylaştığınız XML dosyasının ViewBinding nesnesi
        val sheetBinding = BottomGuestMenuFilterBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        // Köşelerin düzgün görünmesi için arka planı şeffaf yapıyoruz
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // --- BOTTOM SHEET İÇİNDEKİ BUTONLARIN OLAYLARI ---

        // Kapat veya Filtrele Butonu
        sheetBinding.btnApplyFilter.setOnClickListener {
            // Burada filtreleme/seçim mantığınızı çalıştırabilirsiniz
            dialog.dismiss()
        }
        // Sıfırla Butonu
        sheetBinding.btnReset.setOnClickListener {
            sheetBinding.chipGroupCategories.clearCheck()
            sheetBinding.radioGroupPrepTime.clearCheck()
        }

        // Kendi Kategorini Ekle Gönder Butonu
        sheetBinding.btnSendCategory.setOnClickListener {
            val userCategory = sheetBinding.etYourCategory.text.toString()
            if (userCategory.isNotEmpty()) {
                // Yeni chip ekleme işlemi yapılabilir
                sheetBinding.etYourCategory.text?.clear()
            }
        }

        // Dialog'u göster
        dialog.show()


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottomfilter açılımı

        val btnFilter = view.findViewById<Button>(R.id.add_menu_guest)
        btnFilter.setOnClickListener {
            showCategoryBottomSheet()
        }


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}