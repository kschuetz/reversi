pub const Color = enum(u1) {
    Light,
    Dark,

    pub fn fromInt(input: u32) Color {
        return if (input & 1 == 0) .Light else .Dark;
    }

    pub inline fn opponent(self: @This()) Color {
        return switch (self) {
            .Light => .Dark,
            .Dark => .Light,
        };
    }
};

pub const Directions = u4;

pub const Direction = enum(u4) {
    N = 0,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW,

    pub inline fn opposite(self: @This()) Direction {
        return switch (self) {
            .N => .S,
            .E => .W,
            .S => .N,
            .W => .E,
            .NE => .SW,
            .SE => .NW,
            .SW => .NE,
            .NW => .SE,
        };
    }

    pub inline fn mask(self: @This()) Directions {
        return switch (self) {
            .N => 1,
            .E => 2,
            .S => 4,
            .W => 8,
            .NE => 3,
            .SE => 6,
            .SW => 12,
            .NW => 9,
        };
    }

    pub fn isInMask(self: @This(), directions: Directions) bool {
        const m = self.mask();
        return directions & m == m;
    }

    pub fn buildMask(comptime n: bool, comptime e: bool, comptime s: bool, comptime w: bool) Directions {
        return (if (n) mask(.N) else 0) |
            (if (e) mask(.E) else 0) |
            (if (s) mask(.S) else 0) |
            (if (w) mask(.W) else 0);
    }
};

pub const BoardMask = struct {
    value: u64,

    pub const empty: BoardMask = .{ .value = 0 };

    pub inline fn of(mask: u64) BoardMask {
        return .{ .value = mask };
    }

    pub inline fn isSet(self: @This(), square_index: SquareIndex) bool {
        return (self.value & square_index.select()) != 0;
    }

    pub inline fn complement(self: @This()) BoardMask {
        return .{ .value = ~self.value };
    }

    pub inline fn combine(self: @This(), other: BoardMask) BoardMask {
        return .{ .value = self.value | other.value };
    }

    pub inline fn intersect(self: @This(), other: BoardMask) BoardMask {
        return .{ .value = self.value & other.value };
    }

    pub inline fn containsAll(self: @This(), other: BoardMask) bool {
        return self.value & other.value != 0;
    }
};

pub const SquareIndex = struct {
    value: u6,

    pub const max = .{ .value = 63 };

    pub inline fn of(si: u6) SquareIndex {
        return .{ .value = si };
    }

    pub inline fn select(self: @This()) BoardMask {
        return BoardMask.of(@as(u64, 1) << self.value);
    }
};

pub const SquareState = enum(u2) {
    Empty = 0,
    Light = 1,
    Dark = 2,

    pub fn fromInt(input: u32) SquareState {
        return switch (input & 3) {
            0 => .Empty,
            1 => .Light,
            2 => .Dark,
            else => .Light,
        };
    }
};

const std = @import("std");
const testing = std.testing;

test "basic add functionality" {
    try testing.expect(3 + 7 == 10);
}
