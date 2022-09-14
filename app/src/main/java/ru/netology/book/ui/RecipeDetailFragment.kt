package ru.netology.book.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.netology.book.R
import ru.netology.book.databinding.RecipeDetailsFragmentBinding
import ru.netology.book.util.toCategoryString

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: RecipeDetailsFragmentBinding
    private val args: RecipeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe = args.recipe.copy()
        binding.name.text = recipe.name
        binding.author.text = recipe.author
        binding.category.text = recipe.category.toCategoryString()
        binding.content.text = recipe.content

        Glide.with(this).load(args.recipe.picture).placeholder(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_baseline_emoji_food_beverage_24,
                null
            )
        ).into(binding.imageView)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.findItem(R.id.search_bar).isVisible = false
                menu.findItem(R.id.filter_bar).isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigate(R.id.action_recipeDetailFragment_to_allRecipesFragment)
                        true
                    }
                    else -> false
                }
            }
        },
            viewLifecycleOwner
        )
    }
}