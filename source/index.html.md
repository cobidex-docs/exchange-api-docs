---
title: API Reference



toc_footers:
  - <a href='https://www.cobidex.com/en_US/'>Cobidex</a>


search: true

code_clipboard: true
---


# <span id="Introduction">Introduction</span>

Welcome to [Cobidex](https://www.cobidex.com/en_US/) API document for developers.

This file provides the related API application introduction. Open-API includes the port to acquire balance, all orders ,and all transaction record. Ws-API response for the port of K line functions.


# <span id="startToUse">Getting Started</span>
REST, a.k.a Respresntational State Transfer, is an architectural style that defines a set of constraints and properties based on HTTP. REST is known for its clear structure, readability, standardization and scalability. Its advantages are as follows:

- Each URL represents one web resource in RESTful architecture.
- Acting as a representation of resources between client and server.
- Client is enabled to operate server-side resources with 4 HTTP requests - representational state transfer.

Developers are recommended to use REST API to proceed spot trading and withdrawals.



# <span id="a1">Encrypted Verification of API</span>

## <span id="a2">Generate an API Key</span>

Before signing any request, you must generate an API key on API management on users dashboard via cobidex website. After generating the key, there are three things you must bear in mind:

- API Key
 
- Secret Key
 

API Key and Secret are randomly generated and provided, Passphrase is set by user.
## <span id="a3">Initiate a Request</span>
All REST requests must include the following headings:

- ACCESS-KEY API Key as a string.
- ACCESS-SIGN uses base64-encoded signatures (see Signed Messages).
- ACCESS-TIMESTAMP is the timestamp of your request.header MUST be number of seconds since Unix Epoch in UTC. Decimal values are allowed.
- All requests should contain content like application/x-www-form-urlencoded and be valid JSON.

## <span id="a4">Signature</span>
Generate a string to be signed
    -   [open-api Demo](https://github.com/cobidex-docs/exchange-api-docs/blob/main/demo/demo.java) 
    
1、Sort the parameters in ascending order of their parameter names in lexicographic order

2、Traversing the sorted dictionary, splicing all parameters together in "keyvalue" format (non-null parameters)

3、Use MD5 to treat signature strings

**For example:**

api_key = 1234567

time = 12312312312137

secret_key = 789654

sign=md5(api_key1234567time12312312312137789654)


## <span id="a6">Request Process</span>

The root URL for REST access：``` https://api.cobidex.com ```

###  <span id="a7">Request</span>
All requests are based on Https protocol, contentType in the request header must be uniformly set as: ‘application/x-www-form-urlencoded’.

**Request Process Descriptions**

1、Request parameter: parameter encapsulation based on the port request.

2、Submitting request parameter: submit the encapsulated parameter request to the server via POST/GET/PUT/DELETE or other methods.

3、Server response: the server will first perform a security validation, then send back the requested data to the client in JSON format.

4、Data processing: processing server response data.

**Success**

HTTP status code 200 indicates a successful response and may contain content. If the response contains content, it will appear in the corresponding returned content.

**Common Error Code**

- 400 Bad Request – Invalid request format

- 401 Unauthorized – Invalid API Key

- 403 Forbidden – You do not have access to the requested resource

- 404 Not Found

- 429 Too Many Requests

- 500 Internal Server Error

We had a problem with our server

###  <span  id="a8">Pagination</span>

All REST requests that return datasets use cursor-based pagination.

Cursor-based pagination allows results to be obtained before and after the current page of the result and is well suited for real-time data. The subsequent requests can specify the direction of the requested data based on the current returned results, before and/or after it. The before and after cursors can be used by the response headers CB-BEFORE and CB-AFTER.

**For example:**

```GET /orders?before=2&limit=30```


## <span id="a9">Standards and Specification</span>

### <span id="b1">Timestamp</span> 
Unless otherwise specified, all timestamps in APIs are returned in microseconds.

The ACCESS-TIMESTAMP header must be the number of seconds since UTC's time Unix Epoch. Decimal values are allowed. Your timestamp must be within 30 seconds of the API service time, otherwise your request will be considered expired and rejected. If you think there is a large time difference between your server and the API server, then we recommend that you use the time point to check the API server time.

###  <span id="b2">For example</span> 
1524801032573

###  <span id="b3">Numbers</span> 
In order to maintain the accuracy of cross-platform, decimal numbers are returned as strings. We suggest that you might be better to convert the number to string when issuing the request to avoid truncation and precision errors. Integers (such as transaction number and sequence) do not need quotation marks.

###  <span id="b4">Rate Limits</span>  
When a rate limit is exceeded, a status of 429 Too Many Requests will be returned.

REST API

- Public interface: We limit the invocation of public interface via IP: up to 6 requests every 2s.

- Private interface: We limit the invocation of private interface via user ID: up to 6 requests every 2s.

- Special restrictions on specified interfaces are specified.


# <span id="b5"> Spot API</span>

## <span id="1">Balance of assets</span>

1. Interface address: /open/api/user/account
2. Interface specification: (get request)Balance of the assets


|parameter|	Fill in type|	Explain|
|--------|--------|--------|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|--------|--------|--------|
|code|	0|	 |
|msg|	"suc"|	code>0fail|
|data|	{<br>"total_asset":432323.23,<br>"coin_list":[<br>{"coin":"btc","normal":32323.233,"locked":32323.233,"btcValuatin":112.33},<br>{"coin":"ltc","normal":32323.233,"locked":32323.233,"btcValuatin":112.33},<br>]<br>}<br>|total_asset:total assets<br>normal:Balance account<br>locked：Frozen accounts<br>btcValuatin：BTCValuation|

## <span id="2">Acquire full delegation</span>

1. Interface address:/open/api/v2/all_order
2. Interface specification:(getrequest)Acquire full delegation
3. startDate，endDate interval cannot exceed ten minutes
4. No startDate, endDate, default first 10 minutes

* Old interface/open/api/all_order It is still reserved, but not recommended

* v2Version change: Remove from the result return value tradeListTransaction record,Enhance efficiency;If transaction information for a single order is required

you can use /open/api/order_info interface and check it

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol|	Must fill|	Market mark，btcusdt，See below for details|
|startDate|	Selective filling|	（Added) Start time, accurate to seconds“yyyy-MM-dd mm:hh:ss”|
|endDate|	Selective filling|	（Added) End time, accurate to seconds“yyyy-MM-dd mm:hh:ss”|
|pageSize|	Selective filling|	Page size|
|page|	Selective filling|	Page number|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|


Return value:

|field|	Example|	explain|
|-----|------|---------|
|code|	0|	 |
|msg|	"suc"|	code>0fail|
|data|	as follows:|
```ruby
{
    "count":10,
    "orderList":[
        {
            "side":"BUY",
            "total_price":"0.10000000",
            "created_at":1510993841000,
            "avg_price":"0.10000000",
            "countCoin":"btc",
            "source":1,
            "type":1,
            "side_msg":"Purchase",
            "volume":"1.000",
            "price":"0.10000000",
            "source_msg":"WEB",
            "status_msg":"Full deal",
            "deal_volume":"1.00000000",
            "id":424,
            "remain_volume":"0.00000000",
            "baseCoin":"eth",
            "status":2
        },
        {
            "side":"SELL",
            "total_price":"0.09900000",
            "created_at":1510993715000,
            "avg_price":"0.10000000",
            "countCoin":"btc",
            "source":1,
            "type":1,
            "side_msg":"Sell out",
            "volume":"1.000",
            "price":"0.09900000",
            "source_msg":"WEB",
            "status_msg":"Full deal",
            "deal_volume":"1.00000000",
            "id":423,
            "remain_volume":"0.00000000",
            "baseCoin":"eth",
            "status":2
        }
    ]
}
```

