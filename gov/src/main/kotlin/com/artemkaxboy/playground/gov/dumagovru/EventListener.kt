package com.artemkaxboy.playground.gov.dumagovru

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventListener(private val dbInitializer: DbInitializer) {

    @EventListener(classes = [ApplicationStartedEvent::class])
    fun onApplicationStartedEvent(event: ApplicationStartedEvent) {
        println("Application started")
        dbInitializer.init()
    }
}