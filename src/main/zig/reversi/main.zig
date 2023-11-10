const helper = @import("core/helper.zig");

var seed: u64 = 0;

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
    return @truncate(seed);
}

export fn setSeedLo(value: u32) void {
    seed = seed & (0xFFFFFFFF00000000) | value;
}

export fn getSeedHi() u32 {
    return @as(u32, @intCast(seed >> 32));
}

export fn setSeedHi(value: u32) void {
    const foo = @as(u64, @intCast(value)) << 32;
    seed = seed & (0x00000000FFFFFFFF) | foo;
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
