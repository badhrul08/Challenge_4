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
import com.binar.challenge_4.databinding.EditDialogBinding
import com.binar.challenge_4.room.NoteDatabase

class EditDialog: DialogFragment() {

    lateinit var binding: EditDialogBinding
    var mDb: NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = EditDialogBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(95)

        mDb = NoteDatabase.getInstance(requireContext())

        val objectNotes = arguments?.getParcelable<NoteList>("note")

        binding.inputTitleEt.setText(objectNotes?.title)
        binding.inputNoteEt.setText((objectNotes?.details))

        binding.updateBtn.setOnClickListener {
            objectNotes?.title = binding.inputTitleEt.text.toString()
            objectNotes?.details = binding.inputNoteEt.text.toString()

            Thread(Runnable {
                val result = mDb?.noteDao()?.updateNotes(objectNotes)
                activity?.runOnUiThread{
                    if (result != 0){
                        Toast.makeText(requireContext(), "Sukses Mengubah ${objectNotes?.title}", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(requireContext(), "Gagal Mengubah ${objectNotes?.title}", Toast.LENGTH_LONG).show()
                    }
                }
            }).start()
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
