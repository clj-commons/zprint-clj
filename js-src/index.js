const zp = require('../out/main').zprint_clj.core;

function printFile(str, fileName) {
  return zp.format(str, fileName);
}

module.exports = printFile;
