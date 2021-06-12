package pl.kinga.wristapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import pl.kinga.wristapp.R
import pl.kinga.wristapp.viewmodel.ExamineSessionViewModel

class EndFragment: Fragment(R.layout.end_test) {

    private val vm: ExamineSessionViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when(vm.isSecondRound) {
            false -> {
                vm.secondRound()
                hideEnd()
                showContinue()

                val testAgain = view?.findViewById<Button>(R.id.button_continue)
                testAgain?.setOnClickListener {
                    val nav = findNavController()

                    nav.navigate(R.id.action_endFragment_to_startFragment)
                }
            }
            true -> {
                hideContinue()
                showEnd()

                val end = view?.findViewById<Button>(R.id.button_finish)

                end?.setOnClickListener {
                    requireActivity().finishAndRemoveTask()
                }
                vm.endSession()
            }
        }
    }

    private fun showEnd() {
        val finishLayout = view?.findViewById<ConstraintLayout>(R.id.layout_finish)
        finishLayout?.visibility = View.VISIBLE
    }

    private fun showContinue() {
        val continueLayout = view?.findViewById<ConstraintLayout>(R.id.layout_continue)
        continueLayout?.visibility = View.VISIBLE
    }

    private fun hideEnd() {
        val finishLayout = view?.findViewById<ConstraintLayout>(R.id.layout_finish)
        finishLayout?.visibility = View.GONE
    }

    private fun hideContinue() {
        val continueLayout = view?.findViewById<ConstraintLayout>(R.id.layout_continue)
        continueLayout?.visibility = View.GONE
    }
}