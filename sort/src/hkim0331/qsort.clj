(ns hkim0331.qsort)

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

(comment
  (rand-int 10)
  (let [in (take 10 (repeatedly #(rand-int 10)))
        out (qsort in)]
    (when-not (ordered? out)
      out)))

