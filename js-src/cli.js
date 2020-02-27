#!/usr/bin/env node
const fs = require("fs");
const path = require("path");
const program = require("commander");
const fg = require("fast-glob");
const format = require("./index");
const pkg = require("../package.json");

program
  .version(pkg.version)
  .option(
    '-c, --check "<pattern>"',
    'Checks formatting without writing to output, zprint-clj -c "./out/**/*.{clj,cljs,cljc,edn}"'
  )
  .option(
    "-i, --input <pattern>",
    "Input file, directory or glob pattern. If no output specified writes to stdout."
  )
  .option("-o, --out <path>", "Output path, file or directory")
  .option("--config <path>", "An explicit path to .zprintrc")
  .option("--hang", "Enable hang mode (better formatting, but 2x slowdown)")
  .parse(process.argv);

if (program.input || program.check) {
  const paths = fg.sync(program.input || program.check, {
    onlyFiles: true,
    dot: false
  });

  if (paths.length === 0) {
    console.log("Nothing to format, exiting.");
    return;
  }

  console.log(`Checking formatting in ${paths.length} files...`);

  const opts = { isHangEnabled: !!program.hang };
  const srcs = paths.map(p => [p, fs.readFileSync(p, "utf8")]);
  const { config } = program;
  let safeConfigPath;
  try {
    if (fs.existsSync(config)) {
      safeConfigPath = config;
    }
  } catch (e) {
    console.warn(`Warning: ${config} was not found`);
  }

  const outs = srcs.map(([p, src]) =>
    format(src, p, opts, safeConfigPath || null)
  );

  if (program.check) {
    const diffs = srcs.reduce((ret, [p, src], idx) => {
      if (src !== outs[idx]) {
        ret.push(p);
      }
      return ret;
    }, []);
    if (diffs.length > 0) {
      diffs.forEach(d => console.log(d));
      console.log(
        "Code style issues found in the above file(s). Forgot to run ZPrint?"
      );
      process.exit(1);
    } else {
      console.log("All matched files use ZPrint code style!");
    }
  } else if (program.out) {
    Promise.all(
      outs.map(
        (out, idx) =>
          new Promise((resolve, reject) => {
            const pout = path.join(program.out, srcs[idx][0]);
            fs.writeFile(pout, out, "utf8", err =>
              err ? reject(err) : resolve()
            );
          })
      )
    )
      .then(() => {
        console.log("Done.");
      })
      .catch(err => {
        console.log("Something went wrong when writing formatted output...");
        console.error(err);
        process.exit(1);
      });
  } else {
    outs.forEach(out => {
      process.stdout.write(out);
    });
  }
}
