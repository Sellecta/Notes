package shadowteam.ua.notes.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.internal.artificialFrame
import shadowteam.ua.notes.R
import shadowteam.ua.notes.databinding.FragmentEditNotesBinding
import shadowteam.ua.notes.presentation.application.NotesApplication
import shadowteam.ua.notes.presentation.viewmodel.EditNotesViewModel
import shadowteam.ua.notes.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class EditNotesFragment : Fragment() {

    private val args by navArgs<EditNotesFragmentArgs>()
    private val component by lazy{
        (requireActivity().application as NotesApplication)
            .component
    }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EditNotesViewModel::class.java]
    }

    private var _binding: FragmentEditNotesBinding? = null
    private val binding: FragmentEditNotesBinding
        get() = _binding ?: throw Exception("FragmentEditNotesBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditNotesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        initObserver()
        viewModel.getNotes(args.notesId)
        listenerClick()

    }

    private fun initObserver(){
        viewModel.liveNotes.observe(viewLifecycleOwner){
            binding.etName.setText(it.title)
            binding.etDesc.setText(it.description)
        }
    }

    private fun listenerClick(){
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_save ->{
                    viewModel.saveEditNotes(
                        id = args.notesId,
                        title = binding.etName.text.toString(),
                        desc = binding.etDesc.text.toString()
                    )
                    findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.show()
    }
}