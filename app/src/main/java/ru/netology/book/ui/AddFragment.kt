package ru.netology.book.ui

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.book.R
import ru.netology.book.databinding.AddRecipeBinding
import ru.netology.book.viewmodel.RecipeViewModel

class AddFragment : Fragment() {

    private lateinit var binding: AddRecipeBinding
    private val args: AddFragmentArgs by navArgs()
    private val viewModel: RecipeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.type)
            )
        binding.category.adapter = adapter

        if (args.isEdit) {
            (activity as AppCompatActivity).supportActionBar?.title = "Редактировать рецепт"
            binding.author.setText(args.recipe?.author)
            binding.name.setText(args.recipe?.name)
            binding.content.setText(args.recipe?.content)
            binding.category.tag = args.recipe?.category
            args.recipe?.category?.let{
                binding.category.setSelection(it + 1, true)

            }
        }
        binding.buttonSave.setOnClickListener {
            if (binding.name.text.isNullOrBlank() || binding.author.text.isNullOrBlank() || binding.content.text.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Введите все данные", Toast.LENGTH_LONG).show()
            } else {
                viewModel.onSaveButtonClicked(
                    binding.name.text.toString(), binding.author.text.toString(),
                    binding.category.selectedItem.toString(), binding.content.text.toString(),
                    args.recipe?.id
                )
                findNavController().navigate(R.id.action_addFragment_to_allRecipesFragment)
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.findItem(R.id.search_bar).isVisible = false
                menu.findItem(R.id.filter_bar).isVisible = false
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigate(R.id.action_addFragment_to_allRecipesFragment)
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
