(ns hkim0331.java-time
  (:require
   [clojure.string :as str]
   [java-time :as jt]))

;; epoch = unix-time
;; str->milli is a better name for this function?
;; since the argument is not a datetime object.
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

;; epoch->datetime-string
(defn epoch->datetime
  "input is epoch (second)
   returns string formatted. default yyyy/MM/dd hh:mm:ss"
  ([epoch] (epoch->datetime "yyyy/MM/dd hh:mm:ss" epoch))
  ([fmt epoch] (->> (jt/instant->sql-timestamp (* 1000 epoch))
                    jt/local-date-time
                    (jt/format fmt))))

(comment
  (datetime->epoch "2022-08-25 12:34:56")
  (let [ep (datetime->epoch "2022-08-25 12:34:56")]
    (epoch->datetime ep)))

(defn now-in-milli
  "returns milli from epoch"
  []
  (-> (jt/instant)
      jt/to-mills-from-epoch))

(defn now-in-epoch
  "returns seconds from epoch"
  []
  (-> (now-in-milli)
      (quot 1000)))

(comment
  (dotimes [_ 5]
    (println (epoch->datetime (now-in-epoch)))
    (Thread/sleep 1000)))
