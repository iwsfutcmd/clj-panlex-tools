(ns panlex-db.core
  (:gen-class)
  (:require [clojure.java.jdbc :as j]
            [taoensso.tufte :as t]
            [panlex-db.secrets :refer [username pass]]))

(def db {:dbtype "postgresql"
         :dbname "plx"
         :host "localhost"
         :user username
         :password pass})

(def begin '\u0002)

(def end '\u0003)

(defn all-ex [uid]
  (let [query-string "
                    SELECT expr.txt,
                           grp_quality_score(array_agg(denotationx.grp),
                                             array_agg(denotationx.quality))
                                             AS score
                    FROM expr
                    JOIN denotationx ON (denotationx.expr = expr.id)
                    WHERE expr.langvar = uid_langvar(?)
                    GROUP BY expr.id
                    "]
    (j/query db [query-string uid])))
        
(defn prep-string [string state-size]
  (str 
       (apply str (repeat state-size begin))
       string
       end))

(defn chunk-string [string state-size]
  (for [i (range (inc (count string)))]
    (let [prepped-string (prep-string string state-size)]
      [(subs prepped-string i (+ i state-size))
       (nth prepped-string (+ i state-size))])))

(defn build-chain [string-vector state-size]
  (loop [chain {}
         temp-string-vector (map #(t/p :chunk-string (chunk-string % state-size)) string-vector)]
    (if (seq temp-string-vector)
        (recur (reduce #(t/p :inside-reduce (update-in %1 %2 (fnil inc 0))) chain (first temp-string-vector))
               (rest temp-string-vector))
        chain)))
  
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
