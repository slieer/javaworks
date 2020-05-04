package com.webflux.demo;

import com.webflux.demo.model.Employee;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class ReactorTest {
    @Test
    public void createFlux() throws InterruptedException {
        //整型
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);
        //字符串
        Flux<String> stringFlux = Flux.just("hello", "world");
        List<String> list = Arrays.asList("hello", "world");
        //列表
        Flux<String> stringFlux1 = Flux.fromIterable(list);
        //范围
        Flux<Integer> integerFlux1 = Flux.range(1, 5);
        //时间间隔
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(1000));
        longFlux.subscribe(System.out::println);

        //Flux 创建
        Flux<String> stringFlux2 = Flux.from(stringFlux1);
        stringFlux2.subscribe(System.out::println);
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void createMono(){
        //字符串
        Mono<String> stringMono = Mono.just("Hello World");
        //Callable创建
        Mono<String> stringMono1 = Mono.fromCallable(()->
        {
            return "Hello World";
        });
        //Future创建
        Mono<String> stringMono2 = Mono.fromFuture(CompletableFuture.completedFuture("Hello World"));
        Random random = new Random();
        //Suppier创建
        Mono<Double> doubleMono = Mono.fromSupplier(random::nextDouble);
        //Mono创建
        Mono<Double> doubleMono1 = Mono.from(doubleMono);
        //Flux创建
        Mono<Integer> integerMono = Mono.from(Flux.range(1,5));
        integerMono.subscribe(System.out::println);
        stringMono2.subscribe(System.out::println);
    }

    @Test
    public void subscribe(){
        Flux<String> stringFlux = Flux.just("Hello","World");
        //stringFlux.subscribe(System.out::println);
        //订阅方式一
        stringFlux.subscribe(val ->{
            log.info("val:{}",val);
        },error ->{
            log.error("error:{}",error);
        },() ->{
            log.info("Finished");
        },subscription -> {
            subscription.request(1);
        });
        //订阅方式二
        stringFlux.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }
            @Override
            public void onNext(String s) {
                log.info("onNext:{}",s);
            }
            @Override
            public void onError(Throwable throwable) {
            }
            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });
    }

    @Test
    public void map() {
        Flux<Employee> employeeFlux = Flux.fromIterable(list);
        employeeFlux.filter(employee -> employee.getSalary() == 2000)
                .map(employee -> {
                    Leader leader = new Leader();
                    leader.setName(employee.getName());
                    leader.setSalary(employee.getSalary());
                    return leader;
                }).log().subscribe();
        employeeFlux.map((in) -> {
            Leader leader = new Leader();
            leader.setName(in.getName());
            leader.setSalary(in.getSalary());
            return leader;
        });
    }

    @Test
    public void flatMap(){
        Flux<String> stringFlux1 = Flux.just("a","b","c","d","e","f","g","h","i");
        //嵌套Flux
        Flux<Flux<String>> stringFlux2 = stringFlux1.window(2);
        stringFlux2.flatMap(flux1 ->flux1.map(word ->word.toUpperCase()))
                .subscribe(System.out::println);
        //从嵌套Flux还原字符串Flux
        Flux<String> stringFlux3 = stringFlux2.flatMap(flux1 ->flux1);
        // stringFlux1 等于 stringFlux3
        stringFlux3.subscribe(System.out::println);
    }

    @Test
    public void concatMap() throws InterruptedException {
        Flux<String> stringFlux1 = Flux.just("a","b","c","d","e","f","g","h","i");
        Flux<Flux<String>> stringFlux2 = stringFlux1.window(2);
        stringFlux2.concatMap(flux1 ->flux1.map(word ->word.toUpperCase())
                .delayElements(Duration.ofMillis(200)))
                .subscribe(x -> System.out.print("->"+x));
        Thread.sleep(2000);
    }

    @Data
    @ToString
    class Leader {
        private String name;
        private double salary;
    }

    public static final List<Employee> list = Arrays.asList(
            new Employee(1, "Alex", 1000),
            new Employee(2, "Michael", 2000),
            new Employee(3, "Jack", 1500),
            new Employee(4, "Owen", 1500),
            new Employee(5, "Denny", 2000));
}
