package pl.kinga.wristapp.ui

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.R

class Info3Fragment: Fragment(R.layout.fragment_info3) {
    private lateinit var nav: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val contitue1 = requireView().findViewById<Button>(R.id.continue1_button3)
        nav = findNavController()
        contitue1.setOnClickListener {
            nav.navigate(R.id.action_info3Fragment_to_info4Fragment)
        }
    }
}