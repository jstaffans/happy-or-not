;; gorilla-repl.fileformat = 1

;; **
;;; # Happy or not
;;; 
;;; An exploration of customer satisfaction data available through the Helsinki Region Infoshare open data platform.
;; **

;; @@
(ns happy-or-not.repl
  (:require [gorilla-repl.table :refer [table-view]]
            [gorilla-plot.core :refer :all]
            [happy-or-not.core :refer :all]
            [happy-or-not.math :refer :all]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Find interesting data
;;; 
;;; The data set contains entities such as hospitals, day care centers and social services bureaus. Not all of them seem to 
;;; be collecting customer satisfaction ratings, so we need to find those that do.
;;; 
;;; Since this a bit more involved, the data retrieval and transformation happens in the `happy-or-not.core` namespace. I think this is a pretty common pattern for using Gorilla REPL: do the heavy lifting behind the scenes in a Clojure namespace and save the Gorilla REPL session for data exploration.
;;; 
;;; The `extract-interesting` function will return a two-dimensional vector that can be displayed using Gorilla REPL's `table-view` function.
;; **

;; @@
(-> (folders)
    extract-interesting
    (table-view :columns [:place :key :timespan :question]))
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><th>","close":"</th></tr>","separator":"</th><th>","items":[{"type":"html","content":"<span class='clj-keyword'>:place</span>","value":":place"},{"type":"html","content":"<span class='clj-keyword'>:key</span>","value":":key"},{"type":"html","content":"<span class='clj-keyword'>:timespan</span>","value":":timespan"},{"type":"html","content":"<span class='clj-keyword'>:question</span>","value":":question"}],"value":"[:place :key :timespan :question]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nuorten sosiaalityö&quot;</span>","value":"\"Nuorten sosiaalityö\""},{"type":"html","content":"<span class='clj-unkown'>134386</span>","value":"134386"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:start</span>","value":":start"},{"type":"html","content":"<span class='clj-string'>&quot;2016-01-15T00:00:00.000+02:00&quot;</span>","value":"\"2016-01-15T00:00:00.000+02:00\""}],"value":"[:start \"2016-01-15T00:00:00.000+02:00\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:end</span>","value":":end"},{"type":"html","content":"<span class='clj-string'>&quot;2030-01-02T00:00:00.000+02:00&quot;</span>","value":"\"2030-01-02T00:00:00.000+02:00\""}],"value":"[:end \"2030-01-02T00:00:00.000+02:00\"]"}],"value":"{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}"}],"value":"[{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}]"},{"type":"html","content":"<span class='clj-string'>&quot;Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?&quot;</span>","value":"\"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\""}],"value":"[\"Nuorten sosiaalityö\" 134386 [{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}] \"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\"]"}],"value":"#gorilla_repl.table.TableView{:contents [[\"Nuorten sosiaalityö\" 134386 [{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}] \"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\"]], :opts (:columns [:place :key :timespan :question])}"}
;; <=

;; **
;;; OK, so the "Nuorten sosiaalityö" is the only one that's really interesting, since it has long-enough timespan. For those who don't speak Finnish, this seems to be a youth social services bureau. We can now employ another function, `ratings`, to retrieve the actual ratings from the API.
;; **

;; @@
(def social-services-ratings (ratings 134386))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/social-services-ratings</span>","value":"#'happy-or-not.repl/social-services-ratings"}
;; <=

;; **
;;; Let's see what the data looks like:
;; **

;; @@
(keys social-services-ratings)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>(:meta :data)</span>","value":"(:meta :data)"}
;; <=

;; @@
(->> social-services-ratings :data (take 10) clojure.pprint/pprint)
;; @@
;; ->
;;; ({:ts &quot;2016-01-18T12:37:05.000+02:00&quot;, :r 3}
;;;  {:ts &quot;2016-01-18T13:37:30.000+02:00&quot;, :r 3}
;;;  {:ts &quot;2016-01-19T15:28:22.000+02:00&quot;, :r 2}
;;;  {:ts &quot;2016-01-22T14:37:32.000+02:00&quot;, :r 3}
;;;  {:ts &quot;2016-01-25T11:09:57.000+02:00&quot;, :r 3}
;;;  {:ts &quot;2016-01-25T14:14:37.000+02:00&quot;, :r 3}
;;;  {:ts &quot;2016-01-25T14:14:39.000+02:00&quot;, :r 2}
;;;  {:ts &quot;2016-01-26T13:21:49.000+02:00&quot;, :r 3}
;;;  {:ts &quot;2016-01-27T12:37:56.000+02:00&quot;, :r 0}
;;;  {:ts &quot;2016-01-27T13:26:54.000+02:00&quot;, :r 0})
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Ratings per hour of day
;;; 
;; **

