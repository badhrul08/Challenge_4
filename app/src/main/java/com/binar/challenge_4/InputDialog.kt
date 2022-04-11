package com.binar.challenge_4

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.binar.challenge_4.data.NoteList
import com.binar.challenge_4.databinding.InputDialogBinding
import com.binar.challenge_4.room.NoteDatabase

class InputDialog : DialogFragment() {

    lateinit var binding: InputDialogBinding
    var mDb: NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = InputDialogBinding.bind(view)
        dialog?.setCancelable(false)
        super.onViewCreated(view, savedInstanceState)

        binding.inputBtn.setOnClickListener{
            val objectNotes = NoteList(
                null,
                binding.inputTitleEt.text.toString(),
                binding.inputNoteEt.text.toString()
            )
             Thread(Runnable {
                 val result = mDb?.noteDao()?.insertNotes(objectNotes)
                 activity?.runOnUiThread{
                     if (result != 0.toLong()){
                         Toast.makeText(requireContext(), "Sukses Menambahkan ${objectNotes.title}", Toast.LENGTH_LONG).show()
                     }else{
                         Toast.makeText(requireContext(), "Gagal Menambahkan ${objectNotes.title}", Toast.LENGTH_LONG).show()
                     }
                 }
             }).start()
            dialog?.dismiss()
        }
    }
    private fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val displayMetrics = Resources.getSystem().displayMetrics
        val rect = displayMetrics.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}