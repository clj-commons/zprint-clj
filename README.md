<img src="logo.png" width=128 />

_Node.js wrapper for [ZPrint](https://github.com/kkinnear/zprint) Clojure source code formatter_

## Usage

### CLI

```
npm i -g zprint-clj

zprint-clj -i <file> -o <file>
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

### Advanced Customization

You can configure `zprint-clj` with a `.zprintrc` file. The `.zprintrc` is written in EDN format.

Your `.zprintrc` file will be resolved starting from the location of the file being formatted, and searching up the file tree until we find a `.zprintrc` file. If none found, we fall back on `zprint` [default settings](https://github.com/kkinnear/zprint#quick-start).

## Building

**production**

```
clj scripts/build.clj
```

**debug**

```
clj scripts/build_debug.clj
```

## Local Development

The following guide will outline how to locally develop `zprint-clj`:

- Install `zprint-clj` dependencies

  ```bash
  npm install
  ```

- Make your your code changes in the `src` directory

- Compile your changes

  ```bash
  scripts/debug.clj
  ```

- Create a test Clojure file with some test code you want to see formatted

- Open the test file in atom

- Run zprint-clj against your test file

  ```clojure
  js-src/dev-cli.js -i <filename> -o <filename>
  ```

## Editor plugins

- [Atom](https://github.com/roman01la/zprint-atom)

## License

MIT
