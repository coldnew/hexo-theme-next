(ns blog.cmds
  (:require [clojure.string :as str]))

;; for control toggle visible in 4clojure practice.
;; #+ATTR_HTML: :class btn btn-default :onclick blog.cmds.toggle_visible('p001')
(defn ^:export toggle-visible [id]
  (let [e (.getElementById js/document id)
        s (-> e .-style .-display)]
    (set! (-> e .-style .-display)
          (if (or (= s "block") (= s ""))
            "none"
            "block"))))
