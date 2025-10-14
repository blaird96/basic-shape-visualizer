# DFS/BFS Fix Summary

## Issue Description
The original DFS and BFS algorithms were hardcoded to only start from vertex "A". This caused the following problems:

1. If vertex "A" was removed (either individually or through Clear All), DFS/BFS would return "No vertices to search (start at 'A')" even when other vertices existed.
2. If you started with a different vertex (like creating B first), the search algorithms wouldn't work.

## Solution Implemented
**Smart Starting Vertex Selection (Option 3)**

### Changes Made:

#### 1. Graph.java
- Added `getStartingVertex()` helper method that:
  - Returns "A" if it exists (maintains backward compatibility)
  - Otherwise returns the first vertex alphabetically
  - Returns null if no vertices exist

- Updated `dfsOrder()` method:
  - Uses `getStartingVertex()` instead of hardcoded "A"
  - Works with any available vertex

- Updated `bfsOrder()` method:
  - Uses `getStartingVertex()` instead of hardcoded "A"
  - Works with any available vertex

#### 2. MainApp.java
- Updated status messages to show which vertex the search started from:
  - `"DFS Order (starting from X): A → B → C"`
  - `"BFS Order (starting from Y): Y → Z → A"`
- Removed misleading reference to requiring vertex "A"

#### 3. USAGE.md
- Updated documentation to reflect the new behavior
- Clarified that DFS/BFS no longer requires vertex "A"

## Test Scenarios Now Working:
1. ✅ Create A, B, C → Remove A → DFS/BFS works (starts from B)
2. ✅ Clear All → Create B → DFS/BFS works (starts from B)  
3. ✅ Create C, D, E → DFS/BFS works (starts from C)
4. ✅ Normal case with A present → DFS/BFS works (starts from A)

## Backward Compatibility:
- If vertex "A" exists, behavior is identical to before
- Only when "A" doesn't exist does the new smart selection kick in
- All existing functionality preserved
