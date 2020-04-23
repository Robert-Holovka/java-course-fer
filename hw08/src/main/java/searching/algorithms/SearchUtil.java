package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SearchUtil {

	/**
	 * Implementation of Breadth First Traversal algorithm without remembering
	 * visited states.
	 * 
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		Node<S> node;
		LinkedList<Node<S>> seeInto = new LinkedList<>();
		seeInto.add(new Node<>(null, s0.get(), 0));

		while (seeInto.size() > 0) {
			node = seeInto.getFirst();
			seeInto.removeFirst();
			if (goal.test(node.getState())) {
				return node;
			}

			final Node<S> parent = node;
			succ.apply(node.getState()).forEach((t) -> {
				seeInto.add(new Node<>(parent, t.getState(), t.getCost() + parent.getCost()));
			});
		}

		// Solution not found
		return null;
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
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		Node<S> node;
		LinkedList<Node<S>> seeInto = new LinkedList<>();
		HashSet<S> visited = new HashSet<>();

		seeInto.add(new Node<>(null, s0.get(), 0));
		visited.add(s0.get());

		while (seeInto.size() > 0) {
			node = seeInto.getFirst();
			seeInto.removeFirst();
			if (goal.test(node.getState())) {
				return node;
			}

			final Node<S> parent = node;
			succ.apply(node.getState()).forEach((t) -> {
				S state = t.getState();
				if (!visited.contains(state)) {
					seeInto.add(new Node<>(parent, state, t.getCost() + parent.getCost()));
					visited.add(state);
				}
			});
		}

		// Solution not found
		return null;
	}
}
