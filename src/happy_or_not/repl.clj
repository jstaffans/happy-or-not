;; gorilla-repl.fileformat = 1

;; **
;;; # Happy or not
;;; 
;;; 
;; **

;; @@
(ns happy-or-not.repl
  (:require [gorilla-repl.table :refer [table-view]]
            [gorilla-plot.core :refer :all]
            [happy-or-not.core :refer :all]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Find interesting data
;;; 
;;; The data set contains entities such as hospitals, day care centers and social services bureaus. Not all of them seem to 
;;; be collecting customer satisfaction ratings, though. Find those that do!
;;; 
;;; Since this a bit more involved, the data loading and transformation happens in the `happy-or-not.core` namespace.
;; **

;; @@
(-> (folders)
    extract-interesting
    (table-view :columns [:place :key :timespan :question]))
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><th>","close":"</th></tr>","separator":"</th><th>","items":[{"type":"html","content":"<span class='clj-keyword'>:place</span>","value":":place"},{"type":"html","content":"<span class='clj-keyword'>:key</span>","value":":key"},{"type":"html","content":"<span class='clj-keyword'>:timespan</span>","value":":timespan"},{"type":"html","content":"<span class='clj-keyword'>:question</span>","value":":question"}],"value":"[:place :key :timespan :question]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nuorten sosiaalityö&quot;</span>","value":"\"Nuorten sosiaalityö\""},{"type":"html","content":"<span class='clj-unkown'>134386</span>","value":"134386"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:start</span>","value":":start"},{"type":"html","content":"<span class='clj-string'>&quot;2016-01-15T00:00:00.000+02:00&quot;</span>","value":"\"2016-01-15T00:00:00.000+02:00\""}],"value":"[:start \"2016-01-15T00:00:00.000+02:00\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:end</span>","value":":end"},{"type":"html","content":"<span class='clj-string'>&quot;2030-01-02T00:00:00.000+02:00&quot;</span>","value":"\"2030-01-02T00:00:00.000+02:00\""}],"value":"[:end \"2030-01-02T00:00:00.000+02:00\"]"}],"value":"{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}"}],"value":"[{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}]"},{"type":"html","content":"<span class='clj-string'>&quot;Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?&quot;</span>","value":"\"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\""}],"value":"[\"Nuorten sosiaalityö\" 134386 [{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}] \"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\"]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-string'>&quot;Päiväkoti Kotikallio&quot;</span>","value":"\"Päiväkoti Kotikallio\""},{"type":"html","content":"<span class='clj-unkown'>97128</span>","value":"97128"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:start</span>","value":":start"},{"type":"html","content":"<span class='clj-string'>&quot;2015-07-06T00:00:00.000+03:00&quot;</span>","value":"\"2015-07-06T00:00:00.000+03:00\""}],"value":"[:start \"2015-07-06T00:00:00.000+03:00\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:end</span>","value":":end"},{"type":"html","content":"<span class='clj-string'>&quot;2015-09-06T00:00:00.000+03:00&quot;</span>","value":"\"2015-09-06T00:00:00.000+03:00\""}],"value":"[:end \"2015-09-06T00:00:00.000+03:00\"]"}],"value":"{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}"}],"value":"[{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}]"},{"type":"html","content":"<span class='clj-string'>&quot;Oliko sinulla kiva päivä päiväkodissa?&quot;</span>","value":"\"Oliko sinulla kiva päivä päiväkodissa?\""}],"value":"[\"Päiväkoti Kotikallio\" 97128 [{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}] \"Oliko sinulla kiva päivä päiväkodissa?\"]"}],"value":"#gorilla_repl.table.TableView{:contents [[\"Nuorten sosiaalityö\" 134386 [{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}] \"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\"] [\"Päiväkoti Kotikallio\" 97128 [{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}] \"Oliko sinulla kiva päivä päiväkodissa?\"]], :opts (:columns [:place :key :timespan :question])}"}
;; <=

;; **
;;; OK, so the "Nuorten sosiaalityö" is the only one that's really interesting, since it has long-enough timespan. This is a youth social services bureau.
;; **

;; @@
(def social-services-ratings (ratings 134386))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/social-services-ratings</span>","value":"#'happy-or-not.repl/social-services-ratings"}
;; <=

;; **
;;; ## Ratings per hour of day
;;; 
;; **

;; **
;;; Define "happy" to be a rating of 3-4 on a 1-4 scale. Calculate happy ratio for each hour of the day.
;; **

;; @@
(defn is-happy? 
  [rating]
  (>= rating 3))

(defn hour-stats
  "Basic statistics about ratings given during a certain hour."
  [hour-ratings]
  (let [n (count hour-ratings)]
    (when (> n 0)
      {:n           n
       :happy-ratio (/ (->> hour-ratings (filter is-happy?) count) n)})))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/hour-stats</span>","value":"#'happy-or-not.repl/hour-stats"}
;; <=

;; @@
(def stats (->> social-services-ratings
                ratings-by-hour
                (map hour-stats)
                (drop 8)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/stats</span>","value":"#'happy-or-not.repl/stats"}
;; <=

;; @@
(->> stats
     (map :n)
     (bar-chart (range 8 16)))

;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"1d78686a-18b5-407a-9fdf-8455c0165fd8","values":[{"x":8,"y":26},{"x":9,"y":39},{"x":10,"y":122},{"x":11,"y":125},{"x":12,"y":156},{"x":13,"y":247},{"x":14,"y":160},{"x":15,"y":126}]}],"marks":[{"type":"rect","from":{"data":"1d78686a-18b5-407a-9fdf-8455c0165fd8"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"1d78686a-18b5-407a-9fdf-8455c0165fd8","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"1d78686a-18b5-407a-9fdf-8455c0165fd8","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"1d78686a-18b5-407a-9fdf-8455c0165fd8\", :values ({:x 8, :y 26} {:x 9, :y 39} {:x 10, :y 122} {:x 11, :y 125} {:x 12, :y 156} {:x 13, :y 247} {:x 14, :y 160} {:x 15, :y 126})}], :marks [{:type \"rect\", :from {:data \"1d78686a-18b5-407a-9fdf-8455c0165fd8\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"1d78686a-18b5-407a-9fdf-8455c0165fd8\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"1d78686a-18b5-407a-9fdf-8455c0165fd8\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; @@
(->> stats
     (map :happy-ratio)
     (bar-chart (range 8 16)))

;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6","values":[{"x":8,"y":0.8461538461538461},{"x":9,"y":0.7948717948717949},{"x":10,"y":0.7704918032786885},{"x":11,"y":0.736},{"x":12,"y":0.7948717948717949},{"x":13,"y":0.7854251012145749},{"x":14,"y":0.84375},{"x":15,"y":0.888888888888889}]}],"marks":[{"type":"rect","from":{"data":"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6\", :values ({:x 8, :y 11/13} {:x 9, :y 31/39} {:x 10, :y 47/61} {:x 11, :y 92/125} {:x 12, :y 31/39} {:x 13, :y 194/247} {:x 14, :y 27/32} {:x 15, :y 8/9})}], :marks [{:type \"rect\", :from {:data \"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"8a5c7c30-6119-4b3d-81a2-8dcc1dea58c6\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; **
;;; H0 (null hypothesis): the hour of day should make no difference for rating.
;;; TODO: calculate P values for each hour to see if any difference is significant.
;; **

;; @@

;; @@
