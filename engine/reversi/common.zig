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
