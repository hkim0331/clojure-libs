(ns hkim0331.javatime-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [hkim0331.javatime :refer :all]))

(deftest mill->str-test
  (testing "mill->str"
    (is (= (str (milli->str 0))
           "1970-01-01T09:00"))))

;; (epoch->str "yyyy/MM/dd hh:mm" 1661330819)
