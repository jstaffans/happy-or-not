;; gorilla-repl.fileformat = 1

;; **
;;; # Happy or not
;;; 
;;; An exploration of customer satisfaction data available through the Helsinki Region Infoshare open data platform. The source of the data are four-button terminals that are placed near the exit of an agency, bureau hospital or similar with a sign saying "Please rate our service today". The terminal buttons are labeled with smileys, ranging from sad to happy, corresponding to ratings from 0 to 3.
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
;;; OK, so the "Nuorten sosiaalityö" is in fact the only interesting one! It also has a sufficiently long timespan to give us a meaningful amount of data. For those who don't speak Finnish, "tyytyväisyytesi nuorten sosiaalityön palveluun tänään?" translates to "how happy are with the services of the youth service bureau today?". 
;;; 
;;; We can now employ another function, `happy-or-not.core/ratings`, to retrieve the actual ratings from the API.
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
;;; So we seem to have a list of timestamp and rating pairs. Perfect!
;; **

;; **
;;; ## Ratings per hour of day
;;; 
;; **

;; **
;;; To make the statistics simpler, we define "happy" to be a rating of 2-3 on a 0-3 scale. Calculate happy ratio, ie the "mean happiness", for each hour of the working day, which seems to be 8 am and 4 pm for this particular agency.
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
                (take 8)
                (into [])))

(defn population-count
  "Sums the value for the key k over groups."
  [groups k]
  (->> groups
       (map k)
       (reduce +)))

