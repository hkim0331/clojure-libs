(ns hkim0331.javatime-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [hkim0331.javatime :refer :all]))

;; WHY?
(deftest milli->str-test
  (testing "milli->str"
    (is (= (str (milli->str 0))
           "1970-01-01 09:00:01"))))
