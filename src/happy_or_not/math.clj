(ns happy-or-not.math
  (:import [org.apache.commons.math3.stat.inference ChiSquareTest]))

(defn chi-square-test
  "Performs Pearson's Chi Square Test, returns p-value"
  [expected observed]
  (let [test (ChiSquareTest.)]
    (.chiSquareTest test (double-array expected) (long-array observed))))
