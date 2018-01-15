(ns zprint-npm.core
  (:require [zprint.core :as zprint]))

(defn ^:export print-file [s file-name]
  (zprint/zprint-file-str s file-name))
