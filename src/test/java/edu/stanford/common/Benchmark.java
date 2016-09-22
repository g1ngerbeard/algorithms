package edu.stanford.common;

import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.LF;

public class Benchmark {

    public static <T> Result<T> runBenchmark(Supplier<T> supplier) {
        final long start = System.currentTimeMillis();

        T result = supplier.get();

        long duration = System.currentTimeMillis() - start;

        return new Result<>(duration, result);
    }

    public static long runBenchmark(Runnable task) {
        final long start = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - start;
    }

    public static class Result<T> {
        private final long duration;

        private final T result;

        public Result(long duration, T result) {
            this.duration = duration;
            this.result = result;
        }

        public long getDuration() {
            return duration;
        }

        public T getResult() {
            return result;
        }

        @Override
        public String toString() {
            return "Result{" + LF +
                    "duration=" + duration + "," + LF +
                    "result=" + result + LF +
                    '}';
        }
    }
}
