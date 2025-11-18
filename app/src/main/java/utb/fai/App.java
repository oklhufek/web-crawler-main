package utb.fai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.text.html.parser.ParserDelegator;

public class App {

    private static ConcurrentHashMap<URI, Boolean> visitedURIs = new ConcurrentHashMap<>();
    private static ConcurrentLinkedQueue<URIinfo> foundURIs = new ConcurrentLinkedQueue<>();
    private static ConcurrentHashMap<String, Integer> wordFrequency = new ConcurrentHashMap<>();
    private static ExecutorService executor;
    private static int activeThreads = 0;
    private static Object monitor = new Object();

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Missing parameter - start URL");
            return;
        }

        try {
            String startURL = args[0];
            if (!startURL.endsWith("/")) {
                startURL += "/";
            }
            URI uri = new URI(startURL);

            int maxDepth = 2;
            if (args.length >= 2) {
                maxDepth = Integer.parseInt(args[1]);
            }

            int debugLevel = 0;
            if (args.length >= 3) {
                debugLevel = Integer.parseInt(args[2]);
            }

            foundURIs.add(new URIinfo(uri, 0));
            visitedURIs.put(uri, true);

            int numThreads = Runtime.getRuntime().availableProcessors();
            executor = Executors.newFixedThreadPool(numThreads);

            final int finalMaxDepth = maxDepth;
            final int finalDebugLevel = debugLevel;

            while (!foundURIs.isEmpty() || activeThreads > 0) {
                URIinfo uriInfo = foundURIs.poll();
                
                if (uriInfo != null) {
                    synchronized (monitor) {
                        activeThreads++;
                    }
                    
                    executor.submit(() -> {
                        try {
                            processPage(uriInfo, finalMaxDepth, finalDebugLevel);
                        } finally {
                            synchronized (monitor) {
                                activeThreads--;
                                monitor.notifyAll();
                            }
                        }
                    });
                } else {
                    synchronized (monitor) {
                        if (activeThreads == 0) {
                            break;
                        }
                        try {
                            monitor.wait(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            printResults();

        } catch (Exception e) {
            System.err.println("Zachycena neošetřená výjimka, končíme...");
            e.printStackTrace();
        }
    }

    private static void processPage(URIinfo uriInfo, int maxDepth, int debugLevel) {
        URI uri = uriInfo.uri;
        int depth = uriInfo.depth;

        if (debugLevel > 0) {
            System.err.println("Analyzing " + uri + " (depth: " + depth + ")");
        }

        try {
            ParserCallback callBack = new ParserCallback(visitedURIs, foundURIs, wordFrequency);
            callBack.depth = depth;
            callBack.maxDepth = maxDepth;
            callBack.pageURI = uri;
            callBack.debugLevel = debugLevel;

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(uri.toURL().openStream(), "UTF-8")
            );
            
            ParserDelegator parser = new ParserDelegator();
            parser.parse(reader, callBack, true);
            reader.close();

        } catch (Exception e) {
            if (debugLevel > 0) {
                System.err.println("Error loading page: " + uri + " - " + e.getMessage());
            }
        }
    }

    private static void printResults() {
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordFrequency.entrySet());
        sortedWords.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedWords) {
            if (count >= 20) break;
            System.out.println(entry.getKey() + ";" + entry.getValue());
            count++;
        }
    }

    public static void addWord(String word) {
        wordFrequency.merge(word.toLowerCase(), 1, Integer::sum);
    }
}