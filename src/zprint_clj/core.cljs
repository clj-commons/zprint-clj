(ns zprint-clj.core
  (:require [zprint.core :as zprint]))

(defn ^:export format [s file-name]
  (zprint/zprint-file-str s file-name))
