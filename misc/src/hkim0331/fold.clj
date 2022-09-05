(ns hkim0331.misc.fold)

;; https://stackoverflow.com/questions/16800255/how-do-we-do-both-left-and-right-folds-in-clojure

(defn fold-l [f val coll]
  (if (empty? coll)
    val
    (fold-l f (f val (first coll)) (rest coll))))

(comment
  (fold-l + 0 (range 100)))

(defn fold-r [f val coll]
  (if (empty? coll)
    val
    (f (fold-r f val (rest coll)) (first coll))))

(comment
  (fold-r + 0 (range 1000)))
