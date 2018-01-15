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

function run([file, input], opts) {
  const start = Date.now();
  console.log('Formatting', file, '...');
  format(input, file, opts);
  console.log('Done', (Date.now() - start) / 1000, 's');
}

console.log('{isHangEnabled: true}');
for (let i = 3; i > 0; i--) {
  inputs.forEach(entry => run(entry, { isHangEnabled: true }));
}

console.log('{isHangEnabled: false}');
for (let i = 3; i > 0; i--) {
  inputs.forEach(entry => run(entry, { isHangEnabled: false }));
}
