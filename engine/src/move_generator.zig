const common = @import("common.zig");
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const Direction = common.Direction;
const Board = @import("board.zig").Board;
const tables = @import("tables.zig");
const positions = @import("positions.zig");

pub fn generateMoves(board: *const Board) BoardMask {
    var result: BoardMask = BoardMask.empty;
    const unoccupied = board.unoccupiedSquares();
    for (0..64) |i| {
        const si = SquareIndex.of(@truncate(i));
        const selected_square = si.select();
        if (unoccupied.containsAll(selected_square)) {
            const neighbors = tables.neighbor_masks[si.value];
            for (0..7) |d| {
                const dir: Direction = @enumFromInt(d);
                if (dir.isInMask(neighbors)) {
                    const shift_dir = dir.opposite();
                    if (walk(selected_square, board, shift_dir)) {
                        result = result.combine(selected_square);
                        break;
                    }
                }
            }
        }
    }

    return result;
}

fn walk(selected_square: BoardMask, board: *const Board, shift_dir: Direction) bool {
    var op = board.opponent.shift(shift_dir);
    if (op.containsAll(selected_square)) {
        op = op.shift(shift_dir);
        var p = board.player.shift(shift_dir).shift(shift_dir);
        var result = p.containsAll(selected_square);
        while (!result) {
            if (!op.containsAll(selected_square)) {
                return false;
            } else {
                p = p.shift(shift_dir);
                op = op.shift(shift_dir);
                result = p.containsAll(selected_square);
            }
        }
        return result;
    } else return false;
}

const std = @import("std");
const testing = std.testing;

fn checkGeneratedMoves(board: *const Board, expected: []const u32) !void {
    try testing.expect(generateMoves(board).equals(BoardMask.squares(expected)));
}

test "starting position, dark to move" {
    try checkGeneratedMoves(&positions.standardStart, &[_]u32{ 19, 26, 37, 44 });
}

test "starting position, light to move" {
    try checkGeneratedMoves(&positions.standardStart.swapped(), &[_]u32{ 20, 29, 34, 43 });
}

test "empty board" {
    try checkGeneratedMoves(&Board.empty,  &[_]u32{});
}
