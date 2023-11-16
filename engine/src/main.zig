const common = @import("common.zig");
const random = @import("random.zig");
const Board = @import("board.zig").Board;
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const GameState = @import("gamestate.zig").GameState;

// For interaction with frontend
const Registers = struct {
    seed: random.Seed = 0,
    board: Board = Board.empty,
    game_state: GameState = .{},

    pub fn reset(self: *@This()) void {
        self.board = Board.empty;
        self.game_state = .{};
    }
};

var registers: Registers = .{};

export fn reset() void {
    registers.reset();
}

export fn getSquareState(square_index: u32) u32 {
    return @intFromEnum(registers.board.getSquareState(SquareIndex.of(@truncate(square_index))));
}

export fn setSquareState(square_index: u32, state: u32) void {
    registers.board.setSquareState(SquareIndex.of(@truncate(square_index)), common.SquareState.fromInt(state));
}

export fn getSeedLo() u32 {
    return random.getSeedLo(registers.seed);
}

export fn getSeedHi() u32 {
    return random.getSeedHi(registers.seed);
}

export fn setSeed(lo: u32, hi: u32) void {
    registers.seed = random.makeSeed(lo, hi);
}

export fn setCurrentPlayer(color: u32) void {
    registers.game_state.player = common.Color.fromInt(color);
}

export fn getCurrentPlayer() u32 {
    return @intFromEnum(registers.game_state.player);
}

export fn computePlayerMove(max_cycles: i32) i32 {
    return max_cycles;
}

export fn rush() void {}

export fn isPlayerMoveReady() bool {
    return false;
}

export fn getPlayerMove() i32 {
    return 777;
}

export fn computeGameState() u32 {
    const state = GameState.compute(&registers.board, registers.game_state.player);
    registers.game_state = state;
    return @intFromEnum(registers.game_state.status);
}

export fn getWinner() u32 {
    return @intFromEnum(registers.game_state.player);
}

export fn isLegalMove(square_index: u32) bool {
    return registers.game_state.legal_moves.isSet(SquareIndex.of(@truncate(square_index)));
}

export fn getPowerBalance() f64 {
    return registers.game_state.power_balance;
}

export fn generateRandomInt(bound: u32) u32 {
    if (bound <= 0) return 0;
    const out = random.generateInt(registers.seed, bound);
    registers.seed = out.new_seed;
    return out.value;
}
