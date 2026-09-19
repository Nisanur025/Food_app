package com.example.food_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food_app.databinding.FragmentRecipeDetailBinding

// TODO : Artı eksi basıldığı zaman porsiyon sayısı hesaplama yapılacak
private const val ARG_RECIPE_ID = "recipeId"

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private var recipeId: String? = null
    private var servings: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipeId = arguments?.getString(ARG_RECIPE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = recipeId?.let { RecipeRepository.getById(it) } ?: return

        servings = recipe.defaultServings
        bindRecipe(recipe)
        setupServingsStepper()
        setupNotes(recipe)
    }

    private fun bindRecipe(recipe: RecipeDetail) {
        binding.tvRecipeTitle.text = recipe.title
        binding.tvRecipeDescription.text = recipe.description
        binding.tvServingsCount.text = servings.toString()

        binding.rvPhotoGallery.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GalleryAdapter(recipe.photos)
        }

        binding.rvIngredients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = IngredientAdapter(recipe.ingredients)
        }

        binding.rvSteps.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = StepAdapter(recipe.steps)
        }

        binding.etPersonalNotes.setText(recipe.personalNote)
    }

    private fun setupServingsStepper() {
        binding.btnIncreaseServings.setOnClickListener {
            servings++
            binding.tvServingsCount.text = servings.toString()
        }
        binding.btnDecreaseServings.setOnClickListener {
            if (servings > 1) {
                servings--
                binding.tvServingsCount.text = servings.toString()
            }
        }
    }

    private fun setupNotes(recipe: RecipeDetail) {
        binding.etPersonalNotes.addTextChangedListener {
            binding.btnSaveNote.visibility = View.VISIBLE
        }
        binding.btnSaveNote.setOnClickListener {
            binding.tvSaveStatus.visibility = View.VISIBLE
            binding.btnSaveNote.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(recipeId: String) = RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_RECIPE_ID, recipeId)
            }
        }
    }
}