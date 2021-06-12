package pl.kinga.wristapp.viewmodel

import androidx.lifecycle.ViewModel
import java.time.Instant

object ExamineSessionObj {
    var sessionId: Instant? = null
}

class ExamineSessionViewModel : ViewModel() {

    var sessionId: Instant? = null
        private set(value) {
            field = value
            ExamineSessionObj.sessionId = value
        }

    var isSecondRound: Boolean = false
        private set

    fun beginTesting() {
        sessionId = Instant.now()
    }

    fun endSession() {
        sessionId = null
        isSecondRound = false
    }

    fun secondRound() {
        isSecondRound = true
    }

}