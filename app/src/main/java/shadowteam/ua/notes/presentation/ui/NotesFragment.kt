package shadowteam.ua.notes.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.google.android.material.snackbar.Snackbar
import shadowteam.ua.notes.R
import shadowteam.ua.notes.data.worker.LoadDataWorker
import shadowteam.ua.notes.databinding.FragmentNotesBinding
import shadowteam.ua.notes.presentation.application.NotesApplication
import shadowteam.ua.notes.presentation.ui.adapter.NotesAdapter
import shadowteam.ua.notes.presentation.viewmodel.NotesViewModel
import shadowteam.ua.notes.presentation.viewmodel.ViewModelFactory
import java.util.*
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt


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
        workerListener()
        binding.floatingActionButton.setOnClickListener {
            if(binding.textViewLoadData.visibility != View.GONE){binding.textViewLoadData.visibility = View.GONE}
            viewModel.addNotes()

        }
    }

    private fun workerListener() {
        viewModel.liveWorkerState.observe(viewLifecycleOwner) {
            val internetStatusError =
            it.progress.getBoolean(LoadDataWorker.NOT_INTERNET_KEY, false)
            val sizeLoadData =
                it.outputData.getInt(LoadDataWorker.LOAD_DATA_KEY, -1)
            if (it.state == WorkInfo.State.RUNNING) {
                val emptyDb = it.progress.getBoolean(LoadDataWorker.DB_EMPTY_KEY, false)
                if (!emptyDb) {
                    binding.progressBarCircle.visibility = View.VISIBLE
                    binding.progressBarLine.visibility = View.GONE
                    binding.textViewLoadData.visibility = View.GONE
                } else {
                    binding.progressBarCircle.visibility = View.GONE
                    binding.progressBarLine.visibility = View.VISIBLE
                }
            }
            if (it.state == WorkInfo.State.SUCCEEDED) {
                binding.progressBarCircle.visibility = View.GONE
                binding.progressBarLine.visibility = View.GONE
            }
            if(internetStatusError){
                binding.progressBarCircle.visibility = View.GONE
                binding.progressBarLine.visibility = View.GONE
                val styleAlert = it.progress.getInt(LoadDataWorker.INTERNET_PROBLEM_STYLE_KEY, -1)
                if(styleAlert > 0){Snackbar.make(binding.recyclerNotes,R.string.internet_problem, Snackbar.LENGTH_SHORT).show()}
                else{
                    binding.textViewLoadData.visibility = View.VISIBLE
                    binding.textViewLoadData.text = getString(R.string.internet_problem) }
            }
            if (sizeLoadData == 0){
                binding.textViewLoadData.visibility = View.VISIBLE
                binding.textViewLoadData.text = getString(R.string.not_load_data)
            }

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