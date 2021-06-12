package pl.kinga.wristapp.viewmodel

import android.util.DisplayMetrics
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.kinga.wristapp.Ellipse
import pl.kinga.wristapp.domain.Examine
import pl.kinga.wristapp.domain.Hand
import pl.kinga.wristapp.repository.ExamineRepository
import pl.kinga.wristapp.repository.ExamineRepositoryImpl
import java.time.Instant

abstract class OvalViewModel : ViewModel() {

    abstract val ellipses: List<Ellipse>
    private lateinit var iter: Iterator<Ellipse>

    lateinit var displayMetrics: DisplayMetrics

    abstract val handMode: Hand

    private val repository: ExamineRepository = ExamineRepositoryImpl()

    private val _currentEllipse: MutableLiveData<Ellipse?> = MutableLiveData(null)
    val currentEllipse: LiveData<Ellipse?> get() = _currentEllipse

    private val _isEnd = MutableLiveData(false)
    val isEnd: LiveData<Boolean> get() = _isEnd

    private lateinit var examine: Examine

    fun startTest(sessionId: Instant) {
        iter = ellipses.iterator()
        _isEnd.value = false

        examine = Examine(
            deviceHeight = displayMetrics.heightPixels,
            deviceWidth = displayMetrics.widthPixels,
            start = Instant.now(),
            hand = handMode,
            sessionTimestamp = sessionId
        )

        _currentEllipse.value = nextEllipse()
    }

    fun onTouchStart() {
        examine.startRecording(currentEllipse.value!!)
    }

    fun onNewPoint(x: Float, y: Float) {
        examine.addPoint(x, y)
    }

    fun onTouchUp() {
        examine.endRecording()
        val next = nextEllipse()
        _currentEllipse.value = next

        if (next == null) {
            endTest()
        }
    }

    fun endTest() {
        _isEnd.value = true

        examine.endExamine()
        Log.d("EXAM", examine.toString())

        viewModelScope.launch {
            repository.saveExamine(examine)
        }
    }

    private fun nextEllipse(): Ellipse? =
        if (iter.hasNext()) iter.next() else null

}

class LeftOvalViewModel : OvalViewModel() {
    private val central = Ellipse.Left(0.4f, 0.6f, 0.5f, 0.8f, 1, 0f)
    private val leftUp = Ellipse.Left(0.15f, 0.35f, 0.4f, 0.6f, 2, 45f)
    private val leftDown = Ellipse.Left(0.15f, 0.35f, 0.6f, 0.8f, 3, 135f)

    override val ellipses: List<Ellipse> = listOf(central, leftUp, leftDown)

    override val handMode: Hand = Hand.Left

}

class RightOvalViewModel : OvalViewModel() {
    private val central = Ellipse.Right(0.4f, 0.6f, 0.5f, 0.8f, 1, 0f)
    private val leftUp = Ellipse.Right(0.85f, 0.65f, 0.4f, 0.6f, 2, 135f)
    private val leftDown = Ellipse.Right(0.85f, 0.65f, 0.6f, 0.8f, 3, 45f)

    override val ellipses: List<Ellipse> = listOf(central, leftUp, leftDown)

    override val handMode: Hand = Hand.Right

}