|Crypto currency symbol|cobidex-btc pairs|cobidex-usdt pairs|
|----------|-------|-------|
|btc|	-|	btcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|
|etc|	etcbtc|	etcusdt|
|usdt|	-|	-|


## <span id="3">Obtain all transaction records</span>

1. Interface address:/open/api/all_trade
2. Interface Description: (get request) Get all transaction records

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol|	Must fill|	Market mark，btcusdt，See below for details|
|startDate|	Selective filling|	(Added) Start time, accurate to seconds“yyyy-MM-dd HH:mm:ss”|
|endDate|	Selective filling|	(Added) End time, accurate to seconds“yyyy-MM-dd HH:mm:ss”|
|pageSize|	Selective filling|	Page size|
|page|	Selective filling|	Page number|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sort|	Selective filling|	1Representing reverse order|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|-----|------|---------|
|code|	0|	 |
|msg|	"suc"|	code>0fail|
| data|	as follows:|
```ruby
{
    "count":22,
    "resultList":[
        {
            "volume":"1.000",
            "side":"BUY",
            "feeCoin":"YLB",
            "price":"0.10000000",
            "fee":"0.16431104",
            "ctime":1510996571195,
            "deal_price":"0.10000000",
            "id":306,
            "type":"Purchase",
            "bid_id":1001,
            "ask_id":1002,
            "bid_user_id":10001,
            "ask_user_id":10001
 
        },
        {
            "volume":"0.850",
            "side":"BUY",
            "feeCoin":"YLB",
            "price":"0.10000000",
            "fee":"0.13966438",
            "ctime":1510996571190,
            "deal_price":"0.08500000",
            "id":305,
            "type":"Purchase",
            "bid_id":1001,
            "ask_id":1002,
            "bid_user_id":10001,
            "ask_user_id":10001
        },
        {
            "volume":"0.010",
            "side":"BUY",
            "feeCoin":"YLB",
            "price":"0.10000000",
            "fee":"0.00164311",
            "ctime":1510995560344,
            "deal_price":"0.00100000",
            "id":291,
            "type":"Purchase",
            "bid_id":1001,
            "ask_id":1002,
            "bid_user_id":10001,
            "ask_user_id":10001
        }
    ]
}
```
|Crypto currency symbol|cobidex-btc paris|cobidex-usdt paris|
|----------|-------|-------|
|btc|	-|	btcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|
|etc|	etcbtc|	etcusdt|
|usdt|	-|	-|



##  <span id="4">Cancellation of the order</span>

1. Interface address:/open/api/cancel_order
2. Interface specification:(post Request) Cancellation of all orders as per crypto currenty paris

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|order_id|	Must fill|	OrderID|
|symbol|	Must fill|	Market mark，ethbtc，See below for details|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|------------|--------|------------------|
|code	|0	 |
|msg|	"suc"|	code>0fail|
|data|	“”|

|Crypto currency symbol|cobidex-btc pairs（cobidex201）|
|------------|----------|
|btc|	-|
|eth|	ethbtc|
|ltc|	ltcbtc|
|etc|	etcbtc|


##  <span id="5">Cancellation of all orders of attorney according to currency pair</span>

1. Interface address:/open/api/cancel_order_all
2. Interface specification:(postrequest)Cancellation of all orders of attorney according to currency pair（Up to 2,000 cancellations, more than 2,000 please revoke）

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol|	Must fill|	Market mark，ethbtc，See below for details|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field	|Example	|explain|
|------------|--------|--------------|
|code	|0	|
|msg	|"suc"	|code>0fail|
|data	|“”|

 
|Crypto currency symbol|cobidex-btc pairs（cobidex201）|
|------------|------------|
|btc|	-|
|eth|	ethbtc|
|ltc|	ltcbtc|
|etc|	etcbtc|


## <span id="6">Create order</span>


1. Interface address:/open/api/create_order
2. Interface specification:(post Request) Create an order

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|side|	Must fill|	Direction of businessBUY、SELL|
|type|	Must fill|	Type of list，1:limit order、2:market order|
|volume| 	Must fill|	Purchase quantity（Polysemy, multiplexing fields）<br>type=1:Represents the quantity of sales and purchases<br>type=2:Buy means the total price，Selling represents the total number<br>Trading restrictions user/me-User information|
|price|	Selective filling|	Authorized unit price：type=2：No need for this parameter|
|symbol|	Must fill|	Market mark，ethbtc|
|fee_is_user_cobidex_coin|	Selective filling|	（Redundant fields, ignored）0，When the cobidex has the platform currency, this parameter indicates whether to use the platform currency to pay the handling fee, 0 no, 1 yes.|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|-----|------|---------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	{"order_id":34343}|Successful return to the transactionID|

|Crypto currency symbol|cobidex-btc pairs（cobidex201）|
|------------|------------|
|btc|	-|
|eth|	ethbtc|
|ltc|	ltcbtc|
|etc|	etcbtc|


##  <span id="7">Get all trading pairs quotations on the market</span>
1. Interface address:/open/api/get_allticker
2. Interface specification:(get Request) Get all trading pairs quotations on the market 

* This interface does not perform signature verification
* Parameter: None

|field|	Example|	explain|
|------------|--------|-----------------------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	as follows：|Return Value Description<br>date: Server time when data is returned <br>symbol: Transaction pairs（Transaction pairs1(base)Abbreviation_Transaction pairs2(quote)Abbreviation） <br>buy: Buy one price <br>high: Highest price <br>last: Latest Transaction Price <br>low: Minimum price <br>sell: Selling price <br>vol: Volume (latest 24 hours)<br>rose:Ups and downs|
```ruby
{
   "date": 1534335607859,
   "ticker": [
     {
       "symbol": "btcusdt",
       "high": 7408.35984546,
       "vol": 0.01,
       "last": 1,
       "low": 7408.35984546,
       "buy": "3700.00000000",
       "sell": "7408.35984546",
       "rose": 0
     },
     {
       "symbol": "ethusdt",
       "high": 535.96,
       "vol": 6366.8591,
       "last": 20,
       "low": 279.57,
       "rose": -0.44564773
     },
     
     {
       "symbol": "ethbtc",
       "high": 1,
       "vol": 281261,
       "last": 0.1,
       "low": 0.044039,
       "buy": "0.044049",
       "sell": "0.044049",
       "rose": -0.00022701
     },
     {
       "symbol": "nobbtc",
       "high": 0.007419,
       "vol": 1998,
       "last": 1,
       "low": 0.007419,
       "sell": "0.00741900",
       "rose": 0
     },
     {
       "symbol": "ltceth",
       "last": 0.18519949,
       "buy": "1.00000000",
       "sell": "0.18328001"
     }
   ]
 }
```


## <span id="8">Getting K-line data</span>


