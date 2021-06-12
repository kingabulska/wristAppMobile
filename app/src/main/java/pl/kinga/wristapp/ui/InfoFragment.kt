package pl.kinga.wristapp.ui

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.R

class InfoFragment: Fragment(R.layout.fragment_info) {

    private lateinit var nav: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val contitue1 = requireView().findViewById<Button>(R.id.continue1_button)
        nav = findNavController()
        contitue1.setOnClickListener {
            nav.navigate(R.id.action_infoFragment_to_info2Fragment)
        }
    }

}