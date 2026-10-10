package com.example.food_app

import RecipeAdapter
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter

    // Haftalık menü verisi
    private val days = mutableListOf<day_menu>()
    private val dayAdapter = DayMenuAdapter { day -> showRecipeDialog(day) }

    // Kalıcı kayıt
    private val prefs by lazy {
        requireContext().getSharedPreferences("weekly_menu", Context.MODE_PRIVATE)
    }

    // Pazartesi = 0 ... Pazar = 6
    private val dayNames = listOf(
        R.string.day_monday, R.string.day_tuesday, R.string.day_wednesday,
        R.string.day_thursday, R.string.day_friday, R.string.day_saturday, R.string.day_sunday
    )

    // Kayıt yoksa (ilk açılış) gösterilecek örnek yemekler
    private val defaultRecipes = listOf(
        R.string.recipe_lentil_soup, R.string.recipe_oven_meatballs, R.string.recipe_chicken_sote,
        null, null, null, null
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupNavigation()
        setupWeeklyMenu()
    }

    // Tüm Tarifler ve Alışveriş Listesi sayfalarına geçiş
    private fun setupNavigation() {
        binding.allRecipesMc.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, AllRecipesFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnShopList.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flFragment, ShopListFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // Haftalık yemek kartları
    private fun setupWeeklyMenu() {
        if (days.isEmpty()) {
            val todayIndex = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7

            dayNames.forEachIndexed { i, nameRes ->
                val key = "day_$i"
                val recipeName = if (prefs.contains(key)) {
                    prefs.getString(key, null)?.ifEmpty { null }   // kayıtlı değer
                } else {
                    defaultRecipes[i]?.let { getString(it) }        // ilk açılış
                }
                days.add(day_menu(nameRes, recipeName, isToday = i == todayIndex))
            }
        }
        binding.weeklyMenuLayout.adapter = dayAdapter
        dayAdapter.submitList(days.toList())
    }

    // Yemek adı girme dialog'u
    private fun showRecipeDialog(day: day_menu) {
        val input = EditText(requireContext()).apply {
            hint = getString(R.string.hint_recipe_name)
            setText(day.recipeName)
            setSingleLine()
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        }
        val pad = (24 * resources.displayMetrics.density).toInt()
        val container = FrameLayout(requireContext()).apply {
            setPadding(pad, pad / 2, pad, 0)
            addView(input)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(day.dayNameRes)
            .setView(container)
            .setPositiveButton(R.string.save) { _, _ ->
                updateDay(day, input.text.toString().trim().ifEmpty { null })
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun updateDay(day: day_menu, name: String?) {
        val index = days.indexOfFirst { it.dayNameRes == day.dayNameRes }
        if (index == -1) return
        days[index] = day.copy(recipeName = name)
        dayAdapter.submitList(days.toList())   // yeni liste ver, DiffUtil farkı bulur

        // Kalıcı kaydet. Boş bırakılırsa "" yazıyoruz ki örnek yemek geri gelmesin
        prefs.edit().putString("day_$index", name ?: "").apply()
    }

    // Hızlı tarifler listesi
    private fun setupRecyclerView() {
        val recipeList = listOf(
            easy_recipeModel("Avakadolu Tost", "10 dk", "Kolay", R.drawable.bg_square_green),
            easy_recipeModel("Peynirli Omelet", "15 dk", "Orta", R.drawable.bg_square_purple),
            easy_recipeModel("Tavuklu Sebze Sote", "30 dk", "Zor", R.drawable.bg_square_black)
        )

        recipeAdapter = RecipeAdapter(recipeList) { selectedRecipe ->
            Toast.makeText(context, "${selectedRecipe.title} seçildi", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewRecipes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}