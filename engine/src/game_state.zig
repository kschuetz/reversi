const Board = @import("board.zig").Board;
const common = @import("common.zig");
const BoardMask = common.BoardMask;
const Color = common.Color;
const SquareIndex = common.SquareIndex;
const RelativeSquareState = common.RelativeSquareState;

const SafetyMasks = struct {
    ns: BoardMask,
    ew: BoardMask,
    nwse: BoardMask,
    swne: BoardMask,
};

const PlayerState = struct {
    pieces: BoardMask,
    legal_moves: BoardMask,
    safe: SafetyMasks,
};

const GameState = struct {
    player: PlayerState,
    opponent: PlayerState,
    turn_count: u8,

    pub fn next(self: *const @This()) GameState {
        return .{
            .player = self.opponent,
            .opponent = self.player,
            // .turn = self.turn.opponent(),
            .turn_count = self.turn_count + 1,
        };
    }
};
