package com.cralos.rxjavaandroid.example1.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cralos.rxjavaandroid.R;
import com.cralos.rxjavaandroid.example1.models.Task;
import com.cralos.rxjavaandroid.example1.utils.DataSource;
import com.jakewharton.rxbinding3.view.RxView;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private TextView txvResult;
    private SeekBar seekBar;

    /**
     * Composite disposable
     */
    private CompositeDisposable disposable;

    private long timeSinceLastRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
        ejercicio1();
        ejercicio2();
        ejercicio3();
        ejercicio4();
        ejercicio5();
        ejercicio6();
        ejercicio7();
        ejercicio8();
        ejercicio9();
        ejercicio10();
        ejercicio11();
        ejercicio12();
        ejercicio13();
        ejercicio14();
        ejercicio15();
        ejercicio16();
        ejercicio17();
        ejercicio18();
        ejercicio19();
        ejercicio20();
        ejercicio21();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    private void initActivity() {
        txvResult = findViewById(R.id.txvResult);
        seekBar = findViewById(R.id.seekbar);
        disposable = new CompositeDisposable();
    }

    private void ejercicio1() {

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .filter(task -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return task.isComplete();
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("RX_ANDROID", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("RX_ANDROID", "TASK: " + task.getDescription());

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("RX_ANDROID", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("RX_ANDROID", "onComplete");
            }
        });

    }

    private void ejercicio2() {
        Flowable.range(0, 100)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.e("FLOWABLE", "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("FLOWABLE", "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("FLOWABLE", "onError: " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("FLOWABLE", "onComplete");
                    }
                });
    }

    private void ejercicio3() {
        /**Observable*/
        Observable<Task> observable = Observable                    //create a new observable object
                .fromIterable(DataSource.createTasksList())         //apply "fromIterable" operator
                .subscribeOn(Schedulers.io())                       // designate worker thread
                .observeOn(AndroidSchedulers.mainThread());

        /**Observer*/
        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO3", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO3", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO3", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO3", "onComplete");
            }
        });
    }

    private void ejercicio4() {
        Observable<Task> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .filter(task -> task.isComplete())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
                Log.e("ejercicio4", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("ejercicio4", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("ejercicio4", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("ejercicio4", "onComplete");
            }
        });
    }

    private void ejercicio5() {
        final Task task = new Task("Walk the dog", false, 3);
        Observable<Task> observable = Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(task);
                            emitter.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO5", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO5", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO5", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO5", "onComplete");
            }
        });

    }

    private void ejercicio6() {
        /**solo acepta hasta 10 items*/
        Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("EJERCICIO6", "onSubscribe");
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.e("EJERCICIO6", "onNext: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("EJERCICIO6", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("EJERCICIO6", "onComplete");
                    }
                });
    }

    private void ejercicio7() {
        Observable<Integer> observable = Observable
                .range(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO7", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("EJERCICIO7", "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO7", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO7", "onComplete");
            }
        });
    }

    private void ejercicio8() {
        Observable<Task> observable = Observable
                .range(0, 11)
                .subscribeOn(Schedulers.io())
                .map(number -> new Task("Descripción: " + number, false, number))
                .takeWhile(task -> task.getPriority() < 4)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO8", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO8", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO8", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO8", "onComplete");
            }
        });


    }

    private void ejercicio9() {
        Observable<Integer> observable = Observable
                .range(0, 10)
                .subscribeOn(Schedulers.io())
                .repeat(2)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO9", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e("EJERCICIO9", "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO9", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO9", "onComplete");
            }
        });
    }

    private void ejercicio10() {
        Observable<Long> observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(myLong -> myLong < 10)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO10", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Long myLong) {
                Log.e("EJERCICIO10", "onNext: " + myLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO10", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO10", "onComplete");
            }
        });
    }

    private void ejercicio11() {
        Observable<Long> observable = Observable
                .timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Long>() {
            long time = 0; // variable for demonstating how much time has passed

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO11", "onSubscribe");
                time = System.currentTimeMillis() / 1000;
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e("EJERCICIO11", "onNext: " + aLong);
                Log.d("EJERCICIO11", "onNext: " + ((System.currentTimeMillis() / 1000) - time) + " seconds have elapsed.");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO11", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO11", "onComplete");
            }
        });
    }

    private void ejercicio12() {
        Observable<Task> observable = Observable
                .fromArray(DataSource.createTasksArray())
                .subscribeOn(Schedulers.io())
                .filter(task -> task.isComplete())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO12", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO12", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO12", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO12", "onComplete");
            }
        });
    }

    private void ejercicio13() {
        Observable<String> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .map(task -> "Description: " + task.getDescription())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO13", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("EJERCICIO13", "onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO13", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO13", "onComplete");
            }
        });
    }

    private void ejercicio14() {
        Observable<List<Task>> callable = Observable
                .fromCallable(() -> DataSource.getTasksFromRealm())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        callable.subscribe(new Observer<List<Task>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO14", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull List<Task> tasks) {
                Log.e("EJERCICIO14", "onNext, tamaño de la lista: " + tasks.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO14", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO14", "onComplete");

            }
        });

    }

    private void ejercicio15() {
        Observable<Task> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .filter(task -> task.getDescription().equals("Unload the dishwasher"))
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO15", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO15", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO15", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO15", "onClocmple");
            }
        });
    }

    private void ejercicio16() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Take out the trash", true, 3));
        tasks.add(new Task("Walk the dog", false, 2));
        tasks.add(new Task("Make my bed", true, 1));
        tasks.add(new Task("Unload the dishwasher", false, 0));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));
        tasks.add(new Task("Make dinner", true, 5));

        Observable<Task> observable = Observable
                .fromIterable(tasks)
                .subscribeOn(Schedulers.io())
                .distinct(task -> task.getDescription())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO16", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO16", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO16", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO16", "onComplete");
            }
        });
    }

    private void ejercicio17() {
        Observable<Task> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .take(3)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO17", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO17", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO17", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO17", "onComplete");
            }
        });
    }

    private void ejercicio18() {
        Observable<Task> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .takeWhile(task -> !task.getDescription().equals("Make my bed"))
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO18", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.e("EJERCICIO18", "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO18", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO18", "onComplete");
            }
        });
    }

    private void ejercicio19() {
        Observable<String> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .map(task -> "description: " + task.getDescription() + ", priority: " + task.getPriority())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("EJERCICIO19", "onSubscribe");
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e("EJERCICIO19", "onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("EJERCICIO19", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("EJERCICIO19", "onComplete");
            }
        });
    }

    private void ejercicio20() {
        Observable<Task> observable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.buffer(2)
                .subscribe(new Observer<List<Task>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("EJERCICIO20", "onSubscribe");
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Task> tasks) {
                        Log.e("EJERCICIO20", "onNext, tamaño buffer: " + tasks.size());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("EJERCICIO20", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("EJERCICIO20", "onComplete");
                    }
                });

    }

    private void ejercicio21() {
        timeSinceLastRequest = System.currentTimeMillis();
        // Set a click listener to the button with RxBinding Library
        RxView.clicks(txvResult)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)   // Throttle the clicks so 500 ms must pass before registering a new click
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<Unit>() {
                    @Override
                    public void onSubscribe(io.reactivex.disposables.Disposable d) {
                        Log.e("EJERCICIO22", "onSubscribe");
                    }

                    @Override
                    public void onNext(Unit unit) {
                        Log.e("EJERCICIO22", "onNext");
                        Log.d("EJERCICIO22", "onNext: time since last clicked: " + (System.currentTimeMillis() - timeSinceLastRequest));
                        //someMethodToDoRequestApi(); // Execute some method when a click is registered
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("EJERCICIO22", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("EJERCICIO22", "onComplete");
                    }
                });

    }

}
