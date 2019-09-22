(ns bitcoin-rpc.core
  (:require [bitcoin-rpc.client :refer [perform map->Client]]))


(defn client
  [{:keys [url user pass]}]
  (map->Client
    {:url  url
     :user user
     :pass pass}))


(defn get-mem-pool-descendants
  "If txid is in the mempool, returns all in-mempool descendants.
   
   | Name    | Type    | Presence                  | Description                                                |
   | ------- | ------- | ------------------------- | ---------------------------------------------------------- |
   | txid    | string  | Required<br>(exactly 1)   | The transaction id (must be in mempool)                    |
   | verbose | boolean | Optional<br>Default=false | True for a json object, false for array of transaction ids |
   
   https://bitcoin.org/en/developer-reference#getmempooldescendants"
  [client {:as params :keys [txid verbose]}]
  (perform client "getmempooldescendants" params))


(defn save-mem-pool
  "Dumps the mempool to disk.
   
   https://bitcoin.org/en/developer-reference#savemempool"
  [client]
  (perform client "savemempool" nil))


(defn set-tx-fee
  "Set the transaction fee per kB for this wallet.
   
   | Name   | Type              | Presence                | Description                   |
   | ------ | ----------------- | ----------------------- | ----------------------------- |
   | amount | numeric or string | Required<br>(exactly 1) | The transaction fee in BTC/kB |
   
   https://bitcoin.org/en/developer-reference#settxfee"
  [client {:as params :keys [amount]}]
  (perform client "settxfee" params))


(defn get-chain-tx-stats
  "Compute statistics about the total number and rate of transactions in the chain.
   
   | Name      | Type         | Presence                      | Description                                 |
   | --------- | ------------ | ----------------------------- | ------------------------------------------- |
   | nblocks   | number (int) | Optional<br>Default=one month | Size of the window in number of blocks      |
   | blockhash | string       | Optional<br>Default=chain tip | The hash of the block that ends the window. |
   
   https://bitcoin.org/en/developer-reference#getchaintxstats"
  [client {:as params :keys [nblocks blockhash]}]
  (perform client "getchaintxstats" params))


(defn get-block
  "Gets a block with a particular header hash from the local block database either as a JSON object or as a serialized block.
   
   | Name      | Type         | Presence                | Description                                                                              |
   | --------- | ------------ | ----------------------- | ---------------------------------------------------------------------------------------- |
   | blockhash | string (hex) | Required<br>(exactly 1) | The block hash                                                                           |
   | verbosity | number (int) | Optional<br>Default=1   | 0 for hex-encoded data, 1 for a json object, and 2 for json object with transaction data |
   
   https://bitcoin.org/en/developer-reference#getblock"
  [client {:as params :keys [blockhash verbosity]}]
  (perform client "getblock" params))


(defn sign-raw-transaction-with-key
  "Sign inputs for raw transaction (serialized, hex-encoded).
   
   | Name        | Type       | Presence                | Description                                                                                                                  |
   | ----------- | ---------- | ----------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
   | hexstring   | string     | Required<br>(exactly 1) | The transaction hex string                                                                                                   |
   | privkeys    | json array | Required<br>(exactly 1) | A json array of base58-encoded private keys for signing                                                                      |
   | prevtxs     | json array | Optional                | A json array of previous dependent transaction outputs                                                                       |
   | sighashtype | string     | Optional<br>Default=ALL | The signature hash type. Must be one of: \"ALL\" \"NONE\" \"SINGLE\" \"ALL|ANYONECANPAY\" \"NONE|ANYONECANPAY\" \"SINGLE|ANYONECANPAY\"  |
   
   https://bitcoin.org/en/developer-reference#signrawtransactionwithkey"
  [client {:as params :keys [hexstring privkeys prevtxs sighashtype]}]
  (perform client "signrawtransactionwithkey" params))


(defn list-address-groupings
  "Lists groups of addresses which have had their common ownership made public by common use as inputs or as the resulting change in past transactions.
   
   https://bitcoin.org/en/developer-reference#listaddressgroupings"
  [client]
  (perform client "listaddressgroupings" nil))


(defn get-net-totals
  "Returns information about network traffic, including bytes in, bytes out, and current time.
   
   https://bitcoin.org/en/developer-reference#getnettotals"
  [client]
  (perform client "getnettotals" nil))


(defn key-pool-refill
  "Fills the keypool.
   
   | Name    | Type         | Presence                | Description          |
   | ------- | ------------ | ----------------------- | -------------------- |
   | newsize | number (int) | Optional<br>Default=100 | The new keypool size |
   
   https://bitcoin.org/en/developer-reference#keypoolrefill"
  [client {:as params :keys [newsize]}]
  (perform client "keypoolrefill" params))


(defn send-raw-transaction
  "Submits raw transaction (serialized, hex-encoded) to local node and network.
   
   | Name          | Type    | Presence                  | Description                           |
   | ------------- | ------- | ------------------------- | ------------------------------------- |
   | hexstring     | string  | Required<br>(exactly 1)   | The hex string of the raw transaction |
   | allowhighfees | boolean | Optional<br>Default=false | Allow high fees                       |
   
   https://bitcoin.org/en/developer-reference#sendrawtransaction"
  [client {:as params :keys [hexstring allowhighfees]}]
  (perform client "sendrawtransaction" params))


(defn list-labels
  "Returns the list of all labels, or labels that are assigned to addresses with a specific purpose.
   
   | Name    | Type   | Presence | Description                                                                                                        |
   | ------- | ------ | -------- | ------------------------------------------------------------------------------------------------------------------ |
   | purpose | string | Optional | Address purpose to list labels for ('send','receive'). An empty string is the same as not providing this argument. |
   
   https://bitcoin.org/en/developer-reference#listlabels"
  [client {:as params :keys [purpose]}]
  (perform client "listlabels" params))


(defn get-mem-pool-ancestors
  "If txid is in the mempool, returns all in-mempool ancestors.
   
   | Name    | Type    | Presence                  | Description                                                |
   | ------- | ------- | ------------------------- | ---------------------------------------------------------- |
   | txid    | string  | Required<br>(exactly 1)   | The transaction id (must be in mempool)                    |
   | verbose | boolean | Optional<br>Default=false | True for a json object, false for array of transaction ids |
   
   https://bitcoin.org/en/developer-reference#getmempoolancestors"
  [client {:as params :keys [txid verbose]}]
  (perform client "getmempoolancestors" params))


(defn utxo-update-psbt
  "Updates a PSBT with witness UTXOs retrieved from the UTXO set or the mempool.
   
   | Name | Type   | Presence                | Description               |
   | ---- | ------ | ----------------------- | ------------------------- |
   | psbt | string | Required<br>(exactly 1) | A base64 string of a PSBT |
   
   https://bitcoin.org/en/developer-reference#utxoupdatepsbt"
  [client {:as params :keys [psbt]}]
  (perform client "utxoupdatepsbt" params))


(defn get-block-count
  "Returns the number of blocks in the longest blockchain.
   
   https://bitcoin.org/en/developer-reference#getblockcount"
  [client]
  (perform client "getblockcount" nil))


(defn get-raw-change-address
  "Returns a new Bitcoin address, for receiving change.
   
   | Name         | Type   | Presence                               | Description                                                                 |
   | ------------ | ------ | -------------------------------------- | --------------------------------------------------------------------------- |
   | address_type | string | Optional<br>Default=set by -changetype | The address type to use. Options are \"legacy\", \"p2sh-segwit\", and \"bech32\". |
   
   https://bitcoin.org/en/developer-reference#getrawchangeaddress"
  [client {:as params :keys [address_type]}]
  (perform client "getrawchangeaddress" params))


(defn get-block-header
  "If verbose is false, returns a string that is serialized, hex-encoded data for blockheader 'hash'.
   
   | Name      | Type    | Presence                 | Description                                            |
   | --------- | ------- | ------------------------ | ------------------------------------------------------ |
   | blockhash | string  | Required<br>(exactly 1)  | The block hash                                         |
   | verbose   | boolean | Optional<br>Default=true | true for a json object, false for the hex-encoded data |
   
   https://bitcoin.org/en/developer-reference#getblockheader"
  [client {:as params :keys [blockhash verbose]}]
  (perform client "getblockheader" params))


(defn list-unspent
  "Returns array of unspent transaction outputs with between minconf and maxconf (inclusive) confirmations.
   
   | Name           | Type         | Presence                        | Description                                                                           |
   | -------------- | ------------ | ------------------------------- | ------------------------------------------------------------------------------------- |
   | minconf        | number (int) | Optional<br>Default=1           | The minimum confirmations to filter                                                   |
   | maxconf        | number (int) | Optional<br>Default=9999999     | The maximum confirmations to filter                                                   |
   | addresses      | json array   | Optional<br>Default=empty array | A json array of bitcoin addresses to filter                                           |
   | include_unsafe | boolean      | Optional<br>Default=true        | Include outputs that are not safe to spend See description of \"safe\" attribute below. |
   | query_options  | json object  | Optional                        | JSON with query options                                                               |
   
   https://bitcoin.org/en/developer-reference#listunspent"
  [client
   {:as   params
    :keys [minconf maxconf addresses include_unsafe query_options]}]
  (perform client "listunspent" params))


