# Papara Android SDK

[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
![](https://img.shields.io/badge/platform-android-green.svg)
![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
![](https://img.shields.io/badge/Gradle-v2.2.1-red.svg)



To use  **Papara Android SDK**, you have to communicate with us through http://papara.com/iletisim .


## Requirements

- Android  4.1+
- Android Studio 1.0.0+
- JDK 1.8+


## Example Application

To run the example application, first download or clone the repository. After that, you can open the project on Android Studio.

To use the example application, **Papara SandBox Android**  must be installed on the same device.

* Download and Install **Papara Sandbox Android** .
* Replace your **APP_ID**  with pre-written APP_ID in Example App
* Run the Example application.

## Setup
You can use **Gradle** to add the library as **"aar"**  dependency to your build.


### Gradle
![](https://img.shields.io/badge/Gradle-v2.2.1-red.svg)
```groovy
dependencies {
    compile 'com.mobillium.paparasdk:papara-sdk:1.+'
}
```

## Usage

### Application Id

You need a unique api key to integrate sdk to your application. You can get it from
http://papara.com/iletisim/ 

To use Papara SDK, you need to install Papara Android Application on the same device. If it's not installed, a warning message will be displayed on screen. You can download it from https://play.google.com/store/apps/details?id=com.mobillium.papara 

To use Papara SDK on **Sandbox Mode**, you need to install **Papara Sandbox Android Application** on the same device.  You can get sandbox apk. file by communicating with us through http://papara.com/iletisim .



### Initialization

You have to initialize Papara SDK with your **APP_ID**.

**SANDBOX_MODE** : Enable/Disable SandBox mode. (Boolean)

```java
import com.mobillium.paparasdk.Papara;

Papara.sdkInitialize(getApplicationContext(), "APP_ID", SANDBOX_MODE);
```

### Payment Model

After initialising Papara SDK, you need to create a Payment Model before starting payment process like below.

###### Parameters: 
* Wallet Number
* Payment Amount
* Description

```java
import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.models.PaparaPayment;

PaparaPayment paymentModel = new PaparaPayment();
paymentModel.setWalletId("ML9433183768");
paymentModel.setAmount("1.0");
paymentModel.setDesc("description");
```

### Starting Payment Process

After creating payment model correctly, you need to call **startPayment()** method to start payment process.

If payment process **completed** successfully (without any error), **onSuccess()** method will be called.

If payment process **interrupted** with an error, **onError()** method will be called.

If payment process **cancelled** by user, **onCancel()** method will be called.

```java
import com.mobillium.paparasdk.Papara;
import com.mobillium.paparasdk.models.PaparaPayment;
import com.mobillium.paparasdk.utils.PaparaCallback;

Papara.getInstance().startPayment(MainActivity.this, paymentModel, new PaparaCallback() {
                    @Override
                    public void onSuccess() {
                        //Odeme islemi basarili
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        //Odeme islemi basarisiz
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }
                     @Override
                     public void onCancel() {
                        //Odeme islemi iptal edildi
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                        }
                });
```


### Debug Mode
To enable **debugging logs**, you need to call **setDebugEnabled(boolean)** method.

Default value is **false**. 
```java
import com.mobillium.paparasdk.Papara;

Papara.getInstance().setDebugEnabled(true); // or false for disable
```


Lisans
====================

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.