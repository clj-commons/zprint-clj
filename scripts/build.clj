(require '[cljs.build.api :as b])

(println "Building ...")

(let [start (System/nanoTime)]
  (b/build "src"
           {:main 'zprint-npm.core
            :output-to "out/main.js"
            :output-dir "out"
            :optimizations :advanced
            :verbose true})
  (println "... done. Elapsed" (/ (- (System/nanoTime) start) 1e9) "seconds"))
