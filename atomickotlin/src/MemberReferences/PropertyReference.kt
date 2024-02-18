package MemberReferences1

import atomictest.eq

data class Message(
    val sender: String,
    val text: String,
    val isRead: Boolean
)

fun main() {
    val message = listOf(
        Message("Kitty", "Hey!", true),
        Message("Kitty", "Where are you?", false))
    val unread =
        message.filterNot(Message::isRead)
    unread.size eq 1
    unread.single().text eq "Where are you?"
}