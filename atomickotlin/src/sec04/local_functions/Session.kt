package sec04.local_functions

class Session(
    val title: String,
    val speaker: String
)

val sessions = listOf(Session(
    "Kotlin Coroutines", "Roman Elizarov"))

val favoriteSpeakers = setOf("Roman Elizarov")

