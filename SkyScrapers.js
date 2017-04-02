function solvePuzzle(clues) {
  return SkyScrapers.solvePuzzle(clues);
}
var SkyScrapers = (function () {
    function SkyScrapers() {
    }

    SkyScrapers.solvePuzzle = function (clues) {
        var sideLength = ((clues.length + 1) / 4 | 0);
        var solution = [];
        var lockGrid = [];

        // intialize solution array
        for (var i = 0; i < sideLength; i++) {
            var row = [];
            var lockRow = [];
            for (var j = 0; j < sideLength; j++) {
                row.push(0);
                lockRow.push(false);
            }
            solution.push(row);
            lockGrid.push(lockRow);
        }

        SkyScrapers.solveByClues(solution, clues); // get known values based on the clues
        for (var i = 0; i < solution.length; i++) {
            for (var j = 0; j < solution.length; j++) {
                if (solution[i][j] != 0) {
                    lockGrid[i][j] = true;
                }
            }
        }

        var possibleRows = SkyScrapers.getAllPossibleRows(sideLength); // all Perms
        var possibleRowsWithLocking = SkyScrapers.getAllPossibleRowsWithLocking(solution); // all Perms based on known values

        var count = [0];
        SkyScrapers.fillInGrid(solution, possibleRowsWithLocking, lockGrid, 0, clues, count);
        console.log("iterations: " + count);
        return solution;
    };

    SkyScrapers.getVisibleBuildings = function(row) {
        var maxValue = 0;
        var visibleCount = 0;

        for (var i = 0; i < row.length; i++) {
            if (row[i] > maxValue) {
                maxValue = row[i];
                visibleCount++;
            }
        }
        return visibleCount;
    };

    SkyScrapers.maxFilterRows = function(solution, rows, rowNum, clues) {
        var maxFilterRows = [];
        // console.log(rows);
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            var clueNum1 = rowNum + solution.length;
            var clueNum2 = clues.length - 1 - rowNum;
            var clueVal1 = clues[clueNum1];
            var clueVal2 = clues[clueNum2];

            if (clueVal2 !== 0 && SkyScrapers.getVisibleBuildings(row) != clueVal2) {
                continue;
            }

            row.reverse();

            if (clueVal1 !== 0 && SkyScrapers.getVisibleBuildings(row) != clueVal1) {
                row.reverse();
                continue;
            } else {
                row.reverse();

            }

            maxFilterRows.push(row);
        }


        return maxFilterRows;
    };

    SkyScrapers.fillInGrid = function (solution, possibleRowsWithLocking, lockGrid, rowNum, clues, count) {
        var isLastRow = rowNum === (solution.length - 1);
        var possibleRows = possibleRowsWithLocking[rowNum];
        // filter out even more rows based on the part of the grid that's already completed
        var possibleRowsWithCompletion = SkyScrapers.filterRowsFromSolution(solution, possibleRows, rowNum);
        // console.log(possibleRowsWithCompletion.length);
        var maxFilteredRows = possibleRowsWithCompletion.length > 0 ?
             SkyScrapers.maxFilterRows(solution, possibleRowsWithCompletion, rowNum, clues) :
              possibleRowsWithCompletion;
        // console.log(maxFilteredRows.length);

        // var rowConstrained = [];
        // for (int i = 0; i < rowNum; i++) {

        // }
        // possibleRowsWithLocking

        for (var i = 0; i < maxFilteredRows.length; i++) {

            var origRow = solution[rowNum];
            count[0]++;
            var row = maxFilteredRows[i];

            // console.log(solution);
            // console.log(row);

            solution[rowNum] = row;

            SkyScrapers.clearLaterRows(solution, rowNum, lockGrid);

            if (isLastRow) {
                if (SkyScrapers.isValid(solution) && SkyScrapers.isClueSafe(solution, clues, true)) {
                    return solution;
                }
            } else {
                if (SkyScrapers.isValid(solution) && SkyScrapers.isClueSafe(solution, clues, false)) {
                    SkyScrapers.fillInGrid(solution, possibleRowsWithLocking, lockGrid, rowNum+1, clues, count);
                    if (SkyScrapers.isFilledIn(solution)) {
                        return solution;
                    }
                }
            }
            solution[rowNum] = origRow;
        }
    };

    SkyScrapers.clearLaterRows = function (arr, rowNum, lockGrid) {
        for (var i = rowNum + 1; i < arr.length; i++) {
            for (var j = 0; j < arr[i].length; j++) {
                if (lockGrid[i][j] == false) {
                    arr[i][j] = 0;
                }
            }
        }
    };

    SkyScrapers.isFilledIn = function(solution) {
        for (var i = 0; i < solution.length; i++) {
            for (var j = 0; j < solution[i].length; j++) {
                if (solution[i][j] === 0) {
                    return false;
                }
            }
        }
        return true;
    }
    SkyScrapers.isValid = function (solution) {
        for (var j = 0; j < solution[0].length; j++) {
            var heightCounts = Array(solution.length).fill(0);
            for (var i = 0; i < solution.length; i++) {
                var height = solution[i][j];
                if (height > 0) {
                    heightCounts[height - 1]++;
                }
            }
            for (var i = 0; i < heightCounts.length; i++) {
                if (heightCounts[i] > 1) {
                    return false;
                }
            }
        }
        return true;
    };
    SkyScrapers.isClueSafe = function (solution, clues, isStrict) {
        for (var i = 0; i < clues.length; i++) {
            if (clues[i] !== 0) {
                var rowCol = SkyScrapers.getRowCol(solution, i);
                var firstZeroLocation = -1;
                var maxBuildingIndex = -1;
                var maxSoFar = -1;
                var visibleBuildings = 0;

                for (var j = 0; j < rowCol.length; j++) {
                    if (rowCol[j] === 0 && firstZeroLocation === -1) {
                        firstZeroLocation = j;
                    } else if (rowCol[j] === rowCol.length) {
                        maxBuildingIndex = j;
                    }

                    if (rowCol[j] > maxSoFar) {
                        visibleBuildings++;
                        maxSoFar = rowCol[j];
                    }
                }

                if (firstZeroLocation !== -1) {
                    if (maxBuildingIndex !== -1 && firstZeroLocation > maxBuildingIndex) {
                        // doesnt matter that there are zeros later, we've seen all the buildigns we're going to see
                        if (clues[i] !== visibleBuildings) {
                            return false;
                        }
                    }

                } else if(clues[i] !== visibleBuildings) {
                    return false;
                }
            }
        }

        return true;
    };
    SkyScrapers.isFullyIntialized = function (row) {
        for (var i = 0; i < row.length; i++) {
            if (row[i] === 0) {
                return false;
            }
        }
        return true;
    };

    SkyScrapers.solveByClues = function (solution, clues) {
        for (var i = 0; i < clues.length; i++) {
            var clueVal = clues[i];
            if (clueVal === 1 || clueVal === solution.length) {
                SkyScrapers.setRowCol(solution, i, clueVal);
            }
        }
    }

    SkyScrapers.setRowCol = function (solution, clueNum, clueVal) {
        var list = [];
        var section = (clueNum / solution.length | 0);
        var backwards = section === 1 || section === 2;
        var isCol = section === 0 || section === 2;
        var isCountBack = section === 2 || section === 3;
        var index = clueNum % solution.length;
        if (isCountBack) {
            index = solution.length - index - 1;
        }

        var clueMin = 1;
        var clueMax = solution.length;
        for (var i = 0; i < solution.length; i++) {
            var realI = i;
            if (backwards) {
                realI = solution.length - 1 - i;
            }
            if (isCol) {
                if (clueVal == clueMin) {
                    solution[realI][index] = clueMax;
                    break;
                } else if (clueVal == clueMax) {
                    solution[realI][index] = i + 1;
                }
            } else {
                if (clueVal == clueMin) {
                    solution[index][realI] = clueMax;
                    break;
                } else if (clueVal == clueMax) {
                    solution[index][realI] = i + 1;
                }
            }
        }
    };

    SkyScrapers.getRowCol = function (solution, clueNum) {
        var list = [];
        var section = (clueNum / solution.length | 0);
        var backwards = section === 1 || section === 2;
        var isCol = section === 0 || section === 2;
        var isCountBack = section === 2 || section === 3;
        var index = clueNum % solution.length;
        if (isCountBack) {
            index = solution.length - index - 1;
        }
        for (var i = 0; i < solution.length; i++) {
            if (isCol) {
                list.push(solution[i][index]);
            }
            else {
                list.push(solution[index][i]);
            }
        }
        if (backwards) {
          list.reverse();
        }
        var result = Array(list.length).fill(0);
        for (var i = 0; i < list.length; i++) {
            result[i] = list[i];
        }
        return result;
    };

    SkyScrapers.getAllPossibleRowsWithLocking = function (solution) {
        var allPerms = SkyScrapers.getAllPossibleRows(solution.length);
        var withLocking = [];

        for (var i = 0; i < solution.length; i++) { // loop through each row
            // populate a valid list for that row considering the locked elements
            var validForThatRow = [];
            for (var k = 0; k < allPerms.length; k++) {
                var isAMatch = true;
                var rowToCheck = allPerms[k];
                for (var j = 0; j < rowToCheck.length; j++) {
                    var lockedVal = solution[i][j];

                    if (lockedVal !== 0 && rowToCheck[j] !== lockedVal) {
                        isAMatch = false;
                        break;
                    }
                }
                if (isAMatch) {
                    validForThatRow.push(rowToCheck);
                }
            }
            withLocking.push(validForThatRow);
        }

        return withLocking;
    };

    SkyScrapers.filterRowsFromSolution = function(solution, possibleRows, rowNum) {
        // get all the heghts in each column not including teh row we're looking for
        // and then return a list of the possible rows taht can still fit in there.

        var result = [];
        var valuesInColumn = [];
        var buildingHeights = [];

        for (var i = 0; i < solution.length; i++) {
            buildingHeights.push(i+1);
        }

        for (var j = 0; j < solution.length; j++) {
            buildingHeights = [];
            for (var i = 0; i < solution.length; i++) {
                if (i != rowNum) {
                    buildingHeights.push(solution[i][j]);
                }
            }
            valuesInColumn.push(buildingHeights);
        }

        for (var i = 0; i < possibleRows.length; i++) {
            var isAMatch = true;
            var row = possibleRows[i];
            for (var j = 0; j < row.length; j++) {
                var colValues = valuesInColumn[j];
                if (colValues.indexOf(row[j]) > -1) {
                    isAMatch = false;
                    break;
                }
            }

            if (isAMatch) {
                result.push(row);
            }
        }

        return result;
    }

    SkyScrapers.getAllPossibleRows = function (size) {
        var allPerms = [];
        var nums = Array(size).fill(0);

        for (var i = 0; i < nums.length; i++) {
            nums[i] = i + 1;
        }
        allPerms = SkyScrapers.permute(nums);

        return allPerms;
    };
    SkyScrapers.permute = function (nums) {
        var results = [];
        if (nums == null || nums.length === 0) {
            return results;
        }
        var result = [];
        SkyScrapers.dfs(nums, results, result);
        return results;
    };
    SkyScrapers.dfs = function (nums, results, result) {
        if (nums.length === result.length) {
            var temp = result.slice();
            results.push(temp);
        }
        for (var i = 0; i < nums.length; i++) {
            if (result.indexOf(nums[i]) == -1) {
                result.push(nums[i]);
                SkyScrapers.dfs(nums, results, result);
                result.splice(result.length - 1, 1);
            }
        }
    };
    SkyScrapers.printArr = function (arr) {
        console.info();
        for (var i = 0; i < arr.length; i++) {
            for (var j = 0; j < arr.length; j++) {
                java.lang.System.out.print(arr[i][j]);
            }
            console.info();
        }
    };
    return SkyScrapers;
}());
SkyScrapers["__class"] = "SkyScrapers";



