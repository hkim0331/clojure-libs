(ns hkim0331.misc
  (:require
   [clojure.string :as str])
  (:import java.util.Base64))

;;; probe
(defn probe
  ([x]
   (probe x #(println "probe:" %)))
  ([x f]
   (f x)
   x))

;;; string abbreviation
(defn abbrev
  "abbreviate string s. default 8 characters.
   if want other than 8, say n, use (abbrev n s)"
  ([s]
   (abbrev 8 s))
  ([n s]
   (let [re (re-pattern (format "^(.{%d}).*" n))]
     (str/replace s re (str "$1" "...")))))

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

(defn digit?
  [c]
  (seq (filter #(= c %) "0123456789")))

(defn small-alpha?
  [c]
  (seq (filter #(= c %) "abcdefghijklmnopqrstuvwxyz")))

(defn large-alpha?
  [c]
  (seq (filter #(= c %) "ABCDEFGHIJKLMNOPQRSTUVWXYZ")))

(defn alpha?
  [c]
  (or (small-alpha? c) (large-alpha? c)))

(defn- expand-1
  [[[c] coll]]
  (let [n (-> (apply str coll) Integer/parseInt)]
    (->> (repeat n c) (apply str))))

(defn expand
  "retrieve original message from compressed one"
  [s]
  (->> (partition-by alpha? s)
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
  (filter #(= n %) coll))

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
  (fold-r + 0 (range 10))
  :rcf)


;;; inject
(defn- inject-aux [coll ret]
  (if (empty? coll)
    ret
    (inject-aux (rest coll) (conj ret (* (last ret) (first coll))))))

(defn inject [coll]
  (reduce + (inject-aux coll [1])))