1. Interface address:/open/api/get_records
2. Interface specification:(get request)Getting K-line data

* This interface does not perform signature verification

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol|	Must fill|	Market mark，See below for details|
|period|	Must fill|	In minutes，The analogy is 1 in a minute，One day is1440|

|Crypto currency symbol|cobidex-btc pairs|cobidex-usdt pairs|
|------------|----------|----------|
|btc|	-|	btcusdt|
|etc|	etcbtc|	etcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|

Return value:

|field|	Example|	explain|
|------------|-----------|----------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	as follows：|

```ruby
[
        [
            1514445780,  //time stamp
            1.12,        //Opening price
            1.12,        //Highest
            1.12,        //minimum
            1.12,        //Closing price
            0            //volume

        ],
        [
            1514445840,
            1.12,
            1.12,
            1.12,
            1.12,
            0
        ],
        [
            1514445900,
            1.12,
            1.12,
            1.12,
            1.12,
            0
        ]
]
```



##  <span id="9">Get the current market quotations</span>


1. Interface address:/open/api/get_ticker
2. Interface specification:(get Request) to get the current market quotations 

* This interface does not perform signature verification

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol|	Must fill|	Market mark，btcusdt，See below for details|

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0	| 
|msg|	"suc"|	code>0fail|
|data|	as follows：|
```ruby
{
    "high": 1,//Maximum value
    "vol": 10232.26315789,//Trading volume
    "last": 173.60263169,//Latest Transaction Price
    "low": 0.01,//Minimum value
    "buy": "0.01000000",//Buy one price
    "sell": "1.12345680",//Selling price
    "rose": -0.44564773,//Ups and downs
    "time": 1514448473626
}
```
|Crypto currency symbol|cobidex-btc paris|cobidex-usdt pairs|
|------------|----------|----------|
|btc|	-|	btcusdt|
|etc|	etcbtc|	etcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|






## <span id="10">Acquisition of Trading Records</span>

1. Interface address:/open/api/get_trades
2. Interface specification:(get Request) to obtain market transaction records

* This interface does not perform signature verification

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol|	Must fill|	Market mark，See below for details|

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0	| 
|msg|	"suc"|	code>0fail|
|data|	as follows：|
```ruby
[
        {
            "amount": 0.55,//volume
            "price": 0.18519949,//Transaction price
            "id": 447121,
            "type": "buy"//Businesstype，Buy asbuy，buysell
        },
        {
            "amount": 16.45,
            "price": 0.18335468,
            "id": 447120,
            "type": "buy"
        },
        {
            "amount": 2,
            "price": 0.18335468,
            "id": 447119,
            "type": "buy"
        },
        {
            "amount": 2.92,
            "price": 0.183324003,
            "id": 447118,
            "type": "sell"
        }
]
```
|Crypto currency symbol|cobidex-btc pairs|cobidex-usdt pairs|
|------------|-----------|----------|
|btc|	-|	btcusdt|
|etc|	etcbtc|	etcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|







##  <span id="11">Get the latest transaction price of each crypto currency pair</span>


1. Interface address:/open/api/market
2. Interface specification:(getRequest) Get the latest transaction price of each pair of currencies

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	{"btcusdt":15000,"ethusdt":800}|

|Crypto currency symbol|cobidex-btc pairs|cobidex-usdt pairs|
|------------|----------|----------|
|btc|	-|	btcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|
|etc|	etcbtc|	etcusdt|



##  <span id="12">Search the depth of the order book</span>


1. Interface address:/open/api/market_dept
2. Interface specification:(getrequest)Search the depth of buying and selling

* This interface does not perform signature verification

|parameter|	Fill in type|	Explain|
|------------|--------|-----------------------------|
|symbol	|Must fill|	Market mark，ethbtc，See below for details|
|type|	Must fill|	Depth type，step0, step1, step2（Merger depth0-2）；step0time，The highest accuracy|

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	as follows：|
```ruby
{  
    "tick":{
        "asks":[//Selling
            {22112.22,0.9332},
            {22112.21,0.2},
            {22112.21,0.2},
            {22112.21,0.2},
            {22112.21,0.2},
        ],
        "bids":[//Bid
            {22111.22,0.9332},
            {22111.21,0.2},
            {22112.21,0.2},
            {22112.21,0.2},
            {22112.21,0.2},
        ]
    }
}
```


## <span id="14">Get the current delegation</span>


1. Interface address:/open/api/v2/new_order
2. Interface specification:(getrequest)Get the current delegation（Including uncompleted and ongoing commissions）

* Old interface /open/api/new_order It is still reserved, but not recommended

* v2Version change: Remove the tradeList transaction record from the result return value to improve efficiency;If you need transaction information for a single order, you can use /open/api/order_info interface and check it

|parameter|	Fill in type|	Explain|
|------------|--------|--------------------------------------|
|symbo|l	Must fill|	Market mark，btcusdt，See below for details|
|pageSize|	Selective filling|	Page size|
|page|	Selective filling|	Page number|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	as follows:|Order status(status)Explain：<br>INIT(0,"Initial order，No deal has not entered the handicap"),<br>NEW_(1,"New order，Unfinished business enters the market"),<br>FILLED(2,"Full deal"),<br>PART_FILLED(3,"Partial transaction"),<br>CANCELED(4,"Withdrawal of documents"),<br>PENDING_CANCEL(5,"Withdrawal of order"),<br>EXPIRED(6,"Abnormal order");|
```javascript
{
    "count":10,
    "resultList":[
        {
            "side":"BUY",
            "total_price":"0.10000000",
            "created_at":1510993841000,
            "avg_price":"0.10000000",
            "countCoin":"btc",
            "source":1,
            "type":1,
            "side_msg":"Purchase",
            "volume":"1.000",
            "price":"0.10000000",
            "source_msg":"WEB",
            "status_msg":"Full deal",
            "deal_volume":"1.00000000",
            "id":424,
            "remain_volume":"0.00000000",
            "baseCoin":"eth",
            "status":2
        },
        {
            "side":"SELL",
            "total_price":"0.09900000",
            "created_at":1510993715000,
            "avg_price":"0.10000000",
            "countCoin":"btc",
            "source":1,
            "type":1,
            "side_msg":"Sell out",
            "volume":"1.000",
            "price":"0.09900000",
            "source_msg":"WEB",
            "status_msg":"Full deal",
            "deal_volume":"1.00000000",
            "id":423,
            "remain_volume":"0.00000000",
            "baseCoin":"eth",
            "status":2
        }
    ]
}
```
|Crypto currency symbol|cobidex-btc pairs|cobidex-usdt pairs|
|------------|----------|----------|
|btc|	-|	btcusdt|
|eth|	ethbtc|	ethusdt|
|ltc|	ltcbtc|	ltcusdt|
|etc|	etcbtc|	etcusdt|
|usdt|	-|	-/web/new_order-Get the current delegate|



##  <span id="15">Obtain order details</span>

1. Interface address:/open/api/order_info
2. Interface specification:(getrequest)Obtain order details

