const common = @import("common.zig");
const Board = @import("board.zig").Board;
const move_generator = @import("move_generator.zig");
const Side = common.Side;
const Status = common.Status;
const BoardMask = common.BoardMask;

pub const BeginTurnEvaluation = struct {
    status: Status = .InProgress,
    side: Side = .Player,
    legal_moves: BoardMask = BoardMask.empty,
    power_balance: f64 = 0,

    pub fn compute(board: *const Board) BeginTurnEvaluation {
        var result: BeginTurnEvaluation = .{};
        const legal_moves1 = move_generator.generateMoves(board);
        if (legal_moves1.isEmpty()) {
            const opponent_board = board.swapped();
            const legal_moves2 = move_generator.generateMoves(&opponent_board);
            if (legal_moves2.isEmpty()) {
                // game over
                const player_score = board.player.count();
                const opponent_score = board.opponent.count();
                if (player_score == opponent_score) {
                    result.status = .Draw;
                } else {
                    result.status = .Win;
                    result.side = if (player_score > opponent_score) .Player else .Opponent;
                }
            } else {
                result.status = .InProgress;
                result.legal_moves = legal_moves2;
                result.side = .Opponent;
            }
        } else {
            result.status = .InProgress;
            result.legal_moves = legal_moves1;
            result.side = .Player;
        }
        return result;
    }
};
