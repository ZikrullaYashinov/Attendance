package zikrulla.production.attendance.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import zikrulla.production.attendance.R
import zikrulla.production.attendance.adapter.GroupAdapter
import zikrulla.production.attendance.database.AppDatabase
import zikrulla.production.attendance.databinding.FragmentHomeBinding
import zikrulla.production.attendance.viewmodel.GroupsViewModel
import zikrulla.production.attendance.viewmodel.GroupsViewModelFactory
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: GroupAdapter
    private lateinit var viewModel: GroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        load()
        click()
        change()

        return binding.root
    }

    private fun load() {
        viewModel = ViewModelProvider(
            this,
            GroupsViewModelFactory(AppDatabase.getInstance(requireContext()))
        )[GroupsViewModel::class.java]
        adapter = GroupAdapter()
        binding.recyclerView.adapter = adapter


    }

    private fun click() {
        binding.apply {
            addGroup.setOnClickListener {
                Navigation.findNavController(root)
                    .navigate(R.id.action_homeFragment_to_importFragment)
            }
        }
        adapter.itemClick = {
            val bundle = Bundle()
            bundle.putSerializable("group", it)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_homeFragment_to_attendanceFragment, bundle)
        }
    }

    private fun change() {
        viewModel.getGroups().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
}