package coloring.algorithms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class contains concrete implementations of Breadth First Traversal(BFS) and
 * Depth First Traversals(DFS).
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SubspaceExploreUtil {

	/**
	 * Implementation of Breadth First Traversal algorithm without remembering
	 * visited states.
	 * 
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		LinkedList<S> seeInto = new LinkedList<>(Arrays.asList(s0.get()));

		while (seeInto.size() > 0) {
			S temp = seeInto.getFirst();
			seeInto.removeFirst();

			if (!acceptable.test(temp)) {
				continue;
			}

			process.accept(temp);
			seeInto.addAll(succ.apply(temp));
		}
	}

	/**
	 * Implementation of Depth First Traversal algorithm without remembering visited
	 * states.
	 * 
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		LinkedList<S> seeInto = new LinkedList<>(Arrays.asList(s0.get()));

		while (seeInto.size() > 0) {
			S temp = seeInto.getFirst();
			seeInto.removeFirst();

			if (!acceptable.test(temp)) {
				continue;
			}

			process.accept(temp);
			seeInto.addAll(0, succ.apply(temp));
		}
	}

	/**
	 * Implementation of Breadth First Traversal algorithm with remembering visited
	 * states.
	 * 
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		LinkedList<S> seeInto = new LinkedList<>(Arrays.asList(s0.get()));
		Set<S> visited = new HashSet<>(seeInto);

		while (seeInto.size() > 0) {
			S temp = seeInto.getFirst();
			seeInto.removeFirst();

			if (!acceptable.test(temp)) {
				continue;
			}

			process.accept(temp);
			List<S> neighbours = succ.apply(temp);
			neighbours.removeAll(visited);

			seeInto.addAll(neighbours);
			visited.addAll(neighbours);
		}
	}

	/**
	 * Implementation of Depth First Traversal algorithm with remembering visited
	 * states.
	 * 
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void dfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		LinkedList<S> seeInto = new LinkedList<>(Arrays.asList(s0.get()));
		Set<S> visited = new HashSet<>(seeInto);

		while (seeInto.size() > 0) {
			S temp = seeInto.getFirst();
			seeInto.removeFirst();

			if (!acceptable.test(temp)) {
				continue;
			}

			process.accept(temp);
			List<S> neighbours = succ.apply(temp);
			neighbours.removeAll(visited);

			seeInto.addAll(0, neighbours);
			visited.addAll(neighbours);
		}
	}
}
