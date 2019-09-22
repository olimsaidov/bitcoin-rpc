(ns bitcoin-rpc.dev
  (:require [yaml.core :as yaml]
            [clojure.set :refer [rename-keys]]
            [camel-snake-kebab.core :as csk]
            [marge.core :as marge]
            [clojure.string :as str]
            [clojure.java.io :as io]))


(defn strip-backticks
  [s]
  (str/replace s "`" ""))


(defn upper-first
  [s]
  (str (str/upper-case (subs s 0 1))
       (subs s 1)))


(defn parse-markdown
  [md]
  {:name (last (re-find (re-matcher #"##### ([^\s]+)" md)))
   :expl (upper-first (last (re-find (re-matcher #"\{% assign summary_.+?=\"(.+)\" %\}" md))))
   :args (let [matcher (re-matcher #"\*Parameter #\d+---.+\*\n\n\{% itemplate ntpd1 %\}\n((?:.+\n?)+)\n\n\{% enditemplate %\}" md)]
           (->> (repeatedly #(re-find matcher))
                (take-while some?)
                (map second)
                (map (comp #(update % :n strip-backticks) first #(yaml/parse-string % :keywords true)))
                (not-empty)))})


(defn args->table [args]
  (marge/markdown
    [:table
     ["Name" (mapv :n args)
      "Type" (mapv :t args)
      "Presence" (mapv :p args)
      "Description" (mapv :d args)]]))


(defn doc-str
  [{:keys [name args expl]}]
  (format "%s\n\n%shttps://bitcoin.org/en/developer-reference#%s"
          expl
          (or (some-> args args->table (str "\n")) "")
          (str/lower-case name)))


(defn gen-def
  [{:keys [name args] :as all}]
  (if (seq args)
    `(~'defn ~(csk/->kebab-case-symbol name)
       ~(doc-str all)
       [~'client {:keys ~(mapv (comp symbol :n) args) :as ~'params}]
       (~'perform ~'client ~(str/lower-case name) ~'params))
    `(~'defn ~(csk/->kebab-case-symbol name)
       ~(doc-str all)
       [~'client]
       (~'perform ~'client ~(str/lower-case name) nil))))


(comment
  (gen-def
    (parse-markdown
      (slurp "https://raw.githubusercontent.com/bitcoin-dot-org/bitcoin.org/master/_data/devdocs/en/bitcoin-core/rpcs/rpcs/getblockchaininfo.md")))

  (->> (file-seq (io/file "rpcs"))
       (filter #(.endsWith (.getName %) ".md"))
       (map slurp)
       (map parse-markdown)
       (map gen-def)
       (mapv clojure.pprint/pprint)
       (dorun)))



(comment
  (require '[bitcoin-rpc.core :as btc])

  (def client
    (btc/client
      {:url  "http://127.0.0.1:18443"
       :user "bitcoinrpc"
       :pass "WSvGaaP2K2jHUJo6YMqJhc2gOPDzFqMjswWA1iMODu12"}))

  (btc/get-block-chain-info client)
  ;;=>
  ;;{:pruned false,
  ;; :bip9_softforks {:csv {:status "defined", :startTime 0, :timeout 9223372036854775807, :since 0},
  ;;                  :segwit {:status "active", :startTime -1, :timeout 9223372036854775807, :since 0}},
  ;; :difficulty 4.656542373906925E-10,
  ;; :size_on_disk 293,
  ;; :initialblockdownload true,
  ;; :bestblockhash "0f9188f13cb7b2c71f2a335e3a4fc328bf5beb436012afca590b1a11466e2206",
  ;; :verificationprogress 1,
  ;; :warnings "",
  ;; :headers 0,
  ;; :softforks [{:id "bip34", :version 2, :reject {:status false}}
  ;;             {:id "bip66", :version 3, :reject {:status false}}
  ;;             {:id "bip65", :version 4, :reject {:status false}}],
  ;; :chainwork "0000000000000000000000000000000000000000000000000000000000000002",
  ;; :chain "regtest",
  ;; :blocks 0,
  ;; :mediantime 1296688602}

  (btc/generate client {:nblocks 100})
  ;;=>
  ;;["141786f7bd13d5920969564bcc155c90e74b2eae633893a22bf2320495c2c658"
  ;; "32ad1dd239923211cccaa3d7b5275cc1d2598ba288248eb72f6aad5796b1ce17"
  ;; ...]

  (btc/get-balance client {})
  ;; => 5000.0

  ,)
