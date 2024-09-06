const common = @import("common.zig");

const BoardMask = common.BoardMask;
const SquareIndex = common.SquareIndex;
const SquareState = common.SquareState;

pub const Board = struct {
    player: BoardMask,
    opponent: BoardMask,

    pub const empty: Board = .{
        .player = BoardMask.empty,
        .opponent = BoardMask.empty,
    };

    pub const standardStartDark: Board = .{
        .player = BoardMask.squares(&[_]u32{ 28, 35 }),
        .opponent = BoardMask.squares(&[_]u32{ 27, 36 }),
    };

    pub const standardStartLight: Board = standardStartDark.swapped();

    pub fn swapped(self: *const @This()) Board {
        return .{ .player = self.opponent, .opponent = self.player };
    }

    pub fn getSquareState(self: *const @This(), square_index: SquareIndex) SquareState {
        const mask = square_index.select();
        if (self.player.containsAll(mask)) {
            return .Player;
        } else if (self.opponent.containsAll(mask)) {
            return .Opponent;
        } else {
            return .Empty;
        }
    }

    pub fn setSquareState(self: *@This(), square_index: SquareIndex, state: SquareState) void {
        const mask = square_index.select();
        const complement = mask.complement();
        switch (state) {
            .Empty => {
                self.player = self.player.intersect(complement);
                self.opponent = self.opponent.intersect(complement);
            },
            .Player => {
                self.player = self.player.combine(mask);
                self.opponent = self.opponent.intersect(complement);
            },
            .Opponent => {
                self.player = self.player.intersect(complement);
                self.opponent = self.opponent.combine(mask);
            },
        }
    }

    pub inline fn piecesForSide(self: *const @This(), side: common.Side) BoardMask {
        return switch (side) {
            .Player => self.player,
            .Opponent => self.opponent,
        };
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

    pub fn player(self: *@This(), square_index: u32) *@This() {
        self.board.setSquareState(SquareIndex.of(square_index), .Player);
        return self;
    }

    pub fn opponent(self: *@This(), square_index: u32) *@This() {
        self.board.setSquareState(SquareIndex.of(square_index), .Opponent);
        return self;
    }

    pub fn build(self: *@This()) Board {
        return self.board;
    }
};
