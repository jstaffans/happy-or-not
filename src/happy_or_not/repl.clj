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
;;; ## Number of ratings per hour
;;; 
;;; Basic sanity check: customers should only be leaving ratings during business hours.
;; **

;; @@
(->> social-services-ratings
     ratings-by-hour
     (map (fn [ratings] (count ratings)))
     (bar-chart (range 24)))

;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"8f644aec-d850-42a2-8248-57513dfb1017","values":[{"x":0,"y":0},{"x":1,"y":0},{"x":2,"y":0},{"x":3,"y":0},{"x":4,"y":0},{"x":5,"y":0},{"x":6,"y":0},{"x":7,"y":0},{"x":8,"y":26},{"x":9,"y":39},{"x":10,"y":122},{"x":11,"y":125},{"x":12,"y":156},{"x":13,"y":247},{"x":14,"y":160},{"x":15,"y":126},{"x":16,"y":0},{"x":17,"y":0},{"x":18,"y":0},{"x":19,"y":0},{"x":20,"y":0},{"x":21,"y":0},{"x":22,"y":0},{"x":23,"y":0}]}],"marks":[{"type":"rect","from":{"data":"8f644aec-d850-42a2-8248-57513dfb1017"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"8f644aec-d850-42a2-8248-57513dfb1017","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"8f644aec-d850-42a2-8248-57513dfb1017","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"8f644aec-d850-42a2-8248-57513dfb1017\", :values ({:x 0, :y 0} {:x 1, :y 0} {:x 2, :y 0} {:x 3, :y 0} {:x 4, :y 0} {:x 5, :y 0} {:x 6, :y 0} {:x 7, :y 0} {:x 8, :y 26} {:x 9, :y 39} {:x 10, :y 122} {:x 11, :y 125} {:x 12, :y 156} {:x 13, :y 247} {:x 14, :y 160} {:x 15, :y 126} {:x 16, :y 0} {:x 17, :y 0} {:x 18, :y 0} {:x 19, :y 0} {:x 20, :y 0} {:x 21, :y 0} {:x 22, :y 0} {:x 23, :y 0})}], :marks [{:type \"rect\", :from {:data \"8f644aec-d850-42a2-8248-57513dfb1017\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"8f644aec-d850-42a2-8248-57513dfb1017\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"8f644aec-d850-42a2-8248-57513dfb1017\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; @@

;; @@
