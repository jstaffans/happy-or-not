(ns happy-or-not.core
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [com.rpl.specter :refer :all]
            [happy-or-not.util :refer :all]))

(defn get-folders
  "Get customer satisfaction data from a Helsinki Region Infoshare API."
  []
  (-> "http://front1.hel.ninja/happy-or-not/folders?surveys=true"
      (http/get {:accept :json})
      :body
      (json/parse-string true)))

(def folders (memoize get-folders))

(defn extract-interesting
  "Go through raw data looking for folders that actually have
   surveys attached to them."
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

(defn get-ratings
  "Get survey results for a given survey key from the API."
  [key]
  (-> "http://front1.hel.ninja/happy-or-not/surveys/%d/rawresults"
      (format key)
      (http/get {:accept :json})
      :body
      (json/parse-string true)))

(def ratings (memoize get-ratings))

(defn ratings-by-hour
  "Group ratings by the hour of the day. The result is a vector with 24 elements."
  [ratings]
  (let [results (into [] (repeat 24 []))]
    (->> ratings
         :data
         (map (fn [{:keys [ts r]}] [(hour-of-day ts) r]))
         (reduce
          (fn [acc [h r]]
            (update acc h #(conj % r)))
          results))))
