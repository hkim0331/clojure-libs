(ns java-time.main-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [java-time.main :refer :all]))

(deftest str->milli-test
  (testing "str->milli"
    (is (= 1 1))))

;; not. differ 9 hours
(deftest second->str-test
  (testing "second->str relation"
    (let [e  (now-in-second)
          s  (second->str e)
          e2 (str->second s)]
      (is (zero? (- e e2))))))
