package com.example.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class Fragment1 : Fragment() {

    // ── Interface: sends name to Host Activity ────────────────────────────────
    interface Fragment1Listener {
        fun onFragment1Continue(name: String)
    }

    private var listener: Fragment1Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? Fragment1Listener
            ?: throw ClassCastException("$context must implement Fragment1Listener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_1, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName  = view.findViewById<EditText>(R.id.etName)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        btnNext.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isEmpty()) {
                etName.error = "Please enter your name"
                return@setOnClickListener
            }
            listener?.onFragment1Continue(name)
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
