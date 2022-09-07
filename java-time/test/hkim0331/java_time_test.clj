(ns hkim0331.java-time-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [hkim0331.java-time :refer :all]))

(deftest datetime->milli-test
  (testing "datetime->milli"
    (is (= 1 1))))

;; not. differ 9 hours
(deftest epoch-datetime
  (testing "epoch-datetime relation"
    (let [e  (now-in-epoch)
          s  (epoch->datetime e)
          e2 (datetime->epoch s)]
      (is (zero? (- e e2))))))
