
(defn- inject-aux [coll ret]
  (if (empty? coll)
    ret
    (inject-aux (rest coll) (conj ret (* (last ret) (first coll))))))

(defn inject [coll]
  (reduce + (inject-aux coll [1])))
