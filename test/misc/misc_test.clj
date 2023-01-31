(ns misc.misc-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [misc.misc :refer :all]))

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

(deftest compress-test
  (testing "test compress/expand"
    (let [s1 ""
          s2 "aaaaaaaaabbbbbbbdacccccccc"
          s3 "abcabcdef"]
      (is (= s1 (expand (compress s1))))
      (is (= s2 (expand (compress s2))))
      (is (= s3 (expand (compress s3)))))))