|parameter|	Fill in type|	Explain|
|------------|--------|--------------------------------------|
|order_id|	Must fill|	Order ID|
|symbol|	Must fill|	Market mark，ethbtc，See below for details|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	as follows:|
```javascript
{
    "order_info":{
        "id":343,
        "side":"sell",
        "side_msg":"Sell out",
        "created_at":"09-22 12:22",
        "price":222.33,
        "volume":222.33,
        "deal_volume":222.33,
        "total_price":222.33,
        "fee":222.33,
        "avg_price":222.33}
    }
    "trade_list":[
        {
            "id":343,
            "created_at":"09-22 12:22",
            "price":222.33,
            "volume":222.33,
            "deal_price":222.33,
            "fee":222.33
        },
        {
            "id":345,
            "created_at":"09-22 12:22",
            "price":222.33,
            "volume":222.33,
            "deal_price":222.33,
            "fee":222.33
        }
    ]
}
```






## <span id="17">All Transaction Pairs and Accuracy Supported by Query System</span>


1. Interface address:/open/api/common/symbols
2. Interface specification:(get request) query all transaction pairs and accuracy supported by the system

* Parameter: None

Return value:

|field|	Example|	explain|
|------------|--------|---------------|
|code|	0|	 
|msg|	"suc"|	code>0fail|
|data|	as follows：|symbol Transaction pairs<br>base_coin Base currency<br>count_coin Money of Account<br>price_precision Price Precision Number（0 is a single digit）<br>amount_precision Quantitative precision digits (0 is a single digit)|
```javascript
{
"code": "0",
"msg": "suc",
"data": [
{
"symbol": "ethbtc",
"count_coin": "btc",
"amount_precision": 3,
"base_coin": "eth",
"price_precision": 8
},
{
"symbol": "ltcbtc",
"count_coin": "btc",
"amount_precision": 2,
"base_coin": "ltc",
"price_precision": 8
},
{
"symbol": "etcbtc",
"count_coin": "btc",
"amount_precision": 2,
"base_coin": "etc",
"price_precision": 8
},
{
"symbol": "ltceth",
"count_coin": "eth",
"amount_precision": 2,
"base_coin": "ltc",
"price_precision": 8
},
{
"symbol": "etceth",
"count_coin": "eth",
"amount_precision": 2,
"base_coin": "etc",
"price_precision": 8
}
]
}
```


##  <span id="18">Balance and deposit records</span>


1. Interface address:/open/api/user_balance_info
2. Interface Description:(postRequest) Get user assets and recharge records

|parameter|	Fill in type|	Explain|
|------------|--------|--------------------------------------|
|uid|	Selective filling|	useruid（useruid,mobile_number,emailOne of the three must be filled in）|
|mobile_number|	Selective filling|	Inquiry user number, mobile phone number or mailbox|
|email|	Selective filling|	email|
|api_key|	Must fill|	api_key|
|time|	Must fill|	time stamp|
|sign|	Must fill|	autograph|

Return value:

|field|	Example|	explain|
|------------|--------|--------------------------------------------|
|code|	0|“0” - > Success<br>“100004” ->Illegal parameters<br>“100005” -> Signature error<br>“100007” -> illegalIP<br>"110032" -> Users do not have query privileges<br>“110020” -> The user to query does not exist|
|msg|	"suc"|	code>0fail|
|data|	as follows：|balance_info Asset information in various currencies<br>deposit_list Filling Pipeline Information|
```javascript
{
"balance_info":[
    {
        "symbol":"BTC",
        "balance":124.12
    },...
],
"deposit_list":[
    {
        "uid":17203,
        "symbol":"BTC",
        "fee":0.00005,
        "amount":12.02,
        "created_at":"2018-11-14 15:37:51"
    },...
]
}
```


##  <span id="19">Subscription - K Line Market</span>


* request:

`{"event":"sub","params":{"channel":"market_$base$quote_kline_[1min/5min/15min/30min/60min/1day/1week/1month]","cb_id":"custom"}}`


* Return to subscription status once:

`
{"event_rep":"subed","channel":"market_$base$quote_kline_[1min/5min/15min/30min/60min/1day/1week/1month]","cb_id":"Please Return by the Way You Came","ts":1506584998239,"status":"ok"}
`

* Continue to return subscription messages:

```ruby
{
    "channel":"market_$base$quote_kline_[1min/5min/15min/30min/60min/1day/1week/1month]",//Subscription transactions versus market$base$quoteExpressbtckrwetc.

    "ts":1506584998239,//Request time
    "tick":{
        "id":1506602880,//Time scale starting value
        "amount":123.1221,//A turnover
        "vol":1212.12211,//Trading volume
        "open":2233.22,//Opening price
        "close":1221.11,//Closing price
        "high":22322.22,//Highest price
        "low":2321.22//Minimum price
    }
}
```


## <span id="20">Subscription - market quotations in the last 24 hours</span>


* request:

`
{"event":"sub","params":{"channel":"market_$base$quote_ticker","cb_id":"custom"}}
`

* Return to subscription status once:

`
{"event_rep":"subed","channel":"market_$base$quote_ticker","cb_id":"Please Return by the Way You Came","ts":1506584998239,"status":"ok","lower_frame":"0"} // lower_frame: 0 No coin pair off the shelf、 1 Coin pair
`



```ruby
{
    "channel":"market_$base$quote_ticker",//Subscription transactions versus market$base$quoteExpress btckrw etc.
    "ts":1506584998239,//Request time
    "tick":{
        "id":1506584998,//Redundancy, no practical significance, timestamp
        "amount":123.1221,//A turnover
        "vol":1212.12211,//Trading volume
        "open":2233.22,//Opening price
        "close":1221.11,//Closing price
        "high":22322.22,//Highest price
        "low":2321.22,//Minimum price
        "rose":-0.2922,//Gain
        "ts":1506584998239,//Data generation time
        "lower_frame":"0"
    }
}
```
* Continue to return subscription messages:


##  <span id="21">Subscription - Deep Port (High Frequency)</span>


* request:

`
{"event":"sub","params":{"channel":"market_$base$quote_depth_step[0-2]","cb_id":"custom","asks":150,"bids":150}}
`

* Return to subscription status once:

`
{"event_rep":"subed","channel":"market_$base$quote_depth_step[0-2]","cb_id":"Please Return by the Way You Came","asks":150,"bids":150,"ts":1506584998239,"status":"ok"}
`



```ruby
{
    "channel":"market_$base$quote_depth_step[0-2]",//$base$quoteExpressbtckrwetc.,Depth has three dimensions，0、1、2
    "ts":1506584998239,//Request time
    "tick":{
        "asks":[//Selling
            [22112.22,0.9332],
            [22112.21,0.2],
        ],
        "buys":[//Bid
            [22111.22,0.9332],
            [22111.21,0.2],
        ]
    }
}
```
<aside class="notice">
    Note: The first successful subscription will immediately return a full amount of data, and the server will regularly push a full amount of data to the front-end to avoid data problems
</aside>


* Full quantity: the front end directly replaces the original disk outlet


```ruby
{
    "channel":"market_$base$quote_depth_step[0-2]",//$base$quoteExpressbtckrwetc.,Depth has 3 dimensions，0、1、2
    "ts":1506584998239,//Request time
    "tick":{
        "side": "asks", Trading Direction  asks：Selling  buys: Bid  
        "price" : 133.55,  A price segment corresponding to the opening
        "volume" : 44.22   Quantity corresponding to price segment
    }
}
```

