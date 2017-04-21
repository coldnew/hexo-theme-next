(ns blog.color-prompt
  (:require [clojure.string :as str]))

(defn- span [x y]
  (let [;; pre "<span onmousedown=\"return false;\" onselectstart=\"return false;\">"
        pre "<span style=\"-moz-user-select: none; -webkit-user-select: none; -ms-user-select:none; user-select:none;-o-user-select:none;\" onmousedown=\"return false;\" onselectstart=\"return false;\" ondragstart=\"return false\">"
        pos "</span>"
        ;; extra is to make commandline can easy copy without copy the prompt
        extra "<span style=\"width: 0; height: 0; display: inline-block; overflow: hidden;\"><span style=\"display: block;\"></span></span>"]
    (str pre x pos extra y)))

(defn- replace-line-starts
  "Replace line-seqs for every starts. if match to regexp. This function make cljs regexp work as ^regex.
  I use this function due to current cljs doesn't support #\"(?m)^xxx$\" regexp, which work on clojure."
  [regex newval seq]
  (map #(if (str/starts-with? % (->> (re-find regex %) rest str/join ))
          (str/replace % regex newval)
          %) seq))

(defn- color-shell-prompt [classname]
  (let [block (.getElementsByClassName js/document classname)
        length (.-length block)
        user-highlight (span "<font color=\"lightgreen\">$1</font>$2<font color=\"lightblue\">$3</font>" "$4")
        root-highlight (span "<font color=\"crimson\">$1</font>$2<font color=\"lightblue\">$3</font>" "$4")]
    (loop [i 0]
      (when (< i length)
        (let [target (aget block i)]
          (set! (.-innerHTML target)
                (->> (str/split (.-innerHTML target) #"\n")
                     ;; ex: root@raspberrypi:/home/pi#
                     (replace-line-starts #"(root@\w*)(\s*)(.*\#\s)(.*)" root-highlight)
                     ;; ex: "coldnew@Gentoo ~ $ ./zephyr-sdk-0.7.2-i686-setup.run"
                     (replace-line-starts #"(\w*@\w*)(\s*)(.*\$\s)(.*)" user-highlight)
                     ;; ex: Rosia msp430 # emerge mspdebug
                     (replace-line-starts #"(\w*)(\s*)(.*\#\s)(.*)" root-highlight)
                     (str/join "\n"))))
        (recur (inc i))))))

(defn- color-clojure-prompt [classname]
  (let [block (.getElementsByClassName js/document classname)
        length (.-length block)
        prompt-highlight (span "<font color=\"#FFFF75\">$1</font>" "$2")]
    (loop [i 0]
      (when (< i length)
        (let [target (aget block i)]
          (set! (.-innerHTML target)
                (->> (str/split (.-innerHTML target) #"\n")
                     ;; highlight `user> ` or `user=>` (clojure repl)
                     (replace-line-starts #"(user&gt;\s*)(.*)" prompt-highlight)
                     (replace-line-starts #"(user>\s*)(.*)"    prompt-highlight)
                     ;; this should not change color
                     (replace-line-starts #"(user=&gt;\s)(.*)"   "$1$2")
                     (replace-line-starts #"(user=>\s)(.*)"      "$1$2")
                     (replace-line-starts #"(\s\s#_=&gt;\s)(.*)" "$1$2")
                     (replace-line-starts #"(\s\s#_=>\s)(.*)"    "$1$2")
                     (str/join "\n"))))
        (recur (inc i))))))

(defn color-prompt []
  (color-shell-prompt "example")
  (color-shell-prompt "src src-sh")
  (color-clojure-prompt "example")
  (color-clojure-prompt "src src-clojure"))

;; This code rewrite based on my original javascript code (as below, which need jquery)
(comment
  ;; // Color Bash prompt in example block
  ;; $(document).ready(function () {
  ;;   'use strict';

  ;;   // prevent selectable in shell prompt
  ;;   function span (x) {
  ;;     var pre = '<span onmousedown=\"return false;\" onselectstart=\"return false;\">';
  ;;     var pos = '</span>';
  ;;     // extra is to make commandline can easy copy without copy the prompt
  ;;     var extra = '<span style="width: 0; height: 0; display: inline-block; overflow: hidden;"><span style="display: block;"></span></span>';
  ;;     return pre + x + pos + extra;
  ;;   }

  ;;   var userHighlight  = span ('<font color=\"lightgreen\">$1</font><font color=\"lightblue\">$2</font>');
  ;;   var rootHighlight  = span ('<font color=\"crimson\">$1</font><font color=\"lightblue\">$2</font>');
  ;;   var promptHighlight  = span ('<font color=\"#FFFF75\">$1 </font>');

  ;;   var nothing  = span ('$1 ');

  ;;   // https://regex101.com/#javascript
  ;;   function color_shell_prompt(className) {
  ;;     var block = document.getElementsByClassName(className);
  ;;     for(var i = 0, l = block.length; i < l; i++) {
  ;;       // highlight `user@hostname directory $'
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(\w*@\w*)(\s*[:~](.+)\/([^\/]+)[$]\s)/mg, userHighlight);
  ;;       // highlight `user@hostname ~ $'
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(\w*@\w*)(\s*[:~](.*)([^\/]+)[$]\s)/mg, userHighlight);
  ;;       // highlight `root@hostname #'
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(root@\w*)(\s*[:~](.+)\/([^\/]+)[#]\s)/mg, rootHighlight);
  ;;       // highlight `hostname #'
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(\w*)(\s*[:~](.+)\/([^\/]+)[#]\s)/mg, rootHighlight);
  ;;       // highlight `hostname directory #' (Gentoo Linux root)
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(\w*)(\s*\w* [#])/mg, rootHighlight);
  ;;     }
  ;;   }

  ;;   function color_clojure_prompt(className) {
  ;;   var block = document.getElementsByClassName(className);
  ;;     for(var i = 0, l = block.length; i < l; i++) {
  ;;       // highlight `user> ` or `user=>` (clojure repl)
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(user&gt;)\s/mg, promptHighlight);
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(user>\s)/mg, promptHighlight);
  ;;       // this should not change color
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(user=&gt;)\s/mg, nothing);
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(user=>\s)/mg, nothing);
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(\s\s#_=&gt;)\s/mg, nothing);
  ;;       block[i].innerHTML = block[i].innerHTML.replace(/^(\s\s#_=>\s)/mg, nothing);
  ;;     }
  ;;   }

  ;;   // color some class with shellprompt
  ;;   // color_shell_prompt('example');
  ;;   // color_shell_prompt('src src-sh');
  ;;   color_clojure_prompt('example');
  ;;   color_clojure_prompt('src src-clojure');
  ;; });


  ;; function toggle_visible(id) {
  ;;   // toggle the conten
  ;;   var e = document.getElementById(id);
  ;;   if (e.style.display == 'block' || e.style.display=='') e.style.display = 'none';
  ;;   else e.style.display = 'block';
  ;; }
  )
