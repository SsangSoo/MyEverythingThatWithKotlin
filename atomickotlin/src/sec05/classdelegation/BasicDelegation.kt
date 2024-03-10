package sec05.classdelegation

interface AI
class A : AI

class B(val a: A) : AI by a
