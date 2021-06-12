package pl.kinga.wristapp.domain

import pl.kinga.wristapp.Ellipse
import java.time.Instant
import kotlin.properties.Delegates

data class Recording(
    val ellipse: Ellipse,
    val drawing: MutableList<TimePosition> = mutableListOf()
) {
    var start by Delegates.notNull<Instant>()
    var end by Delegates.notNull<Instant>()
}