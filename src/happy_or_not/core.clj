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
(def interesting-folders
  (->> (folders)
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
         :name])))

(defn get-survey-results
  [key]
  (-> "http://front1.hel.ninja/happy-or-not/surveys/%d/rawresults"
      (format key)
      (http/get {:accept :json})
      :body
      (json/parse-string true)))

(def survey-results (memoize get-survey-results))
