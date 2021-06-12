package pl.kinga.wristapp.ui

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.R
import pl.kinga.wristapp.viewmodel.ExamineSessionViewModel

class LeftStartFragment : Fragment(R.layout.fragment_start_left) {

    private val vm: ExamineSessionViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(!vm.isSecondRound) {
            vm.beginTesting()
        }

        val startBtn = view?.findViewById<Button>(R.id.start_tracking_button2)
        startBtn?.setOnClickListener {
            val nav = findNavController()
            nav.navigate(R.id.action_startFragment_to_ovalFragment)
        }
    }
}

