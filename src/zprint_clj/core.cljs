(ns zprint-clj.core
  (:require
    [cljs.reader :refer [read-string]]
    [goog.object :as gobj]
    [zprint.core :as zprint]))

(set! *warn-on-infer* true)

(def fs (js/require "fs"))
(def os (js/require "os"))

;; Zprint Options
;; -----------------------------------------------------------------------------

(def ^:private no-hang
  {:do-in-hang? false,
   :binding {:hang? false},
   :extend {:hang? false},
   :list {:hang? false},
   :map {:hang? false},
   :pair {:hang? false},
   :pair-fn {:hang? false},
   :reader-cond {:hang? false},
   :record {:hang? false}})


;; Utils
;; -----------------------------------------------------------------------------

(defn slurp
  [file]
  (->
    (.readFileSync fs file "utf8")
    (.toString)))


(defn spit [file data] (.writeFileSync fs file data))


(defn get-zprintrc-file-str
  "Look for .zprintrc in the following environments:

    1. in the current directory
    2. in the 'HOME' dir

  When a .zprintrc file is found, build a string path to the .zprintrc file.  If
  nothing found, return nil"
  []
  (let [home-str (.homedir os)
        local-zprintrc-file-str ".zprintrc"
        global-zprintrc-file-str (str home-str "/.zprintrc")]
    (cond
      (.existsSync fs local-zprintrc-file-str) local-zprintrc-file-str
      (.existsSync fs global-zprintrc-file-str) global-zprintrc-file-str
      :default nil)))


(defn set-zprintrc!
  "Read in any .zprintrc file and set it in the options.

  Returns boolean"
  []
  (let [zprintrc-file-str (get-zprintrc-file-str)]
    (try
      (when zprintrc-file-str
        (let [zprintrc-str (slurp zprintrc-file-str)]
          (when zprintrc-str (zprint/set-options! (read-string zprintrc-str)))))
      (catch :default e false))))


(defn- make-cfg
  [opts]
  (cond
    (false? (gobj/get opts "isHangEnabled")) no-hang
    :else {}))


;; Main
;; -----------------------------------------------------------------------------

(defn ^:export format
  ([s file-name] (format s file-name #js {:isHangEnabled true}))
  ([s file-name opts]
   ;; do not apply default zprint options when .zprintrc is found
   (let [opts* (if (set-zprintrc!) {} (make-cfg opts))]
     (zprint/zprint-file-str s file-name opts*))))
