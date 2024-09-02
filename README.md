## Build Instructions

### Prerequisites

- JDK 1.8+
- [SBT](http://www.scala-sbt.org "SBT")
- [Node.js](https://nodejs.org "Node.js")
- [pnpm](https://pnpm.io "pnpm")
- [Zig 0.13](https://ziglang.org)

### Development

#### Initial setup

`cd reversi`

`pnpm install`

#### Development session

In one terminal:
`pnpm run dev`

Once the development server is up and running, you can optionally support live reloading while editing the Scala code.
To do so, open a second terminal and run:
`sbt ~fastLinkJS`

To edit Zig code, open a third terminal in the `engine` directory. To rebuild the Zig code, run `zig build`.

### Release

To build the finished product for production (in the `dist` directory):
`pnpm run build`

To preview the production build:
`pnpm run preview`