(defn population-mean 
  "Calculate the proportion of happy customers over several groups."
  [groups]
  (/ (population-count groups :happy-n) 
     (population-count groups :n)))



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
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"903f038b-c1eb-4b2a-8f3d-93f7d5024621","values":[{"x":8,"y":26},{"x":9,"y":41},{"x":10,"y":125},{"x":11,"y":130},{"x":12,"y":157},{"x":13,"y":253},{"x":14,"y":162},{"x":15,"y":128}]}],"marks":[{"type":"rect","from":{"data":"903f038b-c1eb-4b2a-8f3d-93f7d5024621"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"903f038b-c1eb-4b2a-8f3d-93f7d5024621","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"903f038b-c1eb-4b2a-8f3d-93f7d5024621","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"903f038b-c1eb-4b2a-8f3d-93f7d5024621\", :values ({:x 8, :y 26} {:x 9, :y 41} {:x 10, :y 125} {:x 11, :y 130} {:x 12, :y 157} {:x 13, :y 253} {:x 14, :y 162} {:x 15, :y 128})}], :marks [{:type \"rect\", :from {:data \"903f038b-c1eb-4b2a-8f3d-93f7d5024621\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"903f038b-c1eb-4b2a-8f3d-93f7d5024621\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"903f038b-c1eb-4b2a-8f3d-93f7d5024621\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
;; <=

;; @@
;; happiness proportion per hour of the work day
(bar-chart work-day (map :happy-ratio stats))

;; @@
;; =>
;;; {"type":"vega","content":{"width":400,"height":247.2188,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"48aca391-4217-449d-97cf-5a48253136ca","values":[{"x":8,"y":0.96153843},{"x":9,"y":0.85365856},{"x":10,"y":0.872},{"x":11,"y":0.8384615},{"x":12,"y":0.9235669},{"x":13,"y":0.88142294},{"x":14,"y":0.9382716},{"x":15,"y":0.9453125}]}],"marks":[{"type":"rect","from":{"data":"48aca391-4217-449d-97cf-5a48253136ca"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"width":{"scale":"x","band":true,"offset":-1},"y":{"scale":"y","field":"data.y"},"y2":{"scale":"y","value":0}},"update":{"fill":{"value":"steelblue"},"opacity":{"value":1}},"hover":{"fill":{"value":"#FF29D2"}}}}],"scales":[{"name":"x","type":"ordinal","range":"width","domain":{"data":"48aca391-4217-449d-97cf-5a48253136ca","field":"data.x"}},{"name":"y","range":"height","nice":true,"domain":{"data":"48aca391-4217-449d-97cf-5a48253136ca","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"48aca391-4217-449d-97cf-5a48253136ca\", :values ({:x 8, :y 0.96153843} {:x 9, :y 0.85365856} {:x 10, :y 0.872} {:x 11, :y 0.8384615} {:x 12, :y 0.9235669} {:x 13, :y 0.88142294} {:x 14, :y 0.9382716} {:x 15, :y 0.9453125})}], :marks [{:type \"rect\", :from {:data \"48aca391-4217-449d-97cf-5a48253136ca\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :width {:scale \"x\", :band true, :offset -1}, :y {:scale \"y\", :field \"data.y\"}, :y2 {:scale \"y\", :value 0}}, :update {:fill {:value \"steelblue\"}, :opacity {:value 1}}, :hover {:fill {:value \"#FF29D2\"}}}}], :scales [{:name \"x\", :type \"ordinal\", :range \"width\", :domain {:data \"48aca391-4217-449d-97cf-5a48253136ca\", :field \"data.x\"}} {:name \"y\", :range \"height\", :nice true, :domain {:data \"48aca391-4217-449d-97cf-5a48253136ca\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}
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
                    (map (fn [{:keys [n]}] (double (* (population-mean stats) n)))))
      observed (->> stats (map :happy-n))]
  (str (format "P-value: %.4f" (- 1 (chi-square-test expected observed)))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;P-value: 0.0219&quot;</span>","value":"\"P-value: 0.0219\""}
;; <=

;; **
;;; The interpretation of a P-value of 0.0219 is that there is only about a 2.19% chance that the differences between the groups arose by chance. Since this is less than our threshold P-value of 0.05, we can reject the null hypothesis and say that there **is** a statistically significant difference between the customer ratings depending on the hour of the day.
;;; 
;;; Let's explore each group further and compare it to the rest of the ratings. We will for example compare the customer ratings from 8 to 9 am to the customer ratings from 9 am to 4 pm. Technically, this is a [Z-statistic for the differences in proportions between two populations](http://www.itl.nist.gov/div898/handbook/prc/section3/prc33.htm): one population is the ratings for one given hour of the day and the other population is the ratings for all other hours.
;; **

;; @@
(defn z-statistic 
  [groups sub-group-index]
  (let [p-hat (population-mean groups)
        p1-groups (concat (subvec groups 0 sub-group-index) (subvec groups (inc sub-group-index)))
        p1-hat (population-mean p1-groups)
        p2-group (subvec groups sub-group-index (inc sub-group-index))
        p2-hat (population-mean p2-group)
        pq (* p-hat (- 1 p-hat))
        n1 (population-count p1-groups :n)
        n2 (population-count p2-group :n)]
    (/ (- p1-hat p2-hat) 
       (Math/sqrt 
         (+ (/ pq n1) (/ pq n2))))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;happy-or-not.repl/z-statistic</span>","value":"#'happy-or-not.repl/z-statistic"}
;; <=

;; @@
(-> (map-indexed 
      (fn [i group] 
        (let [hour (+ i 8)]
          [hour (:n group) (:happy-ratio group) (z-statistic stats i)]))
      stats)
    (table-view :columns [:hour :n :mean :z-statistic]))
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><th>","close":"</th></tr>","separator":"</th><th>","items":[{"type":"html","content":"<span class='clj-keyword'>:hour</span>","value":":hour"},{"type":"html","content":"<span class='clj-keyword'>:n</span>","value":":n"},{"type":"html","content":"<span class='clj-keyword'>:mean</span>","value":":mean"},{"type":"html","content":"<span class='clj-keyword'>:z-statistic</span>","value":":z-statistic"}],"value":"[:hour :n :mean :z-statistic]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>8</span>","value":"8"},{"type":"html","content":"<span class='clj-unkown'>26</span>","value":"26"},{"type":"html","content":"<span class='clj-unkown'>0.96153843</span>","value":"0.96153843"},{"type":"html","content":"<span class='clj-double'>-1.0692840017351786</span>","value":"-1.0692840017351786"}],"value":"[8 26 0.96153843 -1.0692840017351786]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>9</span>","value":"9"},{"type":"html","content":"<span class='clj-unkown'>41</span>","value":"41"},{"type":"html","content":"<span class='clj-unkown'>0.85365856</span>","value":"0.85365856"},{"type":"html","content":"<span class='clj-double'>0.9890735230538121</span>","value":"0.9890735230538121"}],"value":"[9 41 0.85365856 0.9890735230538121]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>10</span>","value":"10"},{"type":"html","content":"<span class='clj-unkown'>125</span>","value":"125"},{"type":"html","content":"<span class='clj-unkown'>0.872</span>","value":"0.872"},{"type":"html","content":"<span class='clj-double'>1.0789530387775506</span>","value":"1.0789530387775506"}],"value":"[10 125 0.872 1.0789530387775506]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>11</span>","value":"11"},{"type":"html","content":"<span class='clj-unkown'>130</span>","value":"130"},{"type":"html","content":"<span class='clj-unkown'>0.8384615</span>","value":"0.8384615"},{"type":"html","content":"<span class='clj-double'>2.463066456248311</span>","value":"2.463066456248311"}],"value":"[11 130 0.8384615 2.463066456248311]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>12</span>","value":"12"},{"type":"html","content":"<span class='clj-unkown'>157</span>","value":"157"},{"type":"html","content":"<span class='clj-unkown'>0.9235669</span>","value":"0.9235669"},{"type":"html","content":"<span class='clj-double'>-1.1016271003232707</span>","value":"-1.1016271003232707"}],"value":"[12 157 0.9235669 -1.1016271003232707]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>13</span>","value":"13"},{"type":"html","content":"<span class='clj-unkown'>253</span>","value":"253"},{"type":"html","content":"<span class='clj-unkown'>0.88142294</span>","value":"0.88142294"},{"type":"html","content":"<span class='clj-double'>1.083871714143472</span>","value":"1.083871714143472"}],"value":"[13 253 0.88142294 1.083871714143472]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>14</span>","value":"14"},{"type":"html","content":"<span class='clj-unkown'>162</span>","value":"162"},{"type":"html","content":"<span class='clj-unkown'>0.9382716</span>","value":"0.9382716"},{"type":"html","content":"<span class='clj-double'>-1.8000229703649824</span>","value":"-1.8000229703649824"}],"value":"[14 162 0.9382716 -1.8000229703649824]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>15</span>","value":"15"},{"type":"html","content":"<span class='clj-unkown'>128</span>","value":"128"},{"type":"html","content":"<span class='clj-unkown'>0.9453125</span>","value":"0.9453125"},{"type":"html","content":"<span class='clj-double'>-1.8522203345074677</span>","value":"-1.8522203345074677"}],"value":"[15 128 0.9453125 -1.8522203345074677]"}],"value":"#gorilla_repl.table.TableView{:contents ([8 26 0.96153843 -1.0692840017351786] [9 41 0.85365856 0.9890735230538121] [10 125 0.872 1.0789530387775506] [11 130 0.8384615 2.463066456248311] [12 157 0.9235669 -1.1016271003232707] [13 253 0.88142294 1.083871714143472] [14 162 0.9382716 -1.8000229703649824] [15 128 0.9453125 -1.8522203345074677]), :opts (:columns [:hour :n :mean :z-statistic])}"}
;; <=

;; **
;;; Continuing to use P=0.05, we need to look for Z-statistics that are [greater than 1.96](https://en.wikipedia.org/wiki/1.96) (or less than -1,96). There is only one group where this is true, namely in the customer responses from 11 am to 12 pm. 
;;; 
;;; ## Conclusion
;;; 
;;; We have extracted a meaningful piece of information from the customer ratings, namely that customers are at their unhappiest right before lunch. Maybe employees are tired by then and not providing the best support they can?
;; **

;; **
;;; ## References
;;; 
;;; When I started out writing this post, I had only a basic grasp of statistics and hypothesis testing. I now know a lot more and am eager to continue learning about statistics, which I feel is an often overlooked but very important aspect of software development. Statistics is the underpinning of A/B-testing, defect rate tracking, performance monitoring, service level agreements and much more.
;;; 
;;; These resources were the ones that I found most helpful:
;;; 
;;; * [Khan Academy: Hypothesis Testing and P-values](https://www.khanacademy.org/math/statistics-probability/significance-tests-one-sample/tests-about-population-mean/v/hypothesis-testing-and-p-values)
;;; * [Types of Data: Nominal, Ordinal, Interval/Ratio - Statistics Help](https://www.youtube.com/watch?v=hZxnzfnt5v8)
;;; * [Choosing Which Statistical Test to Use - Statistics Help](https://www.youtube.com/watch?v=rulIUAN0U3w)
;;; * [Chi Square Test - Explained](https://www.youtube.com/watch?v=1Ldl5Zfcm1Y)
;;; * [Degrees of Freedom in a Chi Square Test](https://www.youtube.com/watch?v=JU9ZJlhw1-Y)
;;; * [Statistics in a Nutshell, 2nd edition, Ch. 5 (Categorical Data)](http://shop.oreilly.com/product/0636920023074.do)
;;; 
;;; Many thanks to [Robin Gower](http://www.infonomics.ltd.uk/) for reviewing this post!
;; **

;; @@

;; @@
