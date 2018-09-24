(ns zprint-clj.core
  (:require [cljs.reader :refer [read-string]]
            ["fs"        :as fs]
            [goog.object :as gobj]
            ["os"        :as os]
            [zprint.core :as zprint]))


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

(defn slurp [file]
  (-> (fs/readFileSync file "utf8")
      (.toString)))


(defn spit [file data]
  (fs/writeFileSync file data))


(defn get-zprintrc-file-str
  "Look up 'HOME' in the environment, and build a string to find ~/.zprintrc"
  []
  (let [home-str (os/homedir)]
    (when home-str (str home-str "/.zprintrc"))))


(defn set-zprintrc!
  "Read in any ~/.zprintrc file and set it in the options.

  Returns boolean"
  []
  (let [zprintrc-file-str (get-zprintrc-file-str)]
    (try (when zprintrc-file-str
           (let [zprintrc-str (slurp zprintrc-file-str)]
             (when zprintrc-str
               (zprint/set-options! (read-string zprintrc-str))
               true)))
         (catch :default e
           false))))


(defn- make-cfg
  [opts]
  (cond (false? (gobj/get opts "isHangEnabled")) no-hang
        :else {}))


;; Main
;; -----------------------------------------------------------------------------

(defn ^:export format
  ([s file-name]
   (format s file-name #js {:isHangEnabled true}))
  ([s file-name opts]
   ;; do not apply default zprint options when .zprintrc is found
   (let [opts* (if (set-zprintrc!) {} (make-cfg opts))]
     (zprint/zprint-file-str s file-name opts*))))
