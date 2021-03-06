/*
 * Copyright (c) 2017-2019 AxonIQ B.V. and/or licensed to AxonIQ B.V.
 * under one or more contributor license agreements.
 *
 *  Licensed under the AxonIQ Open Source License Agreement v1.0;
 *  you may not use this file except in compliance with the license.
 *
 */

package io.axoniq.axonserver.component.processor.listener;

import io.axoniq.axonserver.applicationevents.EventProcessorEvents.EventProcessorStatusUpdate;
import io.axoniq.axonserver.applicationevents.EventProcessorEvents.EventProcessorStatusUpdated;
import io.axoniq.axonserver.applicationevents.TopologyEvents;
import io.axoniq.axonserver.component.processor.ClientEventProcessorInfo;
import io.axoniq.axonserver.grpc.control.EventProcessorInfo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Sara Pellegrini on 21/03/2018.
 * sara.pellegrini@gmail.com
 */
@Component
public class ProcessorsInfoTarget implements ClientProcessors {

    private final Map<String, String> clients = new HashMap<>();

    // Map<Client, Map<ProcessorName, ClientProcessor>>
    private final Map<String, Map<String, ClientProcessor>> cache = new HashMap<>();

    private final ClientProcessorMapping mapping;

    public ProcessorsInfoTarget() {
        this.mapping = new ClientProcessorMapping() {};
    }

    @EventListener
    public EventProcessorStatusUpdated onEventProcessorStatusChange(EventProcessorStatusUpdate event) {
        ClientEventProcessorInfo processorStatus = event.eventProcessorStatus();
        String clientName = processorStatus.getClientName();
        Map<String, ClientProcessor> clientData = cache.computeIfAbsent(clientName, c -> new HashMap<>());
        EventProcessorInfo eventProcessorInfo = processorStatus.getEventProcessorInfo();
        ClientProcessor clientProcessor = mapping.map(clientName, clients.get(clientName), processorStatus.getContext(), eventProcessorInfo);
        clientData.put(eventProcessorInfo.getProcessorName(), clientProcessor);
        return new EventProcessorStatusUpdated(processorStatus, false);
    }

    @EventListener
    public void onClientConnected( TopologyEvents.ApplicationConnected event) {
        clients.put(event.getClient(), event.getComponentName());
    }

    @EventListener
    public void onClientDisconnected(TopologyEvents.ApplicationDisconnected event) {
        clients.remove(event.getClient());
        cache.remove(event.getClient());
    }

    @Override
    public Iterator<ClientProcessor> iterator() {
        return cache.entrySet().stream()
                    .flatMap(client -> client.getValue().entrySet().stream().map(Map.Entry::getValue))
                    .iterator();
    }

}
