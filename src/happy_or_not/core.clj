(ns happy-or-not.core
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [com.rpl.specter :refer :all]
            [happy-or-not.util :refer :all]))

(defn get-folders []
  (-> "http://front1.hel.ninja/happy-or-not/folders?surveys=true"
      (http/get {:accept :json})
      :body
      (json/parse-string true)))

(def folders (memoize get-folders))

;; Interesting folders are those that actually have surveys
;; attached to them, which aren't that many actually.
;; The most interesting of those are long-running surveys,
;; where we can expect there to be a decent amount of data.
(defn extract-interesting
  [folders]
  (select
   [:folders
    ALL
    :folders
    ALL
    (selected? :surveys vector?)
    (collect-one :name)
    :surveys
    ALL
    ((eachnav collect-one) :key :activePeriods)
    :question
    :name]
   folders))

(extract-interesting (folders))


(defn get-ratings
  [key]
  (-> "http://front1.hel.ninja/happy-or-not/surveys/%d/rawresults"
      (format key)
      (http/get {:accept :json})
      :body
      (json/parse-string true)))

(def ratings (memoize get-ratings))

(defn ratings-by-hour
  [ratings]
  (let [results (into [] (repeat 24 []))]
    (->> ratings
         :data
         (map (fn [{:keys [ts r]}] [(hour-of-day ts) r]))
         (reduce
          (fn [acc [h r]]
            (update acc h #(conj % r)))
          results))))
