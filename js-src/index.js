const zp = require('../out/main').zprint_npm.core;

function printFile(str, fileName) {
  return zp.print_file(str, fileName);
}

module.exports = printFile;
