<img src="logo.png" width=128 />

[![CircleCI](https://circleci.com/gh/clj-commons/zprint-clj.svg?style=svg)](https://circleci.com/gh/clj-commons/zprint-clj)

_Node.js wrapper for [ZPrint](https://github.com/kkinnear/zprint) Clojure source code formatter_

## Usage

### CLI

#### Install
```
npm i -g zprint-clj
```

#### Format and write back
```
zprint-clj -i "./src/**/*.{clj,cljs,cljc,edn}" -o ./
```

#### Check formatting without writing (useful for CI)
```
zprint-clj --check "./src/**/*.{clj,cljs,cljc,edn}"
```

### API

```
npm i zprint-clj
```

```js
const printFile = require('zprint-clj');
const outputText = format(inputText, inputFilePath, opts);
```

### Options

* `isHangEnabled` `true|false` — enable hang mode (disabled by default), slows down formatting, but results in better formatted output

*Difference between output when Hang mode is on and off*

```diff
diff --git a/hang.clj b/nohang.clj
index 907120f..965fdfa 100644
--- a/hang.clj
+++ b/nohang.clj
@@ -1,6 +1,7 @@
 (ns zprint-clj.core
-  (:require [zprint.core :as zprint]
-            [goog.object :as gobj]))
+  (:require
+    [zprint.core :as zprint]
+    [goog.object :as gobj]))
@@ -15,8 +16,9 @@

 (defn- make-cfg
   [opts]
-  (cond (false? (gobj/get opts "isHangEnabled")) no-hang
-        :else {}))
+  (cond
+    (false? (gobj/get opts "isHangEnabled")) no-hang
+    :else {}))
```

### Advanced Configuration

You can configure `zprint-clj` with a `.zprintrc` file. The `.zprintrc` is written in EDN format.

Your `.zprintrc` file will be resolved if it is found in one of two locations:

1.  The current directory where the zprint-clj process was initialized
2.  The global `HOME` directory

If none found, we fall back on `zprint-clj` [default settings](https://github.com/kkinnear/zprint#quick-start).

## Building

**production**

```
clojure scripts/build.clj
```

**debug**

```
clojure scripts/build_debug.clj
```

## Local Development

The following guide outlines how to locally develop `zprint-clj`. Please make sure you have the [clj cli tool installed](https://clojure.org/guides/deps_and_cli).

- Install `zprint-clj` dependencies

  ```bash
  npm install
  ```

- Make your your code changes in the `src` directory

- Compile your changes

  ```bash
  clojure scripts/build_debug.clj
  ```

- Update `js-src/index` to look like this

  ```bash
  const zp = require("../out/main-debug").zprint_clj.core;
  ```

  > This tells zprint-clj to use the debug version

- Create a test Clojure file with some test code you want to see formatted

- Open the test file in your editor of choice

- Run zprint-clj against your test file

  ```clojure
  js-src/cli.js -i <your-filename-here> -o <your-filename-here>
  ```

  > If you are running in a different directory you will have to update above path to `js-src/cli.js`. The reason we run this instead of `main-debug` is because `cli`, or `index`, act as the entry points for JS for this library.

## Editor plugins

- [Atom](https://github.com/roman01la/zprint-atom)

## License

MIT
