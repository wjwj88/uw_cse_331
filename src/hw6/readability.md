### Readability Feedback
---
**Naming:** 
- Following Java conventions:
  - pa_new and pa_old should be paNew and paOld. Also what is "pa"?
- Using informative names
  - "pa" is not informative. However, it's not even necessary to name the
    object in that case. You could have just written:
        return new ArrayList<EdgesOfNode<String,String>>(pathList.get(node));

**Formatting:** 
- Whitespace in expressions
  - Put a space before a "{" at the end of a line.
  - Put a space after keywords like "for" and "if".
  - Put spaces around operators like "=".
  - Put spaces around ":".
- Parentheses in expressions
  - The outer parentheses are unnecessary in
    "!(e1.getTail().equals(e2.getTail()))".
- Line length
  - A couple of lines are too long (over 100 characters).

**Indenting:** 
  - good

**Commenting:** 
- Informative comments
  - "add nodes to the graph" is not really informative. The code already says
    that. Which nodes are you adding?
- Sufficient comments
  - MarvelPaths is missing an overview comment.
  - MarvelPaths.main has no comments at all in the method body.
