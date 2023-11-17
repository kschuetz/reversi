const common = @import("common.zig");
const GameState = @import("game_state.zig").GameState;
const SquareIndex = common.SquareIndex;

pub fn executeMove(state: *GameState, square_index: SquareIndex) void {
    _ = state;
    _ = square_index;
}
