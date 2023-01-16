(ns javatime.javatime
  ;; without import, java libraries are callable.
  ;; when imported, aliases can be used. not FQN.
  (:import
   [java.time LocalDateTime Instant ZoneId]
   [java.time.format DateTimeFormatter]))

;; % date -d '@1662467696'
;; % date -u -d '@1662467696'

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn now-in-milli
  []
  (System/currentTimeMillis))

(defn now-in-second
  []
  (-> (now-in-milli)
      (quot 1000)))

(comment
  (now-in-milli)
  (now-in-second))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn instant->datetime
  [instant]
  (LocalDateTime/ofInstant instant (ZoneId/of "Asia/Tokyo")))

(comment
  (Instant/now)
  (instant->datetime (Instant/now)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn milli->datetime
  "input milli,
   returns LocalDateTime object"
  [milli]
  (-> milli
      Instant/ofEpochMilli
      instant->datetime))

(defn second->datetime
  "input second from epoch,
   returns LocalDateTime object"
  [second]
  (-> (* 1000 second)
      milli->datetime))

(comment
  (let [dtf (DateTimeFormatter/ofPattern "yyyy/MM/dd")]
    (.format dtf (second->datetime 1661330819))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(comment
  (let [dtf (DateTimeFormatter/ofPattern "yyyy-MM-dd")]
    [(.format dtf (second->datetime 1661330819))
     (.format dtf (-> (now-in-second) (second->datetime)))]))

(defn milli->str
  ([n]
   (milli->str "yyyy-MM-dd hh:mm:dd" n))
  ([fmt n]
   (->> (milli->datetime n)
        (.format (DateTimeFormatter/ofPattern fmt)))))

;; FIXME: DRY!
(defn second->str
  ([n]
   (second->str "yyyy-MM-dd hh:mm:dd" n))
  ([fmt n]
   (->> (second->datetime n)
        (.format (DateTimeFormatter/ofPattern fmt)))))

;; WHY T, Z?
(defn str->second
  "2022-09-06T12:34:56Z -> second"
  [s]
  (-> (Instant/parse s)
      .getEpochSecond))

(defn str->milli
  [s]
  (* 1000 (str->second s)))

(comment
  (second->str 1661330819)
  (second->str "yyyy/MM/dd hh:mm a" 1661330819)
  (second->str (now-in-second))
  (str->milli  "2022-09-06T12:34:56Z")
  (str->second "2022-09-06T12:34:56Z"))