<aside class="notice" style="margin-top:15%">
    Note: The front end of incremental inventory information only needs to replace the quantity corresponding to the price， volume=Delete at 0 o'clock， priceWith the original opening of a price segment price Equal-time updatevolume,  Direct addition of new price
</aside>


* Direct addition of new price




##  <span id="22">Subscription - Deep Port</span>

* request:

`
{"event":"sub","params":{"channel":"market_$base$quote_depth_step[0-2]","cb_id":"custom","asks":150,"bids":150}}
`

* Return to subscription status once:

`
{"event_rep":"subed","channel":"market_$base$quote_depth_step[0-2]","cb_id":"Please Return by the Way You Came","asks":150,"bids":150,"ts":1506584998239,"status":"ok"}
`

* Continue to return subscription messages:

```ruby
{
    "channel":"market_$base$quote_depth_step[0-2]",//$base$quoteRepresents btckrw, etc.,Depth has 3 dimensions，0、1、2
    "ts":1506584998239,//Request time
    "tick":{
        "asks":[//Selling
            [22112.22,0.9332],
            [22112.21,0.2]
        ],
        "buys":[//Bid
            [22111.22,0.9332],
            [22111.21,0.2]
        ]
    }
}
```


## <span id="23">Subscription-Real-time Transaction Information </span>

* request:

`
{"event":"sub","params":{"channel":"market_$base$quote_trade_ticker","cb_id":"custom"}}
`

* Return to subscription status once:

`
{"event_rep":"subed","channel":"market_$base$quote_trade_ticker","cb_id":"Please Return by the Way You Came","ts":1506584998239,"status":"ok"}
`

* Continue to return subscription messages:

```ruby
{
    "channel":"market_$base$quote_trade_ticker",//Subscription transactions versus market$base$quoteExpressbtckrwetc.
    "ts":1506584998239,//Request time
    "tick":{
        "id":12121,//dataThe biggest deal ID
        "ts":1506584998239,//dataThe biggest deal
        "data":[
            {
                "id":12121,//transaction ID
                "side":"buy",//Direction of business buy,sell
                "price":32.233,//Unit Price
                "vol":232,//Number
                "amount":323,//Total
                "ts":1506584998239,//Data generation time
                "ds":'2017-09-10 23:12:21'
            },
            {
                "id":12120,//Transaction ID
                "side":"buy",//Direction of business buy,sell
                "price":32.233,//Unit Price
                "vol":232,//Number
                "amount":323,//Total
                "ts":1506584998239,//Data generation time
                "ds":'2017-09-10 23:12:21'
            }
        ]
    }
}
```



## <span id="24">Request-K Line History Data</span>

* Increase request parameters endIdx，pageSize（Up to 300, default 300 data）,If endIdx is empty, the last 300 historical data are returned


* request:

`
{"event":"req","params":{"channel":"market_$base$quote_kline_[1min/5min/15min/30min/60min/1day/1week/1month]","cb_id":"custom","since":"1506602880"}}//since The default is to return the latest 300, a return value greater than since for up to 1 hours of data, since has strong check, not earlier than the current 1 hours to 59 since
`


* Return a historical data:

```ruby
{
    "event_rep":"rep","channel":"market_$base$quote_kline_[1min/5min/15min/30min/60min/1day/1week/1month]","cb_id":"Please Return by the Way You Came",
    "since":"1506602880",//since Return the latest 300 items by default, return the maximum 1 hour data larger than since when it has value, and since has strong check, not earlier than the current 1 hour.
    "ts":1506584998239,//Request time
    "data":[//300 article
        {
            "id":1506602880,//Time scale starting value
            "amount":123.1221,//A turnover
            "vol":1212.12211,//Trading volume
            "open":2233.22,//Opening price
            "close":1221.11,//Closing price
            "high":22322.22,//Highest price
            "low":2321.22//Minimum price
        },
        {
            "id":1506602880,//Time scale starting value
            "amount":123.1221,//A turnover
            "vol":1212.12211,//Trading volume
            "open":2233.22,//Opening price
            "close":1221.11,//Closing price
            "high":22322.22,//Highest price
            "low":2321.22//Minimum price
        }
    ]
}
```




## <span id="25">Request-transaction history data </span>

* request:

`
{"event":"req","params":{"channel":"market_$base$quote_trade_ticker","cb_id":"custom","top":200}}
`

* Direct return of transaction information:

```ruby
{
    "event_rep":"rep","channel":"market_$base$quote_trade_ticker","cb_id":"Please Return by the Way You Came","ts":1506584998239,"status":"ok",
    "top":200,//Maximum support 200
    "data":[
        {
            "id":12121,//Transaction ID
            "side":"buy",//Direction of businessbuy,sell
            "price":32.233,//Unit Price
            "vol":232,//Number
            "amount":323,//Total
            "ts":1506584998239//Data generation time
        },
        {
            "id":12120,//Transaction ID
            "side":"buy",//Direction of business buy,sell
            "price":32.233,//Unit Price
            "vol":232,//Number
            "amount":323,//Total
            "ts":1506584998239,//Data generation time
            "ds":'2017-09-10 23:12:21'
        }
    ]
}
```



## <span id="26">Request - 24 Market Data on Home Page-</span>

* Request mode:

`
{"event":"req","params":{"channel":"review"}}
`

* Return an example of data:

```ruby
{
   event_rep: "rep",
   channel: "review",
   data: {
          btcusdt: {amount: "999341.34124", close: "6450.18", high: "6486.39", low: "6359.63", open: "6435.79", …}
   }
}
```


# <span id="ws-api-java">Demo API (java) </span>


```

package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

/**
 * @author Squid DateTime:November 22, 2018, 9:25:20 PM 
 * Suggested use websocket client Edition 
 * <dependency> 
 * <groupId>org.java-websocket</groupId> 
 * <artifactId>Java-WebSocket</artifactId> 
 * <version>1.3.0</version> 
 * </dependency> 
 *
 */
public class WsTest {

    public static void main(String[] args) {
        try {
//wsurl 
            String url = "wss://ws.cobidex.com/kline-api/ws";
//Historical data request parameters 
            String reqParam = "{"event":"req","params":{"channel":"market_btcusdt_trade_ticker","cb_id":"btcusdt","top":150}}";
//Subscription parameters 
            String subParam = "{"event":"sub","params":{"channel":"market_btcusdt_trade_ticker","cb_id":"btcusdt","top":150}}";

//Initialization request history data 
            WebSocketUtils wsc = WebSocketUtils.executeWebSocket(url, reqParam);

//Subscribe to real-time data 
            wsc.send(subParam);

//Thread does not end, waiting for new messages，https://www.cobidex.com/en_US/ Generally, a new deal will return in about a minute
            while (true) {
                Thread.sleep(1000);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class WebSocketUtils extends WebSocketClient {
        private static WebSocketUtils wsclient = null;
        private String msg = "";

        public WebSocketUtils(URI serverURI) {
            super(serverURI);
        }

        public WebSocketUtils(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        public WebSocketUtils(URI serverUri, Map<String, String> headers, int connecttimeout) {
            super(serverUri, new Draft_17(), headers, connecttimeout);
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            System.out.println("Links have been established");

        }

        @Override
        public void onMessage(String s) {
            System.out.println("Receive a string message");
        }

        @Override
        public void onClose(int i, String s, boolean b) {
            System.out.println("Link closed");
        }

        @Override
        public void onError(Exception e) {
            System.out.println("Wrong report");
        }

        @Override
        public void onMessage(ByteBuffer socketBuffer) {
            try {
                String marketStr = byteBufferToString(socketBuffer);
                String market = uncompress(marketStr).toLowerCase();
                if (market.contains("ping")) {
                    System.out.println("Receive messageping："+market);
                    String tmp = market.replace("ping", "pong");
                    wsclient.send(market.replace("ping", "pong"));
                } else {
                    msg = market;
                    System.out.println("Receive message："+msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static Map<String, String> getWebSocketHeaders() throws IOException {
            Map<String, String> headers = new HashMap<String, String>();
            return headers;
        }

        private static void trustAllHosts(WebSocketUtils appClient) {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            } };

            try {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                appClient.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static WebSocketUtils executeWebSocket(String url,String sendMsg) throws Exception {
            wsclient = new WebSocketUtils(new URI(url), getWebSocketHeaders(), 1000);
            trustAllHosts(wsclient);
            wsclient.connectBlocking();
            wsclient.send(sendMsg);
            return wsclient;
        }

        // buffer turn String 
        public String byteBufferToString(ByteBuffer buffer) {
            CharBuffer charBuffer = null;
            try {
                Charset charset = Charset.forName("ISO-8859-1");
                CharsetDecoder decoder = charset.newDecoder();
                charBuffer = decoder.decode(buffer);
                buffer.flip();
                return charBuffer.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        // decompression 
        public String uncompress(String str) throws IOException {
            if (str == null || str.length() == 0) {
                return str;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString();
        }

    }
}

```
# Futures API

