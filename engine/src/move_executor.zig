const common = @import("common.zig");
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const Board = @import("board.zig").Board;
const Direction = common.Direction;

pub fn executeMove(board: *Board, square_index: SquareIndex) void {
    const unoccupied = board.unoccupiedSquares();
    const selected_square = square_index.select();
    if (unoccupied.containsAll(selected_square)) {
        board.player = board.player.combine(selected_square);
        for (0..7) |d| {
            const dir: Direction = @enumFromInt(d);
            walk(selected_square, board, dir);
        }
    }
}

fn walk(selected_square: BoardMask, board: *Board, shift_dir: Direction) void {
    var current = selected_square.shift(shift_dir);
    if (board.opponent.containsAll(current)) {
        var squares_to_flip = current;
        current = selected_square.shift(shift_dir);
        while (true) {
            if (board.player.containsAll(current)) {
                flipSquares(board, squares_to_flip);
                return;
            } else if (board.opponent.containsAll(current)) {
                squares_to_flip = squares_to_flip.combine(current);
                current = current.shift(shift_dir);
            } else {
                return;
            }
        }
    }
}

fn flipSquares(board: *Board, squares_to_flip: BoardMask) void {
    board.player = board.player.combine(squares_to_flip);
    board.opponent = board.opponent.intersect(squares_to_flip.complement());
}