(defn get-tx-out-set-info
  "Returns statistics about the unspent transaction output set.
   
   https://bitcoin.org/en/developer-reference#gettxoutsetinfo"
  [client]
  (perform client "gettxoutsetinfo" nil))


(defn clear-banned
  "Clear all banned IPs.
   
   https://bitcoin.org/en/developer-reference#clearbanned"
  [client]
  (perform client "clearbanned" nil))


(defn set-label
  "Sets the label associated with the given address.
   
   | Name    | Type   | Presence                | Description                                        |
   | ------- | ------ | ----------------------- | -------------------------------------------------- |
   | address | string | Required<br>(exactly 1) | The bitcoin address to be associated with a label. |
   | label   | string | Required<br>(exactly 1) | The label to assign to the address.                |
   
   https://bitcoin.org/en/developer-reference#setlabel"
  [client {:as params :keys [address label]}]
  (perform client "setlabel" params))


(defn convert-to-psbt
  "Converts a network serialized transaction to a PSBT.
   
   | Name          | Type    | Presence                                       | Description                                                                                                                                                                                                                                                                                        |
   | ------------- | ------- | ---------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | hexstring     | string  | Required<br>(exactly 1)                        | The hex string of a raw transaction                                                                                                                                                                                                                                                                |
   | permitsigdata | boolean | Optional<br>Default=false                      | If true, any signatures in the input will be discarded and conversion. will continue. If false, RPC will fail if any signatures are present.                                                                                                                                                       |
   | iswitness     | boolean | Optional<br>Default=depends on heuristic tests | Whether the transaction hex is a serialized witness transaction. If iswitness is not present, heuristic tests will be used in decoding. If true, only witness deserializaion will be tried. If false, only non-witness deserialization will be tried. Only has an effect if permitsigdata is true. |
   
   https://bitcoin.org/en/developer-reference#converttopsbt"
  [client {:as params :keys [hexstring permitsigdata iswitness]}]
  (perform client "converttopsbt" params))


(defn list-lock-unspent
  "Returns list of temporarily unspendable outputs.
   
   https://bitcoin.org/en/developer-reference#listlockunspent"
  [client]
  (perform client "listlockunspent" nil))


(defn submit-header
  "Decode the given hexdata as a header and submit it as a candidate chain tip if valid.
   
   | Name    | Type   | Presence                | Description                       |
   | ------- | ------ | ----------------------- | --------------------------------- |
   | hexdata | string | Required<br>(exactly 1) | the hex-encoded block header data |
   
   https://bitcoin.org/en/developer-reference#submitheader"
  [client {:as params :keys [hexdata]}]
  (perform client "submitheader" params))


(defn decode-raw-transaction
  "Return a JSON object representing the serialized, hex-encoded transaction.
   
   | Name      | Type    | Presence                                       | Description                                                                                                                           |
   | --------- | ------- | ---------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
   | hexstring | string  | Required<br>(exactly 1)                        | The transaction hex string                                                                                                            |
   | iswitness | boolean | Optional<br>Default=depends on heuristic tests | Whether the transaction hex is a serialized witness transaction If iswitness is not present, heuristic tests will be used in decoding |
   
   https://bitcoin.org/en/developer-reference#decoderawtransaction"
  [client {:as params :keys [hexstring iswitness]}]
  (perform client "decoderawtransaction" params))


(defn get-memory-info
  "Returns an object containing information about memory usage.
   
   | Name | Type   | Presence                    | Description                                                                                                                                                                                                                                 |
   | ---- | ------ | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | mode | string | Optional<br>Default=\"stats\" | determines what kind of information is returned. - \"stats\" returns general statistics about memory usage in the daemon. - \"mallocinfo\" returns an XML string describing low-level heap state (only available if compiled with glibc 2.10+). |
   
   https://bitcoin.org/en/developer-reference#getmemoryinfo"
  [client {:as params :keys [mode]}]
  (perform client "getmemoryinfo" params))


(defn decode-psbt
  "Return a JSON object representing the serialized, base64-encoded partially signed Bitcoin transaction.
   
   | Name | Type   | Presence                | Description            |
   | ---- | ------ | ----------------------- | ---------------------- |
   | psbt | string | Required<br>(exactly 1) | The PSBT base64 string |
   
   https://bitcoin.org/en/developer-reference#decodepsbt"
  [client {:as params :keys [psbt]}]
  (perform client "decodepsbt" params))


(defn dump-priv-key
  "Reveals the private key corresponding to 'address'.
   
   | Name    | Type   | Presence                | Description                             |
   | ------- | ------ | ----------------------- | --------------------------------------- |
   | address | string | Required<br>(exactly 1) | The bitcoin address for the private key |
   
   https://bitcoin.org/en/developer-reference#dumpprivkey"
  [client {:as params :keys [address]}]
  (perform client "dumpprivkey" params))


(defn list-transactions
  "If a label name is provided, this will return only incoming transactions paying to addresses with the specified label.
   
   | Name              | Type         | Presence                  | Description                                                                                                                                                  |
   | ----------------- | ------------ | ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------ |
   | label             | string       | Optional                  | If set, should be a valid label name to return only incoming transactions with the specified label, or \"*\" to disable filtering and return all transactions. |
   | count             | number (int) | Optional<br>Default=10    | The number of transactions to return                                                                                                                         |
   | skip              | number (int) | Optional<br>Default=0     | The number of transactions to skip                                                                                                                           |
   | include_watchonly | boolean      | Optional<br>Default=false | Include transactions to watch-only addresses (see 'importaddress')                                                                                           |
   
   https://bitcoin.org/en/developer-reference#listtransactions"
  [client {:as params :keys [label count skip include_watchonly]}]
  (perform client "listtransactions" params))


(defn precious-block
  "Treats a block as if it were received before others with the same work.
   
   | Name      | Type   | Presence                | Description                               |
   | --------- | ------ | ----------------------- | ----------------------------------------- |
   | blockhash | string | Required<br>(exactly 1) | the hash of the block to mark as precious |
   
   https://bitcoin.org/en/developer-reference#preciousblock"
  [client {:as params :keys [blockhash]}]
  (perform client "preciousblock" params))


(defn sign-message-with-priv-key
  "Sign a message with the private key of an address.
   
   | Name    | Type   | Presence                | Description                               |
   | ------- | ------ | ----------------------- | ----------------------------------------- |
   | privkey | string | Required<br>(exactly 1) | The private key to sign the message with. |
   | message | string | Required<br>(exactly 1) | The message to create a signature of.     |
   
   https://bitcoin.org/en/developer-reference#signmessagewithprivkey"
  [client {:as params :keys [privkey message]}]
  (perform client "signmessagewithprivkey" params))


(defn verify-tx-out-proof
  "Verifies that a proof points to a transaction in a block, returning the transaction it commits to and throwing an RPC error if the block is not in our best chain.
   
   | Name  | Type   | Presence                | Description                                      |
   | ----- | ------ | ----------------------- | ------------------------------------------------ |
   | proof | string | Required<br>(exactly 1) | The hex-encoded proof generated by gettxoutproof |
   
   https://bitcoin.org/en/developer-reference#verifytxoutproof"
  [client {:as params :keys [proof]}]
  (perform client "verifytxoutproof" params))


(defn uptime
  "Returns the total uptime of the server.
   
   https://bitcoin.org/en/developer-reference#uptime"
  [client]
  (perform client "uptime" nil))


(defn analyze-psbt
  "Analyzes and provides information about the current status of a PSBT and its inputs.
   
   | Name | Type   | Presence                | Description               |
   | ---- | ------ | ----------------------- | ------------------------- |
   | psbt | string | Required<br>(exactly 1) | A base64 string of a PSBT |
   
   https://bitcoin.org/en/developer-reference#analyzepsbt"
  [client {:as params :keys [psbt]}]
  (perform client "analyzepsbt" params))


(defn get-balance
  "Returns the total available balance.
   
   | Name              | Type         | Presence                  | Description                                                         |
   | ----------------- | ------------ | ------------------------- | ------------------------------------------------------------------- |
   | dummy             | string       | Optional                  | Remains for backward compatibility. Must be excluded or set to \"*\". |
   | minconf           | number (int) | Optional<br>Default=0     | Only include transactions confirmed at least this many times.       |
   | include_watchonly | boolean      | Optional<br>Default=false | Also include balance in watch-only addresses (see 'importaddress')  |
   
   https://bitcoin.org/en/developer-reference#getbalance"
  [client {:as params :keys [dummy minconf include_watchonly]}]
  (perform client "getbalance" params))


(defn generate-to-address
  "Mine blocks immediately to a specified address (before the RPC call returns).
   
   | Name     | Type         | Presence                    | Description                                         |
   | -------- | ------------ | --------------------------- | --------------------------------------------------- |
   | nblocks  | number (int) | Required<br>(exactly 1)     | How many blocks are generated immediately.          |
   | address  | string       | Required<br>(exactly 1)     | The address to send the newly generated bitcoin to. |
   | maxtries | number (int) | Optional<br>Default=1000000 | How many iterations to try.                         |
   
   https://bitcoin.org/en/developer-reference#generatetoaddress"
  [client {:as params :keys [nblocks address maxtries]}]
  (perform client "generatetoaddress" params))


