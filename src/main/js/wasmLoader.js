import init from '../../../engine/zig-out/bin/reversi.wasm?init';

window._reversiWasm = init();

// init().then((instance) => {
//     console.log(instance.exports.do_something(4, 5));
// });
