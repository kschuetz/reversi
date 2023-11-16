const common = @import("common.zig");
const random = @import("random.zig");
const Board = @import("board.zig").Board;
const SquareIndex = common.SquareIndex;
const BoardMask = common.BoardMask;
const movegen = @import("movegen.zig");

// For interaction with frontend
const Registers = struct {
    seed: random.Seed = 0,
    board: Board = Board.empty,
    current_player: common.Color = .Light,
    game_status: common.GameStatus = .InProgress,
    winner: common.Color = .Dark,
    legal_moves: BoardMask = BoardMask.empty,
    power_balance: f64 = 0,

    pub fn reset(self: *@This()) void {
        self.board = Board.empty;
        self.current_player = .Dark;
        self.legal_moves = BoardMask.empty;
        self.power_balance = 0;
        self.game_status = .InProgress;
        self.winner = .Dark;
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
    registers.current_player = common.Color.fromInt(color);
}

export fn getCurrentPlayer() u32 {
    return @intFromEnum(registers.current_player);
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
    const legal_moves1 = movegen.generateMoves(registers.current_player, &registers.board);
    if (legal_moves1.isEmpty()) {
        const player = registers.current_player.opponent();
        const legal_moves2 = movegen.generateMoves(player, &registers.board);
        if (legal_moves2.isEmpty()) {
            // game over
            const dark_score = registers.board.dark.count();
            const light_score = registers.board.light.count();
            if (dark_score == light_score) {
                registers.game_status = .Draw;
            } else {
                registers.game_status = .Win;
                registers.winner = if (dark_score > light_score) .Dark else .Light;
            }
        } else {
            registers.game_status = .InProgress;
            registers.legal_moves = legal_moves2;
            registers.current_player = player;
        }
    } else {
        registers.game_status = .InProgress;
        registers.legal_moves = legal_moves1;
    }
    return @intFromEnum(registers.game_status);
}

export fn getWinner() u32 {
    return @intFromEnum(registers.winner);
}

export fn isLegalMove(square_index: u32) bool {
    return registers.legal_moves.isSet(SquareIndex.of(@truncate(square_index)));
}

export fn getPowerBalance() f64 {
    return registers.power_balance;
}

export fn generateRandomInt(bound: u32) u32 {
    if (bound <= 0) return 0;
    const out = random.generateInt(registers.seed, bound);
    registers.seed = out.new_seed;
    return out.value;
}
