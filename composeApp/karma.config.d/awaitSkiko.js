// Compose 1.13.0-beta01 creates the Skia surface in the SkikoComposeUiTest constructor,
// before runTest awaits the async Skiko wasm load on Kotlin/JS. Delay all tests until Skiko is ready.
;(function (config) {
    const fs = require('fs');
    const path = require('path');
    const setupFile = path.resolve(config.basePath, 'kotlin', 'await-skiko-setup.mjs');
    fs.writeFileSync(
        setupFile,
        'import { awaitSkiko } from "./skiko.mjs";\nbefore(() => awaitSkiko);\n',
    );
    config.files.unshift(setupFile);
    config.preprocessors[setupFile] = ['webpack'];
})(config);
