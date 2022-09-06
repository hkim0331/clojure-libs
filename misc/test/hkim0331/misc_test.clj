(ns hkim0331.misc-test
  (:require
   [clojure.test :refer :all]
   [hkim0331.misc :refer :all]))

(deftest abbrev-test
  (testing "testing abbrev"
    (is (= "" (abbrev "")))
    (is (= "1234567" (abbrev "1234567")))
    (is (= "12345678..." (abbrev "12345678...")))))

