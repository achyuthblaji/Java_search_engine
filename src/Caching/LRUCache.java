package Caching;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class implements a Least Recently Used (LRU) cache.
 *  The cache is implemented as a hash map.
 *  The cache is limited to a maximum size.
 *  The cache is ordered by the time of the last access.
 *  The least recently used element is the first element in the list.
 *  The most recently used element is the last element in the list.
 *
 * @author piyushmehta
 */
class Cache {
    int key;
    String value;
    Cache(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * This class implements a Least Recently Used (LRU) cache.
 * The cache is implemented as a hash map.
 *
 * @author piyushmehta
 */
public class LRUCache {

    static Map < Integer, Cache > map = new HashMap < > ();
    static Deque < Integer > deque = new LinkedList < > ();
    int CACHE_CAPACITY = 4;
    static List < String > cachedFileNames = new ArrayList < String > ();

    /**
     * This method adds an element to the cache.
     * If the cache is full, the least recently used element is removed.
     * @author piyushmehta
     * @param key
     * @param value
     */
    public void putElementInCache(int key, String value) {
        if (map.containsKey(key)) {
            Cache curr = map.get(key);
            deque.remove(curr.key);
        } else {
            if (deque.size() == CACHE_CAPACITY) {
                int tempInt = deque.removeLast();
                map.remove(tempInt);
            }
        }
        Cache newItem = new Cache(key, value);
        deque.addFirst(newItem.key);
        map.put(key, newItem);
    }

    /**
     *  This method prints the contents of the cache.
     *  The cache is printed in the order of the time of the last access.
     *
     * @author piyushmehta
     */
    public void getFileNameFromCache() {
        for (Integer item: deque) {
            Cache current = map.get(item);
            cachedFileNames.add(current.value);
        }
        System.out.println("cached pages are: " + cachedFileNames);


    }

    /**
     * This method prints the contents of the cache.
     * @param Source
     * @param Destination
     * @throws IOException
     * @author piyushmehta
     */
    public void copyFiles(String Source, String Destination) {

        File source = new File(Source);
        File destination = new File(Destination);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method add the contents of the cache.
     *
     * @param urlDict
     * @author piyushmehta
     */
    public void storeToFolder(HashMap < String, String > urlDict) {
        String fileSource = "./htmlToTextPages/";
        String fileDestination = "./cached_files/";
        for (Entry < String, String > entry: urlDict.entrySet()) {

            for (String element: cachedFileNames) {

                if (entry.getValue() == element) {
                    String sourceFileName = fileSource + entry.getKey();
                    String destinationFileName = fileDestination + entry.getKey();
                    copyFiles(sourceFileName, destinationFileName);
                    break;

                }
            }
        }

    }
}
