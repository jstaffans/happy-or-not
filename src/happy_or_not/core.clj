(ns happy-or-not.core
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [com.rpl.specter :refer :all]))

(defn get-folders []
  (-> "http://front1.hel.ninja/happy-or-not/folders?surveys=true"
      (http/get {:accept :json})
      :body
      (json/parse-string true)))

(def folders (memoize get-folders))

(def interesting-surveys
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
