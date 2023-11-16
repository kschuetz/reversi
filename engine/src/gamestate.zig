const common = @import("common.zig");
const Board = @import("board.zig").Board;
const movegen = @import("movegen.zig");
const Color = common.Color;
const GameStatus = common.GameStatus;
const BoardMask = common.BoardMask;

pub const GameState = struct {
    status: GameStatus = .InProgress,
    player: Color = .Dark,
    legal_moves: BoardMask = BoardMask.empty,
    power_balance: f64 = 0,

    pub fn compute(board: *const Board, current_player: Color) GameState {
        var result: GameState = .{};
        const legal_moves1 = movegen.generateMoves(current_player, board);
        if (legal_moves1.isEmpty()) {
            const player = current_player.opponent();
            const legal_moves2 = movegen.generateMoves(player, board);
            if (legal_moves2.isEmpty()) {
                // game over
                const dark_score = board.dark.count();
                const light_score = board.light.count();
                if (dark_score == light_score) {
                    result.status = .Draw;
                } else {
                    result.status = .Win;
                    result.player = if (dark_score > light_score) .Dark else .Light;
                }
            } else {
                result.status = .InProgress;
                result.legal_moves = legal_moves2;
                result.player = player;
            }
        } else {
            result.status = .InProgress;
            result.legal_moves = legal_moves1;
            result.player = current_player;
        }
        return result;
    }
};
