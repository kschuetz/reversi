// Config for main application

import {defineConfig} from "vite";
import {buildEngine, isDev, printSbtTask} from "./config-common.js";

const replacementForPublic = isDev()
    ? printSbtTask("publicDev", true)
    : printSbtTask("publicProd", false);

buildEngine(isDev());

export default defineConfig({
    resolve: {
        alias: [
            {
                find: "@public",
                replacement: replacementForPublic,
            },
            {
                find: "@subproject",
                replacement: "."
            }
        ],
    },
});