## Public


Endpoints under **Public** section can be accessed freely without requiring any API-key or signatures

### Test Connectivity

**Get**  `https://openapi.cobidex.com/fapi/v1/ping`

This endpoint checks connectivity to the host

**Response**

200: OK

```javascript
{}
```
### Check Server Time

**Get** `https://openapi.cobidex.com/fapi/v1/time`

**Response**

200: OK

```javascript
{
    "serverTime":1607702400000,
    "timezone":Chinese standard time
}
```
name | type | example | description
-------------- | -------------- | -------------- | --------------
serverTime | long | 1607702400000 |server timestamp
timezone | string | China standard time | server time zone

### Future list

**GET** `https://openapi.cobidex.com /fapi/v1/contracts`

**Response**

200: OK

```javascript
[
    {
        "symbol": "H-HT-USDT",
        "pricePrecision": 8,
        "side": 1,
        "maxMarketVolume": 100000,
        "multiplier": 6,
        "minOrderVolume": 1,
        "maxMarketMoney": 10000000,
        "type": "H",
        "maxLimitVolume": 1000000,
        "maxValidOrder": 20,
        "multiplierCoin": "HT",
        "minOrderMoney": 0.001,
        "maxLimitMoney": 1000000,
        "status": 1
    }
]
```
name | type | example | description
-------------- | -------------- | -------------- | --------------
symbol | string | E-BTC-USDT | Contract name
status | number | 1 | status（0：cannot trade，1：can trade
type | string | S | contract type, E: perpetual contract, S: test contract, others are mixed contract
side | number | 1 | Contract direction(backwards：0，1：forward)
multiplier | number | 0.5 | Contract face value
multiplierCoin | string | BTC | Contract face value unit
pricePrecision | number | 4 | Precision of the price
minOrderVolume | number | 10 | Minimum order volume
minOrderMoney | number | 10 | Minimum order value
maxMarketVolume | number | 100000 | Market price order maximum volume
maxMarketMoney | number | 100000 | Market price order maximum value
maxLimitVolume | number | 100000 | Limit price order maximum volume
maxValidOrder | number | 100000 | Maximum valid order quantity

## Market


Market section can be accessed freely without requiring any API-key or signatures.

### Depth

**GET** `https://openapi.cobidex.com /fapi/v1/depth`

**Market depth data**

**Request**

Query Parameters

limit <span id="opt">OPTIONAL</span> | integer | Default 100, Max 100
-------------- | -------------- | --------------
**Contract name** <span id="reqd">REQUIRED</span> | **string** | **Contract Name E.g. E-BTC-USDT**

**Response**

200: OK

Successfully retrieve market depth data

```javascript
{
  "bids": [
    [
      "3.90000000",   // price
      "431.00000000"  // quantity
    ],
    [
      "4.00000000",
      "431.00000000"
    ]
  ],
  "asks": [
    [
      "4.00000200",  // price
      "12.00000000"  // quantity
    ],
    [
      "5.10000000",
      "28.00000000"
    ]
  ]
}
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
time | long | 1595563624731 | Current Timestamp  (ms)
bids | list | Look below | Order book purchase info
asks | list | Look below | Order book selling info

The fields bids and asks are lists of order book price level entries, sorted from best to worst.

name | type | example | description
-------------- | -------------- | -------------- | --------------
' ' | float | 131.1 | price level
' ' | float | 2.3 | Total order quantity for this price level

### 24hrs ticker

**Get** `https://openapi.cobidex.com /fapi/v1/ticker`

24 hour price change statistics

**Request**

Query Parameters

Contract name <span id="reqd">REQUIRED</span> | string | Contract  name E.g. E-BTC-USDT
-------------- | -------------- | --------------

**Response**

200: OK

Successfully obtain ticker info

```javascript
{
    "high": "9279.0301",
    "vol": "1302",
    "last": "9200",
    "low": "9279.0301",
    "rose": "0",
    "time": 1595563624731
}
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
time | long | 1595563624731 | Open time
high | float | 9900 | Higher price
low | float | 8800.34 | Lower price
last | float | 8900 | Newest price
vol | float | 4999 | Trade volume
rose | string | +0.5 | Price variation

### Get index/marked price

**GET** `https://openapi.cobidex.com /fapi/v1/index`

**Request**

Query Parameters

Contract name <span id="reqd">REQUIRED</span> | string | Contract name E.g. E-BTC-USDT
-------------- | -------------- | --------------
**limit** <span id="opt">OPTIONAL</span> | **string** | **Default 100, Max 100**

**Response:**

200: OK

```javascript
{
    "markPrice": 581.5,
    "indexPrice": 646.3933333333333,
    "lastFundingRate": 0.001,
    "contractName": "E-ETH-USDT",
    "time": 1608273554063
}
```

name | type | example | Description
-------------- | -------------- | -------------- | --------------
indexPrice | float | 0.055 | Index price
markPrice | float | 0.0578 | Marked price
contractName | string | E-BTC-USDT | Contract name
lastFundingRate | float | 0.123 | Current fund rate

### K line/charts data

**GET** `https://openapi.cobidex.com /fapi/v1/klines`

**Request**

Query Parameters

ContractName <span id="reqd">REQUIRED</span> | string | Contract name E.g. E-BTC-USDT
-------------- | -------------- | --------------
**interval** <span id="reqd">REQUIRED</span> | **string** | **K-line interval, identifies the sent value as:<br/> 1min,5min,15min,30min,1h,1day,1week,1month**
**limit** <span id="opt">OPTIONAL</span> | **integer** | **Default 100, Max 300**

**Response:**

200: OK

