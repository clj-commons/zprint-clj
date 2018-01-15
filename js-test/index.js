const fs = require('fs');
const path = require('path');
const printFile = require('../js-src/index');

const FILE_NAME = 'core.cljs';

const input = fs.readFileSync(path.join(__dirname, FILE_NAME), 'utf8');

function run() {
  const start = Date.now();
  console.log('Formatting', FILE_NAME, '...');
  printFile(input, FILE_NAME);
  console.log('Done', (Date.now() - start) / 1000, 's');
}

run();
