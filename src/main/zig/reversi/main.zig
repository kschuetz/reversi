const common = @import("common.zig");
const random = @import("random.zig");
const board = @import("board.zig");
const Board = board.Board;

// For interaction with frontend
const Registers = struct {
    seed: random.Seed = 0,
    board: Board = Board.empty(),
    current_player: common.Color = .Light,

    pub fn reset(self: *@This()) void {
        self.board = Board.empty();
        self.current_player = .Light;
    }
};

var registers: Registers = .{};

export fn reset() void {
    registers.reset();
}

export fn getSquareState(square_index: u32) u32 {
    return @intFromEnum(registers.board.getSquareState(@truncate(square_index)));
}

export fn setSquareState(square_index: u32, state: u32) void {
    registers.board.setSquareState(@truncate(square_index), board.SquareState.fromInt(state));
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
    registers.current_player = common.Color.fromInt(color);
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

export fn computeGameState() void {}

export fn generateRandomInt(bound: u32) u32 {
    if (bound <= 0) return 0;
    const out = random.generateInt(registers.seed, bound);
    registers.seed = out.new_seed;
    return out.value;
}
