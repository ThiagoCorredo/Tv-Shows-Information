# About

Tv Shows Information is simple project to study and play with some android components, architecture and tools for Android development.
The API used was [TvMaze](https://www.tvmaze.com/api).

## Development setup

You require the latest Android Studio (stable channel) to be able to build the app.

If you want to see all the dependencies used in this project, look at the file `~/dependencias.gradle`

### Libraries

- Application entirely written in [Kotlin](https://kotlinlang.org)
- Uses [Coroutines](https://kotlin.github.io/kotlinx.coroutines/) for Asynchronous processing using
- Uses [Koin](https://github.com/InsertKoinIO/koin) for dependency injection
- Uses [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- Uses [Navigation](https://developer.android.com/guide/navigation/navigation-principles)
- Uses [Coil](https://coil-kt.github.io/coil/) for image loading 
- Uses [Retrofit](https://square.github.io/retrofit/) for API calls
- Uses [OkHttp](https://square.github.io/okhttp/) with [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) for logging network requests and responses
- Uses [JUnit4](https://developer.android.com/training/testing/junit-rules) for unit testing
- Uses [MockK for](https://mockk.io/) mocking objects in tests
- Uses [Coroutines-Test](https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test) for testing coroutines

## ToDo

Here are some features or improvements that are planned for future releases:

- [ ] Implement [Espresso Test]
- [ ] Improve [The API doesn't return a good way for pagination, and the library doesn't know when to stop]
- [ ] Fix [Unit test for using Paging lib]

