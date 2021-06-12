package pl.kinga.wristapp.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.kinga.wristapp.Ellipse
import java.time.Instant

data class Examine(
    val deviceHeight: Int,
    val deviceWidth: Int,
    val hand: Hand,
    var start: Instant? = null,
    var end: Instant? = null,
    val sessionTimestamp: Instant
) {
    @JsonIgnore
    val _recordings: MutableList<Recording> = mutableListOf()
    val recordings: List<Recording> get() = _recordings

    @JsonIgnore
    lateinit var currentRecording: Recording

    fun startRecording(ellipse: Ellipse) {
        val record = Recording(ellipse)
        record.start = Instant.now()
        currentRecording = record

        _recordings.add(record)
    }

    fun addPoint(x: Float, y: Float) {
        currentRecording.drawing.add(
            TimePosition(x, y)
        )
    }

    fun endRecording() {
        currentRecording.end = Instant.now()
    }

    fun endExamine() {
        end = Instant.now()
    }

}