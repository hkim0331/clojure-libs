(ns hkim0331.java-time
  (:require
   [clojure.string :as str]
   [java-time :as jt]
   [clojure.string :as s]))

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

;; (now-in-second)

(defn str->milli
  "local-date-time string to integer milli.
   Input string must be in the format 'yyyy-MM-DDThh:mm:ss'
   Returns mlli from 1970-01-01"
  [s]
  (-> s
      jt/sql-timestamp
      jt/to-millis-from-epoch))

;; (java.time.Instant/ofEpochMilli 1661330819000))
;; #object[java.time.Instant 0x6f6a7463 "2022-08-24T08:46:59Z"]

(defn str->second
  [s]
  (-> (str->milli s)
      (quot 1000)))

(defn milli->str
  "input is milli,
   returns string formatted datetime. default yyyy-mm-dd hh:mm:ss"
  ([milli]
   (milli->str "yyyy-MM-dd hh:mm:ss a" milli))
  ([fmt milli]
   (->> (jt/instant->sql-timestamp milli)
        (jt/local-date-time)
        (jt/format fmt))))

(defn second->str
  ""
  ([second]
   (second->str "yyyy-MM-dd hh:mm:ss" second))
  ([fmt second]
   (milli->str fmt (* 1000 second))))

;; (second->str 1667190341)

;; 2022-12-19
(def unix-time->str second->str)

(comment
  (unix-timestamp->datetime-str (* 1667190341 1000))
  :rcf)
