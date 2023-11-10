
const helper = @import("core/helper.zig");

export fn do_something(a: i32, b: i32) i32 {
    return helper.add(a, b);
}
