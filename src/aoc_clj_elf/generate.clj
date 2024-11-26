(ns aoc-clj-elf.generate
  (:require [babashka.fs :as fs]
            [clojure.string :as str] 
            [bling.core :as bling]))

(defn generate-solution
  [solution-template year day]
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
