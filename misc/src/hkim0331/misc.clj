(ns hkim0331.misc
  (:require
   [clojure.string :as str])
  (:import java.util.Base64))

;;; string abbreviation
(defn abbrev
  "abbreviate string s. default 8 characters.
   if want other than 8, say n, use (abbrev n s)"
  ([s]
   (abbrev 8 s))
  ([n s]
   (let [re (re-pattern (format "^(.{%d}).*" n))]
     (str/replace s re (str "$1" "...")))))

;; conversion between `char` and `int`
;;
;; no use!
;;
;; (defn char->int
;;   [c]
;;   (int c))
;;
;; (defn int->char
;;   [n]
;;   (char n))

;;; Base 64
(defn ->base64
  [s]
  (->> s
       (.getBytes s)
       (.encodeToString (Base64/getEncoder))))

(defn base64->
  [s]
  (->> s
       (.decode (Base64/getDecoder))
       (map char)
       (apply str)))

;; compress/expand
(defn compress
  "compress message
   aaa=> a3
   aaabbccc => a3b2c3
   etc."
  [s]
  (->> s
       (partition-by identity)
       (map #(str (first %) (count %)))
       (apply str)))

(defn- expand-1
  [[[c] [n]]]
  (->> (repeat (char->int n) c)
      (apply str)))

(defn expand
  "retrieve original message from compressed one"
  [s]
  (->> s
       (partition-by digit?)
       (partition 2)
       (map expand-1)
       (apply str)))
