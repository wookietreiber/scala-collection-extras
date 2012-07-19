type class based merge, sort and shuffle for scalas (parallel) collections, based on an example
implementation with `ParVector`

the hierarchy is: Mixer extends Sorter extends Merger

`Sorter` provides `sortWith`, `sortBy` and `sorted`

`Mixer` provides `shuffle`

```
sbt console

...

scala> val xs = collection.parallel.immutable.ParVector.fill(100)(scala.util.Random.nextInt(100))
xs: scala.collection.parallel.immutable.ParVector[Int] = ParVector(93, 46, 10, 33, 54, 28, 5, 76,
21, 29, 27, 22, 47, 34, 37, 38, 8, 0, 31, 81, 54, 4, 47, 84, 15, 13, 86, 17, 68, 89, 69, 87, 4, 93,
65, 9, 21, 31, 3, 4, 28, 23, 40, 82, 84, 14, 60, 64, 13, 34, 30, 29, 72, 74, 60, 13, 42, 14, 9, 84,
91, 76, 82, 39, 0, 35, 61, 13, 8, 93, 49, 12, 20, 12, 38, 38, 98, 82, 40, 19, 81, 37, 76, 81, 48,
82, 39, 64, 8, 26, 12, 5, 48, 83, 61, 41, 8, 73, 34, 83)

scala> xs.sorted
res0: scala.collection.parallel.immutable.ParVector[Int] = ParVector(0, 0, 3, 4, 4, 4, 5, 5, 8, 8,
8, 8, 9, 9, 10, 12, 12, 12, 13, 13, 13, 13, 14, 14, 15, 17, 19, 20, 21, 21, 22, 23, 26, 27, 28, 28,
29, 29, 30, 31, 31, 33, 34, 34, 34, 35, 37, 37, 38, 38, 38, 39, 39, 40, 40, 41, 42, 46, 47, 47, 48,
48, 49, 54, 54, 60, 60, 61, 61, 64, 64, 65, 68, 69, 72, 73, 74, 76, 76, 76, 81, 81, 81, 82, 82, 82,
82, 83, 83, 84, 84, 84, 86, 87, 89, 91, 93, 93, 93, 98)

scala> res0.shuffle
res1: scala.collection.parallel.immutable.ParVector[Int] = ParVector(65, 93, 34, 29, 4, 37, 34, 14,
68, 29, 84, 84, 39, 91, 81, 38, 0, 5, 38, 13, 3, 21, 76, 89, 15, 64, 73, 23, 31, 82, 14, 21, 83, 48,
81, 13, 4, 4, 9, 48, 31, 82, 26, 47, 28, 47, 5, 61, 42, 60, 93, 27, 12, 8, 34, 84, 19, 98, 33, 82,
17, 61, 46, 0, 22, 9, 76, 8, 83, 86, 13, 38, 76, 8, 82, 41, 64, 72, 10, 81, 12, 39, 30, 28, 13, 40,
37, 69, 87, 8, 20, 74, 93, 54, 35, 40, 12, 49, 54, 60)
```

