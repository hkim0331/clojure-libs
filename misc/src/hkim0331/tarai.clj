(ns hkim0331.tarai)

(def ^:private version "0.0.1")

(defn tarai
  [x y z]
  (if (<= x y)
    y
    (tarai (tarai (dec x) y z)
           (tarai (dec y) z x)
           (tarai (dec z) x y))))

;; 戻り値が違う tak 関数
(defn tak [x y z]
  (if (<= x y)
    z
    (tak (tak (dec x) y z)
         (tak (dec y) z x)
         (tak (dec z) x y))))


;; nuc で実験。コンテナはひどく遅い。
(comment
  (time (tarai 15 7 0)))

;; m64:
;; "Elapsed time: 10836.377334 msecs"
;; "Elapsed time: 10769.010792 msecs"
;; m3:
;;  "Elapsed time: 10744.901625 msecs"
;; docker-compose up
;;  "Elapsed time: 193561.018473 msecs"
;; m2:
;;  "Elapsed time: 12051.361667 msecs"
;;  "Elapsed time: 12050.578041 msecs"
;;  "Elapsed time: 12041.758583 msecs"
;; nuc:
;;  "Elapsed time: 34066.433117 msecs"

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
                      (swap! memo assoc [x y z] result)
                      result))))]
      (tarai x y z))))

(time (memoized-tarai 15 7 0))
;; "Elapsed time: 1.675791 msecs"

;; lazy function
(defn lazy-tarai [x y z]
  (letfn [(tarai [fx fy fz]
            (if (<= (fx) (fy))
              (fy)
              (tarai (fn [] (tarai (fn [] (- (fx) 1)) fy fz))
                     (fn [] (tarai (fn [] (- (fy) 1)) fz fx))
                     (fn [] (tarai (fn [] (- (fz) 1)) fx fy)))))]
    (tarai (fn [] x) (fn [] y) (fn [] z))))


(time (lazy-tarai 15 7 0))
;; "Elapsed time: 1.012375 msecs"


