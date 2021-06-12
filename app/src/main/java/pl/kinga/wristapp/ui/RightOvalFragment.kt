package pl.kinga.wristapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.PaintView
import pl.kinga.wristapp.R
import pl.kinga.wristapp.viewmodel.ExamineSessionViewModel
import pl.kinga.wristapp.viewmodel.RightOvalViewModel

class RightOvalFragment : Fragment(R.layout.fragment_oval) {

    private val vm: RightOvalViewModel by viewModels()
    private val examVm: ExamineSessionViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val paintView = view?.findViewById<View>(R.id.paintView) as PaintView
        paintView.onTouchStart = vm::onTouchStart
        paintView.onTouchUp = vm::onTouchUp
        paintView.onNewPoint = vm::onNewPoint

        vm.displayMetrics = requireContext().resources.displayMetrics
        vm.currentEllipse.observe(viewLifecycleOwner, Observer { paintView.currentOval = it })

        vm.isEnd.observe(viewLifecycleOwner, Observer {
            if (it) {
                val nav = findNavController()
                nav.navigate(R.id.action_rightOvalFragment_to_endFragment)
            }
        })

        vm.startTest(examVm.sessionId!!)
    }

}