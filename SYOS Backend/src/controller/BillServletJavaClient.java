package controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BillServletJavaClient {
    private static final String SERVER_URL = "http://localhost:8080/SYOS%20Backend/bill";
    private static final int NUM_CLIENTS = 5;
    private static final int REQUESTS_PER_CLIENT = 20;
    private static final int QUEUE_CAPACITY = 100; // Limit queue size to prevent memory issues
    private static final int MAX_CONCURRENT_REQUESTS = 10; // Limit simultaneous requests

    private static final AtomicInteger successfulRequests = new AtomicInteger(0);
    private static final AtomicInteger failedRequests = new AtomicInteger(0);
    private static final BlockingQueue<Runnable> requestQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool with a bounded queue
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_CONCURRENT_REQUESTS, // core pool size
                MAX_CONCURRENT_REQUESTS, // max pool size
                60L, TimeUnit.SECONDS, // keep-alive time
                requestQueue);

        HttpClient client = HttpClient.newHttpClient();
        Random random = new Random();
        CountDownLatch latch = new CountDownLatch(NUM_CLIENTS * REQUESTS_PER_CLIENT);

        System.out.println("Starting stress test with " + NUM_CLIENTS + " clients...");
        System.out.println("Queue capacity: " + QUEUE_CAPACITY);
        System.out.println("Max concurrent requests: " + MAX_CONCURRENT_REQUESTS);

        long startTime = System.currentTimeMillis();

        // Submit all requests to the queue
        for (int i = 0; i < NUM_CLIENTS; i++) {
            final int clientId = i;
            for (int j = 0; j < REQUESTS_PER_CLIENT; j++) {
                try {
                    executor.submit(() -> {
                        try {
                            String customerId = String.valueOf(random.nextInt(10));
                            String json = String.format(
                                    "{\"transactionType\":\"%s\",\"totalAmount\":%.2f,\"discount\":%.2f," +
                                            "\"cashTendered\":%.2f,\"cashChange\":%.2f,\"customerID\":\"%s\"," +
                                            "\"amountAfterDiscount\":%.2f,\"orders\":[{\"orderID\":\"%s\"," +
                                            "\"itemCode\":\"ITM%04d\",\"name\":\"Test Item\",\"quantity\":%d," +
                                            "\"amount\":%.2f,\"price\":%.2f}]}",
                                    random.nextBoolean() ? "ONLINE" : "ONSITE",
                                    50 + random.nextDouble() * 1000,
                                    random.nextDouble() * 50,
                                    100 + random.nextDouble() * 1000,
                                    random.nextDouble() * 100,
                                    customerId,
                                    50 + random.nextDouble() * 1000,
                                    UUID.randomUUID().toString(),
                                    random.nextInt(1000),
                                    1 + random.nextInt(10),
                                    10 + random.nextDouble() * 90,
                                    10 + random.nextDouble() * 90
                            );

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(SERVER_URL))
                                    .header("Content-Type", "application/json")
                                    .POST(HttpRequest.BodyPublishers.ofString(json))
                                    .build();

                            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                            if (response.statusCode() == 200) {
                                successfulRequests.incrementAndGet();
                                System.out.printf("Client %d - Request succeeded: %s%n",
                                        clientId, response.body());
                            } else {
                                failedRequests.incrementAndGet();
                                System.err.printf("Client %d - Request failed: %d %s%n",
                                        clientId, response.statusCode(), response.body());
                            }
                        } catch (Exception e) {
                            failedRequests.incrementAndGet();
                            System.err.printf("Client %d - Request failed: %s%n",
                                    clientId, e.getMessage());
                        } finally {
                            latch.countDown();

                            // Add small delay between requests
                            try {
                                Thread.sleep(random.nextInt(50));
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    });
                } catch (RejectedExecutionException e) {
                    System.err.println("Queue full - waiting for space...");
                    j--; // Retry this request
                    Thread.sleep(100);
                }
            }
        }

        // Wait for all requests to complete
        latch.await();
        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("\nTest completed in " + duration + " ms");
        System.out.println("Successful requests: " + successfulRequests.get());
        System.out.println("Failed requests: " + failedRequests.get());
        System.out.printf("Requests per second: %.2f%n",
                (NUM_CLIENTS * REQUESTS_PER_CLIENT) / (duration / 1000.0));
        System.out.println("Final queue size: " + requestQueue.size());
    }
}