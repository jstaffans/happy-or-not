(defproject happy-or-not "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-beta2"]
                 [kixi/gorilla-repl "0.4.1"]
                 [clj-http "3.7.0"]
                 [cheshire "5.8.0"]
                 [com.rpl/specter "1.0.4"]]
  :profiles {:uberjar {:aot :all}})
