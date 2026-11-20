package com.trek.core.util;


import com.trek.core.dto.command.Command;
import com.trek.core.dto.request.Request;

import java.util.Optional;
import java.util.function.Supplier;

/***
 * @author udaya
 * @param <R>
 * @param <C>
 */
public interface RequestValidator<R extends Request, C extends Command> {

    C validate(R request);

    public class Validation {

        private final boolean isSuccess;

        Validation(boolean predicate) {
            isSuccess = predicate;
        }

        public static Validation of(boolean predicate) {
            return new Validation(predicate);
        }

        public Validation orElseThrow(Supplier<? extends RuntimeException> supplier) {
            if (!isSuccess) {
                throw supplier.get();
            }
            return this;
        }

        public Validation onFail(Runnable runnable) {
            if (!isSuccess) {
                runnable.run();
            }
            return this;
        }

        public <T> Optional<T> onSuccess(Supplier<T> supplier) {
            if (isSuccess) {
                return Optional.ofNullable(supplier.get());
            }
            return Optional.empty();
        }


    }

}

