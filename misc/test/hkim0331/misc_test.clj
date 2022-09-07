(ns hkim0331.misc-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [hkim0331.misc :refer :all]))

(deftest abbrev-test
  (testing "testing abbrev"
    (is (= "" (abbrev "")))
    (is (= "1234567" (abbrev "1234567")))
    (is (= "12345678..." (abbrev "12345678...")))))

(deftest qsort-test
  (testing "test qsort"
    (is (= [] (qsort [])))
    (is (= (range 10) (qsort (reverse (range 10)))))
    (is (= true (ordered? (range 10))))))
