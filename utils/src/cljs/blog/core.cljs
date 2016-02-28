(ns blog.core
  (:require [blog.color-prompt :refer [color-prompt]]
            [blog.color-tree :refer [color-tree]]
            ;; import this for export cmds
            [blog.cmds]))

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
  (color-prompt)
  (color-tree))