(defn get-block-stats
  "Compute per block statistics for a given window.
   
   | Name           | Type              | Presence                       | Description                                  |
   | -------------- | ----------------- | ------------------------------ | -------------------------------------------- |
   | hash_or_height | string or numeric | Required<br>(exactly 1)        | The block hash or height of the target block |
   | stats          | json array        | Optional<br>Default=all values | Values to plot (see result below)            |
   
   https://bitcoin.org/en/developer-reference#getblockstats"
  [client {:as params :keys [hash_or_height stats]}]
  (perform client "getblockstats" params))


(defn unload-wallet
  "Unloads the wallet referenced by the request endpoint otherwise unloads the wallet specified in the argument.
   
   | Name        | Type   | Presence                                                 | Description                       |
   | ----------- | ------ | -------------------------------------------------------- | --------------------------------- |
   | wallet_name | string | Optional<br>Default=the wallet name from the RPC request | The name of the wallet to unload. |
   
   https://bitcoin.org/en/developer-reference#unloadwallet"
  [client {:as params :keys [wallet_name]}]
  (perform client "unloadwallet" params))


(defn wallet-lock
  "Removes the wallet encryption key from memory, locking the wallet.
   
   https://bitcoin.org/en/developer-reference#walletlock"
  [client]
  (perform client "walletlock" nil))


(defn testmem-pool-accept
  "Returns result of mempool acceptance tests indicating if raw transaction (serialized, hex-encoded) would be accepted by mempool.
   
   | Name          | Type       | Presence                  | Description                                                              |
   | ------------- | ---------- | ------------------------- | ------------------------------------------------------------------------ |
   | rawtxs        | json array | Required<br>(exactly 1)   | An array of hex strings of raw transactions. Length must be one for now. |
   | allowhighfees | boolean    | Optional<br>Default=false | Allow high fees                                                          |
   
   https://bitcoin.org/en/developer-reference#testmempoolaccept"
  [client {:as params :keys [rawtxs allowhighfees]}]
  (perform client "testmempoolaccept" params))


(defn ping
  "Requests that a ping be sent to all other nodes, to measure ping time.
   
   https://bitcoin.org/en/developer-reference#ping"
  [client]
  (perform client "ping" nil))


(defn get-network-hash-ps
  "Returns the estimated network hashes per second based on the last n blocks.
   
   | Name    | Type         | Presence                | Description                                                          |
   | ------- | ------------ | ----------------------- | -------------------------------------------------------------------- |
   | nblocks | number (int) | Optional<br>Default=120 | The number of blocks, or -1 for blocks since last difficulty change. |
   | height  | number (int) | Optional<br>Default=-1  | To estimate at the time of the given height.                         |
   
   https://bitcoin.org/en/developer-reference#getnetworkhashps"
  [client {:as params :keys [nblocks height]}]
  (perform client "getnetworkhashps" params))


(defn create-wallet
  "Creates and loads a new wallet.
   
   | Name                 | Type    | Presence                  | Description                                                                                      |
   | -------------------- | ------- | ------------------------- | ------------------------------------------------------------------------------------------------ |
   | wallet_name          | string  | Required<br>(exactly 1)   | The name for the new wallet. If this is a path, the wallet will be created at the path location. |
   | disable_private_keys | boolean | Optional<br>Default=false | Disable the possibility of private keys (only watchonlys are possible in this mode).             |
   | blank                | boolean | Optional<br>Default=false | Create a blank wallet. A blank wallet has no keys or HD seed. One can be set using sethdseed.    |
   
   https://bitcoin.org/en/developer-reference#createwallet"
  [client {:as params :keys [wallet_name disable_private_keys blank]}]
  (perform client "createwallet" params))


(defn create-psbt
  "Creates a transaction in the Partially Signed Transaction format.
   
   | Name        | Type         | Presence                  | Description                                                                                                                                                                                                                                                                                      |
   | ----------- | ------------ | ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
   | inputs      | json array   | Required<br>(exactly 1)   | A json array of json objects                                                                                                                                                                                                                                                                     |
   | outputs     | json array   | Required<br>(exactly 1)   | a json array with outputs (key-value pairs), where none of the keys are duplicated. That is, each address can only appear once and there can only be one 'data' object. For compatibility reasons, a dictionary, which holds the key-value pairs directly, is also accepted as second parameter. |
   | locktime    | number (int) | Optional<br>Default=0     | Raw locktime. Non-0 value also locktime-activates inputs                                                                                                                                                                                                                                         |
   | replaceable | boolean      | Optional<br>Default=false | Marks this transaction as BIP125 replaceable. Allows this transaction to be replaced by a transaction with higher fees. If provided, it is an error if explicit sequence numbers are incompatible.                                                                                               |
   
   https://bitcoin.org/en/developer-reference#createpsbt"
  [client {:as params :keys [inputs outputs locktime replaceable]}]
  (perform client "createpsbt" params))


(defn estimate-smart-fee
  "Estimates the approximate fee per kilobyte needed for a transaction to begin confirmation within conf_target blocks if possible and return the number of blocks for which the estimate is valid.
   
   | Name          | Type         | Presence                         | Description                                                                                                                                                                                                                                                                                                                                                           |
   | ------------- | ------------ | -------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | conf_target   | number (int) | Required<br>(exactly 1)          | Confirmation target in blocks (1 - 1008)                                                                                                                                                                                                                                                                                                                              |
   | estimate_mode | string       | Optional<br>Default=CONSERVATIVE | The fee estimate mode. Whether to return a more conservative estimate which also satisfies a longer history. A conservative estimate potentially returns a higher feerate and is more likely to be sufficient for the desired target, but is not as responsive to short term drops in the prevailing fee market.  Must be one of: \"UNSET\" \"ECONOMICAL\" \"CONSERVATIVE\" |
   
   https://bitcoin.org/en/developer-reference#estimatesmartfee"
  [client {:as params :keys [conf_target estimate_mode]}]
  (perform client "estimatesmartfee" params))


(defn get-rpc-info
  "Returns details of the RPC server.
   
   https://bitcoin.org/en/developer-reference#getrpcinfo"
  [client]
  (perform client "getrpcinfo" nil))


(defn get-mining-info
  "Returns a json object containing mining-related information.
   
   https://bitcoin.org/en/developer-reference#getmininginfo"
  [client]
  (perform client "getmininginfo" nil))


(defn import-pruned-funds
  "Imports funds without rescan.
   
   | Name           | Type   | Presence                | Description                                                            |
   | -------------- | ------ | ----------------------- | ---------------------------------------------------------------------- |
   | rawtransaction | string | Required<br>(exactly 1) | A raw transaction in hex funding an already-existing address in wallet |
   | txoutproof     | string | Required<br>(exactly 1) | The hex output from gettxoutproof that contains the transaction        |
   
   https://bitcoin.org/en/developer-reference#importprunedfunds"
  [client {:as params :keys [rawtransaction txoutproof]}]
  (perform client "importprunedfunds" params))


(defn sign-message
  "Sign a message with the private key of an address.
   
   | Name    | Type   | Presence                | Description                                     |
   | ------- | ------ | ----------------------- | ----------------------------------------------- |
   | address | string | Required<br>(exactly 1) | The bitcoin address to use for the private key. |
   | message | string | Required<br>(exactly 1) | The message to create a signature of.           |
   
   https://bitcoin.org/en/developer-reference#signmessage"
  [client {:as params :keys [address message]}]
  (perform client "signmessage" params))


(defn abandon-transaction
  "Marks an in-wallet transaction and all its in-wallet descendants as abandoned. This allows their inputs to be respent.
   
   | Name | Type         | Presence                | Description        |
   | ---- | ------------ | ----------------------- | ------------------ |
   | txid | string (hex) | Required<br>(exactly 1) | The transaction id |
   
   https://bitcoin.org/en/developer-reference#abandontransaction"
  [client {:as params :keys [txid]}]
  (perform client "abandontransaction" params))


(defn get-difficulty
  "Returns the proof-of-work difficulty as a multiple of the minimum difficulty.
   
   https://bitcoin.org/en/developer-reference#getdifficulty"
  [client]
  (perform client "getdifficulty" nil))


(defn get-tx-out
  "Returns details about an unspent transaction output.
   
   | Name            | Type         | Presence                 | Description                                                                                            |
   | --------------- | ------------ | ------------------------ | ------------------------------------------------------------------------------------------------------ |
   | txid            | string       | Required<br>(exactly 1)  | The transaction id                                                                                     |
   | n               | number (int) | Required<br>(exactly 1)  | vout number                                                                                            |
   | include_mempool | boolean      | Optional<br>Default=true | Whether to include the mempool. Note that an unspent output that is spent in the mempool won't appear. |
   
   https://bitcoin.org/en/developer-reference#gettxout"
  [client {:as params :keys [txid n include_mempool]}]
  (perform client "gettxout" params))


(defn help
  "List all commands, or get help for a specified command.
   
   | Name    | Type   | Presence                         | Description                |
   | ------- | ------ | -------------------------------- | -------------------------- |
   | command | string | Optional<br>Default=all commands | The command to get help on |
   
   https://bitcoin.org/en/developer-reference#help"
  [client {:as params :keys [command]}]
  (perform client "help" params))


(defn get-descriptor-info
  "Analyses a descriptor.
   
   | Name       | Type   | Presence                | Description     |
   | ---------- | ------ | ----------------------- | --------------- |
   | descriptor | string | Required<br>(exactly 1) | The descriptor. |
   
   https://bitcoin.org/en/developer-reference#getdescriptorinfo"
  [client {:as params :keys [descriptor]}]
  (perform client "getdescriptorinfo" params))


