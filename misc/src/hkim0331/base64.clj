(import java.util.Base64)

;; (defn ->base64 [s]
;;   (.encodeToString (Base64/getEncoder) (.getBytes s)))

(defn ->base64
  [s]
  (->> s
       (.getBytes s)
       (.encodeToString (Base64/getEncoder))))

;; (->base64 "abc")

(defn base64->
  [s]
  (->> s
       (.decode (Base64/getDecoder))
       (map char)
       (apply str)))

;; (base64-> (->base64 "Hello, World!"))


