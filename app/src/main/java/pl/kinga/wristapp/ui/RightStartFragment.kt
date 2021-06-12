package pl.kinga.wristapp.ui

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.R

class RightStartFragment : Fragment(R.layout.fragment_start_right) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val startBtn = view?.findViewById<Button>(R.id.start_tracking_button2)
        startBtn?.setOnClickListener {
            val nav = findNavController()
            nav.navigate(R.id.action_rightStartFragment_to_rightOvalFragment)
        }
    }
}