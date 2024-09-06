const common = @import("common.zig");
const Board = @import("board.zig").Board;
const BoardMask = common.BoardMask;


pub const PositionId = enum(u2) {
    Empty = 0,
    StandardStart,

    pub fn fromInt(input: u32) PositionId {
        return switch (input & 3) {
            1 => .StandardStart,
            else => .Empty,
        };
    }
};

pub const standardStart: Board = .{
    .player = BoardMask.squares(&[_]u32{ 28, 35 }),
    .opponent = BoardMask.squares(&[_]u32{ 27, 36 }),
};

pub fn getPosition(id: PositionId) Board {
    return switch (id) {
        .Empty => Board.empty,
        .StandardStart => standardStart,
    };
}
