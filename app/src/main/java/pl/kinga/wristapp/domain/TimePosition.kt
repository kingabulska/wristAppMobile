package pl.kinga.wristapp.domain

import java.time.Instant

data class TimePosition(
    val x: Float,
    val y: Float,
    val timestamp: Instant = Instant.now()
)