(defn finalize-psbt
  "Finalize the inputs of a PSBT.
   
   | Name    | Type    | Presence                 | Description                                                                                                                               |
   | ------- | ------- | ------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------- |
   | psbt    | string  | Required<br>(exactly 1)  | A base64 string of a PSBT                                                                                                                 |
   | extract | boolean | Optional<br>Default=true | If true and the transaction is complete, extract and return the complete transaction in normal network serialization instead of the PSBT. |
   
   https://bitcoin.org/en/developer-reference#finalizepsbt"
  [client {:as params :keys [psbt extract]}]
  (perform client "finalizepsbt" params))


(defn list-wallet-dir
  "Returns a list of wallets in the wallet directory.
   
   https://bitcoin.org/en/developer-reference#listwalletdir"
  [client]
  (perform client "listwalletdir" nil))


(defn import-wallet
  "Imports keys from a wallet dump file (see dumpwallet).
   
   | Name     | Type   | Presence                | Description     |
   | -------- | ------ | ----------------------- | --------------- |
   | filename | string | Required<br>(exactly 1) | The wallet file |
   
   https://bitcoin.org/en/developer-reference#importwallet"
  [client {:as params :keys [filename]}]
  (perform client "importwallet" params))


(defn combine-raw-transaction
  "Combine multiple partially signed transactions into one transaction.
   
   | Name | Type       | Presence                | Description                                                  |
   | ---- | ---------- | ----------------------- | ------------------------------------------------------------ |
   | txs  | json array | Required<br>(exactly 1) | A json array of hex strings of partially signed transactions |
   
   https://bitcoin.org/en/developer-reference#combinerawtransaction"
  [client {:as params :keys [txs]}]
  (perform client "combinerawtransaction" params))


(defn import-address
  "Adds an address or script (in hex) that can be watched as if it were in your wallet but cannot be used to spend.
   
   | Name    | Type    | Presence                  | Description                                 |
   | ------- | ------- | ------------------------- | ------------------------------------------- |
   | address | string  | Required<br>(exactly 1)   | The Bitcoin address (or hex-encoded script) |
   | label   | string  | Optional<br>Default=\"\"    | An optional label                           |
   | rescan  | boolean | Optional<br>Default=true  | Rescan the wallet for transactions          |
   | p2sh    | boolean | Optional<br>Default=false | Add the P2SH version of the script as well  |
   
   https://bitcoin.org/en/developer-reference#importaddress"
  [client {:as params :keys [address label rescan p2sh]}]
  (perform client "importaddress" params))


(defn set-ban
  "Attempts to add or remove an IP/Subnet from the banned list.
   
   | Name     | Type         | Presence                  | Description                                                                                                                                                                                    |
   | -------- | ------------ | ------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | subnet   | string       | Required<br>(exactly 1)   | The IP/Subnet (see getpeerinfo for nodes IP) with an optional netmask (default is /32 = single IP)                                                                                             |
   | command  | string       | Required<br>(exactly 1)   | 'add' to add an IP/Subnet to the list, 'remove' to remove an IP/Subnet from the list                                                                                                           |
   | bantime  | number (int) | Optional<br>Default=0     | time in seconds how long (or until when if [absolute] is set) the IP is banned (0 or empty means using the default time of 24h which can also be overwritten by the -bantime startup argument) |
   | absolute | boolean      | Optional<br>Default=false | If set, the bantime must be an absolute timestamp in seconds since epoch (Jan 1 1970 GMT)                                                                                                      |
   
   https://bitcoin.org/en/developer-reference#setban"
  [client {:as params :keys [subnet command bantime absolute]}]
  (perform client "setban" params))


(defn get-added-node-info
  "Returns information about the given added node, or all added nodes (note that onetry addnodes are not listed here).
   
   | Name | Type   | Presence                      | Description                                                                                 |
   | ---- | ------ | ----------------------------- | ------------------------------------------------------------------------------------------- |
   | node | string | Optional<br>Default=all nodes | If provided, return information about this specific node, otherwise all nodes are returned. |
   
   https://bitcoin.org/en/developer-reference#getaddednodeinfo"
  [client {:as params :keys [node]}]
  (perform client "getaddednodeinfo" params))


(defn get-unconfirmed-balance
  "Returns the server's total unconfirmed balance.
   
   https://bitcoin.org/en/developer-reference#getunconfirmedbalance"
  [client]
  (perform client "getunconfirmedbalance" nil))


(defn sign-raw-transaction-with-wallet
  "Sign inputs for raw transaction (serialized, hex-encoded).
   
   | Name        | Type       | Presence                | Description                                                                                                                |
   | ----------- | ---------- | ----------------------- | -------------------------------------------------------------------------------------------------------------------------- |
   | hexstring   | string     | Required<br>(exactly 1) | The transaction hex string                                                                                                 |
   | prevtxs     | json array | Optional                | A json array of previous dependent transaction outputs                                                                     |
   | sighashtype | string     | Optional<br>Default=ALL | The signature hash type. Must be one of \"ALL\" \"NONE\" \"SINGLE\" \"ALL|ANYONECANPAY\" \"NONE|ANYONECANPAY\" \"SINGLE|ANYONECANPAY\" |
   
   https://bitcoin.org/en/developer-reference#signrawtransactionwithwallet"
  [client {:as params :keys [hexstring prevtxs sighashtype]}]
  (perform client "signrawtransactionwithwallet" params))


(defn submit-block
  "Attempts to submit new block to network.
   
   | Name    | Type   | Presence                    | Description                                                       |
   | ------- | ------ | --------------------------- | ----------------------------------------------------------------- |
   | hexdata | string | Required<br>(exactly 1)     | the hex-encoded block data to submit                              |
   | dummy   | string | Optional<br>Default=ignored | dummy value, for compatibility with BIP22. This value is ignored. |
   
   https://bitcoin.org/en/developer-reference#submitblock"
  [client {:as params :keys [hexdata dummy]}]
  (perform client "submitblock" params))


(defn scan-tx-out-set
  "EXPERIMENTAL warning: this call may be removed or changed in future releases.
   
   | Name        | Type       | Presence                | Description                                                                                                                                                                              |
   | ----------- | ---------- | ----------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | action      | string     | Required<br>(exactly 1) | The action to execute \"start\" for starting a scan \"abort\" for aborting the current scan (returns true when abort was successful) \"status\" for progress report (in %) of the current scan |
   | scanobjects | json array | Required<br>(exactly 1) | Array of scan objects Every scan object is either a string descriptor or an object:                                                                                                      |
   
   https://bitcoin.org/en/developer-reference#scantxoutset"
  [client {:as params :keys [action scanobjects]}]
  (perform client "scantxoutset" params))


(defn rescan-block-chain
  "Rescan the local blockchain for wallet related transactions.
   
   | Name         | Type         | Presence              | Description                                                                                                                 |
   | ------------ | ------------ | --------------------- | --------------------------------------------------------------------------------------------------------------------------- |
   | start_height | number (int) | Optional<br>Default=0 | block height where the rescan should start                                                                                  |
   | stop_height  | number (int) | Optional              | the last block height that should be scanned. If none is provided it will rescan up to the tip at return time of this call. |
   
   https://bitcoin.org/en/developer-reference#rescanblockchain"
  [client {:as params :keys [start_height stop_height]}]
  (perform client "rescanblockchain" params))


(defn get-received-by-label
  "Returns the total amount received by addresses with <label> in transactions with at least [minconf] confirmations.
   
   | Name    | Type         | Presence                | Description                                                   |
   | ------- | ------------ | ----------------------- | ------------------------------------------------------------- |
   | label   | string       | Required<br>(exactly 1) | The selected label, may be the default label using \"\".        |
   | minconf | number (int) | Optional<br>Default=1   | Only include transactions confirmed at least this many times. |
   
   https://bitcoin.org/en/developer-reference#getreceivedbylabel"
  [client {:as params :keys [label minconf]}]
  (perform client "getreceivedbylabel" params))


(defn remove-pruned-funds
  "Deletes the specified transaction from the wallet.
   
   | Name | Type   | Presence                | Description                                            |
   | ---- | ------ | ----------------------- | ------------------------------------------------------ |
   | txid | string | Required<br>(exactly 1) | The hex-encoded id of the transaction you are deleting |
   
   https://bitcoin.org/en/developer-reference#removeprunedfunds"
  [client {:as params :keys [txid]}]
  (perform client "removeprunedfunds" params))


(defn generate
  "Mine up to nblocks blocks immediately (before the RPC call returns) to an address in the wallet.
   
   | Name     | Type         | Presence                    | Description                                |
   | -------- | ------------ | --------------------------- | ------------------------------------------ |
   | nblocks  | number (int) | Required<br>(exactly 1)     | How many blocks are generated immediately. |
   | maxtries | number (int) | Optional<br>Default=1000000 | How many iterations to try.                |
   
   https://bitcoin.org/en/developer-reference#generate"
  [client {:as params :keys [nblocks maxtries]}]
  (perform client "generate" params))


(defn get-wallet-info
  "Returns an object containing various wallet state info.
   
   https://bitcoin.org/en/developer-reference#getwalletinfo"
  [client]
  (perform client "getwalletinfo" nil))


