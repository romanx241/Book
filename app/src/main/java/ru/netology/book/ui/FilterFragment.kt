package ru.netology.book.ui

import android.os.Bundle

import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.book.R
import ru.netology.book.databinding.FragmentFilterBinding
import ru.netology.book.dto.Category
import ru.netology.book.util.toCategoryInt
import ru.netology.book.viewmodel.RecipeViewModel

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.category.observe(viewLifecycleOwner){
            it.forEach {
                when(it.id) {
                    0 -> binding.checkBox1.isChecked = it.isVisible
                    1 -> binding.checkBox2.isChecked = it.isVisible
                    2 -> binding.checkBox3.isChecked = it.isVisible
                    3 -> binding.checkBox4.isChecked = it.isVisible
                    4 -> binding.checkBox5.isChecked = it.isVisible
                    5 -> binding.checkBox6.isChecked = it.isVisible
                    6 -> binding.checkBox7.isChecked = it.isVisible
                }
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menu.findItem(R.id.search_bar).isVisible = false
                    menu.findItem(R.id.filter_bar).isVisible = false
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        android.R.id.home -> {
                            findNavController().navigate(R.id.action_navigation_dashboard_to_allRecipesFragment)
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner
        )
        binding.save.setOnClickListener {
            if (!(binding.checkBox1.isChecked || binding.checkBox2.isChecked || binding.checkBox3.isChecked
                        || binding.checkBox4.isChecked || binding.checkBox5.isChecked
                        || binding.checkBox6.isChecked || binding.checkBox7.isChecked)
            ) {
                Toast.makeText(
                    requireContext(),
                    "Выберите хотя бы одну категорию",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val list = mutableListOf<Category>()
                list.add(
                    Category(
                        RecipeViewModel.EUROPEAN.toCategoryInt(),
                        RecipeViewModel.EUROPEAN,
                        binding.checkBox1.isChecked
                    )
                )
                list.add(
                    Category(
                        RecipeViewModel.MEDITERRANEAN.toCategoryInt(),
                        RecipeViewModel.MEDITERRANEAN,
                        binding.checkBox2.isChecked
                    )
                )
                list.add(
                    Category(
                        RecipeViewModel.RUSSIAN.toCategoryInt(),
                        RecipeViewModel.RUSSIAN,
                        binding.checkBox3.isChecked
                    )
                )
                list.add(
                    Category(
                        RecipeViewModel.WEST.toCategoryInt(),
                        RecipeViewModel.WEST,
                        binding.checkBox4.isChecked
                    )
                )
                list.add(
                    Category(
                        RecipeViewModel.AMERICAN.toCategoryInt(),
                        RecipeViewModel.AMERICAN,
                        binding.checkBox5.isChecked
                    )
                )
                list.add(
                    Category(
                        RecipeViewModel.PANAZIA.toCategoryInt(),
                        RecipeViewModel.PANAZIA,
                        binding.checkBox6.isChecked
                    )
                )
                list.add(
                    Category(
                        RecipeViewModel.AZIA.toCategoryInt(),
                        RecipeViewModel.AZIA,
                        binding.checkBox7.isChecked
                    )
                )
                viewModel.onRecipeFilter(list)
            }
        }
    }
}