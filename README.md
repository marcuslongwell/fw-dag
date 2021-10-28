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

## possible improvements

- use java streams for larger amounts of vertices
- consider using parallel streams for multi-threaded sorting/counting
- some way of checking for an infinite path (currently no validation that graph is actually acyclic)

## notes

- didn't know if I'd have time to merge my two functions that calculate longest paths into one, held off on that for now
- wrote one test that came up wrong, was super disappointed, then realized it was actually me counting the path on my reference that was wrong LOL