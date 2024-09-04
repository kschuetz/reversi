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
        self.setSelectedSquaresTo(square_index.select(), state);
    }

    pub fn setSelectedSquaresTo(self: *@This(), squares: BoardMask, state: SquareState) void {
        const complement = squares.complement();
        switch (state) {
            .Empty => {
                self.dark = self.dark.intersect(complement);
                self.light = self.light.intersect(complement);
            },
            .Dark => {
                self.dark = self.dark.combine(squares);
                self.light = self.light.intersect(complement);
            },
            .Light => {
                self.dark = self.dark.intersect(complement);
                self.light = self.light.combine(squares);
            },
        }
    }

    pub inline fn pieces(self: *const @This(), player: common.Color) BoardMask {
        return switch (player) {
            .Dark => self.dark,
            .Light => self.light,
        };
    }

    pub inline fn playerView(self: *const @This(), player: common.Color) BoardPlayerView {
        return switch (player) {
            .Dark => .{ .player = self.dark, .opponent = self.light },
            .Light => .{ .player = self.light, .opponent = self.dark },
        };
    }

    pub inline fn occupiedSquares(self: *const @This()) BoardMask {
        return (self.dark.combine(self.light));
    }

    pub fn unoccupiedSquares(self: *const @This()) BoardMask {
        return self.occupiedSquares().complement();
    }
};

pub const BoardPlayerView = struct {
    player: BoardMask,
    opponent: BoardMask,

    pub fn swapInPlace(self: *@This()) void {
        const tmp = self.player;
        self.player = self.opponent;
        self.opponent = tmp;
    }

    pub inline fn occupiedSquares(self: *const @This()) BoardMask {
        return (self.player.combine(self.opponent));
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
