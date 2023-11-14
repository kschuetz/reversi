const common = @import("common.zig");
const Direction = common.Direction;
const Directions = common.Directions;

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

pub const Board = struct {
    light: BoardMask,
    dark: BoardMask,

    pub const empty: Board = .{
        .light = BoardMask.empty,
        .dark = BoardMask.empty,
    };

    pub fn getSquareState(self: *@This(), square_index: SquareIndex) SquareState {
        const mask = square_index.select();
        if (self.light.containsAll(mask)) {
            return .Light;
        } else if (self.dark.containsAll(mask)) {
            return .Dark;
        } else {
            return .Empty;
        }
    }

    pub fn setSquareState(self: *@This(), square_index: SquareIndex, state: SquareState) void {
        const mask = square_index.select();
        const complement = mask.complement();
        switch (state) {
            .Empty => {
                self.light = self.light.intersect(complement);
                self.dark = self.dark.intersect(complement);
            },
            .Light => {
                self.light = self.light.combine(mask);
                self.dark = self.dark.intersect(complement);
            },
            .Dark => {
                self.light = self.light.intersect(complement);
                self.dark = self.dark.combine(mask);
            },
        }
    }

    pub inline fn pieces(self: *@This(), player: common.Color) BoardMask {
        return switch (player) {
            .Light => self.light,
            .Dark => self.dark,
        };
    }

    pub fn occupiedSquares(self: *@This()) BoardMask {
        return (self.light | self.dark);
    }

    pub fn unoccupiedSquares(self: *@This()) BoardMask {
        return ~self.occupiedSquares();
    }
};
