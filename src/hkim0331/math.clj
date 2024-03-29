(ns hkim0331.math
  (:require
   [clojure.math :as math]))

;; sq
(defn sq
  "returns n^2"
  [n]
  (* n n))

;; sqrt, Newton Raphson method

;;; clojure.math/power
(defn- pow
  "returns b's power of n. n must be positive."
  [b n]
  (cond
    (zero? n) 1N
    (even? n) (sq (pow b (/ n 2)))
    :else (* b (pow b (dec n)))))

(defn power
  "rewturns b's power of n."
  [b n]
  (cond
    (pos? n) (pow b n)
    (zero? n) 1
    :else (/ 1 (pow b (- n)))))

;; digits
(defn digits
  "Returns digits in positive integer n. "
  [n]
  (if (< n 10)
    1
    (+ 1 (digits (/ n 10)))))

;; primes
;; Excerpted from "Programming Clojure, Third Edition",
;; published by The Pragmatic Bookshelf.
;; Copyrights apply to this code. It may not be used to create training material,
;; courses, books, articles, and the like. Contact us if you are in doubt.
;; We make no guarantees that this code is fit for any purpose.
;; Visit http://www.pragmaticprogrammer.com/titles/shcloj3 for more book information.
(def primes
  (concat
   [2 3 5 7]
   (lazy-seq
    (let [primes-from
          (fn primes-from [n [f & r]]
            (if (some #(zero? (rem n %))
                      (take-while #(<= (* % %) n) primes))
              (recur (+ n f) r)
              (lazy-seq (cons n (primes-from (+ n f) r)))))
          wheel (cycle [2 4 2 4 6 2 6 4 2 4 6 6 2 6  4  2
                        6 4 6 8 4 2 4 2 4 8 6 4 6 2  4  6
                        2 6 6 4 2 4 6 2 6 4 2 4 2 10 2 10])]
      (primes-from 11 wheel)))))

(defn- prime?-aux
  [n]
  (every? #(pos? (mod n %))
          (take-while #(<= (* % %) n) (iterate #(+ 2 %) 3))))

(defn prime?
  "n is prime?"
  [n]
  (cond
    (< n 3) (= n 2)
    (even? n) false
    :else (prime?-aux n)))

(defn prime
  "Returns nth prime number."
  [n]
  (->> primes (take n) last))

;; how to be lazy?
;; 2021-02-14
(defn- factor-odd
  [n m ret]
  (cond
    (= n 1) ret
    (zero? (mod n m)) (recur (/ n m) m (conj ret m))
    :else (recur n (+ m 2) ret)))

(defn- factor-aux
  [n ret]
  (if (even? n)
    (factor-aux (/ n 2) (conj ret 2))
    (factor-odd n 3 ret)))

(defn factor-integer
  "Factor integer of n"
  [n]
  (factor-aux n []))

;; combinations
(defn combinations
  "Choose n items from disjointed collection `coll`."
  [coll n]
  (cond
    (or (empty? coll) (zero? n)) []
    (= 1 n) (map vector coll)
    :else (concat
           (map #(conj % (first coll))
                (combinations (rest coll) (dec n)))
           (combinations (rest coll) n))))

(comment
  (combinations [1 2 3] 2)
  (combinations [1 2 3 4] 2))

;; mode
(defn mode [xs]
  (->> xs
       sort
       (partition-by identity)
       (sort-by count)
       last
       first))

;; 2022-11-23
(defn square? [n]
  (->> (factor-integer n)
       (partition-by identity)
       (map count)
       (every? even?)))

;;(time (square? 1429822969))
;; "Elapsed time: 2.077208 msecs"
;; "Elapsed time: 2.043958 msecs"
;; "Elapsed time: 1.701917 msecs"
;; "Elapsed time: 1.045583 msecs"
;; (time (square? (+ 1 (* 1024 1024))))
;; (time (square? (+ 1 (* 1024 1024))))
;; "Elapsed time: 14.522792 msecs"
;;

(defn is-square [n]
  "using math/sqare, judge n is square or not."
  (let [m (math/sqrt n)]
    (some true? (map #(= n (* % %)) (range 0 (inc m))))))
;;(time (is-square (* 1024 1024)))
;; "Elapsed time: 0.397209 msecs"
;;(time (is-square (+ 1 (* 1024 1024))))
;; "Elapsed time: 0.388208 msecs"
;; nil

;;(time (is-square 1429822969))

;; mode
(defn mode [xs]
  (->> xs
       sort
       (partition-by identity)
       (sort-by count)
       last
       first))

;; Py99-107

(defn power-sum [n m]
  (apply + (map #(pow n %) (range (inc m)))))

;;(power-sum 2 7)

(->> (factor-integer 2095632000)
     (partition-by identity)
     (map (fn [x] [(first x) (count x)]))
     (map (fn [[n m]] (power-sum n m)))
     (reduce *))

(defn sq? [n]
  (let [m (->> (iterate inc 1)
               (drop-while #(< (* % %) n))
               first)]
    (= (* m m) n)))

(filter sq? (range 1000))
(time (sq? (* 1024 1024)))
(time (sq? (inc (* 1024 1024))))

(defn gcd [x y]
  (if (zero? y)
    x
    (gcd y (mod x y))))

(defn gcd-all [xs]
  (reduce gcd xs))


(defn lcm [x y]
  (/ (* x y) (gcd x y)))

(comment
  (gcd-all [6 7 8 10])

  (reduce * (range 1 11))
  (= (reduce lcm (range 1 21))
     (* 2 3 2 5 7 2 3 11 13 2 17 19))
  (reduce lcm (range 1 31))
  :rcf)

