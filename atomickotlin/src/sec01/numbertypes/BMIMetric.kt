package sec01.numbertypes

/** 체질량지수(BMI) 구하기
 * BMI = 몸무게 / 키 ^ 2
 * BMI < 18.5 : 저체중
 * 18.5 <= BMI <= 24.9 : 정상 체중
 * BMI >= 25 : 과체중
 *
 */

fun bmiMetric(
    weight: Double,
    height: Double
): String {
    val bmi = weight / (height * height)
    return  if(bmi < 18.5) "Underweight"
    else if(bmi >= 18.5 && bmi <= 24.9) "Normal weight"
    else "Overweight"
}

fun main() {
    val weight = 76.0  // 160 1bs       
    val height = 1.76  // 68 inches
    val status = bmiMetric(weight, height)
    println(status) // 나는 정상
}