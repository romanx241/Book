
package ru.netology.book.adapter


import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.netology.book.R
import ru.netology.book.databinding.ListItemRecipeBinding
import ru.netology.book.dto.Recipe
import ru.netology.book.util.toCategoryString
import java.util.*

internal class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener, private var items: List<Recipe>
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback), Filterable
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemRecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    fun moveItem(from: Int, to: Int) {

        val list = currentList.toMutableList()
        val fromLocation = list[from]
        list.removeAt(from)
        if (to < from) {
            list.add(to + 1 , fromLocation)
        } else {
            list.add(to - 1, fromLocation)
        }
        submitList(list)


    }

    class ViewHolder(
        private val binding: ListItemRecipeBinding,
        private val listener: RecipeInteractionListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            with(binding) {
                textView2.text = recipe.author
                textView.text = recipe.name
                textView3.text = recipe.category.toCategoryString()
                switch1.isChecked = recipe.isLiked
                switch1.setOnCheckedChangeListener { _, b -> listener.onRecipeLike(recipe, b) }
                if (recipe.picture != "no") {
                    Glide.with(imageView2).load(recipe.picture).placeholder(
                        ResourcesCompat.getDrawable(
                            root.resources,
                            R.drawable.ic_baseline_emoji_food_beverage_24,
                            null
                        )
                    )
                        .transition(DrawableTransitionOptions.withCrossFade()).into(imageView2)
                } else {
                    imageView2.setImageResource(R.drawable.furshet)
                }
                binding.menu.setOnClickListener {
                    showPopupMenu(it, recipe)
                }
                binding.constraintLayout.setOnClickListener {
                    listener.onRecipeDetail(recipe)
                }
            }
        }

        private fun showPopupMenu(view: View, recipe: Recipe) {
            val popupMenu = PopupMenu(view.context, view)
            val context = view.context

            popupMenu.menu.add(0, ID_EDIT, Menu.NONE, context.getString(R.string.edit))
            popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    ID_EDIT -> {
                        listener.onEditClicked(recipe)
                    }
                    ID_REMOVE -> {
                        listener.onRemoveClicked(recipe)
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val resultList = ArrayList<Recipe>()
                val filterResults = FilterResults()
                if (constraint == null || constraint.length < 0 || constraint == "") {
                    filterResults.count = items.size
                    filterResults.values = items
                }
                if (!constraint.isNullOrEmpty()) {
                    val charSearch = constraint.toString().lowercase(Locale.ROOT).trim()
                    for (row in items) {
                        if (row.name.lowercase(Locale.ROOT).trim().contains(charSearch)) {
                            resultList.add(row)
                        }
                    }
                    filterResults.values = resultList
                }
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.also {
                    submitList(it.values as ArrayList<Recipe>)
                }
            }
        }
    }

    fun addFullRecipe(items: List<Recipe>) {
        this.items = items
    }

    companion object {
        private const val ID_EDIT = 0
        private const val ID_REMOVE = 1
    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }
}