var clues = [
[ 2, 2, 1, 3,
  2, 2, 3, 1,
  1, 2, 2, 3,
  3, 2, 1, 3 ],
[ 0, 0, 1, 2,
  0, 2, 0, 0,
  0, 3, 0, 0,
  0, 1, 0, 0 ],
[ 1, 2, 2, 3,
  3, 2, 1, 3,
  2, 2, 1, 3,
  2, 2, 3, 1],
  [ 2, 2, 1, 3,
  2, 2, 3, 1,
  1, 2, 2, 3,
  3, 1, 1, 3 ],
];

var whacks  = [
[ [ 1, 3, 4, 2 ],
  [ 4, 2, 1, 3 ],
  [ 4, 4, 2, 1 ],
  [ 0, 0, 0, 0 ] ],
[ [ 2, 1, 4, 3 ],
  [ 3, 4, 1, 2 ],
  [ 4, 2, 3, 1 ],
  [ 1, 3, 2, 4 ] ]
];


function testClueSafe () {
    assertEquals(false, SkyScrapers.isClueSafe(whacks[0], clues[0], true));
    assertEquals(false, SkyScrapers.isClueSafe(whacks[1], clues[0], true));
    // SkyScrapers.printArr(whacks[0]);
    // System.out.println(Arrays.toString(clues[1]));
    assertEquals(true, SkyScrapers.isClueSafe(whacks[0], clues[1], true));
    assertEquals(true, SkyScrapers.isClueSafe(whacks[1], clues[1], true));

    assertEquals(true, SkyScrapers.isClueSafe(whacks[0], clues[3], false));
    // assertEquals(false, SkyScrapers.isClueSafe(whacks[1], clues[0], false));
}

