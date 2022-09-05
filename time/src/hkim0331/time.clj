(ns hkim0331.time
  (:require
   [clojure.string :as str]
   [java-time :as jt]))

;;;
;;; FIXME: when #(quot % 1000)?
;;;

;; epoch = unix-time
(defn datetime->milli
  "input: yyyy-MM-DD hh:mm:ss
   returns mlli seconds from 1970-01-01"
  [s]
  (let [[date time] (str/split s #" ")]
    (-> (str date "T" time)
        jt/sql-timestamp
        jt/to-millis-from-epoch)))

;; (java.time.Instant/ofEpochMilli 1661330819000))
;; #object[java.time.Instant 0x6f6a7463 "2022-08-24T08:46:59Z"]
;; (str *1)
;; "2022-08-24T08:46:59Z"

(defn datetime->epoch
 [s]
 (-> (datetime->milli s)
     (quot 1000)))

(defn epoch->datetime
  "input is epoch (second)
   returns string"
  ([epoch] (epoch->datetime "yyyy/MM/dd hh:mm:ss" epoch))
  ([fmt epoch] (->> (jt/instant->sql-timestamp (* 1000 epoch))
                    jt/local-date-time
                    (jt/format fmt))))

(comment
  (datetime->epoch "2022-08-25 12:34:56")
  (let [ep (datetime->epoch "2022-08-25 12:34:56")]
    (epoch->datetime ep)))


;; Appetizer
(defn now-in-epoch
  []
  (-> (jt/instant)
      (jt/to-millis-from-epoch)
      (quot 1000)))

(comment
  (dotimes [_ 5]
    (println (epoch->datetime (now-in-epoch)))
    (Thread/sleep 1000)))

