package sec05.composition3

interface Building
interface Kitchen

interface House: Building {
    val kitchens : List<Kitchen>
}