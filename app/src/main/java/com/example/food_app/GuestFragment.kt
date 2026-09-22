package com.example.food_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.databinding.FragmentGuestBinding

class GuestFragment : Fragment() {

    private var _binding: FragmentGuestBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuAdapter: MenuAdapter
    private val menuListesi = mutableListOf<menuItem>()
    private var guestCount = 6 // Varsayılan kişi sayısı

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupGuestCounter()

        // FilterBottomGuestMenu'den dönen sonuc
        childFragmentManager.setFragmentResultListener(
            "REQUEST_KEY_FILTER",
            viewLifecycleOwner
        ) { _, bundle ->
            val dishName = bundle.getString("SELECTED_DISH_NAME") ?: return@setFragmentResultListener
            val category = bundle.getString("SELECTED_CATEGORY") ?: "Genel"
            val time = bundle.getString("SELECTED_TIME") ?: ""

            menuListesi.add(
                menuItem(
                    ad = dishName,
                    kategori = category,
                    sure = time,
                    tip = getMenuTipiFromCategory(category)
                )
            )
            menuAdapter.notifyItemInserted(menuListesi.size - 1)
            binding.recyclerViewMenu.scrollToPosition(menuListesi.size - 1)
        }

        // Menü Ekle Butonu Tıklaması
        binding.addMenuGuest.setOnClickListener {
            FilterBottomGuestMenu().show(childFragmentManager, "FilterBottomGuestMenu")
        }
    }

    private fun setupRecyclerView() {
        if (menuListesi.isEmpty()) {
            menuListesi.addAll(
                listOf(
                    menuItem("Mercimek çorbası", "Çorba", "1 gün önceden hazır", MenuTipi.CORBA),
                    menuItem("Güveç", "Ana yemek", "", MenuTipi.ANA_YEMEK),
                    menuItem("Sütlaç", "Tatlı", "2 gün önceden hazır", MenuTipi.TATLI),
                    menuItem("Tavuklu salata", "Salata", "", MenuTipi.SALATA)
                )
            )
        }

        // Callback fonksiyonu (onDeleteClick) parametre olarak ekliyoruz
        menuAdapter = MenuAdapter(menuListesi) { item, position ->
            //  Veri listesinden elemanı çıkarıyoruz
            menuListesi.removeAt(position)

            //  Adapter'a silme işlemini bildiriyoruz
            menuAdapter.notifyItemRemoved(position)

            //  Kalan elemanların pozisyon indekslerini güncelliyoruz
            menuAdapter.notifyItemRangeChanged(position, menuListesi.size)
        }

        binding.recyclerViewMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }
    }

    private fun setupGuestCounter() {
        binding.tvNumPerson.text = guestCount.toString()

        binding.btnNumPersonMinus.setOnClickListener {
            if (guestCount > 1) {
                guestCount--
                binding.tvNumPerson.text = guestCount.toString()
            }
        }

        binding.btnNumPersonPlus.setOnClickListener {
            guestCount++
            binding.tvNumPerson.text = guestCount.toString()
        }
    }

    private fun getMenuTipiFromCategory(category: String): MenuTipi {
        val lower = category.lowercase()
        return when {
            lower.contains("çorba") -> MenuTipi.CORBA
            lower.contains("ana") || lower.contains("yemek") -> MenuTipi.ANA_YEMEK
            lower.contains("tatlı") -> MenuTipi.TATLI
            lower.contains("salata") -> MenuTipi.SALATA
            else -> MenuTipi.ANA_YEMEK
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}