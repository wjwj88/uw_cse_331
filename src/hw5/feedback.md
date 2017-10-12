### HW5 Feedback

**CSE 331 16su**

**Name:** Jun Wang (wangj48)

**Graded By:** <Justin Bare> (<ta email>)

### Score: 77.5/85
---

**Problem 1 - Written Excercises:** 13/13

- a. 4
  - 1: 
    - RI: 
    - AF: 
  - 2:
    - RI: Missing size <= entries.length
    - AF: 
- b. 3
- c. 6
  - 1. 
  - 2. 
  - 3. 
  - 4. 
  - 5. 
  - 6. 

**Problem 2 - Graph Specification:** 22/25

- API/Javadoc: 14/15
  - Missing Javadoc for hasEdge(String head, String tail, String edge) (-1)
  - 
- Writeup: 8/10
  - Missing key design details or explanation of why each method was chosen. (-2)

**Problem 3 - Tests :** 9/12

- You are missing tests for your addEdge method (-2)
- All of your tests should have descriptive names and comments about what they are testing (-1)

**Problem 4 - Graph Implementation:** 23.5/25

- Correctness: 10/10
  - Failed 1 staff test
    - testListChildrenSortedByEdge.test 
expected:<...of n1 in A are: n1(e[A) n1(eZ) n2(eA) n2(eJ) n2(eZ]) n3(e3)
> 
but was:<...of n1 in A are: n1(e[Z) n1(eA) n2(eA) n2(eZ) n2(eJ]) n3(e3)
>
- Style: 8.5/10
  - Declare fields as interface type instead of concrete type (e.g. Map instead of HashMap) (-.5)
  - You need more internal method comments in long methods with lots of nested loops and ifs (-1)
- Writeup: 5/5

**Problem 5 - Test Driver:** 5/5

**Problem 6 - Validate:** 5/5
