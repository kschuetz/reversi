const std = @import("std");

// Although this function looks imperative, note that its job is to
// declaratively construct a build graph that will be executed by an external
// runner.
pub fn build(b: *std.Build) void {
    const exe = b.addExecutable(.{
        .name = "reversi",
        .root_source_file = b.path("src/main.zig"),
        .target = b.resolveTargetQuery(std.Target.Query.parse(
            .{ .arch_os_abi = "wasm32-freestanding" },
        ) catch unreachable),
        .optimize = .ReleaseSafe, //optimize,
    });
    exe.entry = .disabled;
    exe.rdynamic = true;
    b.installArtifact(exe);

    const main_tests = b.addTest(.{
        .root_source_file = b.path("src/main.zig"),
        .target = b.standardTargetOptions(.{}),
        .optimize = .ReleaseSafe,
    });

    const run_main_tests = b.addRunArtifact(main_tests);

    // This creates a build step. It will be visible in the `zig build --help` menu,
    // and can be selected like this: `zig build test`
    // This will evaluate the `test` step rather than the default, which is "install".
    const test_step = b.step("test", "Run library tests");
    test_step.dependOn(&run_main_tests.step);
}
