(ns zprint-clj.core
  (:require [zprint.core :as zprint]
            [goog.object :as gobj]))

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

(defn- make-cfg
  [opts]
  (cond (false? (gobj/get opts "isHangEnabled")) no-hang
        :else {}))

(defn ^:export format
  ([s file-name] (format s file-name #js {:isHangEnabled true}))
  ([s file-name opts] (zprint/zprint-file-str s file-name (make-cfg opts))))