;; **
;;; Define "happy" to be a rating of 2-3 on a 0-3 scale. Calculate happy ratio, ie the "mean happiness", for each hour of the working day, which is 8 am and 4 pm for this particular agency.
;; **

;; @@
(defn is-happy? 
  "Reduce the 0-3 ratings to two categories: happy or not happy."
  [rating]
  (>= rating 2))

(defn group-stats
  "Basic statistics about ratings in a given group."
  [group-ratings]
  (let [n (count group-ratings)
        happy-n (->> group-ratings (filter is-happy?) count)]
    (when (> n 0)
      {:n           n
       :happy-n     happy-n
       :happy-ratio (float (/ happy-n n))})))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/group-stats</span>","value":"#'happy-or-not.repl/group-stats"}
;; <=

;; @@
(def work-day (range 8 16))

(def stats (->> social-services-ratings
                ;; ratings-by-hour is another function in the happy-or-not.core namespace
                ;; which groups the ratings by the hour of the day.
                ratings-by-hour
                (map group-stats)
                ;; only data for 8-16 (working hours)
                (drop 8)   
                (take 8)))

(def population-mean (/ (->> stats
                             (map :happy-n)
                             (reduce +))
                        (->> stats
                             (map :n)
                             (reduce +))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/population-mean</span>","value":"#'happy-or-not.repl/population-mean"}
;; <=

;; **
;;; Let's explore the data a bit.
;; **

;; @@
;; number of responses per hour of the work day
(bar-chart work-day (map :n stats))

;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"28a68678-47fd-4fbf-9de3-89eded252763","values":[{"x":8,"y":26},{"x":9,"y":41},{"x":10,"y":125},{"x":11,"y":130},{"x":12,"y":157},{"x":13,"y":253},{"x":14,"y":162},{"x":15,"y":128}]}],"marks":[{"type":"rect","from":{"data":"28a68678-47fd-4fbf-9de3-89eded252763"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"28a68678-47fd-4fbf-9de3-89eded252763","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"28a68678-47fd-4fbf-9de3-89eded252763","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"28a68678-47fd-4fbf-9de3-89eded252763\", :values ({:x 8, :y 26} {:x 9, :y 41} {:x 10, :y 125} {:x 11, :y 130} {:x 12, :y 157} {:x 13, :y 253} {:x 14, :y 162} {:x 15, :y 128})}], :marks [{:type \"rect\", :from {:data \"28a68678-47fd-4fbf-9de3-89eded252763\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"28a68678-47fd-4fbf-9de3-89eded252763\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"28a68678-47fd-4fbf-9de3-89eded252763\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; @@
;; happiness proportion per hour of the work day
(bar-chart work-day (map :happy-ratio stats))

;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f","values":[{"x":8,"y":0.96153843},{"x":9,"y":0.85365856},{"x":10,"y":0.872},{"x":11,"y":0.8384615},{"x":12,"y":0.9235669},{"x":13,"y":0.88142294},{"x":14,"y":0.9382716},{"x":15,"y":0.9453125}]}],"marks":[{"type":"rect","from":{"data":"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f\", :values ({:x 8, :y 0.96153843} {:x 9, :y 0.85365856} {:x 10, :y 0.872} {:x 11, :y 0.8384615} {:x 12, :y 0.9235669} {:x 13, :y 0.88142294} {:x 14, :y 0.9382716} {:x 15, :y 0.9453125})}], :marks [{:type \"rect\", :from {:data \"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"2f8ceacd-d5ab-4e10-82af-3c8cbaeaac2f\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; **
;;; As we can see from the previous chart, there is a slight difference in the proportion of happy customers depending on the hour of the day. Let's see if the difference is statistically significant.
;;; 
;;; ### Hypothesis
;;; 
;;; * H0 (null hypothesis): there is no difference depending on the hour of day
;;; * H1: there is a difference.
;;; 
;;; Let's set the P value to 0.05.
;;; 
;;; [Pearson's Chi Square Test](https://en.wikipedia.org/wiki/Pearson%27s_chi-squared_test) is a common method for determining if differences between groups arose by chance or not. The drawback is that you won't know **which** group 
;;; is significantly different, but we can tackle that problem later. 
;;; 
;;; We start by determining what the amount of "happy" responses **should** be in each group, if they would all have exactly the same proportion. Then we apply a Chi Square Test function from the Apache commons-math library, for which there's a small wrapper function in the `happy-or-not.math` namespace.
;; **

;; @@
(let [expected (->> stats
                    (map (fn [{:keys [n]}] (double (* population-mean n)))))
      observed (->> stats (map :happy-n))]
  (str (format "P-value: %.4f" (- 1 (chi-square-test expected observed)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;P-value: 0.0219&quot;</span>","value":"\"P-value: 0.0219\""}
;; <=

;; **
;;; The interpretation of a P-value of 0.0219 is that there is only about a 2.19% chance that the differences between the groups arose by chance. Since this is less than our threshold P-value of 0.05, we can reject the null hypothesis and say that there **is** a statistically significant difference between the customer ratings depending on the hour of the day.
;; **

;; @@
(defn standard-error 
  "Approximate standard error: sqrt(p̂*(1-p̂)/n).
   https://en.wikipedia.org/wiki/Binomial_proportion_confidence_interval"
  [n sample-mean]
  (Math/sqrt (* sample-mean (/ (- 1 sample-mean) n))))

(defn t-statistic
  [{:keys [n happy-ratio]}]
  (/ (- happy-ratio population-mean) (standard-error n happy-ratio)))

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/t-statistic</span>","value":"#'happy-or-not.repl/t-statistic"}
;; <=

;; @@
(as-> stats s
      (map (juxt :n :happy-ratio t-statistic) s)
      (table-view s :columns [:n :mean :t-statistic]))
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><th>","close":"</th></tr>","separator":"</th><th>","items":[{"type":"html","content":"<span class='clj-keyword'>:n</span>","value":":n"},{"type":"html","content":"<span class='clj-keyword'>:mean</span>","value":":mean"},{"type":"html","content":"<span class='clj-keyword'>:t-statistic</span>","value":":t-statistic"}],"value":"[:n :mean :t-statistic]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>26</span>","value":"26"},{"type":"html","content":"<span class='clj-unkown'>0.96153843</span>","value":"0.96153843"},{"type":"html","content":"<span class='clj-double'>1.6524402520869022</span>","value":"1.6524402520869022"}],"value":"[26 0.96153843 1.6524402520869022]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>41</span>","value":"41"},{"type":"html","content":"<span class='clj-unkown'>0.85365856</span>","value":"0.85365856"},{"type":"html","content":"<span class='clj-double'>-0.825348078606929</span>","value":"-0.825348078606929"}],"value":"[41 0.85365856 -0.825348078606929]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>125</span>","value":"125"},{"type":"html","content":"<span class='clj-unkown'>0.872</span>","value":"0.872"},{"type":"html","content":"<span class='clj-double'>-0.9108269358448551</span>","value":"-0.9108269358448551"}],"value":"[125 0.872 -0.9108269358448551]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>130</span>","value":"130"},{"type":"html","content":"<span class='clj-unkown'>0.8384615</span>","value":"0.8384615"},{"type":"html","content":"<span class='clj-double'>-1.882258034231413</span>","value":"-1.882258034231413"}],"value":"[130 0.8384615 -1.882258034231413]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>157</span>","value":"157"},{"type":"html","content":"<span class='clj-unkown'>0.9235669</span>","value":"0.9235669"},{"type":"html","content":"<span class='clj-double'>1.1483326000714302</span>","value":"1.1483326000714302"}],"value":"[157 0.9235669 1.1483326000714302]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>253</span>","value":"253"},{"type":"html","content":"<span class='clj-unkown'>0.88142294</span>","value":"0.88142294"},{"type":"html","content":"<span class='clj-double'>-0.8754841037546861</span>","value":"-0.8754841037546861"}],"value":"[253 0.88142294 -0.8754841037546861]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>162</span>","value":"162"},{"type":"html","content":"<span class='clj-unkown'>0.9382716</span>","value":"0.9382716"},{"type":"html","content":"<span class='clj-double'>2.0654761717962806</span>","value":"2.0654761717962806"}],"value":"[162 0.9382716 2.0654761717962806]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-unkown'>128</span>","value":"128"},{"type":"html","content":"<span class='clj-unkown'>0.9453125</span>","value":"0.9453125"},{"type":"html","content":"<span class='clj-double'>2.2936634602126538</span>","value":"2.2936634602126538"}],"value":"[128 0.9453125 2.2936634602126538]"}],"value":"#gorilla_repl.table.TableView{:contents ([26 0.96153843 1.6524402520869022] [41 0.85365856 -0.825348078606929] [125 0.872 -0.9108269358448551] [130 0.8384615 -1.882258034231413] [157 0.9235669 1.1483326000714302] [253 0.88142294 -0.8754841037546861] [162 0.9382716 2.0654761717962806] [128 0.9453125 2.2936634602126538]), :opts (:columns [:n :mean :t-statistic])}"}
;; <=

;; @@

;; @@
