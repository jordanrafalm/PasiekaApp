package com.example.pasiekaapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.FirebaseFirestore

class PasiekiFragment : Fragment() {

    private val apiaryViewModel: ApiaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pasieki, container, false)

        val fragmentContainerView1 = view.findViewById<View>(R.id.fragmentContainerViewPasieka1)
        val fragmentContainerView2 = view.findViewById<View>(R.id.fragmentContainerViewPasieka2)
        val fragmentContainerView3 = view.findViewById<View>(R.id.fragmentContainerViewPasieka3)
        val fragmentContainerView4 = view.findViewById<View>(R.id.fragmentContainerViewPasieka4)

        // Initialize the visibility to GONE (will become VISIBLE if corresponding fragment is added)
        fragmentContainerView1.visibility = View.GONE
        fragmentContainerView2.visibility = View.GONE
        fragmentContainerView3.visibility = View.GONE
        fragmentContainerView4.visibility = View.GONE

        val addApiaryButton: Button = view.findViewById(R.id.addApiaryButton)
        addApiaryButton.setOnClickListener {
            val intent = Intent(activity, ApiaryActivity::class.java)
            startActivity(intent)
        }

        // Observe the number of apiaries and dynamically add fragments
        apiaryViewModel.apiaryCountLiveData.observe(viewLifecycleOwner) { count ->
            if (count >= 1) {
                addApiaryFragment(R.id.fragmentContainerViewPasieka1, "PASIEKA1")
                fragmentContainerView1.visibility = View.VISIBLE
            }
            if (count >= 2) {
                addApiaryFragment(R.id.fragmentContainerViewPasieka2, "PASIEKA2")
                fragmentContainerView2.visibility = View.VISIBLE
            }
            if (count >= 3) {
                addApiaryFragment(R.id.fragmentContainerViewPasieka3, "PASIEKA3")
                fragmentContainerView3.visibility = View.VISIBLE
            }
            if (count >= 4) {
                addApiaryFragment(R.id.fragmentContainerViewPasieka4, "PASIEKA4")
                fragmentContainerView4.visibility = View.VISIBLE
            }
        }

        // Load the count of apiaries
        apiaryViewModel.loadApiaryCount()

        return view
    }

    private fun addApiaryFragment(containerId: Int, documentName: String) {
        childFragmentManager.commit {
            replace<PasiekaFragment>(containerId, null, Bundle().apply {
                putString("documentName", documentName)
            })
        }
    }
}
