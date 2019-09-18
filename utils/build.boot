(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"resources"}
 :dependencies '[[adzerk/boot-cljs          "2.1.5"  :scope "test"]
                 [adzerk/boot-cljs-repl     "0.4.0"      :scope "test"]
                 [adzerk/boot-reload        "0.6.0"      :scope "test"]
                 [pandeiro/boot-http        "0.8.3"      :scope "test"]
                 [org.clojure/clojurescript "1.10.520"]
                 [crisptrutski/boot-cljs-test "0.3.4" :scope "test"]
                 [com.cemerick/piggieback    "0.2.2" :scope "test"]
                 [weasel                     "0.7.0" :scope "test"]
                 [org.clojure/tools.nrepl    "0.2.13" :scope "test"]
                 [domina "1.0.3"]
                 [hipo "0.5.2"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[pandeiro.boot-http    :refer [serve]]
 '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(deftask build []
  (comp
   (cljs)))

(deftask run []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (speak)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced :parallel-build true})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none :source-map true :parallel-build true}
                 reload {:on-jsload 'blog.core/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))

(deftask prod
  "Simple alias to run application in production mode"
  []
  (comp (production)
        (build)))

(deftask testing []
  (set-env! :source-paths #(conj % "test/cljs"))
  identity)

;;; This prevents a name collision WARNING between the test task and
;;; clojure.core/test, a function that nobody really uses or cares
;;; about.
(ns-unmap 'boot.user 'test)

(deftask test []
  (comp (testing)
        (test-cljs :js-env :phantom
                   :exit?  true)))

(deftask auto-test []
  (comp (testing)
        (watch)
        (test-cljs :js-env :phantom)))
