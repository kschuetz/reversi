const common = @import("common.zig");
const Board = @import("board.zig").Board;
const move_generator = @import("move_generator.zig");
const Color = common.Color;
const Status = common.Status;
const BoardMask = common.BoardMask;

pub const BeginTurnEvaluation = struct {
    status: Status = .InProgress,
    player: Color = .Dark,
    legal_moves: BoardMask = BoardMask.empty,
    power_balance: f64 = 0,

    pub fn compute(board: *const Board, current_player: Color) BeginTurnEvaluation {
        var result: BeginTurnEvaluation = .{};
        const board_view1 = board.playerView(current_player);
        const legal_moves1 = move_generator.generateMoves(&board_view1);
        if (legal_moves1.isEmpty()) {
            const player = current_player.opponent();
            const board_view2 = board.playerView(player);
            const legal_moves2 = move_generator.generateMoves(&board_view2);
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
