# Papara Android SDK

![](https://img.shields.io/badge/platform-android-green.svg)
![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
[![](https://jitpack.io/v/paparateam/papara-android.svg)](https://jitpack.io/#paparateam/papara-android)



To use **Papara Android SDK**, you have to communicate with us through https://www.papara.com/contact .


## Requirements

- Android 4.1+
- Android Studio 2.0.0+
- JDK 1.8+


## Example Application

To run the example application, first download or clone the repository. After that, you can open the project on Android Studio.

To use the example application, **Papara SandBox Android**  must be installed on the same device.

* Download and Install **Papara Sandbox Android** .
* Replace your **APP_ID**  with pre-written APP_ID in Example App
* Run the Example application.

## Setup
You can use **Gradle** to add the library as **"aar"**  dependency to your build.

Add it in your root build.gradle at the end of repositories.

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Gradle

```groovy
    dependencies {
	        implementation 'com.github.paparateam:papara-android:2.0.7'
    }
 ```


## Usage

### Application Id

You need a unique **APP_ID** to integrate sdk to your application. You can generate unique app id for your application.

To use Papara SDK, you need to install Papara Android Application on the same device. If it's not installed, a warning message will be displayed on screen. You can download it from https://play.google.com/store/apps/details?id=com.mobillium.papara 

To use Papara SDK on **Sandbox Mode**, you need to install **Papara Sandbox Android Application** on the same device.  You can get sandbox apk. file by communicating with us through https://www.papara.com/contact .



### Initialization

Open your project's manifest.xml file and paste the codes below into application block. 

After that, append your **APP_ID** to the end of the name value of intent filter action. For example if you generate Papara APP_ID is **1234**, the declaration looks like:

```xml
<application>
    <activity 
        android:name="com.papara.sdk.PaparaControllerActivity"
        android:theme="@style/Theme.Transparent"
        android:launchMode="singleTask">
            <intent-filter>
                <action android:name="papara.sdk.action.App1234"/>
                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>

    </activity>
</application>
```

You need to initialize Papara SDK before you can use it. Add a call to **Papara.sdkInitialize** from onCreate in your Activity class with your **APP_ID**.

**Important**

If you initialize Papara SDK like below;

```java

Papara.sdkInitialize(getApplicationContext(), "APP_ID", SANDBOX_MODE); e.g APP_ID = 1234 
```

your project's manifest.xml had to intent-filter action name **App+APP_ID**

```xml
<action android:name="papara.sdk.action.App1234"/>
```

**SANDBOX_MODE** : Enable/Disable SandBox mode. (Boolean)

```java

Papara.sdkInitialize(getApplicationContext(), "APP_ID", SANDBOX_MODE);
```

### Get Papara Account Number

After initialising Papara SDK correctly, you need to call **getAccountNumber()** method to start fetching Papara account number process.

If getting process **completed** successfully (without any error), **onSuccess()** method will be called with **accountNumber** info.

If getting process **interrupted** with an error, **onError()** method will be called.

If getting process **cancelled** by user, **onCancel()** method will be called.



```java

Papara.getInstance().getAccountNumber(MainActivity.this, new PaparaAccountNumberCallback() {
    @Override
    public void onSuccess(String message, int code, String accountNumber) {
        //Getting Successful
        Toast.makeText(MainActivity.this, "Success " + accountNumber, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message, int code) {
        //Getting Failed
        showResultDialog(message, code);
        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

     }

     @Override
     public void onCancel(String message, int code) {
         //Getting Cancelled by user
         showResultDialog(message, code);
         Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
      }
});

```


### Send Money Model

After initialising Papara SDK, you need to create a Send Money Model before starting payment process like below.

###### Parameters: 
* Receiver (Phone Number / Papara Number)
* Money Amount
* Send Type (Phone Number / Papara Number)

```java

PaparaSendMoney sendMoneyModel = new PaparaSendMoney();
sendMoneyModel.setReceiver("555XXXXXXX");
sendMoneyModel.setAmount("1.0");
sendMoneyModel.setType(TYPE_SEND_PHONE);
```

### Starting Send Money Process

After creating payment model correctly, you need to call **sendMoney()** method to start sending process.

If sending process **completed** successfully (without any error), **onSuccess()** method will be called.

If sending process **interrupted** with an error, **onError()** method will be called.

If sending process **cancelled** by user, **onCancel()** method will be called.



```java

Papara.getInstance().sendMoney(MainActivity.this, sendMoney, new PaparaSendMoneyCallback() {
    @Override
    public void onSuccess(String message, int code) {
        //Sending Money Successfull
        showResultDialog(message, code);
        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
     }

     @Override
     public void onFailure(String message, int code) {
        //Sending Money Failed
        showResultDialog(message, code);
        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

      }

      @Override
      public void onCancel(String message, int code) {
          //Sending Money Cancelled by user
          showResultDialog(message, code);
          Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
      }
});
```

### Starting Payment Process

Firstly, you need make an API request with specific parameters for getting **PaymentResponse** model to start payment process. To Create API payment log check documentation [here](https://merchant-api.test.papara.com/#AcceptPayments-step1) 

[Also you can follow code below to create payment](https://github.com/paparateam/papara-android/blob/master/app/src/main/java/com/mobillium/paparasdk/sdk/sampleapp/ui/PaymentActivity.java#L47)

 ```java

private void paymentRequest() {
   PaymentRequest cardAddRequest = new PaymentRequest(etAmount.getText().toString().trim().replace("\\,","\\."),"123456789","Test payment","https://www.paparamerchant.com/ipn","https://www.paparamerchant.com/checkout");
        ServiceOperations.makeRequest(PaymentActivity.this, new RequestModel(Request.Method.POST, "payments", "Loading...", cardAddRequest), new ServiceCallback<PaymentResponse>() {
            @Override
            public void success(PaymentResponse result) {
                PaparaPayment paparaPayment = new PaparaPayment();
                paparaPayment.setPaymentId(result.getId());
                paparaPayment.setPaymentUrl(result.getPaymentUrl());
                paparaPayment.setReturningRedirectUrl(result.getReturningRedirectUrl());
                Papara.getInstance().makePayment(PaymentActivity.this, paparaPayment, new PaparaPaymentCallback() {
                    @Override
                    public void onSuccess(String message, int code) {
                        //Payment Successful
                        showResultDialog(message, code);
                        Toast.makeText(PaymentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String message, int code) {
                        //Payment Failed
                        showResultDialog(message, code);
                        Toast.makeText(PaymentActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel(String message, int code) {
                        //Payment Cancelled by user
                        showResultDialog(message, code);
                        Toast.makeText(PaymentActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void error(ServiceException e) {


            }
        }, new TypeToken<BaseResponse<PaymentResponse>>() {
        });
    }
```

Secondly, you need to create a **PaparaPayment** model by using result of the API request. PaparaPayment model needs 3 parameters
- Payment Id
- Payment URL 
- Returning Redirect URL

```java

PaparaPayment paparaPayment = new PaparaPayment();
paparaPayment.setPaymentId(result.getId());
paparaPayment.setPaymentUrl(result.getPaymentUrl());
paparaPayment.setReturningRedirectUrl(result.getReturningRedirectUrl());

```
Finally, you need to call **makePayment()** method for starting payment process.

If payment process **completed** successfully (without any error), **onSuccess()** method will be called.

If payment process **interrupted** with an error, **onError()** method will be called.

If payment process **cancelled** by user, **onCancel()** method will be called.



```java

Papara.getInstance().makePayment(MainActivity.this, paparaPayment, new PaparaPaymentCallback() {
    @Override
    public void onSuccess(String message, int code) {
        //Payment Successfull
        showResultDialog(message, code);
        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
     }

     @Override
     public void onFailure(String message, int code) {
        //Payment Failed
        showResultDialog(message, code);
        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

      }

      @Override
      public void onCancel(String message, int code) {
          //Payment Cancelled by user
          showResultDialog(message, code);
          Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
      }
});
```

Result **messages** and result **codes** will be returned to your app. You can use these default messages or override the messages according to result codes. Web API related result codes can be requested through https://www.papara.com/contact .

Papara SDK related static result codes:

 - **0** : Process Cancelled by User 
 - **-1** : An Unexpected Error(Out of any case) 
 - **-2** : Null Payment/Send Money Model 
 - **-3** : Invalid Money Amount Format
 - **-4** : Papara App is not installed on device 

### Debug Mode
To enable **debugging logs**, you need to call **setDebugEnabled(boolean)** method.

Default value is **false**. 
```java

Papara.getInstance().setDebugEnabled(true); // or set false for disabling
```
