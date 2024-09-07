const common = @import("common.zig");
const random = @import("random.zig");
const Board = @import("board.zig").Board;
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const BeginTurnEvaluation = @import("begin_turn_evaluation.zig").BeginTurnEvaluation;
const gameStateModule = @import("game_state.zig");
const positions = @import("positions.zig");
const move_executor = @import("move_executor.zig");

// For interaction with frontend
const Registers = struct {
    seed: random.Seed = 0,
    board: Board = Board.empty,
    begin_turn_evaluation: BeginTurnEvaluation = .{},

    pub fn reset(self: *@This()) void {
        self.board = Board.empty;
        self.begin_turn_evaluation = .{};
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

export fn getCurrentPlayer() u32 {
    return @intFromEnum(registers.begin_turn_evaluation.side);
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

export fn computeBeginTurnEvaluation() u32 {
    const result = BeginTurnEvaluation.compute(&registers.board);
    registers.begin_turn_evaluation = result;
    return @intFromEnum(registers.begin_turn_evaluation.status);
}

export fn getWinner() u32 {
    return @intFromEnum(registers.begin_turn_evaluation.side);
}

export fn isLegalMove(square_index: u32) bool {
    return registers.begin_turn_evaluation.legal_moves.isSet(SquareIndex.of(@truncate(square_index)));
}

export fn getPowerBalance() f64 {
    return registers.begin_turn_evaluation.power_balance;
}

export fn executeMove(square_index: u32) void {
    move_executor.executeMove(&registers.board, SquareIndex.of(@truncate(square_index)));
}

export fn generateRandomInt(bound: u32) u32 {
    if (bound <= 0) return 0;
    const out = random.generateInt(registers.seed, bound);
    registers.seed = out.new_seed;
    return out.value;
}

export fn initializeBoard(position_id: u32) void {
    registers.board = positions.getPosition(positions.PositionId.fromInt(position_id));
}
