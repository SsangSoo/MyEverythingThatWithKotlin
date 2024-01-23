package Testing

import atomictest.eq

fun main() {
    calculateBMI(160.0, 68.0) eq "Normal weight"
    calculateBMI(100.0, 68.0) eq "Underweight"
    calculateBMI(200.0, 68.0) eq "Overweight"
}

fun calculateBMI(
    weight: Double,
    height: Double
) : String {
    val bmi = weight  / (height * height) * 703.07
    return if (bmi < 18.5) "Underweight"
    else if(bmi < 25) "Normal weight"
    else "Overweight"
}