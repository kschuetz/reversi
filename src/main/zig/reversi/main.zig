const random = @import("random.zig");

var seed: random.Seed = 0;

export fn reset() void {}

export fn getSquareState(square_index: i32) i32 {
    _ = square_index;
    return 99;
}

export fn setSquareState(square_index: i32, state: i32) void {
    _ = square_index;
    _ = state;
}

export fn getSeedLo() u32 {
    return random.getSeedLo(seed);
}

export fn getSeedHi() u32 {
    return random.getSeedHi(seed);
}

export fn setSeed(lo: u32, hi: u32) void {
    seed = random.makeSeed(lo, hi);
}

export fn setCurrentPlayer(color: i32) void {
    _ = color;
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
    const out = random.generateInt(seed, bound);
    seed = out.new_seed;
    return out.value;
}
