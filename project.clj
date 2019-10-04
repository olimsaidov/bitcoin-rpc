(defproject bitcoin-rpc "0.2.2-SNAPSHOT"
  :description "Clojure Bitcoin RPC client"
  :url "https://github.com/olimsaidov/bitcoin-rpc"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[clj-http "3.10.0"]
                 [cheshire "5.9.0"]]

  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.10.1"]
                                  [marge "0.16.0"]
                                  [camel-snake-kebab "0.4.0"]
                                  [io.forward/yaml "1.0.9" :exclusions [org.flatland/ordered]]
                                  [org.flatland/ordered "1.5.7"]]
                   :source-paths ["env/dev"]
                   :repl-options {:init-ns bitcoin-rpc.dev}}})
