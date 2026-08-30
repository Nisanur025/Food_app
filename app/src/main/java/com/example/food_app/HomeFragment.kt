package com.example.food_app

import RecipeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter









    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getArguments() != null) {
            mParam1 = requireArguments()!!.getString(ARG_PARAM1)
            mParam2 = requireArguments()!!.getString(ARG_PARAM2)
        }
    }

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
    }

    private fun setupRecyclerView() {
        // Örnek veri listen
        val recipeList = listOf(
            easy_recipeModel("Avakadolu Tost", "10 dk", "Kolay", R.drawable.bg_square_green),
            easy_recipeModel("Peynirli Omelet", "15 dk", "Orta", R.drawable.bg_square_purple),
           easy_recipeModel("Tavuklu Sebze Sote", "30 dk", "Zor", R.drawable.bg_square_black)
        )

        recipeAdapter = RecipeAdapter(recipeList) { selectedRecipe ->
            Toast.makeText(context, "${selectedRecipe.title} seçildi", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewRecipes.apply { // XML'indeki RecyclerView ID'si "recyclerView" ise
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipeAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"


        fun newInstance(param1: String?, param2: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.setArguments(args)
            return fragment
        }
    }
}