package tf_idf;

import java.util.HashMap;
import java.util.*;

import java.util.List;
import java.util.Map.Entry;

import tf_idf.Count_heap.Node;

public class tf_idf_new {
	static int normalizing_factor = 100;

	/**
	 * This method populates heap key is file_name and value is tf_idf_score
	 *
	 * @param tf_idf_score
	 * @author AchyuthBalaji
	 */

	public static List<String> populate_heap(HashMap<String, Double> tf_idf_score) {
		List<String> ranking_list = new ArrayList<String>();
		System.out.println("Size of the map is : " + tf_idf_score.size());
		Node pq = Count_heap.newNode("sample", 0.0);
		for (Entry<String, Double> entry : tf_idf_score.entrySet()) {
			if (entry.getValue() > 0) {
				pq = Count_heap.push(pq, entry.getKey(), entry.getValue());

			}
		}
		while (Count_heap.isEmpty(pq) == 0) {

			ranking_list.add(Count_heap.peek(pq));
			pq = Count_heap.pop(pq);

		}

		return ranking_list;

	}

	/**
	 * This method computes Term Frequency score
	 *
	 * @param key_word_count
	 * @param total_number_of_words_count
	 * @author AchyuthBalaji
	 */

	public static double tf_score(int key_word_count, int total_number_of_words_count) {

		key_word_count = key_word_count * normalizing_factor;

		double tf_score = ((double) key_word_count / (double) total_number_of_words_count);

		return tf_score;
	}

	/**
	 * This method computes IDF score
	 *
	 * @param documents_containing_key_word
	 * @param number_of_files
	 * @author AchyuthBalaji
	 */

	public static double idf_score(Integer documents_containing_key_word, Integer number_of_files) {
		double idf_score = 0.0;
		if (documents_containing_key_word != 0) {
//		idf_score=Math.log(number_of_files / documents_containing_key_word);
			idf_score = 1 + Math.log(number_of_files + 1 / documents_containing_key_word + 1);
		}
		return idf_score;

	}

	/**
	 * This method computes tf_idf score and is stored in Hash Map
	 *
	 * @param key_word_count
	 * @param complete_word_count
	 * @param documents_containing_key_word
	 * @author AchyuthBalaji
	 */
	public static HashMap<String, Double> compute_tf_idf(HashMap<String, Integer> key_word_count,
			HashMap<String, Integer> complete_word_count, Integer documents_containing_key_word) {

		double _idf_score = 0.0;
		HashMap<String, Double> tf_idf_score = new HashMap<String, Double>();

		_idf_score = idf_score(documents_containing_key_word, complete_word_count.size());
		for (String file_name : key_word_count.keySet()) {
			double _tf_score = tf_score(key_word_count.get(file_name), complete_word_count.get(file_name));
			if (_tf_score * _idf_score != 0) {

			}
			tf_idf_score.put(file_name, _tf_score * _idf_score);
			System.out.println("computed scores");
			System.out.println(file_name);
			System.out.println(_tf_score * _idf_score);
		}

		return tf_idf_score;
	}

}
