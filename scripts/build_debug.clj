(require '[cljs.build.api :as b])

(println "Building ...")

(let [start (System/nanoTime)]
  (b/build "src"
           {:main 'zprint-clj.core,
            :output-to "out/main-debug.js",
            :output-dir "out",
            :optimizations :advanced,
            :pseudo-names true,
            :static-fns true,
            :fn-invoke-direct true,
            :elide-asserts true,
            :optimize-constants true,
            :verbose true})
  (println "... done. Elapsed" (/ (- (System/nanoTime) start) 1e9) "seconds"))
