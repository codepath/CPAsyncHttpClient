# AsyncHttpClient library

Lightweight async HTTP client based on [OkHttp](https://square.github.io/okhttp/).  It tries to follow a similar API inspired by this [library](https://github.com/android-async-http/android-async-http)


## Setup

```gradle
dependencies {
  implementation 'com.codepath.libraries:asynchttpclient:0.0.6'
}
```

## Basic usage

1. Please check to make sure all network calls are using `https://` instead of `http://`!

2. Verify that network access is allowed via the `<uses-permission>`:

    ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
                 package="com.codepath.example">
       
           <uses-permission android:name="android.permission.INTERNET" />```
    ```

3. Add API calls.
   
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
   
   See [example calls](https://github.com/codepath/AsyncHttpClient/blob/master/example/src/main/java/com/codepath/example/TestActivity.java):
