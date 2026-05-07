package com.example.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class Fragment3 : Fragment() {

    // ── Interface: checkbox state → Host Activity ─────────────────────────────
    interface Fragment3Listener {
        fun onCheckboxStateChanged(isChecked: Boolean)
    }

    private var listener: Fragment3Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Host Activity optionally implements Fragment3Listener
        listener = context as? Fragment3Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_3, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val cbAgree    = view.findViewById<CheckBox>(R.id.cbAgree)
        val btnFinish  = view.findViewById<Button>(R.id.btnFinish)

        // Display name received from Host Activity via Bundle
        val userName = arguments?.getString(ARG_USER_NAME) ?: "User"
        tvUserName.text = userName

        // Button starts disabled
        btnFinish.isEnabled = false
        btnFinish.alpha = 0.5f

        // ── Checkbox listener ─────────────────────────────────────────────────
        cbAgree.setOnCheckedChangeListener { _, isChecked ->

            // Notify Host Activity via interface
            listener?.onCheckboxStateChanged(isChecked)

            // Enable / disable button
            btnFinish.isEnabled = isChecked
            btnFinish.alpha = if (isChecked) 1.0f else 0.5f

            // Change button text: "Continue" → "Finish" and back
            btnFinish.text = if (isChecked) "Finish" else "Continue"
        }

        // Finish button action
        btnFinish.setOnClickListener {
            Toast.makeText(requireContext(), "Welcome, $userName! 🎉", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        private const val ARG_USER_NAME = "USER_NAME"

        /** Factory: pass the name from Host Activity */
        fun newInstance(userName: String): Fragment3 {
            val fragment = Fragment3()
            val args = Bundle()
            args.putString(ARG_USER_NAME, userName)
            fragment.arguments = args
            return fragment
        }
    }
}
