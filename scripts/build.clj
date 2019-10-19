(require '[cljs.build.api :as b])

(println "Building ...")

(let [start (System/nanoTime)]
  (b/build "src"
           {:main 'zprint-clj.core,
            :target :nodejs
            :output-to "main.js",
            :output-dir "out",
            :optimizations :advanced,
            :static-fns true,
            :fn-invoke-direct true,
            :elide-asserts true,
            :infer-externs true
            :verbose false})
  (println "... done. Elapsed" (/ (- (System/nanoTime) start) 1e9) "seconds"))
