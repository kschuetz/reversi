const common = @import("common.zig");

const BoardMask = common.BoardMask;
const SquareIndex = common.SquareIndex;
const SquareState = common.SquareState;

pub const Board = struct {
    dark: BoardMask,
    light: BoardMask,

    pub const empty: Board = .{
        .dark = BoardMask.empty,
        .light = BoardMask.empty,
    };

    pub const standardStart: Board = .{
        .dark = BoardMask.squares(&[_]u32{ 28, 35 }),
        .light = BoardMask.squares(&[_]u32{ 27, 36 }),
    };

    pub fn getSquareState(self: *const @This(), square_index: SquareIndex) SquareState {
        const mask = square_index.select();
        if (self.dark.containsAll(mask)) {
            return .Dark;
        } else if (self.light.containsAll(mask)) {
            return .Light;
        } else {
            return .Empty;
        }
    }

    pub fn setSquareState(self: *@This(), square_index: SquareIndex, state: SquareState) void {
        const mask = square_index.select();
        const complement = mask.complement();
        switch (state) {
            .Empty => {
                self.dark = self.dark.intersect(complement);
                self.light = self.light.intersect(complement);
            },
            .Dark => {
                self.dark = self.dark.combine(mask);
                self.light = self.light.intersect(complement);
            },
            .Light => {
                self.dark = self.dark.intersect(complement);
                self.light = self.light.combine(mask);
            },
        }
    }

    pub inline fn pieces(self: *const @This(), player: common.Color) BoardMask {
        return switch (player) {
            .Dark => self.dark,
            .Light => self.light,
        };
    }

    pub inline fn occupiedSquares(self: *const @This()) BoardMask {
        return (self.dark.combine(self.light));
    }

    pub fn unoccupiedSquares(self: *const @This()) BoardMask {
        return self.occupiedSquares().complement();
    }
};

pub const BoardBuilder = struct {
    board: Board,

    pub fn new() BoardBuilder {
        return .{ .board = Board.empty };
    }

    pub fn dark(self: *@This(), square_index: u32) *@This() {
        self.board.setSquareState(SquareIndex.of(square_index), .Dark);
        return self;
    }

    pub fn light(self: *@This(), square_index: u32) *@This() {
        self.board.setSquareState(SquareIndex.of(square_index), .Dark);
        return self;
    }

    pub fn build(self: *@This()) Board {
        return self.board;
    }
};
