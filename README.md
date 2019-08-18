# AsyncHttpClient library

Lightweight async HTTP client based on [OkHttp](https://square.github.io/okhttp/).  It tries to follow a similar API inspired by this [library](https://github.com/android-async-http/android-async-http)


## Setup

```gradle
dependencies {
  implementation 'com.codepath.libraries:asynchttpclient:0.0.6'
}
```

## Basic usage

GET:

```java
AsyncHttpClient cp = new AsyncHttpClient();
cp.get("https://api.thecatapi.com/v1/images/search", new TextHttpResponseHandler() {
  @Override
   public void onSuccess(int statusCode, Headers headers, String response) {
     Log.d("DEBUG", response);
   }

   @Override
   public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
   }
});
```
