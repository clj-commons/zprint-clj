#!/usr/bin/env node
const fs = require('fs');
const path = require('path');
const program = require('commander');
const printFile = require('./index');

program
  .version('0.1.0')
  .option('-i, --input <file>', 'Input file')
  .option('-o, --out <file>', 'Output file path')
  .parse(process.argv);

if (program.input) {
  const cwd = process.cwd();
  const filePath = path.resolve(cwd, program.input);

  const src = fs.readFileSync(filePath, 'utf8');
  const out = printFile(src, filePath);

  if (program.out) {
    fs.writeFileSync(program.out, out, 'utf8');
  } else {
    process.stdout.write(out);
  }
}