(defn bump-fee
  "Bumps the fee of an opt-in-RBF transaction T, replacing it with a new transaction B.
   
   | Name    | Type        | Presence                | Description           |
   | ------- | ----------- | ----------------------- | --------------------- |
   | txid    | string      | Required<br>(exactly 1) | The txid to be bumped |
   | options | json object | Optional                |                       |
   
   https://bitcoin.org/en/developer-reference#bumpfee"
  [client {:as params :keys [txid options]}]
  (perform client "bumpfee" params))


(defn list-received-by-label
  "List received transactions by label.
   
   | Name              | Type         | Presence                  | Description                                                       |
   | ----------------- | ------------ | ------------------------- | ----------------------------------------------------------------- |
   | minconf           | number (int) | Optional<br>Default=1     | The minimum number of confirmations before payments are included. |
   | include_empty     | boolean      | Optional<br>Default=false | Whether to include labels that haven't received any payments.     |
   | include_watchonly | boolean      | Optional<br>Default=false | Whether to include watch-only addresses (see 'importaddress').    |
   
   https://bitcoin.org/en/developer-reference#listreceivedbylabel"
  [client {:as params :keys [minconf include_empty include_watchonly]}]
  (perform client "listreceivedbylabel" params))


(defn get-block-chain-info
  "Returns an object containing various state info regarding blockchain processing.
   
   https://bitcoin.org/en/developer-reference#getblockchaininfo"
  [client]
  (perform client "getblockchaininfo" nil))


(defn send-many
  "Send multiple times.
   
   | Name            | Type         | Presence                                         | Description                                                                                                                                                                                                                                                          |
   | --------------- | ------------ | ------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | dummy           | string       | Required<br>(exactly 1)                          | Must be set to \"\" for backwards compatibility.                                                                                                                                                                                                                       |
   | amounts         | json object  | Required<br>(exactly 1)                          | A json object with addresses and amounts                                                                                                                                                                                                                             |
   | minconf         | number (int) | Optional<br>Default=1                            | Only use the balance confirmed at least this many times.                                                                                                                                                                                                             |
   | comment         | string       | Optional                                         | A comment                                                                                                                                                                                                                                                            |
   | subtractfeefrom | json array   | Optional                                         | A json array with addresses. The fee will be equally deducted from the amount of each selected address. Those recipients will receive less bitcoins than you enter in their corresponding amount field. If no addresses are specified here, the sender pays the fee. |
   | replaceable     | boolean      | Optional<br>Default=fallback to wallet's default | Allow this transaction to be replaced by a transaction with higher fees via BIP 125                                                                                                                                                                                  |
   | conf_target     | number (int) | Optional<br>Default=fallback to wallet's default | Confirmation target (in blocks)                                                                                                                                                                                                                                      |
   | estimate_mode   | string       | Optional<br>Default=UNSET                        | The fee estimate mode, must be one of: \"UNSET\" \"ECONOMICAL\" \"CONSERVATIVE\"                                                                                                                                                                                           |
   
   https://bitcoin.org/en/developer-reference#sendmany"
  [client
   {:as   params
    :keys [dummy amounts minconf comment subtractfeefrom replaceable conf_target estimate_mode]}]
  (perform client "sendmany" params))


(defn decode-script
  "Decode a hex-encoded script.
   
   | Name      | Type   | Presence                | Description            |
   | --------- | ------ | ----------------------- | ---------------------- |
   | hexstring | string | Required<br>(exactly 1) | the hex-encoded script |
   
   https://bitcoin.org/en/developer-reference#decodescript"
  [client {:as params :keys [hexstring]}]
  (perform client "decodescript" params))


(defn logging
  "Gets and sets the logging configuration.
   
   | Name    | Type       | Presence | Description                                        |
   | ------- | ---------- | -------- | -------------------------------------------------- |
   | include | json array | Optional | A json array of categories to add debug logging    |
   | exclude | json array | Optional | A json array of categories to remove debug logging |
   
   https://bitcoin.org/en/developer-reference#logging"
  [client {:as params :keys [include exclude]}]
  (perform client "logging" params))


(defn list-wallets
  "Returns a list of currently loaded wallets.
   
   https://bitcoin.org/en/developer-reference#listwallets"
  [client]
  (perform client "listwallets" nil))


(defn get-addresses-by-label
  "Returns the list of addresses assigned the specified label.
   
   | Name  | Type   | Presence                | Description |
   | ----- | ------ | ----------------------- | ----------- |
   | label | string | Required<br>(exactly 1) | The label.  |
   
   https://bitcoin.org/en/developer-reference#getaddressesbylabel"
  [client {:as params :keys [label]}]
  (perform client "getaddressesbylabel" params))


(defn encrypt-wallet
  "Encrypts the wallet with 'passphrase'.
   
   | Name       | Type   | Presence                | Description                                                                                      |
   | ---------- | ------ | ----------------------- | ------------------------------------------------------------------------------------------------ |
   | passphrase | string | Required<br>(exactly 1) | The pass phrase to encrypt the wallet with. It must be at least 1 character, but should be long. |
   
   https://bitcoin.org/en/developer-reference#encryptwallet"
  [client {:as params :keys [passphrase]}]
  (perform client "encryptwallet" params))


(defn list-banned
  "List all banned IPs/Subnets.
   
   https://bitcoin.org/en/developer-reference#listbanned"
  [client]
  (perform client "listbanned" nil))


