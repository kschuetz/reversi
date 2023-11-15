const common = @import("common.zig");

const BoardMask = common.BoardMask;
const SquareIndex = common.SquareIndex;
const SquareState = common.SquareState;

pub const Board = struct {
    light: BoardMask,
    dark: BoardMask,

    pub const empty: Board = .{
        .light = BoardMask.empty,
        .dark = BoardMask.empty,
    };

    pub fn getSquareState(self: *const @This(), square_index: SquareIndex) SquareState {
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
