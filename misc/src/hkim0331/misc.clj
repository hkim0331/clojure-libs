(ns hkim0331.misc
  (:require
   [clojure.string :as str])
  (import java.util.Base64))

;;; string abbreviation
(defn abbrev
  "abbreviate string s. default 8 characters.
   if want other than 8, say n, use (abbrev n s)"
  ([s]
   (abbrev 8 s))
  ([n s]
   (let [re (re-pattern (format "^(.{%d}).*" n))]
     (str/replace s re (str "$1" "...")))))

;;; conversion between `char` and `int`
(defn char->int
  [c]
  (- (int c) (int \0)))

(defn int->char
  [n])

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

