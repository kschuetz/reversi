const common = @import("common.zig");
const Color = common.Color;
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const Direction = common.Direction;
const boardModule = @import("board.zig");
const Board = boardModule.Board;
const BoardPlayerView = boardModule.BoardPlayerView;
const tables = @import("tables.zig");

pub fn generateMovesForPlayer(player: Color, board: *const Board) BoardMask {
    const board_view = board.playerView(player);
    return generateMoves(&board_view);
}

pub fn generateMoves(board: *const BoardPlayerView) BoardMask {
    var result: BoardMask = BoardMask.empty;
    const unoccupied = board.unoccupiedSquares();
    for (0..63) |i| {
        const si = SquareIndex.of(@truncate(i));
        if (unoccupied.isSet(si)) {
            const neighbors = tables.neighbor_masks[si.value];
            for (0..7) |d| {
                const dir: Direction = @enumFromInt(d);
                if (dir.isInMask(neighbors)) {
                    const shift_dir = dir.opposite();
                    if (walk(si, board, shift_dir)) {
                        result = result.combine(si.select());
                        break;
                    }
                }
            }
        }
    }

    return result;
}

fn walk(square_index: SquareIndex, board: *const BoardPlayerView, shift_dir: Direction) bool {
    var op = board.opponent.shift(shift_dir);
    if (op.isSet(square_index)) {
        op = op.shift(shift_dir);
        var p = board.player.shift(shift_dir).shift(shift_dir);
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
    try testing.expect(generateMovesForPlayer(player, board).equals(BoardMask.squares(expected)));
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
