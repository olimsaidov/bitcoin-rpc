# Bitcoin

[rpc-doc]:https://bitcoin.org/en/developer-reference#rpcs

Bitcoin Core provides a remote procedure call ([RPC](rpc-doc)) interface for various administrative tasks, wallet operations, and queries about network and block chain data.

This library implements RPC interface over http post request.

## Installation

Add `[bitcoin-rpc "0.2.0"]` to your dependencies.

## Usage

```clojure
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

(btc/get-balance client)

;; => 5000.0
```

## License

[homepage]:http://twitter.com/olimsaidov

Copyright © 2019 [Olim Saidov][homepage].

Distributed under the Eclipse Public License version 1.0
