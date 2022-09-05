(ns hkim0331.qsort-test
  (:require
   [clojure.test :refer :all]
   [hkim0331.qsort :refer [qsort ordered?]]))

(deftest qsort-test
  (testing "test qsort"
    (is (= [] (qsort [])))
    (is (= (range 10) (qsort (reverse (range 10)))))
    (is (= true (ordered? (range 10))))))