function testIsValid () {
    assertEquals(false, SkyScrapers.isValid(whacks[0]));
    assertEquals(true, SkyScrapers.isValid(whacks[1]));
}

function assertEquals(a, b) {
    if (a != b) {
        console.log('failed');
        console.log(a);
        console.log(b);
    } else {
        // console.log('success');
    }
}
testClueSafe();
testIsValid();



function describe(name, fn) {
  console.log(name);
  fn();
}
function it(newName, fn2) {
    console.log(newName);
    fn2();
}

// describe("SkyScrapers", function () {
//     it("can solve 4x4 puzzle 1", function () {
//         var clues = [2, 2, 1, 3,
//                      2, 2, 3, 1,
//                      1, 2, 2, 3,
//                      3, 2, 1, 3];
//         var expected = [[1, 3, 4, 2],
//                         [4, 2, 1, 3],
//                         [3, 4, 2, 1],
//                         [2, 1, 3, 4]];
//         var actual = solvePuzzle(clues);
//         assert(expected, actual);
//     });
//     it("can solve 4x4 puzzle 2", function () {
//         var clues = [0, 0, 1, 2,
//                      0, 2, 0, 0,
//                      0, 3, 0, 0,
//                      0, 1, 0, 0];
//         var expected = [[2, 1, 4, 3],
//                         [3, 4, 1, 2],
//                         [4, 2, 3, 1],
//                         [1, 3, 2, 4]];
//         var actual = solvePuzzle(clues);
//         assert(expected, actual);
//     });
// });

