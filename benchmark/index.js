const fs = require('fs');
const path = require('path');
const format = require('../js-src/index');

const files = [
  'stubs/100.cljs',
  'stubs/700.cljs',
  'stubs/1500.cljs',
  'stubs/11k.cljs'
];

const inputs = files.map(file => [
  file,
  fs.readFileSync(path.join(__dirname, file), 'utf8')
]);

function run(file, input) {
  const start = Date.now();
  console.log('Formatting', file, '...');
  format(input, file);
  console.log('Done', (Date.now() - start) / 1000, 's');
}

for (let i = 5; i > 0; i--) {
  inputs.forEach(entry => run(...entry));
}
