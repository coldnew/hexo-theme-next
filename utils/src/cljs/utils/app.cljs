(ns utils.app
  (:require [utils.color-prompt :refer [color-prompt]]))

(defn debug-helper []
  (.log js/console "starting clojurescript helper for coldnew's blog."))

(defn init []
  (debug-helper)
  (color-prompt))