## PushHistory API

Push history API is used to search the push result. The related APIs are encapsulated in the class *com.pax.market.api.sdk.java.api.pushHistory.PushHistoryApi*.

**Constructors of TerminalApkApi**

```
public PushHistoryApi(String baseUrl, String apiKey, String apiSecret);
public PushHistoryApi(String baseUrl, String apiKey, String apiSecret, Locale locale);
```

**Constructor parameters description**

|Name|Type|Description|
|:---|:---|:---|
|baseUrl|String|the base url of REST API|
|apiKey|String|the apiKey of marketplace, get this key from PAXSTORE admin console, refe to chapter Apply access rights|
|apiSecret|String|apiSecret, get api secret from PAXSTORE admin console, refer to chapter Apply access rights|
|locale|Locale|the locale, the default locale is Locale.ENGLISH, the language of message and errors in return object depend on locale|

### **SearchParameterPushHistory**

SearchParameterPushHistory API allow the third party system  to find all the terminal push history of parameter application

**API**

```
public Result<ParameterPushHistoryDTO> searchParameterPushHistory(int pageNo, int pageSize, String packageName, String serialNo, PushStatus pushStatus, Date pushTime)
```

**Input parameter(s) description**  

| Name                | Type                     | Nullable | Description                                                  |
| :------------------ | :----------------------- | :------- | :----------------------------------------------------------- |
| pageNo              | int                      | false    | page number, value must >=1                                  |
| pageSize            | int                      | false    | the record number per page, range is 1 to 1000               |
| packageName         | String                   | false    | search filter by app packageName                             |
| serialNo            | String                   | true     | only terminal with specified serialNo will search out                         |
| pushStatus          | PushStatus               | true     | the push status  the value can be PushStatus.Success, PushStatus.Failed |
| pushTime            | Date                     | true     | search the push history after the push time                  |



**Sample codes**

```
PushHistoryApi pushHistoryApi = new PushHistoryApi("https://api.whatspos.com/p-market-api", "RCA9MDH6YN3WSSGPW6TJ", "TUNLDZVZECHNKZ4FW07XFCKN2W0N8ZDEA5ENKZYN");
Result<ParameterPushHistoryDTO> result = pushHistoryApi.searchParameterPushHistory(1, 2,  "com.pax.android.demoapp", null, PushHistoryApi.PushStatus.Success, StringUtils.parseDateTime("2019-11-20 00:00:00"));
```

**Client side validation failed sample result(JSON formatted)**

```
{
	"businessCode": -1,
	"validationErrors": ["packageName cannot be null!"]
}
```

**Successful sample result(JSON formatted)**

```
{
  "businessCode": 0,
  "rateLimit": "",
  "pageNo": 1,
  "limit": 2,
  "hasNext": true,
  "totalCount": 17,
  "dataset": [
    {
      "parameterPushStatus": "Success",
      "appPushStatus": "Success",
      "appName": "PAXSTORE SDK Demo",
      "appPushTime": 1575274370000,
      "pushStartTime": 1575274320000,
      "terminalId": 1013403755,
      "versionName": "7.0.0-inner",
      "parameterPushError": null,
      "parameterPushTime": 1575274373000,
      "serialNo": "HMP4C15A12000186",
      "pushType": "Terminal",
      "parameterVariables": "{\"#{test}\": \"44\"}",
      "parameterValues": "{\"sys_F2_sys_param_termId\": \"#{test}\", \"sys_F2_sys_param_merCode\": \"000000000000001\", \"sys_F2_sys_param_merName\": \"Union Pay\", \"sys_F2_sys_param_acqInsCode\": \"00000000000\"}",
      "appPushError": null
    },
    {
      "parameterPushStatus": "Success",
      "appPushStatus": "Success",
      "appName": "PAXSTORE SDK Demo",
      "appPushTime": 1575102052000,
      "pushStartTime": 1575102000000,
      "terminalId": 1013403370,
      "versionName": "5.02.02",
      "parameterPushError": null,
      "parameterPushTime": 1575102054000,
      "serialNo": "1170000652",
      "pushType": "Terminal",
      "parameterVariables": "{}",
      "parameterValues": "{\"sys_F1_sys_cap_test01\": \"333\", \"sys_F1_sys_cap_test02\": \"111\"}",
      "appPushError": null
    }
  ]
}

```

The type in dataSet of is ParameterPushHistoryDTO. And the structure shows like below.

|Property Name|Type|Description|
|:--|:--|:--|
|TerminalId		|long	|the id of terminal|
|SerialNo		|string	|the serial number of terminal|
|AppName		|string	|the name of the app pushed|
|VersionName	|string	|the version name of app|
|PushStartTime	|long	|the start time of the push, it is millisecond|
|AppPushTime	|long	|app push time|
|AppPushStatus	|string	|the push result status, value can be Success and Fail|
|AppPushError	|string	|the reason of app push fail|
|ParameterPushTime|long	|parameter push time|
|ParameterPushStatus|string|the parameter push result status, value can be Success and Fail|
|ParameterPushError|string|the reason of parameter push failed|
|ParameterValues|string	|parameter values|
|ParameterVariables|string|parameter variables|
|PushType		|string	|push type, value can be Terminal or Group|	

