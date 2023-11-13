pub const Color = enum(u1) {
    Light,
    Dark,

    pub fn fromInt(input: u32) Color {
        return if (input & 1 == 0) .Light else .Dark;
    }
};
