package shadowteam.ua.notes.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import shadowteam.ua.notes.R
import shadowteam.ua.notes.databinding.FragmentEditNotesBinding
import shadowteam.ua.notes.databinding.FragmentNotesBinding
import shadowteam.ua.notes.presentation.application.NotesApplication
import shadowteam.ua.notes.presentation.ui.adapter.NotesAdapter
import shadowteam.ua.notes.presentation.viewmodel.NotesViewModel
import shadowteam.ua.notes.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class NotesFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as NotesApplication)
            .component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var notesAdapter: NotesAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
    }

    private var _binding: FragmentNotesBinding? = null
    private val binding: FragmentNotesBinding
        get() = _binding ?: throw Exception("FragmentNotesBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotesBinding.inflate(
            inflater,
            container,
            false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        binding.recyclerNotes.adapter = notesAdapter
        binding.recyclerNotes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        viewModel.liveListNotes.observe(viewLifecycleOwner){
            notesAdapter.submitList(it)
        }
        setSwiperListener()
        setupClickListener()
        binding.floatingActionButton.setOnClickListener {
            viewModel.addNotes()
        }
    }

    private fun setupClickListener(){
        notesAdapter.onNotesItemClickListener ={
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToEditNotesFragment(it.id))
        }
    }
    private fun setSwiperListener(){
        val callback = object :ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val notes = notesAdapter.currentList[viewHolder.adapterPosition]
                viewModel.delNotes(notes.id)
            }

        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerNotes)
    }
}