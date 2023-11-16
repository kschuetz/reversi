pub const Color = enum(u1) {
    Dark = 0,
    Light,

    pub fn fromInt(input: u32) Color {
        return if (input & 1 == 0) .Dark else .Light;
    }

    pub inline fn opponent(self: @This()) Color {
        return switch (self) {
            .Light => .Dark,
            .Dark => .Light,
        };
    }
};

pub const GameStatus = enum(u2) {
    InProgress = 0,
    Win,
    Draw,
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

    pub fn squares(list: []const u32) BoardMask {
        var result = BoardMask.empty;
        for (list) |si| {
            result = result.combine(SquareIndex.of(si).select());
        }
        return result;
    }

    pub inline fn isEmpty(self: @This()) bool {
        return self.value == 0;
    }

    pub inline fn isSet(self: @This(), square_index: SquareIndex) bool {
        return (self.value & square_index.select().value) != 0;
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

    pub inline fn shift(self: @This(), dir: Direction) BoardMask {
        return switch (dir) {
            .N => of(self.value >> 8),
            .NE => of(self.value >> 7).intersect(BoardMasks.EDGE_SW.complement()),
            .E => of(self.value << 1).intersect(BoardMasks.EDGE_W.complement()),
            .SE => of(self.value << 9).intersect(BoardMasks.EDGE_NW.complement()),
            .S => of(self.value << 8),
            .SW => of(self.value << 7).intersect(BoardMasks.EDGE_NE.complement()),
            .W => of(self.value >> 1).intersect(BoardMasks.EDGE_E.complement()),
            .NW => of(self.value >> 9).intersect(BoardMasks.EDGE_SE.complement()),
        };
    }

    pub fn count(self: @This()) u8 {
        var current = self.value;
        var result: u8 = 0;
        for (0..64) |_| {
            if (current & 1 == 1) {
                result += 1;
            }
            current = current >> 1;
        }
        return result;
    }

    pub inline fn equals(self: @This(), other: @This()) bool {
        return self.value == other.value;
    }
};

const BoardMasks = struct {
    const N: u64 = 0xFF;
    const E: u64 = 0x80;
    const W: u64 = 0x01;

    pub const EDGE_N: BoardMask = BoardMask.of(0xFF);

    pub const EDGE_E: BoardMask = BoardMask.of(E | (E << 8) | (E << 16) | (E << 24) |
        (E << 32) | (E << 40) | (E << 48) | (E << 56));

    pub const EDGE_S: BoardMask = BoardMask.of(N << 56);

    pub const EDGE_W: BoardMask = BoardMask.of(W | (W << 8) | (W << 16) | (W << 24) |
        (W << 32) | (W << 40) | (W << 48) | (W << 56));

    pub const EDGE_NE = EDGE_N.combine(EDGE_E);

    pub const EDGE_SE = EDGE_S.combine(EDGE_E);

    pub const EDGE_SW = EDGE_S.combine(EDGE_W);

    pub const EDGE_NW = EDGE_N.combine(EDGE_W);
};

pub const SquareIndex = struct {
    value: u6,

    pub const max = .{ .value = 63 };

    pub inline fn of(si: u32) SquareIndex {
        return .{ .value = @truncate(si) };
    }

    pub inline fn select(self: @This()) BoardMask {
        return BoardMask.of(@as(u64, 1) << self.value);
    }
};

pub const SquareState = enum(u2) {
    Empty = 0,
    Dark = 1,
    Light = 2,

    pub fn fromInt(input: u32) SquareState {
        return switch (input & 3) {
            0 => .Empty,
            1 => .Dark,
            2 => .Light,
            else => .Dark,
        };
    }
};