function assert(expected, actual){
    console.log("expected:\n" + expected);
    console.log("actual:\n" + actual);
    assertEquals(actual.length, expected.length);
    for (var i = 0; i < expected.length; i++) {
        assertEquals(actual[i].toString(), expected[i].toString());
    }
}

describe("SkyScrapers", function () {
    it("can solve 6x6 puzzle 1", function () {
        var clues = [ 3, 2, 2, 3, 2, 1,
                      1, 2, 3, 3, 2, 2,
                      5, 1, 2, 2, 4, 3,
                      3, 2, 1, 2, 2, 4];

        var expected = [[ 2, 1, 4, 3, 5, 6],
                        [ 1, 6, 3, 2, 4, 5],
                        [ 4, 3, 6, 5, 1, 2],
                        [ 6, 5, 2, 1, 3, 4],
                        [ 5, 4, 1, 6, 2, 3],
                        [ 3, 2, 5, 4, 6, 1]];

        var timeStart = new Date().getTime();
        var actual = solvePuzzle(clues);
        var timeEnd = new Date().getTime();
        var timeTaken = timeEnd - timeStart;
        console.log("timeTaken:" + timeTaken);
        assert(expected, actual);
    });

    it("can solve 6x6 puzzle 2", function () {
        var clues = [ 0, 0, 0, 2, 2, 0,
                      0, 0, 0, 6, 3, 0,
                      0, 4, 0, 0, 0, 0,
                      4, 4, 0, 3, 0, 0];

        var expected = [[ 5, 6, 1, 4, 3, 2 ],
                        [ 4, 1, 3, 2, 6, 5 ],
                        [ 2, 3, 6, 1, 5, 4 ],
                        [ 6, 5, 4, 3, 2, 1 ],
                        [ 1, 2, 5, 6, 4, 3 ],
                        [ 3, 4, 2, 5, 1, 6 ]];

        var timeStart = new Date().getTime();
        var actual = solvePuzzle(clues);
        var timeEnd = new Date().getTime();
        var timeTaken = timeEnd - timeStart;
        console.log("timeTaken:" + timeTaken);
        assert(expected, actual);
    });
    it("can solve 6x6 puzzle 3", function () {
        var clues = [ 0, 3, 0, 5, 3, 4,
                      0, 0, 0, 0, 0, 1,
                      0, 3, 0, 3, 2, 3,
                      3, 2, 0, 3, 1, 0];

        var expected = [[ 5, 2, 6, 1, 4, 3 ],
                        [ 6, 4, 3, 2, 5, 1 ],
                        [ 3, 1, 5, 4, 6, 2 ],
                        [ 2, 6, 1, 5, 3, 4 ],
                        [ 4, 3, 2, 6, 1, 5 ],
                        [ 1, 5, 4, 3, 2, 6 ]];
        var timeStart = new Date().getTime();
        var actual = solvePuzzle(clues);
        var timeEnd = new Date().getTime();
        var timeTaken = timeEnd - timeStart;
        console.log("timeTaken:" + timeTaken);
        assert(expected, actual);
    });
    it("can solve 6x6 puzzle 4", function () {

        var clues = [ 0, 3, 0, 3, 2, 3,
                      3, 2, 0, 3, 1, 0,
                      0, 3, 0, 5, 3, 4,
                      0, 0, 0, 0, 0, 1];

        var expected = [[ 6,2,3,4,5,1 ],
                        [ 5,1,6,2,3,4 ],
                        [ 4,3,5,1,6,2 ],
                        [ 2,6,4,5,1,3 ],
                        [ 1,5,2,3,4,6 ],
                        [ 3,4,1,6,2,5 ]];
        var timeStart = new Date().getTime();
        var actual = solvePuzzle(clues);
        var timeEnd = new Date().getTime();
        var timeTaken = timeEnd - timeStart;
        console.log("timeTaken:" + timeTaken);
        assert(expected, actual);
    });

});

    // function assert(expected, actual){
    //     assertEquals(actual.length, 6);
    //     for (var i = 0; i < 6; i++) {
    //         assertEquals(actual[i].toString(), expected[i].toString());
    //     }
    // }

// function assertEquals(a, b) {
    // if (a !== b) {
        // console.log("test failed");
      // console.log(a);
      // console.log(b);
    // }

// }
