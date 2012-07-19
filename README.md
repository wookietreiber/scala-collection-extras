type class based merge, sort and shuffle for scalas (parallel) collections, based on an example
implementation with `ParVector`

the hierarchy is: Mixer extends Sorter extends Merger

`Sorter` provides `sortWith`, `sortBy` and `sorted`

`Mixer` provides `shuffle`

