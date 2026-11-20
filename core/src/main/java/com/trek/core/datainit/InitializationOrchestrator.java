package com.trek.core.datainit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author udaya
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitializationOrchestrator implements CommandLineRunner {

    private final List<BaseInitializer> initializers;

    @Override
    public void run(String... args) {
        initializers.forEach(BaseInitializer::initialize);
    }
}