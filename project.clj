(defproject io.github.jjcomer/aoc-clj-elf "0.1.2"
  :description "A clojure/babashka library for managing Advent of Code solutions"
  :url "https://github.com/jjcomer/aoc-clj-elf"
  :license {:name "Unlicense"
            :url "https://unlicense.org"}
  :dependencies [[org.clojure/clojure "1.12.3"]
                 [io.github.paintparty/bling "0.8.8"]
                 [org.babashka/cli "0.8.66"]
                 [org.babashka/http-client "0.4.23"]
                 [babashka/fs "0.5.27"]] 
  :deploy-repositories {"clojars" {:sign-releases false}}
  :repl-options {:init-ns aoc-clj-elf.core})
