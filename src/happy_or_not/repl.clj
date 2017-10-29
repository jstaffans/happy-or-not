;; gorilla-repl.fileformat = 1

;; **
;;; # Happy or not
;;; 
;;; 
;; **

;; @@
(ns happy-or-not.repl
  (:require [gorilla-repl.table :refer [table-view]]
            [happy-or-not.core :refer :all]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(table-view interesting-folders :columns [:place :key :timespan :question])
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><th>","close":"</th></tr>","separator":"</th><th>","items":[{"type":"html","content":"<span class='clj-keyword'>:place</span>","value":":place"},{"type":"html","content":"<span class='clj-keyword'>:key</span>","value":":key"},{"type":"html","content":"<span class='clj-keyword'>:timespan</span>","value":":timespan"},{"type":"html","content":"<span class='clj-keyword'>:question</span>","value":":question"}],"value":"[:place :key :timespan :question]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-string'>&quot;Nuorten sosiaalityö&quot;</span>","value":"\"Nuorten sosiaalityö\""},{"type":"html","content":"<span class='clj-unkown'>134386</span>","value":"134386"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:start</span>","value":":start"},{"type":"html","content":"<span class='clj-string'>&quot;2016-01-15T00:00:00.000+02:00&quot;</span>","value":"\"2016-01-15T00:00:00.000+02:00\""}],"value":"[:start \"2016-01-15T00:00:00.000+02:00\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:end</span>","value":":end"},{"type":"html","content":"<span class='clj-string'>&quot;2030-01-02T00:00:00.000+02:00&quot;</span>","value":"\"2030-01-02T00:00:00.000+02:00\""}],"value":"[:end \"2030-01-02T00:00:00.000+02:00\"]"}],"value":"{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}"}],"value":"[{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}]"},{"type":"html","content":"<span class='clj-string'>&quot;Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?&quot;</span>","value":"\"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\""}],"value":"[\"Nuorten sosiaalityö\" 134386 [{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}] \"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\"]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-string'>&quot;Päiväkoti Kotikallio&quot;</span>","value":"\"Päiväkoti Kotikallio\""},{"type":"html","content":"<span class='clj-unkown'>97128</span>","value":"97128"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:start</span>","value":":start"},{"type":"html","content":"<span class='clj-string'>&quot;2015-07-06T00:00:00.000+03:00&quot;</span>","value":"\"2015-07-06T00:00:00.000+03:00\""}],"value":"[:start \"2015-07-06T00:00:00.000+03:00\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:end</span>","value":":end"},{"type":"html","content":"<span class='clj-string'>&quot;2015-09-06T00:00:00.000+03:00&quot;</span>","value":"\"2015-09-06T00:00:00.000+03:00\""}],"value":"[:end \"2015-09-06T00:00:00.000+03:00\"]"}],"value":"{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}"}],"value":"[{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}]"},{"type":"html","content":"<span class='clj-string'>&quot;Oliko sinulla kiva päivä päiväkodissa?&quot;</span>","value":"\"Oliko sinulla kiva päivä päiväkodissa?\""}],"value":"[\"Päiväkoti Kotikallio\" 97128 [{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}] \"Oliko sinulla kiva päivä päiväkodissa?\"]"}],"value":"#gorilla_repl.table.TableView{:contents [[\"Nuorten sosiaalityö\" 134386 [{:start \"2016-01-15T00:00:00.000+02:00\", :end \"2030-01-02T00:00:00.000+02:00\"}] \"Tyytyväisyytesi nuorten sosiaalityön palveluun tänään?\"] [\"Päiväkoti Kotikallio\" 97128 [{:start \"2015-07-06T00:00:00.000+03:00\", :end \"2015-09-06T00:00:00.000+03:00\"}] \"Oliko sinulla kiva päivä päiväkodissa?\"]], :opts (:columns [:place :key :timespan :question])}"}
;; <=

;; @@

;; @@
