(ns hkim0331.java-time
  (:require
   [clojure.string :as str]
   [java-time :as jt]))

(defn now-in-milli
  "returns milli from epoch"
  []
  (-> (jt/instant)
      jt/to-millis-from-epoch))

(defn now-in-epoch
  "returns seconds from epoch"
  []
  (-> (now-in-milli)
      (quot 1000)))

;; % date -d '@1662467696'
;; % date -u -d '@1662467696'

;; str->milli is a better name for this function?
;; since the argument is not a datetime object.
(defn datetime->milli
  "local-date-time string to integer milli.
   Input is a string, 'yyyy-MM-DD hh:mm:ss'
   Returns mlli seconds from 1970-01-01"
  [s]
  (let [[date time] (str/split s #" ")]
    (-> (str date "T" time)
        jt/sql-timestamp
        jt/to-millis-from-epoch)))

;; (java.time.Instant/ofEpochMilli 1661330819000))
;; #object[java.time.Instant 0x6f6a7463 "2022-08-24T08:46:59Z"]

(defn datetime->epoch
  [s]
  (-> (datetime->milli s)
      (quot 1000)))

(defn milli->datetime
  "input is milli,
   returns string formatted datetime. default yyyy-mm-dd hh:mm:ss"
  ([milli]
   (milli->datetime "yyyy-MM-dd hh:mm:ss a" milli))
  ([fmt milli]
   (as-> (jt/instant->sql-timestamp milli) $
         (jt/local-date-time $)
         (jt/format fmt $))))

(defn epoch->datetime
  ""
  ([epoch]
   (epoch->datetime "yyyy-MM-dd hh:mm:ss" epoch))
  ([fmt epoch]
   (milli->datetime fmt (* 1000 epoch))))


(comment
  (datetime->epoch "2022-08-25 12:34:56")
  (let [ep (datetime->epoch "2022-08-25 12:34:56")]
    (epoch->datetime ep)))

(defn now-in-milli
  "returns milli from epoch"
  []
  (-> (jt/instant)
      jt/to-millis-from-epoch))

(defn now-in-epoch
  "returns seconds from epoch"
  []
  (-> (now-in-milli)
      (quot 1000)))

(comment
  (dotimes [_ 5]
    (println (epoch->datetime (now-in-epoch)))
    (Thread/sleep 1000)))

