### HW3 Feedback

**CSE 331 16su**

**Name:** Jun Wang (wangj48)

**Graded By:** Kevin Zatloukal (kevinz)

### Score: 95/100
---

**Problem 3 - HolaWorld:** 5/5

**Problem 4 - RandomHello:** 9/10

- You can remove the comment that says "YOUR CODE GOES HERE".

- It would be preferable to create "String[] greetings" as a static field.
  That would avoid reallocating it on every call. 
  (The same applies to randomGenerator.)
  
- It's safer to write randomGenerator.nextInt(greetings.length) instead of
  randomGenerator.nextInt(5) as it's easy to forget to update the second one
  if you add more greetings later on.

**Problem 5 - Testing (Fibonacci) Java Code with JUnit :** 5/5

**Problem 6 - Answering Questions about the (Fibonacci) Code:** 13/15

- Question 1: 5/5
- Question 2: 3/5
   - testBaseCase only required fixing the "<= 0" condition (as in part a)
- Question 3: 5/5

**Problem 7 - Implementation:** 63/65

- Ball.java: 5/5
- BallContainer.java: 25/25
- Box.java: 33/35
  - It doesn't seem necessary to use LinkedHashSets in getBallsFromSmallest.
    You could do this just as well with ArrayLists.
  - You also probably want to avoid writing your own sort routines in any case.
    You can call Collections.sort to do this faster and with less work.
  - If I'm understanding the descriptions of your approaches in answers.txt
    correctly, it sounds like both are sorting the list of balls in
    getBallsFromSmallest. Can you think of other approaches that don't sort the
    balls in getBallsFromSmallest?
