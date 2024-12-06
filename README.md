# aoc-clj-elf

## Overview 
Clojure based [AOC](http://adventofcode.com) helper.

This is a set of scripts which will help you work on Advent Of Code puzzles. 

Specifically it will help you:

* Pull puzzle input from AOC (and store to disk)
* Generate solution files
* Execute tests within your solutions
* Run your solutions with the pulled input (and time your execution)

This framework also supports having multiple years together in the same repository. Your session for each year is different so you will need to add an auth for each year you want to use.

## Usage

### Requirements

Before you use this framework there is a pre-requisite of [babashka](https://babashka.org/)

On a mac you can install babashka using brew:

```shell
$ brew install babashka
```

For other installation instructions see the [official docs](https://github.com/babashka/babashka#installation)

### Setup your Repo

Create a new empty repo and include a `bb.edn` file in the root. Add the following to your `bb.edn` file:

```clojure
{:paths ["src"]
 :deps {com.github.jjcomer/aoc-clj-elf {:mvn/version "0.1.1"}}
 :mvn/repos {"jitpack" {:url "https://jitpack.io"}}
 :tasks {:requires ([babashka.fs :as fs]
                    [aoc-clj-elf.core :as aoc])
         -input-dir "input"
         -input {:depends [-input-dir]
                 :task (fs/create-dirs -input-dir)}
         -auth-file "auth.edn"
         get {:doc "Fetches a specific input (defaults to today)"
              :depends [-auth-file -input]
              :task (aoc/get-input-task -auth-file -input *command-line-args*)}
         new {:doc "Creates a skeleton solution for a specific day"
              :task (aoc/gen-new-task *command-line-args*)}
         solve {:doc "Executes the solution against the input"
                :depends [-auth-file -input]
                :task (aoc/solve-task -input -auth-file *command-line-args*)}
         test {:doc "Tests the solution agaist a sample"
               :task (aoc/run-tests-task *command-line-args*)}
         auth {:doc "Set the auth for the Advent of Code API"
               :depends [-auth-file]
               :task (aoc/store-auth-task -auth-file *command-line-args*)}}}
```

This will setup all the needed commands! If you would like to add in more dependencies to use in your solutions, add your dependencies to the `:deps`.

### Authentication

In order to be able to pull input for your puzzles you will need to provide your AOC session cookie value from your browser.

```TODO Instructions for finding cookie value``` 

Once you have your token, you will need to store the value for the scipts to use. This is done using the `auth` command:

```shell
$ bb auth --year <YEAR> --session <SESSION TOKEN>
```

If you don't specify a year, the script will assume the current year. You can store session tokens for multiple years within the same repo.

Session tokens are stored within the `auth.edn` file at the root of the repo. This file has already been added to the `.gitignore` file to prevent accidental check-ins.

### Generating Solution Files

When you are ready to start solving a problem the framework can generate a starting file for you to use and which will be compatible with the included runners.

To create a new solution file execute the `new` task:

```shell
$ bb new --year <YEAR> --day <DAY>
```

This will create a new file under `src/y<YEAR>/d<DAY>.clj`

### Downloading Input

Your puzzle input can be downloaded directly from AOC. Using the `get` task you can pull any available input. With no parameters the input for today will be pulled.

```shell
$ bb get --year <YEAR> --day <DAY>
```

If the input has already been pulled, the task will not re-pull the input.

### Testing your Solution

A `clojure.test` runner has also been included if you want to write tests against your solution. This can be a helpful way to use the samples which are provided in the puzzles each day.

All tests in the solution's namespace will be executed. To run your tests use the `test` task.

```shell
$ bb test --year <YEAR> --day <DAY>
```

### Running your Solution

When you are ready to run your solution, the framework is ready to help with the `solve` task. This task will check for input and automatically download if missing. The input will be passed to the generator fn within your ns. The result of the generator will be passed to both the `part-1` and `part-2` solving functions. The result of both functions will be printed. If no parameters are passed today's date will be used.

```shell
$ bb solve --year <YEAR> --day <DAY>
```

### Adding Dependencies

If you solutions need any additional dependencies you can add them to the `:deps` map within the `bb.edn`. They will be pull automatically on the next task execution.

Currently all solutions will run within GraalVM and more specifically the babashka runtime environment. Not all features of clojure are supported. There are a number of libraries pre-included with babashka that you can make use of with no additional configuration. See the [docs](https://book.babashka.org/#libraries) for more information.

### Customizations

If you would like to change the templated solution files, the template is found at `scripts\solution_template.clj`

## Future Features

* ~~Allow for using JVM clojure (for more speed)~~
* Support for multiple solutions per-day
* Switch to macros for defining generators
* Switch to macros for defining solvers
* Criterion (Benchmarking) integration
* Ability to run all days sequentially
* Optionally pprint solution output
* More emojis ☃️
* Better output formatting
* Provide a filter for only running particular tests
* Add docs for connecting REPLs
* Add docs on finding session cookie

## Changelog

### 2024-12-05 -- 0.1.1
* Use the correct timezone for AoC -- Thanks to @jacoobes

### 2024-11-26
* Converted all logic to a library to make upgrading much much easier
* Updated the README

### 2024-11-23
* Changed all execution to babashka
* Removed benchmarking as it does not work on GraalVM

### 2022-12-01
* Update User-Agent as per [request](https://www.reddit.com/r/adventofcode/comments/z9dhtd/please_include_your_contact_info_in_the_useragent/)

### 2022-11-30
* Added `bench` task using criterium to benchmark solution time
* Added section for generator logic to the solution template

### 2022-09-14
* Changed over to execute using the JVM (will add back BB execution)
* *Breaking* changed the parameters to keywords and updated documentation
* Added `.tools-version` file to help choose a compatible jvm