(defproject aoc-clj-elf "0.1.1"
  :description "A clojure/babashka library for managing Advent of Code solutions"
  :url "https://github.com/jjcomer/aoc-clj-elf"
  :license {:name "Unlicense"
            :url "https://unlicense.org"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.github.paintparty/bling "0.4.2"]
                 [org.babashka/cli "0.8.61"]
                 [org.babashka/http-client "0.4.22"]
                 [babashka/fs "0.5.22"]] 
  :repl-options {:init-ns aoc-clj-elf.core})
