(ns blog.color-tree
  (:require [clojure.string :as str]))

(defn- replace-line-starts
  "Replace line-seqs for every starts. if match to regexp. This function make cljs regexp work as ^regex.
  I use this function due to current cljs doesn't support #\"(?m)^xxx$\" regexp, which work on clojure."
  [regex newval seq]
  (map #(if (str/starts-with? % (->> (re-find regex %) rest str/join ))
          (str/replace % regex newval)
          %) seq))

(defn- tree [x1 x2 x3 color]
  (str x1 "<font color=\"" color "\">" x2 "</font>" x3))
(defn- treeB
  ([x1 x2 x3] (tree x1 x2 x3 "lightblue"))
  ([x1 x2] (treeB x1 x2 ""))
  ([x2]    (treeB "" x2 "")))
(defn- treeR
  ([x1 x2 x3] (tree x1 x2 x3 "#ff3232"))
  ([x1 x2] (treeR x1 x2 ""))
  ([x2]    (treeR "" x2 "")))
(defn- treeG
  ([x1 x2 x3] (tree x1 x2 x3 "lightgreen"))
  ([x1 x2] (treeG x1 x2 ""))
  ([x2]    (treeG "" x2 "")))
(defn- treeLB [x1 x2 x3 x4]
  (str x1 "<font color=\"cyan\">" x2 "</font>" x3 "<font color=\"lightblue\">" x4 "</font>"))
(defn- treeLW [x1 x2 x3 x4]
  (str x1 "<font color=\"cyan\">" x2 "</font>" x3 x4))

;; <b> blue
;; <r> red
;; <g> green
;; <lb> link (cyan -> blue)
;; <lw> link (cyan -> default)
(defn- color-tree-prompt [classname]
  (let [block (.getElementsByClassName js/document classname)
        length (.-length block)]
    (loop [i 0]
      (when (< i length)
        (let [target (aget block i)]
          (set! (.-innerHTML target)
                (->> (str/split (.-innerHTML target) #"\n")
                     ;; highlight `. <b>` for blue color
                     (replace-line-starts #"(\.\s*)(&lt;b&gt;)" (treeB "$1"))
                     ;; highlight `/usr/local <b>` for blue color
                     (replace-line-starts #"(/.*\s*)(&lt;b&gt;)" (treeB "$1"))
                     ;; blue
                     (replace-line-starts #"([├─└─]*\s*)([\w\-\.]*\s*)(&lt;b&gt;)" (treeB "$1" "$2"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s*[├─└─]*\s*)([\w\-\.]*\s*)(&lt;b&gt;)" (treeB "$1" "$2"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s│&nbsp;&nbsp;\s[├─└─\s]*\s*)([\w\-\.]*\s*)(&lt;b&gt;)" (treeB "$1" "$2"))
                     ;; red
                     (replace-line-starts #"([├─]*\s*)([\w\-\.]*\s*)(&lt;r&gt;)" (treeR "$1" "$2"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s*[├─└─]*\s*)([\w\-\.]*\s*)(&lt;r&gt;)" (treeR "$1" "$2"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s│&nbsp;&nbsp;\s[├─└─\s]*\s*)([\w\-\.]*\s*)(&lt;r&gt;)" (treeR "$1" "$2"))
                     ;; green
                     (replace-line-starts #"(│&nbsp;&nbsp;\s*[├─└─\s]*\s*)([\w\-\.]*\s*)(&lt;g&gt;)" (treeG "$1" "$2"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s│&nbsp;&nbsp;\s[├─└─\s]*\s*)([\w\-\.]*\s*)(&lt;g&gt;)" (treeG "$1" "$2"))
                     (replace-line-starts #"([├─└─]*\s*)([\w\-\.]*\s*)(&lt;g&gt;)" (treeG "$1" "$2"))
                     ;; link
                     (replace-line-starts #"([├─└─\s]*\s*)(.*\s*)(-&gt;)(.*\s*)(&lt;lb&gt;)" (treeLB "$1" "$2" "$3" "$4"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s*[├─└─\s]*\s*)(.*\s*)(-&gt;)(.*\s*)(&lt;lb&gt;)" (treeLB "$1" "$2" "$3" "$4"))
                     (replace-line-starts #"([├─└─\s]*\s*)(.*\s*)(-&gt;)(.*\s*)(&lt;lw&gt;)" (treeLW "$1" "$2" "$3" "$4"))
                     (replace-line-starts #"(│&nbsp;&nbsp;\s*[├─└─\s]*\s*)(.*\s*)(-&gt;)(.*\s*)(&lt;lw&gt;)" (treeLW "$1" "$2" "$3" "$4"))
                     ;; color word with color hint: outdir<b>  , README.txt<g>
                     (map #(str/replace % #"(\w*)(&lt;b&gt;)" "<font color=\"lightblue\">$1</font>"))
                     (map #(str/replace % #"([\w\-\.]*)(&lt;g&gt;)" "<font color=\"lightgreen\">$1</font>"))
                     (str/join "\n"))))
        (recur (inc i))))))


(defn color-tree []
  (color-tree-prompt "example"))