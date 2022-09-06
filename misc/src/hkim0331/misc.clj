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

;;; qsort
(defn- smaller
  "returns items smaller than n in coll"
  [n coll]
  (filter #(< % n) coll))

(defn- larger
  "returns items larger than n in coll"
  [n coll]
  (filter #(< n %) coll))

(defn- eqls
  "returns items equal to n in coll"
  [n coll]
  (filter (partial = n) coll))

(defn qsort
  "quick-sort coll"
  [coll]
  (if (seq coll)
    (let [p (first coll)]
      (concat (qsort (smaller p coll))
              (eqls p coll)
              (qsort (larger p coll))))
    []))

(defn ordered?
  "is coll sorted? clojure has `sorted?`
   so this function's name becomes `orderd?"
  [coll]
  (->> coll
       (partition 2 1)
       (map #(<= (first %) (second %)))
       (every? true?)))

;;; fold
;; https://stackoverflow.com/questions/16800255/how-do-we-do-both-left-and-right-folds-in-clojure
(defn fold-l
  [f val coll]
  (if (empty? coll)
    val
    (fold-l f (f val (first coll)) (rest coll))))

(defn fold-r
  [f val coll]
  (if (empty? coll)
    val
    (f (fold-r f val (rest coll)) (first coll))))

(comment
  (fold-l + 0 (range 10))
  (fold-r + 0 (range 10)))

;;; inject
(defn- inject-aux [coll ret]
  (if (empty? coll)
    ret
    (inject-aux (rest coll) (conj ret (* (last ret) (first coll))))))

(defn inject [coll]
  (reduce + (inject-aux coll [1])))
