package sec05.classdelegation

import atomictest.eq

class DelegatedControls(
    private val controls: SpaceShipControls =
        SpaceShipControls()
) : Controls by controls {
    override fun turboBoost(): String =
        "${controls.turboBoost()}... booooooost!"
}

fun main() {
    val controls = DelegatedControls()
    controls.forward(100) eq "forward 100"
    controls.turboBoost() eq
            "turbo boost... booooooost!"
}