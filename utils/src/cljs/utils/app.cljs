(ns utils.app
  (:require [utils.color-prompt :refer [color-prompt]]))

(enable-console-print!)

(defn debug-helper []
  (.log js/console "starting clojurescript helper for coldnew's blog."))

;; Extend NodeList and HTMLCollection to make them sequable
(extend-type js/NodeList
  ISeqable
  (-seq [array] (array-seq array 0)))

(extend-type js/HTMLCollection
  ISeqable
  (-seq [array] (array-seq array 0)))

(defn init []
  (debug-helper)
  (color-prompt))