# 아토믹 코틀린 At 57. 부생성자

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

#### 부생성자
- 객체를 생성하는 방법이 여럿 필요할 경우 이름 붙은 인자나 디폴트 인자를 통하는 게 가장 쉽다. 하지만 때로는 오버로드한 생성자를 여러 개 만들어야 할 경우도 있다.
- **같은 클래스에 속한 객체를 만들어내는 방법을 여러 가지 원할 경우** 생성자를 **'오버로드'**해야 한다.
- **코틀랜**에서는 **오버로드한 생성자**를 **부생성자(secondary constructor)**라고 부른다.
- **생성자 파라미터 목록(클래스 이름 뒤에 옴)과 프로퍼티 초기화, ini 블록들을 모두 합한 생성자는 `주생성자(primary constructor)`라고 부른다.**

- `부생성자`를 만들려면 `constructor` 키워드 다음에 **주생성자나 다른 부생성자의 파라미터 리스트와 구별되는 파라미터 목록을 넣어야 한다.**
- 부생성자 안에서는 `this` 키워드를 통해 **주생성자나 다른 부생성자를 호출**한다.

```kotlin
package sec05.secondaryconstructors

import atomictest.*

class WithSecondary(i: Int) {
    init {
        trace("Primary: $i")
    }
    constructor(c: Char) : this(c - 'A') {
        trace("Secondary: '$c'")
    }
    constructor(s: String) :
        this(s.first()) { // 1
        trace("Secondary: \"$s\"")
        }
    /* 주생성자를 호출하지 않으면
        컴파일이 되지 않는다.
    constructor(f: Float) { // 2
        trace("Secondary: $f")
    }
    */
}

fun main() {
    fun sep() = trace("-".repeat(10))
    WithSecondary(1)
    sep()
    WithSecondary('D')
    sep()
    WithSecondary("Last Constructor")
    trace eq
"""
Primary: 1
----------
Primary: 3
Secondary: 'D'
----------
Primary: 11
Secondary: 'L'
Secondary: "Last Constructor"
"""
}
```

- **부생성자에서 다른 생성자를 호출(this 사용)하는 부분은 생성자 로직 앞에 위치**해야 한다.
- 생성자 본문이 다른 초기화의 결과에 영향을 받을 수 있기 때문이다.
- 따라서 **다른 생성자 호출이 생성자 본문보다 앞에 있어야 한다.**

- **호출할 생성자는 인자 목록이 결정**한다.

- `주 생성자`는 언제나 **부생성자에 의해 직접 호출**되거나 **다른 부생성자의 호출을 통해 간접적으로 호출**되어야 한다.
  - 그렇지 않으면 '// 2'에서처럼 **코틀린이 컴파일 오류**를 낸다.
  - 따라서 **생성자 사이에 공유되어야 하는 모든 초기화 로직은 반드시 주생성자에 위치**해야 한다.

```kotlin
package sec05.secondaryconstructors

import atomictest.eq
import sec05.secondaryconstructors.Material.*

enum class Material {
  Ceramic, Metal, Plastic
}

class GardenItem(val name: String) {
  var material: Material = Plastic

  constructor(
    name: String, material: Material    // 1
  ) : this(name) {                      // 2
    this.material = material            // 3
  }
  constructor(
    material: Material
  ) : this("Strange Thing", material)   // 4
  override fun toString() = "$material $name"
}

fun main() {
  GardenItem("Elf").material eq Plastic
  GardenItem("Snowman").name eq "Snowman"
  GardenItem("Gazing Ball", Metal) eq   // 5
          "Metal Gazing Ball"
  GardenItem(material = Ceramic) eq
          "Ceramic Strange Thing"
}
```

- // 1 : **주생성자의 파라미터만** `val`이나 `var`를 덧붙여 **프로퍼티로 선언**할 수 있다.
- // 2 : **부생성자에 반환 타입을 지정할 수 없다.**
- // 3 : `material` 파라미터는 **프로퍼티와 같은 이름**이므로 `this`를 사용해 **모호성을 없애야 한다.**
- // 4 : **부생성자 본문을 적지 않아도 된다(하지만 this() 호출은 반드시 포함해야 한다.)**

// 5 에서 첫 번째 부생성자를 호출할 때 material 프로퍼티가 두 번 대입된다. 먼저(//2에서) 주생성자를 호출하고 모든 클래스 프로퍼티 값을 초기화할 때 `Plastic` 값이 할당되며, 이후 //3에 의해 material 파라미터로 설정된다.

- **디폴트 인자**를 써서 **부생성자를 주생성자 하나로 만들면 GardenItem 클래스를 더 단순하게 만들 수 있다.**

## 함수

- 없음.

## 참고

- **부생성자**를 쓸 때 `init`블록을 꼭 쓸 필요는 없다.**

