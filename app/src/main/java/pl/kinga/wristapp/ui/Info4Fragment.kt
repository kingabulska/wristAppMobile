package pl.kinga.wristapp.ui

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.R

class Info4Fragment: Fragment(R.layout.fragment_info4) {
    private lateinit var nav: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val contitue1 = requireView().findViewById<Button>(R.id.start_test_button)
        nav = findNavController()
        contitue1.setOnClickListener {
            nav.navigate(R.id.action_info4Fragment_to_startFragment)
        }
    }
}