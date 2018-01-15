#!/usr/bin/env node
const fs = require('fs');
const path = require('path');
const program = require('commander');
const format = require('./index');

program
  .version('0.1.0')
  .option('-i, --input <file>', 'Input file')
  .option('-o, --out <file>', 'Output file path')
  .option('--hang', 'Enable hang mode (better formatting, but 2x slowdown)')
  .parse(process.argv);

if (program.input) {
  const cwd = process.cwd();
  const filePath = path.resolve(cwd, program.input);
  const opts = { isHangEnabled: !!program.hang };

  const src = fs.readFileSync(filePath, 'utf8');
  const out = format(src, filePath, opts);

  if (program.out) {
    fs.writeFileSync(program.out, out, 'utf8');
  } else {
    process.stdout.write(out);
  }
}
