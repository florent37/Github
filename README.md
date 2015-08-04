# Github

This Github android sample application can give you a quick summary of your github repos.

![Alt sample](https://raw.githubusercontent.com/florent37/Github/master/screens/stats_small.png)
![Alt sample](https://raw.githubusercontent.com/florent37/Github/master/screens/events_small.png)

#[Dagger2][google.github.io/dagger/]

```java
@Singleton
@Component(modules = {GithubModule.class, ContextModule.class})
public interface GithubComponent {

    GithubAPI githubApi();
    RepoManager repoManager();
    UserManager userManager();

    void inject(MainActivity mainActivity);
    void inject(ListRepoFragment listRepoFragment);
    void inject(ListEventFragment listEventFragment);
}
```

#[RxAndroid](https://github.com/ReactiveX/RxAndroid) & [RetroLambda](https://github.com/evant/gradle-retrolambda)

Using the github API with Retrofit

```java
githubAPI.userEvents(userManager.getUser().getLogin())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorReturn(null)
                    .subscribe(events -> {
                        if (events != null) carpaccio.mapList("event", events);
                    });
```

#[Carpaccio](https://github.com/florent37/Carpaccio)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center_vertical"
    android:orientation="vertical"
    android:paddingLeft="10dp"
    android:paddingRight="10dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="10dp"
        android:tag="
                setText($event.userName);
                font(Roboto-Medium.ttf);
                "
        android:textColor="#333"
        android:textSize="18sp"
        tools:text="UserName" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="10dp"
            android:tag="
                setText($event.action);
                font(Roboto-Regular.ttf);"
            tools:text="Starred" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="10dp"
            android:tag="
                setText($event.repoName);
                "
            android:textColor="#5bbce4"
            tools:text="UserName" />
    </LinearLayout>

</LinearLayout>
```
