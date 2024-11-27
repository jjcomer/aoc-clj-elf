(ns aoc-clj-elf.generate
  (:require [babashka.fs :as fs]
            [clojure.java.io :as io]
            [clojure.string :as str] 
            [bling.core :as bling]))

(def solution-template
  "(ns y__YEAR__.d__DAY__
  (:require [clojure.test :as t :refer [deftest]]))

;; PROBLEM LINK https://adventofcode.com/__YEAR__/day/__DAY__

;; Generator Logic

;; Solution Logic

;; Entry Points

(defn generator
  \"The generator fn is used to parse your input into. The output of this fn will be passed into each of the solving fns\"
  [input])

(defn solve-part-1
  \"The solution to part 1. Will be called with the result of the generator\"
  [input])

(defn solve-part-2
  \"The solution to part 2. Will be called with the result of the generator\"
  [input])

;; Tests
;; Use tests to verify your solution. Consider using the sample data provided in the question

(deftest sample-test
  (t/is (= 2 (+ 1 1))))
")

(defn generate-solution
  [year day]
  (let [safe-year (str "y" year)
        safe-day (str "d" day)
        solution-filename (str safe-day ".clj")
        solution-path (fs/path "src" safe-year)
        full-path (fs/path solution-path solution-filename)]
    (if (fs/exists? full-path)
      (bling/callout {:level :warn}
                     (format "Solution for year %s and day %s already exists" year day))
      (do
        (when-not (fs/exists? solution-path)
          (fs/create-dir solution-path))
        (-> solution-template
            slurp
            (str/replace #"__YEAR__" (str year))
            (str/replace #"__DAY__" (str day))
            (#(spit (str full-path) %)))
        (bling/callout {:level :positive}
                       (str "Created template for year %s and day %s at %s" year day (str full-path)))))))
