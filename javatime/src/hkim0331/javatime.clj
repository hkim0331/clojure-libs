(ns hkim0331.javatime
  (:require
   [clojure.string :as str])
  (:import
   [java.time LocalDateTime Instant ZoneId]
   [java.time.format DateTimeFormatter]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn milli->datetime
  "input milli,
   returns LocalDateTime object"
  [milli]
  (-> milli
      Instant/ofEpochMilli
      (LocalDateTime/ofInstant (ZoneId/of "Asia/Tokyo"))))

(defn epoch->datetime
  "input epich (second),
   returns LocalDateTime object"
  [epoch]
  (-> epoch
      (* 1000)
      milli->datetime))

(comment
  (let [dtf (DateTimeFormatter/ofPattern "yyyy/MM/dd")]
    (.format dtf (epoch->datetime 1661330819))))

(defn epoch->str
  ([epoch] (epoch->str "yyyy/MM/dd hh:mm:ss" epoch))
  ([fmt epoch]
   (->> epoch
        epoch->datetime
        (.format (DateTimeFormatter/ofPattern fmt)))))

(comment
  (epoch->str 161330819)
  (epoch->str "yyyy/MM/dd hh:mm" 1661330819))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn instant->datetime
  [instant]
  (LocalDateTime/ofInstant instant (ZoneId/of "Asia/Tokyo")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn str->epoch
  "string '2022/09/06 12:34:56' -> epoch second"
  [s]
  (let [[date time] (str/split s #" ")]
    (-> (str date "T" time ".00Z")
        Instant/parse
        .getEpochSecond)))

(defn str->milli
  [s]
  (* 1000 (str->epoch s)))

(comment
  (str->epoch "2022-09-06 12:34:56")
  (str->milli "2022-09-06 12:34:56"))

;;; Instant
(comment
  (Instant/ofEpochSecond 0)
  (Instant/now)
  (-> (Instant/now)
      (instant->datetime)))

(-> (Instant/now)
    instant->datetime)