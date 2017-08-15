(defproject panlex-db "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.7.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [com.taoensso/tufte "1.1.1"]]
  :main ^:skip-aot panlex-db.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
