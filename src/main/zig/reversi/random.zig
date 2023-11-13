pub const Seed = u64;

pub fn Result(comptime T: type) type {
    return struct {
        value: T,
        new_seed: Seed,
    };
}

pub inline fn getSeedLo(seed: Seed) u32 {
    return @truncate(seed);
}

pub inline fn getSeedHi(seed: Seed) u32 {
    return @as(u32, @intCast(seed >> 32));
}

pub inline fn makeSeed(lo: u32, hi: u32) Seed {
    return @as(u64, lo) | @as(u64, @intCast(hi)) << 32;
}

pub fn generateInt2(seed: Seed, bound: u32) Result(u32) {
    return .{
        .value = bound - 1,
        .new_seed = nextSeed(seed),
    };
}

pub fn generateInt(seed: Seed, bound: u32) Result(u32) {
    if (bound == 0) return .{
        .value = 0,
        .new_seed = seed,
    };
    var bits: u64 = undefined;
    var val: u64 = undefined;
    var next_seed: Seed = seed;
    var safety: i32 = 100;
    while (safety > 0) : (safety -= 1) {
        next_seed = nextSeed(next_seed);
        bits = bitsFrom(31, next_seed);
        val = bits % @as(u64, bound);
        if ((bits - val + (bound - 1) >= 0)) {
            break;
        }
    }
    return .{
        .value = if (safety > 0) @truncate(val) else bound - 1,
        .new_seed = next_seed,
    };
}

const mul: u64 = 0x5DEECE66D;

fn nextSeed(seed_in: Seed) Seed {
    return (seed_in *% mul +% 0xB) & @as(u64, ((1 << 48) - 1));
}

fn bitsFrom(bits: u6, seed: Seed) u64 {
    return (seed >> (48 - bits));
}
