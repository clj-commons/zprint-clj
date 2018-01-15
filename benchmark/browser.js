const format = s => zprint_clj.core.format(s, '<stdin>');

const withTime = fn => {
  return (...args) => {
    const start = performance.now();
    const ret = fn(...args);
    console.log(
      `${Math.round(performance.now() - start)}ms`,
      `${ret.split('\n').length} LOC`
    );
    return ret;
  };
};

fetch('stubs/700.cljs')
  .then(r => r.text())
  .then(withTime(format))
  .catch(console.error);
