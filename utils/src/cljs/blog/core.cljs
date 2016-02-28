(ns blog.core
  (:require [blog.color-prompt :refer [color-prompt]]))

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

;; for control toggle visible in 4clojure practice.
;; #+ATTR_HTML: :class btn btn-default :onclick toggle_visible('p001')
(defn ^:export toggle-visible [id]
  (.log js/console (str "toffll2 " id))
  (let [e (.getElementById js/document id)
        s (-> e .-style .-display)]
    (set! (-> e .-style .-display)
     (if (or (= s "block") (= s ""))
       "none"
       "block"))))