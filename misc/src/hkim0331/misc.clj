(ns hkim0331.misc
  (:require
   [clojure.string :as str]))

(defn abbrev
  "abbreviate string s. default 8 characters.
   if want other than 8, say n, use (abbrev n s)"
  ([s]
   (abbrev 8 s))
  ([n s]
   (let [re (re-pattern (format "^(.{%d}).*" n))]
     (str/replace s re (str "$1" "...")))))
