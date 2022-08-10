package shadowteam.ua.notes.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import shadowteam.ua.notes.domain.dataclass.Notes

class NotesDifUtils :DiffUtil.ItemCallback<Notes>() {
    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
        return oldItem == newItem
    }
}