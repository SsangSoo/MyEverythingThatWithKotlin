package sec05.composition

interface Building
interface Kitchen

interface House: Building {
    val kitchen : Kitchen
}