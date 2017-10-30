(ns happy-or-not.util
  (:import [java.time.format DateTimeFormatter]
           [java.time ZonedDateTime]))

(def ^:private formatter DateTimeFormatter/ISO_OFFSET_DATE_TIME)

(defn hour-of-day
  [timestamp]
  (->> timestamp
       (.parse formatter)
       (ZonedDateTime/from)
       (.getHour)))