```javascript
[
    {
        "high": "6228.77",
        "vol": "111",
        "low": "6228.77",
        "idx": 1594640340,
        "close": "6228.77",
        "open": "6228.77"
    },
    {
        "high": "6228.77",
        "vol": "222",
        "low": "6228.77",
        "idx": 1587632160,
        "close": "6228.77",
        "open": "6228.77"
    },
    {
        "high": "6228.77",
        "vol": "333",
        "low": "6228.77",
        "idx": 1587632100,
        "close": "6228.77",
        "open": "6228.77"
    }
]
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
idx | long | 1538728740000 | Start timestamp (ms）
open | float | 36.00000 | Open price
close | float | 33.00000 | Closing price
high | float | 36.00000 | Max price
low | float | 30.00000 | Min price
vol | float | 2456.111 | Trade volume


## Trading

### Security: TRADE

All interfaces under the transaction require signature and API-key verification

### Order creation

**POST** `https://openapi.cobidex.com /fapi/v1/order`

Creation of single new orders

**Request**

Headers

X-CH-TS <span id="opt">OPTIONAL</span> | string | Time stamp
-------------- | -------------- | --------------
**X-CH-APIKEY** <span id="opt">OPTIONAL</span> | **string** | **Your API-key**
**X-CH-SIGN** <span id="opt">OPTIONAL</span> | **string** | **Signature**

Body Parameters

**volume** <span id="reqd">REQUIRED</span> | **number** | **Order quantity**
-------------- | -------------- | --------------
**price** <span id="reqd">REQUIRED</span> | **number** | **Order price**
**ContractName** <span id="reqd">REQUIRED</span> | **string** | **Contract name E.g. E-BTC-USDT**
**type** <span id="reqd">REQUIRED</span> | **string** | **Order type, LIMIT/MARKET**
**side** <span id="reqd">REQUIRED</span>  | **string** | **trade direction, BUY/SELL**
**open** <span id="reqd">REQUIRED</span> | **string** | **Open balancing direction, OPEN/CLOSE**
**position Type** <span id="reqd">REQUIRED</span> | **number** | **Hold-up position, 1 full position, 2 restrictive position**
**clientOrderId** <span id="opt">OPTIONAL</span> | **string** | **Client order identity, a string with length less than 32 bit**
**timeInForce** <span id="opt">OPTIONAL</span> | **string** | **IOC, FOK, POST_ONLY**

**Response:**

200: OK

```javascript
{
    "orderId": 256609229205684228
}
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
orderId | String | 256609229205684228 | Order ID


### Cancel order

**POST** `https://openapi.cobidex.com /fapi/v1/cancel`

Speed limit rules: 20 times/ 2 seconds

**Request**

Headers

X-CH-SIGN <span id="reqd">REQUIRED</span> | string | Signature
-------------- | -------------- | --------------
**X-CH-APIKEY** <span id="reqd">REQUIRED</span> | **string** | **Your API-key**
**X-CH-TS** <span id="reqd">REQUIRED</span> | **integer** | **Time stamp**

Body Parameters

contractName <span id="reqd">REQUIRED</span> | string | Contract name E.g. E-BTC-USDT
-------------- | -------------- | --------------
**orderId** <span id="reqd">REQUIRED</span> | **string** | **Order ID**

**Response:**

200: OK

```javascript
{
    "orderId": 256609229205684228
}
```

### Order details

**GET** `https://openapi.cobidex.com /fapi/v1/order`


**Request**

Headers

contractName <span id="reqd">REQUIRED</span> | string 
-------------- | --------------


**Response:**

200: OK

```javascript
[
    {
       "side": "BUY",
       "executedQty": 0,
       "orderId": 259396989397942275,
       "price": 10000.0000000000000000,
       "origQty": 1.0000000000000000,
       "avgPrice": 0E-8,
       "transactTime": "1607702400000",
       "action": "OPEN",
       "contractName": "E-BTC-USDT",
       "type": "LIMIT",
       "status": "INIT"
    }
]
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
orderId | long | 150695552109032492 | Order ID（system generated
contractName | string | E-BTC-USDT | Contract name
price | float | 10.5 | Order price
origQty | float | 10.5 | Order quantity
executedQty | float | 20 | Order quantity
avgPrice | float | 10.5 | Average transaction price
symbol | string | BHTUSDT | Coin pair name
status | string | NEW | Order status. Possible values are：NEW(new order，not filled)、PARTIALLY_FILLED（partially filled）、FILLED（fully filled）、CANCELLED（already cancelled）andREJECTED（order rejected）
side | string | NEW | Order direction. Possible values can only be：BUY（buy long）and SELL（sell short）
action | string | OPEN | OPEN/CLOSE
transactTime | long | 1607702400000 | Order creation time

###  Open order

**GET** `https://openapi.cobidex.com /fapi/v1/openOrders`

Speed limit rules:

Obtain open contract, the user's current order

**Request**

Headers

X-CH-SIGN <span id="reqd">REQUIRED</span> | string | Signature
-------------- | -------------- | --------------
**X-CH-APIKEY** <span id="reqd">REQUIRED</span> | **string** | **Your API-key**
**X-CH-TS** <span id="reqd">REQUIRED</span> | **integer** | **Time stamp**

Body Parameters

contractName <span id="reqd">REQUIRED</span> | string | Contract name E.g. E-BTC-USDT
-------------- | -------------- | --------------
**orderId** <span id="reqd">REQUIRED</span> | **string** | **Order ID**

**Response:**

200: OK

```javascript
[
    {
       "side": "BUY",
       "executedQty": 0,
       "orderId": 259396989397942275,
       "price": 10000.0000000000000000,
       "origQty": 1.0000000000000000,
       "avgPrice": 0E-8,
       "transactTime": "1607702400000",
       "action": "OPEN",
       "contractName": "E-BTC-USDT",
       "type": "LIMIT",
       "status": "INIT"
    }
]
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
orderId | long | 150695552109032492 | Order ID（system generated）
contractName | string | E-BTC-USDT | Contract name
price | float | 4765.29 | Order price
origQty | float | 1.01 | Order quantity
executedQty | float | 1.01 | Filled orders quantity
avgPrice | float | 4754.24 | Filled orders average price
type | string | LIMIT | Order type. Possible values can only be:LIMIT(limit price) andMARKET（market price）
side | string | BUY | Order direction. Possible values can only be：BUY（buy long）and SELL（sell short）
status | string | NEW | Order status. Possible values are：NEW(new order，not filled)、PARTIALLY_FILLED（partially filled）、FILLED（fully filled）、CANCELLED（already cancelled）andREJECTED（order rejected）
action | string | OPEN | OPEN/CLOSE
transactTime | long | 1607702400000 | Order creation time,

### Order record

**GET** `https://openapi.cobidex.com /fapi/v1/myTrades`

Speed limit rules: 20 times/ 2 seconds

**Request**

Headers

X-CH-SIGN <span id="reqd">REQUIRED</span> | string | Signature
-------------- | -------------- | --------------
**X-CH-APIKEY** <span id="reqd">REQUIRED</span> | **string** | **Your API-key**
**X-CH-TS** <span id="reqd">REQUIRED</span> | **integer** | **Time stamp**

Body Parameters

