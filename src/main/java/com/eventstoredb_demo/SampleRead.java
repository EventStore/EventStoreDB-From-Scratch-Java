package com.eventstoredb_demo;

import java.nio.charset.StandardCharsets;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.eventstore.dbclient.ReadResult;
import com.eventstore.dbclient.ReadStreamOptions;
import com.eventstore.dbclient.RecordedEvent;
import com.eventstore.dbclient.ResolvedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleRead {
        public static void main(String[] args) throws Exception {


               //////////////////////////////////////////////
               // Create a connection 
               // The assumption is that an unsecured instance of
               // eventstoredb is running locally
               // If running in codespaces run the start_server script
               // If running on your own machine run the docker container
               // details at web page below
               // https://developers.eventstore.com/getting-started.html#installation
               /////////////////////////////////////////////
               
                // configure the settings
                EventStoreDBClientSettings settings = EventStoreDBConnectionString.
                parseOrThrow("esdb://localhost:2113?tls=false");
               
                // apply the settings and create an instance of the client
                EventStoreDBClient client = EventStoreDBClient.create(settings); 


                ReadStreamOptions options = ReadStreamOptions.get()
                        .forwards()
                        .fromStart()
                        .maxCount(10);

                //get events from stream
                String eventStream = "SampleStream";
                ReadResult result = client.readStream(eventStream, options).get();


                for (ResolvedEvent resolvedEvent : result.getEvents()) {                                 // For each event in stream
                        RecordedEvent recordedEvent = resolvedEvent.getOriginalEvent();                  // Get the original event (can ignore for now)
                                                                                                         //
                        System.out.println("************************");                                  //
                        System.out.println("You have read an event!");                                   //
                        System.out.println("Stream: " + recordedEvent.getStreamId());                    // Print the stream name of the event
                        System.out.println("Event Type: " + recordedEvent.getEventType());               // Print the type of the event 
                        System.out.println("Event Body: " + new String(recordedEvent.getEventData(),     // Print the body of the event after converting it from a byte array
                                                                       StandardCharsets.UTF_8));         // UTF8 is used to convert byte array to string
                        System.out.println("************************");
                }
                
                

        }
    
   
    }
