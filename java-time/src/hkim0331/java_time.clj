(ns hkim0331.java-time
  (:require
   [clojure.string :as str]
   [java-time :as jt]))

;; % date -d '@1662467696'
;; % date -u -d '@1662467696'

(defn now-in-milli
  "returns milli from epoch"
  []
  (System/currentTimeMillis))

(defn now-in-second
  "returns seconds from epoch"
  []
  (-> (now-in-milli)
      (quot 1000)))


(defn str->milli
  "local-date-time string to integer milli.
   Input string must be in format 'yyyy-MM-DDThh:mm:ss'
   Returns mlli seconds from 1970-01-01"
  [s]
  (-> (jt/sql-timestamp s)
      (jt/to-millis-from-epoch)))

;; (java.time.Instant/ofEpochMilli 1661330819000))
;; #object[java.time.Instant 0x6f6a7463 "2022-08-24T08:46:59Z"]

(defn str->second
  [s]
  (-> (datetime->milli s)
      (quot 1000)))

(defn milli->str
  "input is milli,
   returns string formatted datetime. default yyyy-mm-dd hh:mm:ss"
  ([milli]
   (milli->datetime "yyyy-MM-dd hh:mm:ss a" milli))
  ([fmt milli]
   (->> (jt/instant->sql-timestamp milli)
        (jt/local-date-time)
        (jt/format fmt))))

(defn second->str
  ""
  ([second]
   (epoch->datetime "yyyy-MM-dd hh:mm:ss" second))
  ([fmt second]
   (milli->datetime fmt (* 1000 second))))

(comment
  (datetime->epoch "2022-08-25 12:34:56")
  (let [ep (datetime->epoch "2022-08-25 12:34:56")]
    (epoch->datetime ep))
  (dotimes [_ 5]
    (println (epoch->datetime (now-in-epoch)))
    (Thread/sleep 1000)))
