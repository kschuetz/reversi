const common = @import("common.zig");
const Color = common.Color;
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const Direction = common.Direction;
const Board = @import("board.zig").Board;
const tables = @import("tables.zig");

pub fn generateMoves(player: Color, board: *const Board) BoardMask {
    var result: BoardMask = BoardMask.empty;
    const my_pieces = board.pieces(player);
    const opponent_pieces = board.pieces(player.opponent());
    const unoccupied = board.unoccupiedSquares();
    for (0..64) |i| {
        const si = SquareIndex.of(@truncate(i));
        if (unoccupied.isSet(si)) {
            const neighbors = tables.neighbor_masks[si.value];
            for (0..7) |d| {
                const dir: Direction = @enumFromInt(d);
                if (dir.isInMask(neighbors)) {
                    const shift_dir = dir.opposite();
                    if (walk(si, my_pieces, opponent_pieces, shift_dir)) {
                        result = result.combine(si.select());
                        break;
                    }
                }
            }
        }
    }

    return result;
}

fn walk(square_index: SquareIndex, player: BoardMask, opponent: BoardMask, shift_dir: Direction) bool {
    var op = opponent.shift(shift_dir);
    if (op.isSet(square_index)) {
        op = op.shift(shift_dir);
        var p = player.shift(shift_dir).shift(shift_dir);
        var result = p.isSet(square_index);
        while (!result) {
            if (!op.isSet(square_index)) {
                return false;
            } else {
                p = p.shift(shift_dir);
                op = op.shift(shift_dir);
                result = p.isSet(square_index);
            }
        }
        return result;
    } else return false;
}

const std = @import("std");
const testing = std.testing;

fn checkGeneratedMoves(board: *const Board, player: Color, expected: []const u32) !void {
    try testing.expect(generateMoves(player, board).equals(BoardMask.squares(expected)));
}

test "starting position, dark to move" {
    try checkGeneratedMoves(&Board.standardStart, .Dark, &[_]u32{ 19, 26, 37, 44 });
}

test "starting position, light to move" {
    try checkGeneratedMoves(&Board.standardStart, .Light, &[_]u32{ 20, 29, 34, 43 });
}

test "empty board, dark to move" {
    try checkGeneratedMoves(&Board.empty, .Dark, &[_]u32{});
}

test "empty board, light to move" {
    try checkGeneratedMoves(&Board.empty, .Light, &[_]u32{});
}
