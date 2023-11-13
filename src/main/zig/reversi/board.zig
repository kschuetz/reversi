pub const BoardMask = u64;

pub const SquareIndex = u6;

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

    pub fn empty() Board {
        return .{
            .light = 0,
            .dark = 0,
        };
    }

    pub fn getSquareState(self: *@This(), square_index: SquareIndex) SquareState {
        const mask: u64 = @as(u64, 1) << square_index;
        if (self.light & mask != 0) {
            return .Light;
        } else if (self.dark & mask != 0) {
            return .Dark;
        } else {
            return .Empty;
        }
    }

    pub fn setSquareState(self: *@This(), square_index: SquareIndex, state: SquareState) void {
        const mask: u64 = @as(u64, 1) << square_index;
        const complement = ~mask;
        switch (state) {
            .Empty => {
                self.light = self.light & complement;
                self.dark = self.dark & complement;
            },
            .Light => {
                self.light = self.light | mask;
                self.dark = self.dark & complement;
            },
            .Dark => {
                self.light = self.light & complement;
                self.dark = self.dark | mask;
            },
        }
    }

    pub fn occupiedSquares(self: *@This()) BoardMask {
        return (self.light | self.dark);
    }

    pub fn unoccupiedSquares(self: *@This()) BoardMask {
        return ~self.occupiedSquares();
    }
};
