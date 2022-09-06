(ns hkim0331.javatime-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [hkim0331.javatime :refer :all]))

(deftest mill->datetime-test
  (testing "mill->datetime"
    (is (= (str (milli->datetime 0))
           "1970-01-01T09:00"))))

;; (epoch->str "yyyy/MM/dd hh:mm" 1661330819)
