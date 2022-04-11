package com.binar.challenge_4

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.challenge_4.adapter.NoteAdapter
import com.binar.challenge_4.data.NoteList
import com.binar.challenge_4.databinding.FragmentHomeScreenBinding
import com.binar.challenge_4.databinding.InputDialogBinding
import com.binar.challenge_4.room.NoteDatabase

class HomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private var mDb: NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeScreenBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        mDb = NoteDatabase.getInstance(requireContext())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        fetchData()

        binding.floatingBtn.setOnClickListener {
            val inputDialog = InputDialog()
            inputDialog.show(parentFragmentManager, "test")
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        Thread(Runnable {
            val listNote = mDb?.noteDao()?.getAllNotes()
            activity?.runOnUiThread{
                listNote?.let {
                    val adapter = NoteAdapter(it)
                    binding.recyclerView.adapter = adapter
                }
            }
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        NoteDatabase.destroyInstance()
    }
}