contractName <span id="reqd">REQUIRED</span> | string | Contract name E.g. E-BTC-USDT
-------------- | -------------- | --------------
**limit** <span id="opt">OPTIONAL</span> | **string** | **Lines per page, default 100, max 1000**
**fromId** <span id="opt">OPTIONAL</span> | **integer** | **Start retrieving from this tradeId**

**Response:**

200: OK

```javascript
[
  {
    "symbol": "ETHBTC",
    "id": 100211,
    "bidId": 150695552109032492,
    "askId": 150695552109032493,
    "price": "4.00000100",
    "qty": "12.00000000",
    "time": 1499865549590,
    "isBuyer": true,
    "isMaker": false,
    "fee":"0.001"
  },...
]
```

name | type | example | description
-------------- | -------------- | -------------- | --------------
symbol | string | ETHBTC | Coin name(trade pair)
tradeId | number | 28457 | Trade ID
bidId | long | 150695552109032492 | Buyer order ID
askId | long | 150695552109032493 | Seller order ID
bidUserId | integer | 10024 | Buyer user ID
askUserId | integer | 10025 | Seller user ID
price | float | 4.01 | Filled price
qty | float | 12 | Trade quantity
amount | float | 5.38 | Filled amount
time | number | 1499865549590 | Trade time stamp
fee | number | 0.001 | Trading fees
side | string | buy | Current order direction BUY purchase, SELL  selling
contractName | string | E-BTC-USDT | Contract name
isMaker | boolean | true | is it maker?
isBuyer | boolean | true | is it buyer?

## Account

### Security: USER_DATA

All interfaces under the account require signature and API-key verification


### Account info

**GET** `https://openapi.cobidex.com /fapi/v1/account`

Speed limit rules: 20 times/ 2 seconds

**Request**

Headers

X-CH-SIGN <span id="reqd">REQUIRED</span> | string | Signature
-------------- | -------------- | --------------
**X-CH-APIKEY** <span id="reqd">REQUIRED</span> | **string** | **Your API-key**
**X-CH-TS** <span id="reqd">REQUIRED</span> | **integer** | **Time stamp**


**Response:**

200: OK

```javascript
{
    "account": [
        {
            "marginCoin": "USDT",
            "accountNormal": 999.5606,
            "accountLock": 23799.5017,
            "partPositionNormal": 9110.7294,
            "totalPositionNormal": 0,
            "achievedAmount": 4156.5072,
            "unrealizedAmount": 650.6385,
            "totalMarginRate": 0,
            "totalEquity": 99964804.560,
            "partEquity": 13917.8753,
            "totalCost": 0,
            "sumMarginRate": 873.4608,
            "positionVos": [
                {
                    "contractId": 1,
                    "contractName": "E-BTC-USDT",
                    "contractSymbol": "BTC-USDT",
                    "positions": [
                        {
                            "id": 13603,
                            "uid": 10023,
                            "contractId": 1,
                            "positionType": 2,
                            "side": "BUY",
                            "volume": 69642.0,
                            "openPrice": 11840.2394,
                            "avgPrice": 11840.3095,
                            "closePrice": 12155.3005,
                            "leverageLevel": 24,
                            "holdAmount": 7014.2111,
                            "closeVolume": 40502.0,
                            "pendingCloseVolume": 0,
                            "realizedAmount": 8115.9125,
                            "historyRealizedAmount": 1865.3985,
                            "tradeFee": -432.0072,
                            "capitalFee": 2891.2281,
                            "closeProfit": 8117.6903,
                            "shareAmount": 0.1112,
                            "freezeLock": 0,
                            "status": 1,
                            "ctime": "2020-12-11T17:42:10",
                            "mtime": "2020-12-18T20:35:43",
                            "brokerId": 21,
                            "marginRate": 0.2097,
                            "reducePrice": 9740.8083,
                            "returnRate": 0.3086,
                            "unRealizedAmount": 2164.5289,
                            "openRealizedAmount": 2165.0173,
                            "positionBalance": 82458.2839,
                            "settleProfit": 0.4883,
                            "indexPrice": 12151.1175,
                            "keepRate": 0.005,
                            "maxFeeRate": 0.0025
                        }
                    ]
                }
            ]
        }
    ]
}
```

name | type | description
-------------- | -------------- | --------------
account | [] | Balance collection

`account` field:

name | type | example | description
-------------- | -------------- | -------------- | --------------
marginCoin | string | USDT | Margin coin
accountNormal | float | 10.05 | Balance account
accountLock | float | 10.07 | Margin frozen account
partPositionNormal | float | 10.07 | Restricted position margin balance
totalPositionNormal | float | 10.07 | Full position initial margin
achievedAmount | float | 10.07 | Profit and losses occurred 
unrealizedAmount | float | 10.05 | Unfilled profit and losses
totalMarginRate | float | 10.05 | Full position margin rate
totalEquity | float | 10.07 | Full position equity
partEquity | float | 10.07 | Restricted position equity
totalCost | float | 10.07 | Full position costs
sumMarginRate | float | 10.07 | All accounts margin rate
positionVos | [ ] |  | Position contract record |


`positionVos` field:

name | type | example | description
-------------- | -------------- | -------------- | --------------
contractId | integer | 2 | Contract id
contractName | string | E-BTC-USDT | Contract name
contractSymbol | string | BTC-USDT | Contract coin pair
positions | [ ] |  | Position details

`positionVos` field:

name | type | example | description
-------------- | -------------- | -------------- | --------------
id | integer | 2 | Position id
uid | integer | 10023 | User ID
positionType | integer | 1 | Hold position type(1 full，2 restrictive)
side | string | SELL | Hold position direction SELL sell long, BUY buy short
volume | float | 1.05 | Hold quantity
openPrice | float | 1.05 | Open position price
avgPrice | float | 1.05 | Hold average price
closePrice | float | 1.05 | Balancing average price
leverageLevel | float | 1.05 | Leverage multiple
holdAmount | float | 1.05 | Hold position margin
closeVolume | float | 1.05 | Balanced quantity
pendingCloseVolume | float | 1.05 | The number of pending closing orders
realizedAmount | float | 1.05 | Profit and losses occurred
historyRealizedAmount | float | 1.05 | Historic accumulated profit and losses
tradeFee | float | 1.05 | Trading fees 
capitalFee | float | 1.05 | Capital costs
closeProfit | float | 1.05 | Balancing profit and loss
shareAmount | float | 1.05 | Amount to share
freezeLock | integer | 0 | Position freeze status: 0 normal, 1 liquidation freeze, 2 delivery freeze
status | integer | 0 | Position effectiveness，0 ineffective 1 effective
ctime | time|  | Creation time
mtime | time |  | Update time
brokerId | integer | 1023 | Client id
lockTime | time |  | liquidation lock time
marginRate | float | 1.05 | Margin rate
reducePrice | float | 1.05 | Price reduction
returnRate | float | 1.05 | Return rate (profit rate)
unRealizedAmount | float | 1.05 | Unfilled profit and losses
openRealizedAmount | float | 1.05 | Open position unfilled  profit and losses
positionBalance | float | 1.05 | Position value
indexPrice | float | 1.05 | Newest marked price
keepRate | float | 1.05 | Scaled minimum kept margin rate
maxFeeRate | float | 1.05 | Balancing maximum fees rate
