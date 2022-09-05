(ns hkim0331.tarai)

(defn tarai
 [x y z]
 (if (<= x y)
   y
   (tarai (tarai (dec x) y z)
          (tarai (dec y) z x)
          (tarai (dec z) x y))))

;; nuc で実験。コンテナはひどく遅い。
(comment
  (time (tarai 15 7 0)))
;;"Elapsed time: 28853.187034 msecs"
;; docker-compose up
;;"Elapsed time: 193561.018473 msecs"


(comment
  (time (tarai 15 8 0)))
;; m2:
;;"Elapsed time: 12051.361667 msecs"
;;"Elapsed time: 12050.578041 msecs"
;;"Elapsed time: 12041.758583 msecs"
;; nuc:
;;"Elapsed time: 34066.433117 msecs"

;;(time (tarai 14 7 0))
;; clj : 1883msec
;; cljs: 2665msec

;; openjdk@11
;;(time (my.tarai/tarai 14 7 0))
;;"Elapsed time: 1665.51025 msecs"
;;14

;; docker compose
;; (time (my.tarai/tarai 14 7 0))
;; "Elapsed time: 1671.123333 msecs"
;; 14

(defn tak [x y z]
  (if (<= x y)
    z
    (tak (tak (dec x) y z)
         (tak (dec y) z x)
         (tak (dec z) x y))))

;;(time (tak 12 6 0))

;; memoized
(defn memoized-tarai [x y z]
  (let [memo (atom {})]
    (letfn [(tarai [x y z]
              (or (get @memo [x y z])
                  (if (<= x y)
                    y
                    (let [result (tarai (tarai (- x 1) y z)
                                        (tarai (- y 1) z x)
                                        (tarai (- z 1) x y))]
                      (do (swap! memo assoc [x y z] result)
                          result)))))]
      (tarai x y z))))

;;(time (memoized-tarai 14 7 0))

;; lazy function
(defn lazy-tarai [x y z]
  (letfn [(tarai [fx fy fz]
            (if (<= (fx) (fy))
              (fy)
              (tarai (fn [] (tarai (fn [] (- (fx) 1)) fy fz))
                     (fn [] (tarai (fn [] (- (fy) 1)) fz fx))
                     (fn [] (tarai (fn [] (- (fz) 1)) fx fy)))))]
    (tarai (fn [] x) (fn [] y) (fn [] z))))

(comment
  (time (lazy-tarai 14 7 0)))



