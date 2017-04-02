function solvePuzzle(clues) {
  return SkyScrapers.solvePuzzle(clues);
}
var SkyScrapers = (function () {
    function SkyScrapers() {
    }
    SkyScrapers.solvePuzzle = function (clues) {
        var sideLength = ((clues.length + 1) / 4 | 0);
        var possibleRows = SkyScrapers.getAllPossibleRows(sideLength);
        var solution = (function (dims) { var allocate = function (dims) { if (dims.length == 0) {
            return undefined;
        }
        else {
            var array = [];
            for (var i = 0; i < dims[0]; i++) {
                array.push(allocate(dims.slice(1)));
            }
            return array;
        } }; return allocate(dims); })([sideLength, sideLength]);
        solution = SkyScrapers.fillInGrid(solution, possibleRows, 0, clues);
        console.log("WHAAAT");
        console.log(solution);
        return solution;
    };
    SkyScrapers.fillInGrid = function (solution, possibleRows, rowNum, clues) {
        var isLastRow = rowNum === (solution.length - 1);
        for (var i = 0; i < possibleRows.length; i++) {
            solution[rowNum] = possibleRows[i].slice();
            SkyScrapers.clearLaterRows(solution, rowNum);
            var isValid = SkyScrapers.isValid(solution);
            if (isValid && isLastRow && SkyScrapers.isClueSafe(solution, clues, true)) {
                return solution;
            }
            else if (!isLastRow && isValid && SkyScrapers.isClueSafe(solution, clues, false)) {
                var result = SkyScrapers.fillInGrid(solution, possibleRows, rowNum + 1, clues);
                if (result[solution.length - 1][solution.length - 1] !== 0) {
                    return result;
                }
            }
        }
        return (function (dims) { var allocate = function (dims) { if (dims.length == 0) {
            return undefined;
        }
        else {
            var array = [];
            for (var i = 0; i < dims[0]; i++) {
                array.push(allocate(dims.slice(1)));
            }
            return array;
        } }; return allocate(dims); })([solution.length, solution.length]);
    };
    SkyScrapers.clearLaterRows = function (arr, rowNum) {
        for (var x = rowNum + 1; x < arr.length; x++) {
            SkyScrapers.zeroRow(arr, x);
        }
    };
    SkyScrapers.zeroRow = function (arr, row) {
        for (var j = 0; j < arr[row].length; j++) {
            arr[row][j] = 0;
        }
    };
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
                if (isStrict || SkyScrapers.isFullyIntialized(rowCol)) {
                    var visibleBuildings = 1;
                    var maxSoFar = rowCol[0];
                    for (var j = 1; j < rowCol.length; j++) {
                        if (rowCol[j] > maxSoFar) {
                            visibleBuildings++;
                            maxSoFar = rowCol[j];
                        }
                    }
                    if (clues[i] !== visibleBuildings) {
                        return false;
                    }
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


function describe(name, fn) {
  console.log(name);
  fn();
}
function it(newName, fn2) {
    fn2();
}
describe("Skyscrapers", function () {
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
        var actual = solvePuzzle(clues);
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
        var actual = solvePuzzle(clues);
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
        var actual = solvePuzzle(clues);
        assert(expected, actual);
    });
});

function assert(expected, actual){
    assertEquals(actual.length, 6);
    for (var i = 0; i < 6; i++) {
        assertEquals(actual[i].toString(), expected[i].toString());
    }
}

function assertEquals(a, b) {
  // console.log(a);
  // console.log(b);
}