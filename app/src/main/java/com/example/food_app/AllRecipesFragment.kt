package com.example.food_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.food_app.databinding.FragmentAllRecipesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllRecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllRecipesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null




    private var _binding: FragmentAllRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AllRecipeAdapter






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


    // Geçici örnek veri — gerçek veri kaynağına (Room/API) bağlanana kadar
    // adapter'ın ve masonry grid'in çalıştığını görmek için
    private fun sampleRecipes(): List<AllRecipe> = listOf(
        AllRecipe(
            id = "1",
            title = "Mercimek çorbası",
            imageUrl = "https://picsum.photos/seed/1/400/650",
            durationMinutes = 25,
            difficulty = "Kolay",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "2",
            title = "Akdeniz salatası",
            imageUrl = "https://picsum.photos/seed/2/400/250",
            durationMinutes = 10,
            difficulty = "Kolay",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "3",
            title = "Karides tava",
            imageUrl = "https://picsum.photos/seed/3/400/300",
            durationMinutes = 35,
            difficulty = "Orta",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "4",
            title = "Çikolatalı kurabiye",
            imageUrl = "https://picsum.photos/seed/4/400/550",
            durationMinutes = 20,
            difficulty = "Kolay",
            category = "Tatlı"
        ),
        AllRecipe(
            id = "5",
            title = "Sebzeli omlet",
            imageUrl = "https://picsum.photos/seed/5/400/400",
            durationMinutes = 12,
            difficulty = "Kolay",
            category = "Kahvaltı"
        ),
        AllRecipe(
            id = "6",
            title = "Fırında somon",
            imageUrl = "https://picsum.photos/seed/6/400/700",
            durationMinutes = 30,
            difficulty = "Orta",
            category = "Ana yemek"
        ),
        AllRecipe(
            id = "7",
            title = "Meyveli smoothie",
            imageUrl = "https://picsum.photos/seed/7/400/280",
            durationMinutes = 5,
            difficulty = "Kolay",
            category = "Kahvaltı"
        ),
        AllRecipe(
            id = "8",
            title = "Mantı",
            imageUrl = "https://picsum.photos/seed/8/400/500",
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
                // TODO: Navigation Component ile tarif detay sayfasına geç
                // findNavController().navigate(AllRecipesFragmentDirections.toDetail(recipe.id))
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllRecipesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllRecipesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}