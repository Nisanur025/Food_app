import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.databinding.RvHomePageBinding
import com.example.food_app.easy_recipeModel

class RecipeAdapter(
    private val recipeList: List<easy_recipeModel>,
    private val onItemClick: (easy_recipeModel) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: RvHomePageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RvHomePageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        with(holder.binding) {
            recipeTitle.text = recipe.title
            time.text = "${recipe.duration} | ${recipe.difficulty}"
            imgRecipe.setBackgroundResource(recipe.imageResId)

            root.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun getItemCount(): Int = recipeList.size
}