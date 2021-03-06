# Pascar: A Programming Language that has Pascal-like Syntaxes

[![Build Status](https://travis-ci.org/kmizu/pascar.png?branch=master)](https://travis-ci.org/pascar/pascar)

Pascar is yet another statically typed programming language.  Pascar has: 

* Powerful Type System
  * based on Hindley-Milner type system
  * support object system based on row polymorphism
* Lexically-scoped Variables
* First-class Functions
* String Interpolation
  * found in Ruby, Scala, Kotlin, etc.
* Loop Expression
* Space-sensitive and Line-sensitive Syntax
  * list literals
  * map literals
  * set literals
* , etc.

Pascar's syntax is heavily inspired by Pascal.

## Instalattion and Quick Start

It requires Java 8 or later.

You can download the binary distribution (executable jar) from the [release page](https://github.com/pascar/pascar/releases/tag/releases/v0.0.1).  Put the file on an directory and execute the pascar.jar by `java -jar` command:

```
$ java -jar pascar.jar

Usage: java -jar pascar.jar (-f <fileName> | -e <expression>)
<fileName>   : read a program from <fileName> and execute it
-e <expression> : evaluate <expression>
```

Write the folowing lines and save it to `hello.pascar`.

```pascal
writeln('Hello, World!')
```

And run the interpreter by `java -jar pascar.jar hello.pascar`:

```
$ java -jar pascar.jar hello.pascar

Hello, World!
```

## Syntax

### Variable Declaration

```pascal
var one = 1
```

Declare variable `one` and `one` is bound to `1`.  You can omit
semicolon(`;`) at the last of the declaration:

```pascal
var name = expression [;]
```

### Constant Declaration

```pascal
const name = expression [;]
```

Declared constant 'one_immutable' is bound to '2'. You can omit
semicolo (`:`) at the last of the declarat ion:

You can use type annotaion as you like:

```pascal
var name: String = 'FOO'
```

### If Expression

The syntax of if expression is like Pascal:

```pascal
if i < 3 then
  writeln('i < 3')
else
  writeln('i >= 3')
end
```

### While Expression

Thke syntax of while expression is like Pascal:

```pascal
var i = 0
while i < 10 do
begin
  i := i + 1
  writeln(i)
end
```

### Function Literal

```
var add = (x, y) => x + y
```

Declare variable `add` and `add` is bounded to **the function literal** that
calculates `x + y`.  If an anonymous function has block body, you can write as
the following:

```
var printAndAdd = (x, y) => {
  writeln(x)
  writeln(y)
  x + y
}
```

Note that semicolon at the end of each expression of block can be omitted.

### Named Function

If you want to define recursive functions, anonymous function literal cannot be used.
Instead, you can use the notation for recursive functions:

```pascal
function fact(n)
begin
  if n < 2 then 
    1 
  else 
    n * fact(n - 1) 
  end
end
fact(0) // 1
fact(1) // 1
fact(2) // 2
fact(3) // 6
fact(4) // 24
fact(5) // 120
// The result of type inference of fact is : Int => Int
```

### Function Invocation

```pascal
const add = (x, y) => x + y
writeln(add(1, 2))
```

A function can be invoked as the form `fun(p1, p2, ..., pn)`.  The evaluation
result of `fun` must be a function object.

### List Literal

```pascal
const list = [1, 2, 3, 4, 5]
writeln(list)
```

A list literal can be expressed as the form `[e1, e2, ...,en]`.  Note that
separator characters have also line feeds and spaces in Klassic unlike other programming languages.

```
const list = [
  1
  2
  3
  4
  5
]
writeln(list)
var list = [[1 2 3]
            [4 5 6]
            [7 8 9]]
```

The type of list literal is a instance of special type constructor `List<'a>`.

### Map Literal

```
const map = %['A': 1, 'B': 2]
map Map#get 'A' // => 1
map Map#get 'B' // => 2
map Map#get 'C' // => null
```

A map literal can be expressed as the form `%[k1:v1, ..., kn:vn]` (`kn` and `vn` are expressions).  Note that
separator characters also include line feeds and spaces in Klassic unlike other programmign languages:

```
const map2 = %[
  'A' : 1
  'b' : 2
]
```

The type of map literal is a instance of special type constructor `Map<'k, 'v>`.

### Set Literal

A map literal can be expressed as the form `%(v1, ..., vn)` (`vn` are expressions).  Note that
separator characters also include line feeds and spaces in Klassic unlike other programmign languages:

```
const set1 = %(1, 2, 3)
```

```
const set2 = %(1 2 3) // space is omitted
```

```
const set3 = %(
  1
  2
  3
)
```

The type of set literal is a instance of special type constructor `Set<'a>`.

### Numeric Literal

Pascar supports various literal.  The followings are explanations:

### Int

```
writeln(100)
writeln(200)
writeln(300)
```

The max value of Int literals is `Int.MaxValue` in Scala and the min value of integer literals is 
`Int.MinValue` in Scala.

### Byte

The suffix of byte literal is `BY`.  The max value of long literals is `Byte.MaxValue` in Scala and 
the min value of long literals is `Byte.MinValue` in Scala.

```
writeln(127BY)
writeln(-127BY)
writeln(100BY)
```

### Short

The suffix of short literal is `S`.  The max value of long literals is `Short.MaxValue` in Scala and 
the min value of long literals is `Short.MinValue` in Scala.

```
writeln(100S)
writeln(200S)
writeln(300S)
```

### Long

```
writeln(100L)
writeln(200L)
writeln(300L)
```

The suffix of long literal is `L`.  The max value of long literals is `Long.MaxValue` in Scala and 
the min value of long literals is `Long.MinValue` in Scala.

### Double

```
writeln(1.0)
writeln(1.5)
```

The max value of double literal is `Double.MaxValue` in Scala and the min value of double literal is `Double.MinValue`
in Scala.

### Float

```
writeln(1.0F)
writeln(1.5F)
```

The max value of float literal is `Float.MaxValue` in Scala and the min value of float literal is `Float.MinValue`
in Scala.

### Comment

Pascar provides two kinds of comment

### (Nestable) Block Comment

```
1 + (* nested
  (* comment */ here *) 2 // => 3
```

### Line comment

```
1 + // comment
    2 // => 3
```

## Type System

Pascar is a statically-typed programming language.

### Hindley-Milner Type Inference

Pascar's type inference is based on HM.  It means that type annotations is not required in many cases:

```
function fold_left(list) 
begin
  (z) => (f) => {
    If isEmpty(list) Then z Else fold_left(tail(list))(f(z, head(list)))(f) End If
  }
end
// The result of type inference: List<'a> => 'b => (('b, 'a) => 'b) => 'b
```

### Row Polymorphism

Pascar has simple object system based on row polymorphism.  For example,

```
function add(o)
begin
  o.x + o.y
end
```

the type of above program is inferred:

```
add: { x: Int; y: Int; ... } 
```

It means that `add` function accepts any object that has field `x` and field `y`.
Although it is not subtyping strictly, many situations that need subtyping are
covered.

### Type Cast

In some cases, escape hatches from type system are required. In such cases,
user can insert cast explicitly.

```
var s: * = (100 :> Double) // 100 is casted to Double type
```

## Built-in Functions

Pascar supports some kind of built-in functions.

### Standard output Functions

- `writeln: (param:Any) => Any`  
    display the `param` into the standard output.  
    ```
    writeln('Hello, World!')
    ```

### String Functions

- `substring: (s:String, begin:Int, end:Int) => String`  
    Returns a substring of the String `s`. The substring begins at the index `begin` and ends at the index `end` - 1.  
    ```
    substring('FOO', 0, 1) // => 'F'
    ```

- `at: (s:String, index:Int) => String`  
    Returns a String with a character value at the index `index` of the String `s`.  
    ```
    at('BAR', 2) // => 'R'
    ```

- `matches: (s:String, regex:String) => Boolean`  
    Returns true if the String `s` matches the regular expression `regex`, false otherwise.  
    ```
    val pattern = '[0-9]+'
    matches('199', pattern) // => true
    matches('a', pattern)   // => false
    ```

### Numeric Functions

- `sqrt: (value:Double) => Double`  
    Returns the square root of the Double `value`.
    ```
    sqrt(2.0) // => 1.4142135623730951
    sqrt(9.0) // => 3.0
    ```
- `int: (vaue:Double) => Int`  
    Returns the Double `value` as the Int value.
    ```
    int(3.14159265359) // => 3
    ```

- `double: (value:Int) => Double`  
    Returns the Int `value` as the Double value.  
    ```
    double(10) // => 10.0
    ```

- `floor: (value:Double) => Int`  
    Returns the truncated Double `value` as the Int value.
    ```
    floor(1.5) // => 1
    floor(-1.5) // => -1
    ```

- `ceil: (value:Double) => Int`  
    Returns the rounded-up Double `value` as the Int value.
    ```
    ceil(4.4)  // => 5
    ceil(4.5)  // => 5
    ceil(-4.4) // => -4
    ceil(-4.5) // => -4
    ```

- `abs: (value:Double) => Double`  
    Returns the absolute value of the Double `value`.
    ```
    abs(10.5)  // => 10.5
    abs(-10.5) // => 10.5
    ```

### List Functions

- `map: (list:List<'a>) => (fun:('a) => 'b) => List<'b>`  
    Returns a new List consisting of the results of applying the given function `fun` to the elements of the given List `list`.
    ```
    map([1 2 3])((x) => x + 1) // => [2 3 4]
    map([2 3 4]){x => x + 1}   // => [3 4 5]
    ```

- `head: (list:List<'a>) => List<'a>`  
  Returns the first element of the List `list`.
  ```
  head([1 2 3 4]) // => 1
  ```

- `tail: (list:List<'a>) => List<'a>`  
    Returns a new List consisting of the elements of the given List `list` except for the first element.
    ```
    tail([1 2 3 4]) // => [2 3 4]
    ```

- `cons: (value:'a) => (list:List<'a>) => List<'a>`  
    Creates a new List, the head of which is `value` and the tail of which is `list`.  
    ```
    cons(1)([2 3 4]) // => [1 2 3 4]
    ```

- `size: (list:List<'a>) => Int`  
    Returns the size of the List `list`.  
    ```
    size([1 2 3 4 5]) // => 5
    ```

- `isEmpty: (list:List<'a>) => Boolean`  
    Returns true if the List `list` is empty, false otherwise.  
    ```
    isEmpty([])       // => true
    isEmpty([1 2 3])  // => false
    ```

- `foldLeft: (list:List<'a>) => (acc:'b) => (fun:('b, 'a) => 'b) => 'b`  
    Applies a function `fun` to a start value `acc` and all elements of the List `list`, going left to right.
    ```
    foldLeft([1 2 3 4])(0)((x, y) => x + y)         // => 10
    foldLeft([1.0 2.0 3.0 4.0])(0.0){x, y => x + y} // => 10.0
    foldLeft([1.0 2.0 3.0 4.0])(1.0){x, y => x * y} // => 24.0
    ```

### Thread Functions

- `thread: (fun:() => Unit) => Unit`  
    Creates a new thread and starts runnng the passed argument function `fun` asynchronously.  
    ```
    thread(() => {
      sleep(1000)
      writeln('Hello from another thread.')
    })
    writeln('Hello from main thread.')
    // => 'Hello from main thread.'
    // => 'Hello from another thread.'
    ```

- `sleep: (millis:Int) => Unit`  
    Causes the current thread to sleep for the `millis` milliseconds.
    ```
    sleep(1000)
    ```

### Utility Functions

- `stopwatch: (fun:() => Unit) => Int`  
    Returns the time in milliseconds taken to evaluate the passed argument function `fun`.
    ```
    val time = stopwatch( => {
      sleep(1000)
      writeln('1')
    })
    writeln('it took #{time} milli seconds')
    ```

- `ToDo: () => Unit`  
    Throws `pascar.runtime.NotImplementedError` when evaluated.
    ```
    ToDo()  // => throw NotImplementedError
    ```

### Assertion Functions

- `assert: (condition:Boolean) => Unit`  
    Asserts that the `condtion` should be true, and throws `pascar.runtime.AssertionError` if the `condition` is false.
  ```
  assert(2 == 1 + 1)  // => OK
  assert(3 > 5)       // => NG: AssertionError
  ```

- `assertResult: (expected:Any)(actual:Any) => Unit`  
    Asserts that the `actual` value should be equal to the `expected` value, and throws `pascar.runtime.AssertionError` if the `actual` value is not equal to the `expected` value.
    ```
    val add = (x, y) => {
      x + y
    }
    assertResult(5)(add(2, 3))  // => OK
    assertResult(2)(add(1, 2))  // => NG: AssertionError
    ```

### Interoperating Functions

- `url: (value:String) => java.net.URL`  
    Creates new `java.net.URL` object from a String `value`.
    ```
    url('https://github.com/pascar/pascar')
    ```

- `uri: (value:String) => java.net.URI`  
    Creates new `java.net.URI` object from a String `value`.
    ```
    uri('https://github.com/pascar/pascar')
    ```

- `desktop: () => java.awt.Desktop`  
    Returns the Desktop instance of the current browser context via Java Desktop API.
    ```
    desktop()->browse(uri('https://github.com/pascar/pascar'))
    ```
