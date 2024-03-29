# 아토믹 코틀린 At 59. 기반 클래스 초기화

## 용어

- 없음.
 
## 예약어 및 코틀린 개념

#### 기반 클래스 초기화

- 클래스가 다른 클래스를 상속할 때, 코틀린은 두 클래스가 모두 제대로 초기화되도록 보장한다.
- 코틀린은 다음 생성자가 호출되도록 보장함으로써 올바른 객체를 생성한다.
  - 멤버 객체들의 생성자
  - 파생 클래스에 추가된 객체의 생성자
  - 기반 클래스의 생성자

- 기반 클래스에 생성자 파라미터가 있다면, 파생 클래스가 생성되는 동안 반드시 기반 클래스의 생성자 인자를 제공해야 한다.

```kotlin
package sec05.baseclassinit

import atomictest.eq

open class GreateApe(
    val weight: Double,
    val age: Int
)

open class Bonobo(weight: Double, age: Int) : GreateApe(weight, age)

class Chimpanzee(weight: Double, age: Int) : GreateApe(weight, age)

class BonoboB(weight: Double, age: Int) : Bonobo(weight, age)

fun GreateApe.info() = "wt: $weight age: $age"

fun main() {
    GreateApe(100.0, 12).info() eq "wt: 100.0 age: 12"
    Bonobo(110.0, 13).info() eq "wt: 110.0 age: 13"
    BonoboB(130.0, 15).info() eq "wt: 130.0 age: 15"
}
```

- 파라미터가 있는 기반(상위) 클래스를 상속하는 클래스는 반드시 생성자 인자를 기반(상위) 클래스에 전달해야 한다.
  - 그렇지 않으면 컴파일러 오류가 발생한다.

- 코틀린은 **객체에 사용할 메모리를 확보한 후 기반 클래스의 생성자를 먼저 호출**하고, **다음 번 파생(하위=자식) 클래스의 생성자를 호출**하며 (파생 단계에서) 맨 **나중에 파생된 클래스의 생성자를 호출**한다.
- 이런 식으로 **모든 생성자 호출은 자신 이전에 생성되는 모든 객체의 올바름에 의존**한다.
  - 실제로 상속 정보는 각 클래스가 알아야 할 모든 정보다.

- 클래스를 상속할 때는 기반 클래스 생성자의 인자 목록을 기반 클래스 이름 뒤에 붙여야 한다.
  - 이렇게 해야 하위 클래스 객체를 생성하는 중에 기반 클래스 생성자를 호출하게 된다.

```kotlin
package sec05.baseclassinit

open class SuperClass1(val i: Int) 
class SubClass1(i: Int) : SuperClass1(i)

open class SuperClass2
class SubClass2 : SuperClass2()
```

- 기반 클래스 생성자 파라미터가 없어도 코틀린은 기반 클래스의 생성자를 인자 없이 호출하기 위해 기반 클래스 이름 뒤에 괄호를 붙이도록 강제한다.
- 기반 클래스에 **부생성자가 있으면 기반 클래스의 주생성자 대신 부생성자를 호출할 수도 있다.**

- 파생 클래스의 파라미터가 꼭 기반 클래스 생성자의 파라미터의 수, 타입, 순서에 의해 제약을 받을 필요는 없다. 
- **파생 클래스의 책임은 기반 클래스 생성자를 호출할 때 제대로 인자를 제공하는 것뿐**이다.
- **기반 클래스 생성자 중에 일치하는 생성자를 찾을 수 있는 인자 목록을 사용해서 오버로드한 생성자를 호출**할 수도 있다.
- 파생 클래스의 부생성자는 기반 클래스의 생성자를 호출할 수 있고, 파생 클래스 자신의 생성자를 호출할 수도 있다.

- **기반 클래스 생성자를 호출**하려면 `super`러는 키워드를 적고, **함수를 호출할 때처럼 생성자 인자를 전달**하면 된다.
- **클래스 자신의 다른 생성자를 호출할 때는 `this`호출을 사용**한다.

## 함수

- 없음.

## 참고
- 하위 클래스를 상위 클래스를 상속한 사실을 알고, 하위클래스의 생성자는 상위 클래스의 함수를 호출할 수 있다.
- 반대로 상위 클래스는 런타임시 자신이 하위 클래스인지 아닌지 알 수 없고, 하위 클래스에서 새로 정의된 함수를 호출할 수 없다.

