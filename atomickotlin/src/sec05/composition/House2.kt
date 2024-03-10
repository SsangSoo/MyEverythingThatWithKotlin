package sec05.composition2

interface Building
interface Kitchen

interface House2: Building {
    val kitchen1 : Kitchen
    val kitchen2 : Kitchen
}