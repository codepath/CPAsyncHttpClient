# AsyncHttpClient library

This is a lightweight asynchronous HTTP client powered by [OkHttp](https://square.github.io/okhttp/) but with a significantly simplified and easier to use API design.  

The goal of this library is to have an API that clearly and cleanly supports the following features:

 * Asynchronous network requests without any need for manual thread handling
 * Response `onSuccess` callbacks run on the mainthread (by default)
 * Easy way to catch all errors and failures and handle them
 * Easy pluggable Text, JSON, and Bitmap response handlers to parse the response
 
This client tries to follow a similar API inspired by this [older now deprecated android-async-http library](https://github.com/android-async-http/android-async-http).

## Setup

To use this library, add the following to your `.gradle` file:

```gradle
dependencies {
  implementation 'com.codepath.libraries:asynchttpclient:0.0.8'
}
```

## Basic usage

1. Make sure all network calls are using `https://` instead of `http://`

2. Verify that network access is allowed via the `<uses-permission>`:

    ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                 package="com.codepath.example">
       
           <uses-permission android:name="android.permission.INTERNET" />```
    ```

3. Add any networking calls by leveraging the `AsyncHttpClient`
   
   ```java
   AsyncHttpClient client = new AsyncHttpClient();
   client.get("https://api.thecatapi.com/v1/images/search", new TextHttpResponseHandler() {
       @Override
       public void onSuccess(int statusCode, Headers headers, String response) {
           Log.d("DEBUG", response);
       }
 
       @Override
       public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
           Log.d("DEBUG", errorResponse);
       }
   });
   ```
   
   This example uses `TextHttpResponseHandler` which presents the response as raw text. We could use the `JsonHttpResponseHandler` instead to have the API response automatically parsed for us into JSON. See [other example calls here](https://github.com/codepath/AsyncHttpClient/blob/master/example/src/main/java/com/codepath/example/TestActivity.java).

## More documentation

For a more detailed usage, check out the [CodePath Async Http Client Guide](https://guides.codepath.com/android/Using-CodePath-Async-Http-Client)
