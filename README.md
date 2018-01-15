# ZPrint

_Node.js wrapper for [ZPrint](https://github.com/kkinnear/zprint) Clojure source code formatter_

## Usage

### CLI

```
npm i -g zprint-clj
zprint-clj -i <file> -o <file>
```

### API

```js
const printFile = require('zprint-clj');
const outputText = printFile(inputText, inputFilePath);
```
