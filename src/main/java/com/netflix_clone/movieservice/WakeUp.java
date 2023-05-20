package com.netflix_clone.movieservice;

import com.netflix_clone.movieservice.repository.categoryRepository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created on 2023-05-04
 * Project user-service
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WakeUp {
    private final CategoryRepository repository;

    @EventListener(value = {ApplicationReadyEvent.class})
    public void message(){
        log.warn("{} is ready", repository.wakeUpMsg("\n" +
                " \t\t\t  __  __            _           ____                  _          \n" +
                " \t\t\t |  \\/  | _____   _(_) ___     / ___|  ___ _ ____   _(_) ___ ___ \n" +
                " \t\t\t | |\\/| |/ _ \\ \\ / / |/ _ \\____\\___ \\ / _ \\ '__\\ \\ / / |/ __/ _ \\\n" +
                " \t\t\t | |  | | (_) \\ V /| |  __/_____|__) |  __/ |   \\ V /| | (_|  __/\n" +
                " \t\t\t |_|  |_|\\___/ \\_/ |_|\\___|    |____/ \\___|_|    \\_/ |_|\\___\\___|"));
    }
}
