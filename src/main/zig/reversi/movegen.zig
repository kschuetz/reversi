usingnamespace @import("common.zig");
usingnamespace @import("board.zig");
const tables = @import("tables.zig");

pub fn generateMoves(player: Color, board: *Board) BoardMask {
    var result: BoardMask = 0;
    const my_pieces = board.pieces(player);
    const opponent_pieces = board.pieces(player.opponent());
    const unoccupied = board.unoccupiedSquares();
    for (0..64) |i| {
        const si = SquareIndex.of(i);
        if (unoccupied.isSet(si)) {
            const neighbors = tables.neighbor_masks[si];
            for (0..7) |d| {
                const dir = @enumFromInt(d);
                if (dir.isInMask(neighbors)) {
                    const shift_dir = dir.opposite();
                    if (walk(si, my_pieces, opponent_pieces, shift_dir)) {
                        result = result | si.select();
                        break;
                    }
                }
            }
        }
    }

    return result;
}

fn walk(square_index: SquareIndex, player: BoardMask, opponent: BoardMask, shift_dir: Direction) bool {
    _ = square_index;
    _ = player;
    _ = opponent;
    _ = shift_dir;
    return false;
}

//   private def walk(squareIndex: SquareIndex,
//                    player: BoardMask,
//                    opponent: BoardMask,
//                    shiftDirection: Direction): Boolean = {
//     var op = opponent.shift(shiftDirection)
//     if op.isSet(squareIndex) then {
//       op = op.shift(shiftDirection)
//       var p = player.shift(shiftDirection).shift(shiftDirection)
//       var result = p.isSet(squareIndex)
//       var loop = true
//       while (loop && !result) {
//         if !op.isSet(squareIndex) then loop = false
//         else {
//           p = p.shift(shiftDirection)
//           op = op.shift(shiftDirection)
//           result = p.isSet(squareIndex)
//         }
//       }
//       result
//     } else false
//   }
