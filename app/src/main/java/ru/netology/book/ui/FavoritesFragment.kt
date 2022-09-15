package ru.netology.book.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.book.adapter.RecipeAdapter
import ru.netology.book.adapter.RecipeInteractionListener
import ru.netology.book.databinding.FragmentFavoriteBinding
import ru.netology.book.dto.Category
import ru.netology.book.dto.Recipe
import ru.netology.book.viewmodel.RecipeViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val viewModel: RecipeViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Избранные рецепты"

        viewModel.dataLike.observe(viewLifecycleOwner) {
            val adapter = RecipeAdapter(
                object : RecipeInteractionListener {

                    override fun onRemoveClicked(recipe: Recipe) {
                        viewModel.onRemoveClicked(recipe)
                    }

                    override fun onEditClicked(recipe: Recipe) {
                        findNavController().navigate(
                            FavoritesFragmentDirections.actionNavigationNotificationsToAddFragment(
                                recipe,
                                true
                            )
                        )
                    }

                    override fun onRecipeDetail(recipe: Recipe) {
                        findNavController().navigate(
                            FavoritesFragmentDirections.actionNavigationNotificationsToRecipeDetailFragment(
                                recipe
                            )
                        )
                    }

                    override fun onRecipeLike(recipe: Recipe, isLiked: Boolean) {
                        viewModel.onRecipeLike(recipe, isLiked)
                    }

                    override fun onRecipeFilter(list: List<Category>) {}
                }, it
            )
            binding.postRecyclerView.adapter = adapter
            if (it.isNullOrEmpty()) {
                binding.placeholder.visibility = View.VISIBLE
            } else {
                binding.placeholder.visibility = View.GONE
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}