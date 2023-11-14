usingnamespace @import("common.zig");

pub const neighbor_masks: [64]Directions = blk: {
    var arr: [64]Directions = undefined;
    for (0..7) |row| {
        for (0..7) |col| {
            const mask = Direction.buildMask(row > 0, col < 7, row < 7, col > 0);
            arr[row * 8 + col] = mask;
        }
    }
    break :blk arr;
};
