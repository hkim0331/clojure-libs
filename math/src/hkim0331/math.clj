(ns hkim0331.math)

;; sq
(defn sq
  "returns n^2"
  [n]
  (* n n))

;; sqrt, Newton Raphson method

;; power
(defn- pow
  "returns b's power of n. n must be positive."
  [b n]
  (cond
    (zero? n) 1N
    (even? n) (sq (pow b (/ n 2)))
    :else (* b (pow b (dec n)))))

(defn power
  ""
  [b n]
  (cond
    (pos? n) (pow b n)
    (zero? n) 1
    :else (/ 1 (pow b (- n)))))

(comment
  (power 2 0)
  (power 2 10)
  (power 2 100))

;; digits
(defn digits
  "Returns digits in positive integer n. "
  [n]
  (if (< n 10)
    1
    (+ 1 (digits (/ n 10)))))

(comment
  (digits 12345)
  (digits (power 10 10)))

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
  (let [inc2 (comp inc inc)]
    (every? #(pos? (mod n %))
            (take-while #(<= (* % %) n) (iterate inc2 3)))))

(defn prime?
  "n is prime?"
  [n]
  (cond
    (< n 3) (= n 2)
    (even? n) false
    :else (prime?-aux n)))

(defn prime
  "Returns nth prime number"
  [n]
  (->> primes
       (take n)
       last))

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
  "factor integer of n"
  [n]
  (factor-aux n []))

;; combinations
(defn combinations
  "choose n items from disjointed collection `coll`"
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
