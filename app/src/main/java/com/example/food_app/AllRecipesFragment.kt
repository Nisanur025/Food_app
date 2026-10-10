package com.example.food_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.food_app.databinding.FragmentAllRecipesBinding


class AllRecipesFragment : Fragment() {


    private var _binding: FragmentAllRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AllRecipeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        adapter.submitList(sampleRecipes())



        //filtre butonuna tıklandığında çalışacak
        binding.filterBtn.setOnClickListener {
            val filterBottomSheet = FilterBottomSheetFragment()

            // Uygula'ya tıklandığında çalışacak kodlar
            filterBottomSheet.onApplyFilterListener = { filterResult ->
                // Burada tarif listesini filtrelenmiş veriye göre güncelleyebilirsin
            }

            // BottomSheet'i göster (Aşağıdan yukarı doğru süzülerek açılır)
            filterBottomSheet.show(childFragmentManager, FilterBottomSheetFragment.TAG)
        }

    }


    private fun sampleRecipes(): List<AllRecipe> = listOf(
        AllRecipe(
            id = "1",
            title = "Mercimek çorbası",
            imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=600",
            durationMinutes = 25,
            difficulty = "Kolay",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "2",
            title = "Akdeniz salatası",
            imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600",
            durationMinutes = 10,
            difficulty = "Kolay",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "3",
            title = "Karides tava",
            imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=600",
            durationMinutes = 35,
            difficulty = "Orta",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "4",
            title = "Çikolatalı kurabiye",
            imageUrl = "https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=600",
            durationMinutes = 20,
            difficulty = "Kolay",
            category = "Tatlı"
        ),
        AllRecipe(
            id = "5",
            title = "Sebzeli omlet",
            imageUrl = "https://images.unsplash.com/photo-1510693206972-df098062cb71?w=600",
            durationMinutes = 12,
            difficulty = "Kolay",
            category = "Kahvaltı"
        ),
        AllRecipe(
            id = "6",
            title = "Fırında somon",
            imageUrl = "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=600",
            durationMinutes = 30,
            difficulty = "Orta",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "7",
            title = "Meyveli smoothie",
            imageUrl = "https://images.unsplash.com/photo-1553530666-ba11a7da3888?w=600",
            durationMinutes = 5,
            difficulty = "Kolay",
            category = "Kahvaltı"
        ),
        AllRecipe(
            id = "8",
            title = "Taco",
            imageUrl = "https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=600",
            durationMinutes = 40,
            difficulty = "Orta",
            category = "Ana yemek"
        )
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = AllRecipeAdapter(
            onRecipeClick = { recipe ->
                val detailFragment = RecipeDetailFragment.newInstance(recipe.id)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.flFragment, detailFragment)
                    .addToBackStack(null)
                    .commit()
            },
            onFavoriteClick = { recipe ->
                // TODO: favori durumunu veritabanına/ViewModel'e kaydet
            }
        )

        binding.recyclerViewRecipes.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            adapter = this@AllRecipesFragment.adapter
        }
    }


}