(defn fund-raw-transaction
  "Add inputs to a transaction until it has enough in value to meet its out value.
   
   | Name      | Type        | Presence                                       | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
   | --------- | ----------- | ---------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | hexstring | string      | Required<br>(exactly 1)                        | The hex string of the raw transaction                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
   | options   | json object | Optional                                       | for backward compatibility: passing in a true instead of an object will result in {\"includeWatching\":true} \"replaceable\": bool,           (boolean, optional, default=fallback to wallet's default) Marks this transaction as BIP125 replaceable. Allows this transaction to be replaced by a transaction with higher fees \"conf_target\": n,              (numeric, optional, default=fallback to wallet's default) Confirmation target (in blocks) \"estimate_mode\": \"str\",        (string, optional, default=UNSET) The fee estimate mode, must be one of: \"UNSET\" \"ECONOMICAL\" \"CONSERVATIVE\" } |
   | iswitness | boolean     | Optional<br>Default=depends on heuristic tests | Whether the transaction hex is a serialized witness transaction If iswitness is not present, heuristic tests will be used in decoding                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
   
   https://bitcoin.org/en/developer-reference#fundrawtransaction"
  [client {:as params :keys [hexstring options iswitness]}]
  (perform client "fundrawtransaction" params))


(defn get-raw-transaction
  "Return the raw transaction data.
   
   | Name      | Type    | Presence                  | Description                                               |
   | --------- | ------- | ------------------------- | --------------------------------------------------------- |
   | txid      | string  | Required<br>(exactly 1)   | The transaction id                                        |
   | verbose   | boolean | Optional<br>Default=false | If false, return a string, otherwise return a json object |
   | blockhash | string  | Optional                  | The block in which to look for the transaction            |
   
   https://bitcoin.org/en/developer-reference#getrawtransaction"
  [client {:as params :keys [txid verbose blockhash]}]
  (perform client "getrawtransaction" params))


(defn validate-address
  "Return information about the given bitcoin address.
   
   | Name    | Type   | Presence                | Description                     |
   | ------- | ------ | ----------------------- | ------------------------------- |
   | address | string | Required<br>(exactly 1) | The bitcoin address to validate |
   
   https://bitcoin.org/en/developer-reference#validateaddress"
  [client {:as params :keys [address]}]
  (perform client "validateaddress" params))


(defn get-block-hash
  "Returns hash of block in best-block-chain at height provided.
   
   | Name   | Type         | Presence                | Description      |
   | ------ | ------------ | ----------------------- | ---------------- |
   | height | number (int) | Required<br>(exactly 1) | The height index |
   
   https://bitcoin.org/en/developer-reference#getblockhash"
  [client {:as params :keys [height]}]
  (perform client "getblockhash" params))


(defn combine-psbt
  "Combine multiple partially signed Bitcoin transactions into one transaction.
   
   | Name | Type       | Presence                | Description                                                     |
   | ---- | ---------- | ----------------------- | --------------------------------------------------------------- |
   | txs  | json array | Required<br>(exactly 1) | A json array of base64 strings of partially signed transactions |
   
   https://bitcoin.org/en/developer-reference#combinepsbt"
  [client {:as params :keys [txs]}]
  (perform client "combinepsbt" params))


(defn backup-wallet
  "Safely copies current wallet file to destination, which can be a directory or a path with filename.
   
   | Name        | Type   | Presence                | Description                       |
   | ----------- | ------ | ----------------------- | --------------------------------- |
   | destination | string | Required<br>(exactly 1) | The destination directory or file |
   
   https://bitcoin.org/en/developer-reference#backupwallet"
  [client {:as params :keys [destination]}]
  (perform client "backupwallet" params))


(defn create-raw-transaction
  "Create a transaction spending the given inputs and creating new outputs.
   
   | Name        | Type         | Presence                  | Description                                                                                                                                                                                                                                                                                      |
   | ----------- | ------------ | ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
   | inputs      | json array   | Required<br>(exactly 1)   | A json array of json objects                                                                                                                                                                                                                                                                     |
   | outputs     | json array   | Required<br>(exactly 1)   | a json array with outputs (key-value pairs), where none of the keys are duplicated. That is, each address can only appear once and there can only be one 'data' object. For compatibility reasons, a dictionary, which holds the key-value pairs directly, is also accepted as second parameter. |
   | locktime    | number (int) | Optional<br>Default=0     | Raw locktime. Non-0 value also locktime-activates inputs                                                                                                                                                                                                                                         |
   | replaceable | boolean      | Optional<br>Default=false | Marks this transaction as BIP125-replaceable. Allows this transaction to be replaced by a transaction with higher fees. If provided, it is an error if explicit sequence numbers are incompatible.                                                                                               |
   
   https://bitcoin.org/en/developer-reference#createrawtransaction"
  [client {:as params :keys [inputs outputs locktime replaceable]}]
  (perform client "createrawtransaction" params))


(defn load-wallet
  "Loads a wallet from a wallet file or directory.
   
   | Name     | Type   | Presence                | Description                        |
   | -------- | ------ | ----------------------- | ---------------------------------- |
   | filename | string | Required<br>(exactly 1) | The wallet directory or .dat file. |
   
   https://bitcoin.org/en/developer-reference#loadwallet"
  [client {:as params :keys [filename]}]
  (perform client "loadwallet" params))


(defn wallet-process-psbt
  "Update a PSBT with input information from our wallet and then sign inputs that we can sign for.
   
   | Name        | Type    | Presence                  | Description                                                                                                                                                          |
   | ----------- | ------- | ------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | psbt        | string  | Required<br>(exactly 1)   | The transaction base64 string                                                                                                                                        |
   | sign        | boolean | Optional<br>Default=true  | Also sign the transaction when updating                                                                                                                              |
   | sighashtype | string  | Optional<br>Default=ALL   | The signature hash type to sign with if not specified by the PSBT. Must be one of \"ALL\" \"NONE\" \"SINGLE\" \"ALL|ANYONECANPAY\" \"NONE|ANYONECANPAY\" \"SINGLE|ANYONECANPAY\" |
   | bip32derivs | boolean | Optional<br>Default=false | If true, includes the BIP 32 derivation paths for public keys if we know them                                                                                        |
   
   https://bitcoin.org/en/developer-reference#walletprocesspsbt"
  [client {:as params :keys [psbt sign sighashtype bip32derivs]}]
  (perform client "walletprocesspsbt" params))


(defn get-network-info
  "Returns an object containing various state info regarding P2P networking.
   
   https://bitcoin.org/en/developer-reference#getnetworkinfo"
  [client]
  (perform client "getnetworkinfo" nil))


(defn add-node
  "Attempts to add or remove a node from the addnode list.
   
   | Name    | Type   | Presence                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
   | ------- | ------ | ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | node    | string | Required<br>(exactly 1) | The node (see getpeerinfo for nodes). The node to add as a string in the form of `<IP address>:<port>`.  The IP address may be a hostname resolvable through DNS, an IPv4 address, an IPv4-as-IPv6 address, or an IPv6 address                                                                                                                                                                                                                                                                                                                                                            |
   | command | string | Required<br>(exactly 1) | 'add' to add a node to the list, 'remove' to remove a node from the list, 'onetry' to try a connection to the node once. What to do with the IP address above.  Options are:<br>• `add` to add a node to the addnode list.  Up to 8 nodes can be added additional to the default 8 nodes. Not limited by `-maxconnections`<br>• `remove` to remove a node from the list.  If currently connected, this will disconnect immediately<br>• `onetry` to immediately attempt connection to the node even if the outgoing connection slots are full; this will only attempt the connection once |
   
   https://bitcoin.org/en/developer-reference#addnode"
  [client {:as params :keys [node command]}]
  (perform client "addnode" params))


(defn get-connection-count
  "Returns the number of connections to other nodes.
   
   https://bitcoin.org/en/developer-reference#getconnectioncount"
  [client]
  (perform client "getconnectioncount" nil))


(defn prune-block-chain
  "Does PruneBlockChain.
   
   | Name   | Type         | Presence                | Description                                                                                                                                                                   |
   | ------ | ------------ | ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | height | number (int) | Required<br>(exactly 1) | The block height to prune up to. May be set to a discrete height, or a unix timestamp to prune blocks whose block time is at least 2 hours older than the provided timestamp. |
   
   https://bitcoin.org/en/developer-reference#pruneblockchain"
  [client {:as params :keys [height]}]
  (perform client "pruneblockchain" params))


(defn get-mem-pool-entry
  "Returns mempool data for given transaction.
   
   | Name | Type   | Presence                | Description                             |
   | ---- | ------ | ----------------------- | --------------------------------------- |
   | txid | string | Required<br>(exactly 1) | The transaction id (must be in mempool) |
   
   https://bitcoin.org/en/developer-reference#getmempoolentry"
  [client {:as params :keys [txid]}]
  (perform client "getmempoolentry" params))


(defn get-new-address
  "Returns a new Bitcoin address for receiving payments.
   
   | Name         | Type   | Presence                                | Description                                                                                                                                                                                                            |
   | ------------ | ------ | --------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | label        | string | Optional<br>Default=\"\"                  | The label name for the address to be linked to. It can also be set to the empty string \"\" to represent the default label. The label does not need to exist, it will be created if there is no label by the given name. |
   | address_type | string | Optional<br>Default=set by -addresstype | The address type to use. Options are \"legacy\", \"p2sh-segwit\", and \"bech32\".                                                                                                                                            |
   
   https://bitcoin.org/en/developer-reference#getnewaddress"
  [client {:as params :keys [label address_type]}]
  (perform client "getnewaddress" params))


(defn wallet-createfunded-psbt
  "Creates and funds a transaction in the Partially Signed Transaction format.
   
   | Name        | Type         | Presence                  | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
   | ----------- | ------------ | ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | inputs      | json array   | Required<br>(exactly 1)   | A json array of json objects                                                                                                                                                                                                                                                                                                                                                                                                                                                |
   | outputs     | json array   | Required<br>(exactly 1)   | a json array with outputs (key-value pairs), where none of the keys are duplicated. That is, each address can only appear once and there can only be one 'data' object. For compatibility reasons, a dictionary, which holds the key-value pairs directly, is also accepted as second parameter.                                                                                                                                                                            |
   | locktime    | number (int) | Optional<br>Default=0     | Raw locktime. Non-0 value also locktime-activates inputs                                                                                                                                                                                                                                                                                                                                                                                                                    |
   | options     | json object  | Optional                  | \"replaceable\": bool,           (boolean, optional, default=false) Marks this transaction as BIP125 replaceable. Allows this transaction to be replaced by a transaction with higher fees \"conf_target\": n,              (numeric, optional, default=Fallback to wallet's confirmation target) Confirmation target (in blocks) \"estimate_mode\": \"str\",        (string, optional, default=UNSET) The fee estimate mode, must be one of: \"UNSET\" \"ECONOMICAL\" \"CONSERVATIVE\" } |
   | bip32derivs | boolean      | Optional<br>Default=false | If true, includes the BIP 32 derivation paths for public keys if we know them                                                                                                                                                                                                                                                                                                                                                                                               |
   
   https://bitcoin.org/en/developer-reference#walletcreatefundedpsbt"
  [client
   {:as params :keys [inputs outputs locktime options bip32derivs]}]
  (perform client "walletcreatefundedpsbt" params))


(defn wallet-passphrase-change
  "Changes the wallet passphrase from 'oldpassphrase' to 'newpassphrase'.
   
   | Name          | Type   | Presence                | Description            |
   | ------------- | ------ | ----------------------- | ---------------------- |
   | oldpassphrase | string | Required<br>(exactly 1) | The current passphrase |
   | newpassphrase | string | Required<br>(exactly 1) | The new passphrase     |
   
   https://bitcoin.org/en/developer-reference#walletpassphrasechange"
  [client {:as params :keys [oldpassphrase newpassphrase]}]
  (perform client "walletpassphrasechange" params))


(defn get-chain-tips
  "Return information about all known tips in the block tree, including the main chain as well as orphaned branches.
   
   https://bitcoin.org/en/developer-reference#getchaintips"
  [client]
  (perform client "getchaintips" nil))


(defn import-multi
  "Import addresses/scripts (with private or public keys, redeem script (P2SH)), optionally rescanning the blockchain from the earliest creation time of the imported scripts.
   
   | Name     | Type        | Presence                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
   | -------- | ----------- | ----------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | requests | json array  | Required<br>(exactly 1) | Data to be imported \"range\": n or [n,n],                                       (numeric or array) If a ranged descriptor is used, this specifies the end or the range (in the form [begin,end]) to import \"internal\": bool,                                          (boolean, optional, default=false) Stating whether matching outputs should be treated as not incoming payments (also known as change) \"watchonly\": bool,                                         (boolean, optional, default=false) Stating whether matching outputs should be considered watchonly. \"label\": \"str\",                                            (string, optional, default='') Label to assign to the address, only allowed with internal=false \"keypool\": bool,                                           (boolean, optional, default=false) Stating whether imported public keys should be added to the keypool for when users request new addresses. Only allowed when wallet private keys are disabled }, ... ] |
   | options  | json object | Optional                |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
   
   https://bitcoin.org/en/developer-reference#importmulti"
  [client {:as params :keys [requests options]}]
  (perform client "importmulti" params))


(defn get-block-template
  "If the request parameters include a 'mode' key, that is used to explicitly select between the default 'template' request or a 'proposal'.
   
   | Name             | Type        | Presence                | Description                                                                                                                                                                      |
   | ---------------- | ----------- | ----------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | template_request | json object | Required<br>(exactly 1) | A json object in the following spec \"rules\": [           (json array, required) A list of strings \"support\",         (string) client side supported softfork deployment ... ], } |
   
   https://bitcoin.org/en/developer-reference#getblocktemplate"
  [client {:as params :keys [template_request]}]
  (perform client "getblocktemplate" params))


(defn abort-rescan
  "Stops current wallet rescan
   
   https://bitcoin.org/en/developer-reference#abortrescan"
  [client]
  (perform client "abortrescan" nil))


(defn verify-message
  "Verify a signed message.
   
   | Name      | Type   | Presence                | Description                                                                 |
   | --------- | ------ | ----------------------- | --------------------------------------------------------------------------- |
   | address   | string | Required<br>(exactly 1) | The bitcoin address to use for the signature.                               |
   | signature | string | Required<br>(exactly 1) | The signature provided by the signer in base 64 encoding (see signmessage). |
   | message   | string | Required<br>(exactly 1) | The message that was signed.                                                |
   
   https://bitcoin.org/en/developer-reference#verifymessage"
  [client {:as params :keys [address signature message]}]
  (perform client "verifymessage" params))


(defn lock-unspent
  "Updates list of temporarily unspendable outputs.
   
   | Name         | Type       | Presence                        | Description                                                            |
   | ------------ | ---------- | ------------------------------- | ---------------------------------------------------------------------- |
   | unlock       | boolean    | Required<br>(exactly 1)         | Whether to unlock (true) or lock (false) the specified transactions    |
   | transactions | json array | Optional<br>Default=empty array | A json array of objects. Each object the txid (string) vout (numeric). |
   
   https://bitcoin.org/en/developer-reference#lockunspent"
  [client {:as params :keys [unlock transactions]}]
  (perform client "lockunspent" params))


(defn set-network-active
  "Disable/enable all p2p network activity.
   
   | Name  | Type    | Presence                | Description                                 |
   | ----- | ------- | ----------------------- | ------------------------------------------- |
   | state | boolean | Required<br>(exactly 1) | true to enable networking, false to disable |
   
   https://bitcoin.org/en/developer-reference#setnetworkactive"
  [client {:as params :keys [state]}]
  (perform client "setnetworkactive" params))


(defn import-pub-key
  "Adds a public key (in hex) that can be watched as if it were in your wallet but cannot be used to spend.
   
   | Name   | Type    | Presence                 | Description                        |
   | ------ | ------- | ------------------------ | ---------------------------------- |
   | pubkey | string  | Required<br>(exactly 1)  | The hex-encoded public key         |
   | label  | string  | Optional<br>Default=\"\"   | An optional label                  |
   | rescan | boolean | Optional<br>Default=true | Rescan the wallet for transactions |
   
   https://bitcoin.org/en/developer-reference#importpubkey"
  [client {:as params :keys [pubkey label rescan]}]
  (perform client "importpubkey" params))


(defn disconnect-node
  "Immediately disconnects from the specified peer node.
   
   | Name    | Type         | Presence                                | Description                                |
   | ------- | ------------ | --------------------------------------- | ------------------------------------------ |
   | address | string       | Optional<br>Default=fallback to nodeid  | The IP address/port of the node            |
   | nodeid  | number (int) | Optional<br>Default=fallback to address | The node ID (see getpeerinfo for node IDs) |
   
   https://bitcoin.org/en/developer-reference#disconnectnode"
  [client {:as params :keys [address nodeid]}]
  (perform client "disconnectnode" params))


(defn derive-addresses
  "Derives one or more addresses corresponding to an output descriptor.
   
   | Name       | Type             | Presence                | Description                                                                                              |
   | ---------- | ---------------- | ----------------------- | -------------------------------------------------------------------------------------------------------- |
   | descriptor | string           | Required<br>(exactly 1) | The descriptor.                                                                                          |
   | range      | numeric or array | Optional                | If a ranged descriptor is used, this specifies the end or the range (in [begin,end] notation) to derive. |
   
   https://bitcoin.org/en/developer-reference#deriveaddresses"
  [client {:as params :keys [descriptor range]}]
  (perform client "deriveaddresses" params))


(defn get-node-addresses
  "Return known addresses which can potentially be used to find new nodes in the network.
   
   | Name  | Type         | Presence              | Description                                                                                 |
   | ----- | ------------ | --------------------- | ------------------------------------------------------------------------------------------- |
   | count | number (int) | Optional<br>Default=1 | How many addresses to return. Limited to the smaller of 2500 or 23% of all known addresses. |
   
   https://bitcoin.org/en/developer-reference#getnodeaddresses"
  [client {:as params :keys [count]}]
  (perform client "getnodeaddresses" params))


(defn get-address-info
  "Return information about the given bitcoin address.
   
   | Name    | Type   | Presence                | Description                                    |
   | ------- | ------ | ----------------------- | ---------------------------------------------- |
   | address | string | Required<br>(exactly 1) | The bitcoin address to get the information of. |
   
   https://bitcoin.org/en/developer-reference#getaddressinfo"
  [client {:as params :keys [address]}]
  (perform client "getaddressinfo" params))


(defn set-hd-seed
  "Set or generate a new HD wallet seed.
   
   | Name       | Type    | Presence                        | Description                                                                                                                                                                                                                                                                                                                                                                                      |
   | ---------- | ------- | ------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
   | newkeypool | boolean | Optional<br>Default=true        | Whether to flush old unused addresses, including change addresses, from the keypool and regenerate it. If true, the next address from getnewaddress and change address from getrawchangeaddress will be from this new seed. If false, addresses (including change addresses if the wallet already had HD Chain Split enabled) from the existing keypool will be used until it has been depleted. |
   | seed       | string  | Optional<br>Default=random seed | The WIF private key to use as the new HD seed. The seed value can be retrieved using the dumpwallet command. It is the private key marked hdseed=1                                                                                                                                                                                                                                               |
   
   https://bitcoin.org/en/developer-reference#sethdseed"
  [client {:as params :keys [newkeypool seed]}]
  (perform client "sethdseed" params))


(defn join-psbts
  "Joins multiple distinct PSBTs with different inputs and outputs into one PSBT with inputs and outputs from all of the PSBTs No input in any of the PSBTs can be in more than one of the PSBTs.
   
   | Name | Type       | Presence                | Description                                                     |
   | ---- | ---------- | ----------------------- | --------------------------------------------------------------- |
   | txs  | json array | Required<br>(exactly 1) | A json array of base64 strings of partially signed transactions |
   
   https://bitcoin.org/en/developer-reference#joinpsbts"
  [client {:as params :keys [txs]}]
  (perform client "joinpsbts" params))


(defn get-raw-mem-pool
  "Returns all transaction ids in memory pool as a json array of string transaction ids.
   
   | Name    | Type    | Presence                  | Description                                                |
   | ------- | ------- | ------------------------- | ---------------------------------------------------------- |
   | verbose | boolean | Optional<br>Default=false | True for a json object, false for array of transaction ids |
   
   https://bitcoin.org/en/developer-reference#getrawmempool"
  [client {:as params :keys [verbose]}]
  (perform client "getrawmempool" params))


(defn get-best-block-hash
  "Returns the hash of the best (tip) block in the longest blockchain.
   
   https://bitcoin.org/en/developer-reference#getbestblockhash"
  [client]
  (perform client "getbestblockhash" nil))


(defn import-priv-key
  "Adds a private key (as returned by dumpprivkey) to your wallet.
   
   | Name    | Type    | Presence                 | Description                        |
   | ------- | ------- | ------------------------ | ---------------------------------- |
   | privkey | string  | Required<br>(exactly 1)  | The private key (see dumpprivkey)  |
   | label   | string  | Optional                 | An optional label                  |
   | rescan  | boolean | Optional<br>Default=true | Rescan the wallet for transactions |
   
   https://bitcoin.org/en/developer-reference#importprivkey"
  [client {:as params :keys [privkey label rescan]}]
  (perform client "importprivkey" params))


(defn add-multi-sig-address
  "Adds a P2SH multisig address to the wallet.
   
   | Name         | Type         | Presence                                | Description                                                                 |
   | ------------ | ------------ | --------------------------------------- | --------------------------------------------------------------------------- |
   | nrequired    | number (int) | Required<br>(exactly 1)                 | The number of required signatures out of the n keys or addresses.           |
   | keys         | json array   | Required<br>(exactly 1)                 | A json array of bitcoin addresses or hex-encoded public keys                |
   | label        | string       | Optional                                | A label to assign the addresses to.                                         |
   | address_type | string       | Optional<br>Default=set by -addresstype | The address type to use. Options are \"legacy\", \"p2sh-segwit\", and \"bech32\". |
   
   https://bitcoin.org/en/developer-reference#addmultisigaddress"
  [client {:as params :keys [nrequired keys label address_type]}]
  (perform client "addmultisigaddress" params))


(defn get-peer-info
  "Returns data about each connected network node as a json array of objects.
   
   https://bitcoin.org/en/developer-reference#getpeerinfo"
  [client]
  (perform client "getpeerinfo" nil))


(defn list-received-by-address
  "List balances by receiving address.
   
   | Name              | Type         | Presence                  | Description                                                       |
   | ----------------- | ------------ | ------------------------- | ----------------------------------------------------------------- |
   | minconf           | number (int) | Optional<br>Default=1     | The minimum number of confirmations before payments are included. |
   | include_empty     | boolean      | Optional<br>Default=false | Whether to include addresses that haven't received any payments.  |
   | include_watchonly | boolean      | Optional<br>Default=false | Whether to include watch-only addresses (see 'importaddress').    |
   | address_filter    | string       | Optional                  | If present, only return information on this address.              |
   
   https://bitcoin.org/en/developer-reference#listreceivedbyaddress"
  [client
   {:as   params
    :keys [minconf include_empty include_watchonly address_filter]}]
  (perform client "listreceivedbyaddress" params))


(defn list-since-block
  "Get all transactions in blocks since block [blockhash], or all transactions if omitted.
   
   | Name                 | Type         | Presence                  | Description                                                                                                                                                                |
   | -------------------- | ------------ | ------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | blockhash            | string       | Optional                  | If set, the block hash to list transactions since, otherwise list all transactions.                                                                                        |
   | target_confirmations | number (int) | Optional<br>Default=1     | Return the nth block hash from the main chain. e.g. 1 would mean the best block hash. Note: this is not used as a filter, but only affects [lastblock] in the return value |
   | include_watchonly    | boolean      | Optional<br>Default=false | Include transactions to watch-only addresses (see 'importaddress')                                                                                                         |
   | include_removed      | boolean      | Optional<br>Default=true  | Show transactions that were removed due to a reorg in the \"removed\" array (not guaranteed to work on pruned nodes)                                                         |
   
   https://bitcoin.org/en/developer-reference#listsinceblock"
  [client
   {:as params
    :keys
        [blockhash target_confirmations include_watchonly include_removed]}]
  (perform client "listsinceblock" params))


(defn send-to-address
  "Send an amount to a given address.
   
   | Name                  | Type              | Presence                                         | Description                                                                                                                                                       |
   | --------------------- | ----------------- | ------------------------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | address               | string            | Required<br>(exactly 1)                          | The bitcoin address to send to.                                                                                                                                   |
   | amount                | numeric or string | Required<br>(exactly 1)                          | The amount in BTC to send. eg 0.1                                                                                                                                 |
   | comment               | string            | Optional                                         | A comment used to store what the transaction is for. This is not part of the transaction, just kept in your wallet.                                               |
   | comment_to            | string            | Optional                                         | A comment to store the name of the person or organization to which you're sending the transaction. This is not part of the transaction, just kept in your wallet. |
   | subtractfeefromamount | boolean           | Optional<br>Default=false                        | The fee will be deducted from the amount being sent. The recipient will receive less bitcoins than you enter in the amount field.                                 |
   | replaceable           | boolean           | Optional<br>Default=fallback to wallet's default | Allow this transaction to be replaced by a transaction with higher fees via BIP 125                                                                               |
   | conf_target           | number (int)      | Optional<br>Default=fallback to wallet's default | Confirmation target (in blocks)                                                                                                                                   |
   | estimate_mode         | string            | Optional<br>Default=UNSET                        | The fee estimate mode, must be one of: \"UNSET\" \"ECONOMICAL\" \"CONSERVATIVE\"                                                                                        |
   
   https://bitcoin.org/en/developer-reference#sendtoaddress"
  [client
   {:as   params
    :keys [address amount comment comment_to subtractfeefromamount replaceable conf_target estimate_mode]}]
  (perform client "sendtoaddress" params))


(defn create-multi-sig
  "Creates a multi-signature address with n signature of m keys required.
   
   | Name         | Type         | Presence                   | Description                                                                 |
   | ------------ | ------------ | -------------------------- | --------------------------------------------------------------------------- |
   | nrequired    | number (int) | Required<br>(exactly 1)    | The number of required signatures out of the n keys.                        |
   | keys         | json array   | Required<br>(exactly 1)    | A json array of hex-encoded public keys.                                    |
   | address_type | string       | Optional<br>Default=legacy | The address type to use. Options are \"legacy\", \"p2sh-segwit\", and \"bech32\". |
   
   https://bitcoin.org/en/developer-reference#createmultisig"
  [client {:as params :keys [nrequired keys address_type]}]
  (perform client "createmultisig" params))


(defn dump-wallet
  "Dumps all wallet keys in a human-readable format to a server-side file.
   
   | Name     | Type   | Presence                | Description                                                      |
   | -------- | ------ | ----------------------- | ---------------------------------------------------------------- |
   | filename | string | Required<br>(exactly 1) | The filename with path (either absolute or relative to bitcoind) |
   
   https://bitcoin.org/en/developer-reference#dumpwallet"
  [client {:as params :keys [filename]}]
  (perform client "dumpwallet" params))


(defn wallet-passphrase
  "Stores the wallet decryption key in memory for 'timeout' seconds.
   
   | Name       | Type         | Presence                | Description                                                                     |
   | ---------- | ------------ | ----------------------- | ------------------------------------------------------------------------------- |
   | passphrase | string       | Required<br>(exactly 1) | The wallet passphrase                                                           |
   | timeout    | number (int) | Required<br>(exactly 1) | The time to keep the decryption key in seconds; capped at 100000000 (~3 years). |
   
   https://bitcoin.org/en/developer-reference#walletpassphrase"
  [client {:as params :keys [passphrase timeout]}]
  (perform client "walletpassphrase" params))


(defn get-tx-out-proof
  "Returns a hex-encoded proof that \"txid\" was included in a block.
   
   | Name      | Type       | Presence                | Description                                              |
   | --------- | ---------- | ----------------------- | -------------------------------------------------------- |
   | txids     | json array | Required<br>(exactly 1) | A json array of txids to filter                          |
   | blockhash | string     | Optional                | If specified, looks for txid in the block with this hash |
   
   https://bitcoin.org/en/developer-reference#gettxoutproof"
  [client {:as params :keys [txids blockhash]}]
  (perform client "gettxoutproof" params))


(defn get-mem-pool-info
  "Returns details on the active state of the TX memory pool.
   
   https://bitcoin.org/en/developer-reference#getmempoolinfo"
  [client]
  (perform client "getmempoolinfo" nil))


(defn stop
  "Stop Bitcoin server.
   
   https://bitcoin.org/en/developer-reference#stop"
  [client]
  (perform client "stop" nil))


(defn get-transaction
  "Get detailed information about in-wallet transaction <txid>.
   
   | Name              | Type    | Presence                  | Description                                                                  |
   | ----------------- | ------- | ------------------------- | ---------------------------------------------------------------------------- |
   | txid              | string  | Required<br>(exactly 1)   | The transaction id                                                           |
   | include_watchonly | boolean | Optional<br>Default=false | Whether to include watch-only addresses in balance calculation and details[] |
   
   https://bitcoin.org/en/developer-reference#gettransaction"
  [client {:as params :keys [txid include_watchonly]}]
  (perform client "gettransaction" params))


(defn get-received-by-address
  "Returns the total amount received by the given address in transactions with at least minconf confirmations.
   
   | Name    | Type         | Presence                | Description                                                   |
   | ------- | ------------ | ----------------------- | ------------------------------------------------------------- |
   | address | string       | Required<br>(exactly 1) | The bitcoin address for transactions.                         |
   | minconf | number (int) | Optional<br>Default=1   | Only include transactions confirmed at least this many times. |
   
   https://bitcoin.org/en/developer-reference#getreceivedbyaddress"
  [client {:as params :keys [address minconf]}]
  (perform client "getreceivedbyaddress" params))


(defn verify-chain
  "Verifies blockchain database.
   
   | Name       | Type         | Presence | Description                             |
   | ---------- | ------------ | -------- | --------------------------------------- |
   | checklevel | number (int) | Optional | How thorough the block verification is. |
   | nblocks    | number (int) | Optional | The number of blocks to check.          |
   
   https://bitcoin.org/en/developer-reference#verifychain"
  [client {:as params :keys [checklevel nblocks]}]
  (perform client "verifychain" params))


(defn prioritise-transaction
  "Accepts the transaction into mined blocks at a higher (or lower) priority.
   
   | Name      | Type         | Presence                | Description                                                                                                                                                                                                                                                                                                               |
   | --------- | ------------ | ----------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | txid      | string       | Required<br>(exactly 1) | The transaction id.                                                                                                                                                                                                                                                                                                       |
   | dummy     | number (int) | Optional                | API-Compatibility for previous API. Must be zero or null. DEPRECATED. For forward compatibility use named arguments and omit this parameter.                                                                                                                                                                              |
   | fee_delta | number (int) | Required<br>(exactly 1) | The fee value (in satoshis) to add (or subtract, if negative). Note, that this value is not a fee rate. It is a value to modify absolute fee of the TX. The fee is not actually paid, only the algorithm for selecting transactions into a block considers the transaction as it would have paid a higher (or lower) fee. |
   
   https://bitcoin.org/en/developer-reference#prioritisetransaction"
  [client {:as params :keys [txid dummy fee_delta]}]
  (perform client "prioritisetransaction" params))
