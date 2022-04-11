package com.binar.challenge_4.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.binar.challenge_4.EditDialog
import com.binar.challenge_4.HomeScreen
import com.binar.challenge_4.data.NoteList
import com.binar.challenge_4.databinding.UiHomeBinding
import com.binar.challenge_4.room.NoteDatabase

class NoteAdapter (private val listNote: List<NoteList>): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder (val binding: UiHomeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = UiHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleTv.text =listNote[position].title
        holder.binding.detailsTv.text = listNote[position].details

        holder.binding.editBtn.setOnClickListener{
            val editDialog = EditDialog()
            val activity = FragmentActivity()
            val fragmentManager = activity.supportFragmentManager
            editDialog.show(fragmentManager,"edit data")
        }

        holder.binding.deleteBtn.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Ya"){_, _ ->
                val mDb = NoteDatabase.getInstance(holder.binding.root.context)

                Thread(Runnable {
                    val result = mDb?.noteDao()?.deleteNotes(listNote[position])
                    (holder.binding.root.context as HomeScreen). activity?.runOnUiThread {
                        if (result != 0) {
                            Toast.makeText(
                                it.context,
                                "Data ${listNote[position].title} Berhasil Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                it.context,
                                "Data ${listNote[position].title} Gagal Dihapus",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    (holder.binding.root.context as HomeScreen).fetchData()
                }).start()
            }
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }
}