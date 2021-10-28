# fw-dag

directed acrylic graph exercise

## thoughts as I go

- read that could use the same algorithm as shortest path and just flip the numbers
- figured out how to topologically sort vertices on the graph from linked wikipedia article
- got the longest path working from start vertex to end vertex (found YT video that explained it, implemented algorithm myself)
- realized that the algorithm doesn't work if I start in the middle (still accounts for anything that got sorted after starting node, only want it to include paths that began with starting node)
- needed to figure out a way to be able to specify any two vertices AND still work specifying only a starting vertex
- decided to figure out any two vertices first, which should solve earlier problem
- got two vertices working, got starting vertex to any vertex working
- need to be able to handle millions of vertices, will create random dag via factory
- when adding tests that tested the timing it took to traverse larger dags, I got an estimate of about the time they were failing
  - it seemd like I could rely pretty consistently on less than 100ms for smaller ones
  - usually medium ones exceeded 1 second
  - usually larger ones exceeded 3 seconds
  - usually largest ones exceeded 5 seconds
- all of these were exceeded by quite a bit actually (not a few milliseconds), so in my efforts to make this more efficient, the goal was to get tests to pass every time lower than where they typically hit now
- realized I needed a consistent test with consistent vertices and consistent paths, so added a tree dag factory method so I could actually measure performance impact with no randomization

## possible improvements

- make the vertices array on the class mutable, sort once after creating/adding vertices/edges
- not creating/copying so many arraylists (possibly consider other, faster implementations as well)
  - would arrays be faster? would have to test
- use java streams for larger amounts of vertices
- consider using parallel streams for multi-threaded sorting/counting
- some way of checking for an infinite path (currently no validation that graph is actually acyclic)
- could maybe implement sub-graphs within graphs recursively for more readable code (dunno how this would impact performance)

## notes

- didn't know if I'd have time to merge my two functions that calculate longest paths into one, held off on that for now
- wrote one test that came up wrong, was super disappointed, then realized it was actually me counting the path on my reference that was wrong LOL
- you can reference the references folder for pngs containing a depiction of the graph used in some of the preliminary tests