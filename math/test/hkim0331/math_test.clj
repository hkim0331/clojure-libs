(ns hkim0331.math-test
  (:require
   [clojure.test :refer :all]
   [hkim0331.math :refer :all]))

(deftest power-test
  (testing "test math"
    (is (= 0 (sq 0)))
    (is (= 4 (sq -2)))
    (is (= 4 (sq 2)))
    (is (= 1 (power 2 0)))
    (is (= 1024 (power 2 10)))))

(deftest digits-test
  (testing "test digits"
    (is (= 1 (digits 0)))
    (is (= 5 (digits 12345)))
    (is (= 20 (digits (power 10 19))))))

(deftest prime?-test
  (testing "test prime?"
    (is (= false (prime? 1)))
    (is (= true (prime? 2)))
    (is (= true (prime? 3)))))

(deftest factor-test
  (testing "test factor-integer"
    (is (= []  (factor 1)))
    (is (= [2] (factor 2)))
    (is (= [2 2 2 2 2 2 2 2 2 2] (factor 1024)))
    (is (= [3 3 3607 3803] (factor 123456789)))))

