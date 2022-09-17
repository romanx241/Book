package ru.netology.book.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import ru.netology.book.R
import ru.netology.book.adapter.RecipeAdapter
import ru.netology.book.adapter.RecipeInteractionListener
import ru.netology.book.databinding.AllRecipeBinding
import ru.netology.book.dto.Category
import ru.netology.book.dto.Recipe
import ru.netology.book.viewmodel.RecipeViewModel


class AllRecipesFragment : Fragment(), RecipeInteractionListener {

    private val viewModel: RecipeViewModel by activityViewModels()
    private lateinit var binding: AllRecipeBinding
    private var adapter: RecipeAdapter = RecipeAdapter(this, mutableListOf())

    private val itemTouchHelper by lazy {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(UP or DOWN or START or END, 0) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val adapter = recyclerView.adapter as RecipeAdapter
                    val from = viewHolder.bindingAdapterPosition
                    val to = target.bindingAdapterPosition
                    adapter.moveItem(from, to)
                    adapter.notifyItemMoved(from, to)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                }

                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                    super.onSelectedChanged(viewHolder, actionState)

                    if (actionState == ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.alpha = 0.5f
                    }
                }

                override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                    super.clearView(recyclerView, viewHolder)

                    viewHolder.itemView.alpha = 1.0f
                }
            }
        ItemTouchHelper(simpleItemTouchCallback)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.postRecyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                AllRecipesFragmentDirections.actionAllRecipesFragmentToAddFragment(null)
            )
        }
        viewModel.addInitRecipes()
        viewModel.data.observe(viewLifecycleOwner) {
            viewModel.addFullRecipe(it)
            adapter.addFullRecipe(it)
            itemTouchHelper.attachToRecyclerView(binding.postRecyclerView)
            if(it.isNullOrEmpty()){
                binding.placeholder.visibility = View.VISIBLE
            } else {
                binding.placeholder.visibility = View.GONE
                adapter.submitList(it)
            }
            binding.floatingActionButton.visibility = View.VISIBLE
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menu.findItem(R.id.search_bar).isVisible = true
                    menu.findItem(R.id.filter_bar).isVisible = true

                    val search = menu.findItem(R.id.search_bar).actionView as SearchView
                    search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            return false
                        }
                        override fun onQueryTextChange(p0: String?): Boolean {
                            if (p0.isNullOrBlank()) {
                                adapter.submitList(viewModel.allRecipe)
                            } else {
                                adapter.filter.filter(p0)
                            }
                            return false
                        }
                    })
                }
                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.filter_bar -> {
                            findNavController().navigate(AllRecipesFragmentDirections.actionAllRecipesFragmentToNavigationDashboard())
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AllRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onRemoveClicked(recipe: Recipe) {
        viewModel.onRemoveClicked(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        findNavController().navigate(
            AllRecipesFragmentDirections.actionAllRecipesFragmentToAddFragment(
                recipe,
                true
            )
        )
    }

    override fun onRecipeDetail(recipe: Recipe) {
        findNavController().navigate(
            AllRecipesFragmentDirections.actionAllRecipesFragmentToRecipeDetailFragment(
                recipe
            )
        )
    }

    override fun onRecipeLike(recipe: Recipe, isLiked: Boolean) {
        viewModel.onRecipeLike(recipe, isLiked)
    }

    override fun onRecipeFilter(list: List<Category>) {}

    companion object {

        @JvmStatic
        private val ARG_RECIPE_ID = "recipe_id"

        @JvmStatic
        fun newInstance(recipeId: Int): AllRecipesFragment {
            val args = Bundle().apply {
                putInt(ARG_RECIPE_ID, recipeId)
            }
            return AllRecipesFragment().apply {
                arguments = args
            }
        }
    }
}