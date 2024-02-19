package sec04.local_functions

import atomictest.eq

fun main() {
    sessions.any(
        fun(session: Session): Boolean { // 1
            if (session.title.contains("Kotlen") &&
                session.speaker in favoriteSpeakers
            ) {
            return true
            }
        // 추가검사
        return false
        }) eq true
}