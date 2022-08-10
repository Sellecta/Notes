package shadowteam.ua.notes.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import shadowteam.ua.notes.databinding.NotesItemBinding
import shadowteam.ua.notes.domain.dataclass.Notes
import javax.inject.Inject

class NotesAdapter @Inject constructor(
) :ListAdapter<Notes, NotesViewHolder>(NotesDifUtils()) {

    var onNotesItemClickListener: ((Notes) ->Unit)?  = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = getItem(position)
        with(holder.binding){
            textViewTitle.text = notes.title
            textViewDescription.text = notes.description
            textViewTime.text = notes.data
            root.setOnClickListener{
                onNotesItemClickListener?.invoke(notes)
            }
        }

    }
}